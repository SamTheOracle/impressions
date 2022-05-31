package com.oracolo.impressions.server;

import java.time.LocalTime;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.oracolo.data.DeviceImpression;
import com.oracolo.data.HourOfDayImpression;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;

@QuarkusTest
@TestHTTPEndpoint(ImpressionsRest.class)
class ImpressionsRestTest {

	@Test
	@DisplayName("Should correctly get device impressions")
	void shouldGetDeviceImpression() {
		DeviceImpression[] deviceImpressions = Assertions.assertDoesNotThrow(
				() -> RestAssured.given().when().get("/by_device").then().statusCode(200).extract().as(DeviceImpression[].class));
		Assertions.assertTrue(deviceImpressions.length > 0);
		Arrays.stream(deviceImpressions).forEach(deviceImpression -> Assertions.assertTrue(deviceImpression.getNumberOfImpressions() > 0));
	}

	@Test
	@DisplayName("Should correctly get device impression by hour of day")
	void shouldGetHourImpression() {
		HourOfDayImpression[] hourOfDayImpressions = Assertions.assertDoesNotThrow(
				() -> RestAssured.given().when().get("/by_hour").then().statusCode(200).extract().as(HourOfDayImpression[].class));
		Assertions.assertEquals(24,hourOfDayImpressions.length);
		Arrays.stream(hourOfDayImpressions).forEach(hourOfDayImpression -> {
			Assertions.assertTrue(hourOfDayImpression.getHourOfDay().isBefore(LocalTime.MAX));
			Assertions.assertTrue(hourOfDayImpression.getCount()>0);
		});
	}

	@Test
	@DisplayName("Should correctly get device impression by day of week")
	void shouldGetDayOfWeekImpression() {

	}

	@Test
	@DisplayName("Should get device impression by day of month")
	void shouldGetImpressionByDayOfMonth() {

	}
}