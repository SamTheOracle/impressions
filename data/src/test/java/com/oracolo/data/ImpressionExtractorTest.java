package com.oracolo.data;

import java.util.Set;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class ImpressionExtractorTest {

	@Inject
	AsyncImpressionExtractor impressionExtractor;

	@Test
	@DisplayName("Should parse file correctly")
	void shouldParseCorrectly() throws InterruptedException {
		CompletionStage<Set<DeviceImpression>> deviceImpressionsCompletionStage = impressionExtractor.deviceImpressions();
		Assertions.assertFalse(impressionExtractor.resourceReady());
		Set<DeviceImpression> deviceImpressions = Assertions.assertDoesNotThrow(
				() -> deviceImpressionsCompletionStage.toCompletableFuture().join());
		Assertions.assertTrue(impressionExtractor.resourceReady());
		DeviceImpression testDeviceImpression = new DeviceImpression(100, 64);
		Assertions.assertNotNull(deviceImpressionsCompletionStage);
		Assertions.assertTrue(deviceImpressions.contains(testDeviceImpression));
		Assertions.assertTrue(
				deviceImpressions.stream().filter(deviceImpression -> deviceImpression.equals(testDeviceImpression)).findAny().filter(
						deviceImpression -> deviceImpression.getNumberOfImpressions()
								== testDeviceImpression.getNumberOfImpressions()).isPresent());
		Set<DayOfWeekImpression> dayOfWeekImpressions = Assertions.assertDoesNotThrow(()->impressionExtractor.dayOfWeekImpressions().toCompletableFuture().join());
		Assertions.assertFalse(dayOfWeekImpressions.isEmpty());
		Assertions.assertTrue(dayOfWeekImpressions.size()<=7);
		Set<HourOfDayImpression> hourOfDayImpressions = Assertions.assertDoesNotThrow(()->impressionExtractor.hourOfDayImpressions().toCompletableFuture().join());
		Assertions.assertFalse(hourOfDayImpressions.isEmpty());
		Assertions.assertTrue(hourOfDayImpressions.size()<=24);
		Set<DayOfMonthImpression> dayOfMonthImpressions = Assertions.assertDoesNotThrow(()->impressionExtractor.dayOfMonthImpressions().toCompletableFuture().join());
		Assertions.assertFalse(dayOfMonthImpressions.isEmpty());
	}
}
