package com.oracolo.data;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.Set;
import java.util.concurrent.CompletionStage;

@QuarkusTest
public class ImpressionExtractorTest {

    @Inject
    AsyncImpressionExtractor impressionExtractor;

    @Test
    @DisplayName("Should parse file correctly")
    void shouldParseCorrectly() throws InterruptedException {
        CompletionStage<Set<DeviceImpression>> deviceImpressionsCompletionStage = impressionExtractor.deviceImpressions();
        Assertions.assertFalse(impressionExtractor.resourceReady());
        Set<DeviceImpression> deviceImpressions = Assertions.assertDoesNotThrow(()->deviceImpressionsCompletionStage.toCompletableFuture().join());
        Assertions.assertTrue(impressionExtractor.resourceReady());
        DeviceImpression testDeviceImpression = new DeviceImpression(10,5);
        Assertions.assertNotNull(deviceImpressionsCompletionStage);
        Assertions.assertTrue(deviceImpressions.contains(testDeviceImpression));
    }
}
