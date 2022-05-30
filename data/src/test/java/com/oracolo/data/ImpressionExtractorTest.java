package com.oracolo.data;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.time.DayOfWeek;
import java.util.Set;

@QuarkusTest
public class ImpressionExtractorTest {

    @Inject
    ImpressionExtractor impressionExtractor;

    @Test
    @DisplayName("Should parse file correctly")
    void shouldParseCorrectly(){
        Set<DeviceImpression> deviceImpressions = impressionExtractor.deviceImpressions();
        DeviceImpression testDeviceImpression = new DeviceImpression(10,5);
        Assertions.assertNotNull(deviceImpressions);
        Assertions.assertTrue(deviceImpressions.contains(testDeviceImpression));
        Assertions.assertThrows(Exception.class,()->deviceImpressions.add(testDeviceImpression));
        Set<DayOfWeekImpression> dayOfWeekImpressions = impressionExtractor.dayOfWeekImpressions();
        Assertions.assertNotNull(dayOfWeekImpressions);
        Assertions.assertTrue(dayOfWeekImpressions.size()!=0);
        Assertions.assertThrows(Exception.class,()->dayOfWeekImpressions.add(new DayOfWeekImpression(DayOfWeek.FRIDAY,12)));
    }
}
