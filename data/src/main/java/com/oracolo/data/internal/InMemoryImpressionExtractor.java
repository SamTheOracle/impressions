package com.oracolo.data.internal;

import com.oracolo.data.*;
import io.quarkus.runtime.Startup;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@ApplicationScoped
@Startup
class InMemoryImpressionExtractor implements ImpressionExtractor {
    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryImpressionExtractor.class.getName());
    private static Set<DayOfWeekImpression> IMMUTABLE_WEEKDAY_IMPRESSION;
    private static Set<DeviceImpression> IMMUTABLE_DEVICE_IMPRESSION;

    @ConfigProperty(name = "impressions.file-path")
    String filePath;

    @ConfigProperty(name = "impressions.csv-delimiter", defaultValue = ",")
    String csvDelimiter;

    @PostConstruct
    void grindData() {
        doGrind(filePath, csvDelimiter);
    }

    @Override
    public Set<DeviceImpression> deviceImpressions() {
        return IMMUTABLE_DEVICE_IMPRESSION;
    }

    @Override
    public Set<DayOfMonthImpression> dayOfMonthImpressions() {
        return null;
    }

    @Override
    public Set<DayOfWeekImpression> dayOfWeekImpressions() {
        return IMMUTABLE_WEEKDAY_IMPRESSION;
    }

    @Override
    public Set<HourOfDayImpression> hourOfDayImpressions() {
        return null;
    }

    private static void doGrind(String filePath, String csvDelimiter) {
        Path path = Path.of(filePath);
        try (Reader reader = Files.newBufferedReader(path)) {
            CSVParser parser = CSVParser.parse(reader, CSVFormat.DEFAULT.builder().setDelimiter(csvDelimiter).setHeader().build());
            Stream<CSVRecord> csvRecordStream = StreamSupport.stream(parser.spliterator(),true);
            Map<Integer,Long> deviceImpressionCount = new ConcurrentHashMap<>();
            Map<DayOfWeek,Long> dayOfWeekCount = new ConcurrentHashMap<>();
            csvRecordStream.forEach(csvRecord->{
                Map<String,String> headerValue = csvRecord.toMap();
                int deviceId = Integer.parseInt(headerValue.get("device_id"));
                long timestamp = Long.parseLong(headerValue.get("timestamp"));
                Instant instant = Instant.ofEpochMilli(timestamp);
                DayOfWeek dayOfWeek = instant.atZone(ZoneOffset.UTC).getDayOfWeek();
                deviceImpressionCount.merge(deviceId,1L,Long::sum);
                dayOfWeekCount.merge(dayOfWeek,1L,Long::sum);
            });
            IMMUTABLE_DEVICE_IMPRESSION = deviceImpressionCount.entrySet().stream().map(entry->new DeviceImpression(entry.getKey(),entry.getValue())).collect(Collectors.toUnmodifiableSet());
            IMMUTABLE_WEEKDAY_IMPRESSION = dayOfWeekCount.entrySet().stream().map(entry->new DayOfWeekImpression(entry.getKey(),entry.getValue())).collect(Collectors.toUnmodifiableSet());
        } catch (Exception e) {
            LOGGER.error("Error during parsing.",e);
        }


    }
}
