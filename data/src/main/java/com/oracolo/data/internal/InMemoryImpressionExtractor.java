package com.oracolo.data.internal;

import com.oracolo.data.*;
import io.quarkus.runtime.Startup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.context.ManagedExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@ApplicationScoped
@Startup
final class InMemoryImpressionExtractor implements AsyncImpressionExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryImpressionExtractor.class.getName());

    private static  Set<HourOfDayImpression> IMMUTABLE_HOUR_OF_DAY_IMPRESSIONS;
    private static Set<DayOfMonthImpression> IMMUTABLE_DAY_OF_MONTH_IMPRESSIONS;
    private static Set<DayOfWeekImpression> IMMUTABLE_WEEKDAY_IMPRESSIONS;
    private static Set<DeviceImpression> IMMUTABLE_DEVICE_IMPRESSIONS;


    @ConfigProperty(name = "impressions.file-path")
    String filePath;

    @ConfigProperty(name = "impressions.csv-delimiter", defaultValue = ",")
    String csvDelimiter;

    @Inject
    ManagedExecutor executor;

    private CompletableFuture<Void> fileParseTask;

    @PostConstruct
    void grindData() {
        fileParseTask = executor.runAsync(()->doGrind(filePath,csvDelimiter));
    }

    @Override
    public CompletionStage<Set<DeviceImpression>> deviceImpressions() {
        return fileParseTask.thenApply((unused)-> IMMUTABLE_DEVICE_IMPRESSIONS);
    }

    @Override
    public CompletionStage<Set<DayOfMonthImpression>> dayOfMonthImpressions() {
        return fileParseTask.thenApply(unused -> IMMUTABLE_DAY_OF_MONTH_IMPRESSIONS);
    }

    @Override
    public CompletionStage<Set<DayOfWeekImpression>> dayOfWeekImpressions() {
        return fileParseTask.thenApply((unused -> IMMUTABLE_WEEKDAY_IMPRESSIONS));
    }

    @Override
    public CompletionStage<Set<HourOfDayImpression>> hourOfDayImpressions() {
        return fileParseTask.thenApply(unused -> IMMUTABLE_HOUR_OF_DAY_IMPRESSIONS);
    }

    @Override
    public boolean resourceReady() {
        return fileParseTask.isDone();
    }

    private static void doGrind(String filePath, String csvDelimiter) {
        Path path = Path.of(filePath);
        try (Reader reader = Files.newBufferedReader(path)) {
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.builder().setDelimiter(csvDelimiter).setHeader().build());
            Stream<CSVRecord> csvRecordStream = StreamSupport.stream(parser.spliterator(), true);
            Map<Integer, Long> deviceImpressionCount = new ConcurrentHashMap<>();
            Map<DayOfWeek, Long> dayOfWeekCount = new ConcurrentHashMap<>();
            Map<LocalDate,Long> dayOfMonthCount = new ConcurrentHashMap<>();
            Map<LocalTime,Long> hourOfDayCount = new ConcurrentHashMap<>();
            csvRecordStream.forEach(csvRecord -> {
                Map<String, String> headerValue = csvRecord.toMap();
                int deviceId = Integer.parseInt(headerValue.get("device_id"));
                long timestamp = Long.parseLong(headerValue.get("timestamp"));
                Instant instant = Instant.ofEpochMilli(timestamp);
                ZonedDateTime zonedDate = instant.atZone(ZoneOffset.UTC);
                DayOfWeek dayOfWeek = zonedDate.getDayOfWeek();
                LocalDate dayOfMonthDate = zonedDate.toLocalDate();
                LocalTime hourOfDay = zonedDate.toLocalTime().truncatedTo(ChronoUnit.HOURS);
                deviceImpressionCount.merge(deviceId, 1L, Long::sum);
                dayOfWeekCount.merge(dayOfWeek, 1L, Long::sum);
                dayOfMonthCount.merge(dayOfMonthDate,1L,Long::sum);
                hourOfDayCount.merge(hourOfDay,1L,Long::sum);
            });
            IMMUTABLE_DEVICE_IMPRESSIONS = deviceImpressionCount.entrySet().stream().map(entry -> new DeviceImpression(entry.getKey(), entry.getValue())).collect(Collectors.toUnmodifiableSet());
            IMMUTABLE_WEEKDAY_IMPRESSIONS = dayOfWeekCount.entrySet().stream().map(entry -> new DayOfWeekImpression(entry.getKey(), entry.getValue())).collect(Collectors.toUnmodifiableSet());
            IMMUTABLE_DAY_OF_MONTH_IMPRESSIONS = dayOfMonthCount.entrySet().stream().map(entry->new DayOfMonthImpression(entry.getKey(),entry.getValue())).collect(
                    Collectors.toUnmodifiableSet());
            IMMUTABLE_HOUR_OF_DAY_IMPRESSIONS = hourOfDayCount.entrySet().stream().map(entry->new HourOfDayImpression(entry.getKey(),entry.getValue())).collect(
                    Collectors.toUnmodifiableSet());
        } catch (Exception e) {
            LOGGER.error("Error during parsing.", e);
        }

    }
}
