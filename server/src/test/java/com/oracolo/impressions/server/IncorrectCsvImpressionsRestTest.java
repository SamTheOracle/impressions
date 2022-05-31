package com.oracolo.impressions.server;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.oracolo.data.DeviceImpression;
import com.oracolo.impressions.server.dto.ErrorDto;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.restassured.RestAssured;

@QuarkusTest
@TestHTTPEndpoint(ImpressionsRest.class)
@TestProfile(Profiles.IncorrectCsvProfile.class)
class IncorrectCsvImpressionsRestTest {

	@Test
	@DisplayName("Should get an error")
	void shouldGetAnError() {
		ErrorDto errorDto1 = Assertions.assertDoesNotThrow(
				() -> given().when().get("/by_device").then().statusCode(500).extract().as(ErrorDto.class));
		Assertions.assertNotNull(errorDto1);
		Assertions.assertNotNull(errorDto1.getExceptionMessage());
		Assertions.assertEquals("Error during csv parsing", errorDto1.getError());
		ErrorDto errorDto2 = Assertions.assertDoesNotThrow(
				() -> given().when().get("/by_hour").then().statusCode(500).extract().as(ErrorDto.class));
		Assertions.assertNotNull(errorDto2);
		Assertions.assertNotNull(errorDto2.getExceptionMessage());
		Assertions.assertEquals("Error during csv parsing", errorDto2.getError());
		ErrorDto errorDto3 = Assertions.assertDoesNotThrow(
				() -> given().when().get("/by_dayofweek").then().statusCode(500).extract().as(ErrorDto.class));
		Assertions.assertNotNull(errorDto3);
		Assertions.assertNotNull(errorDto3.getExceptionMessage());
		Assertions.assertEquals("Error during csv parsing", errorDto3.getError());
		ErrorDto errorDto4 = Assertions.assertDoesNotThrow(
				() -> given().when().get("/by_dayofmonth").then().statusCode(500).extract().as(ErrorDto.class));
		Assertions.assertNotNull(errorDto4);
		Assertions.assertNotNull(errorDto4.getExceptionMessage());
		Assertions.assertEquals("Error during csv parsing", errorDto4.getError());
		ErrorDto errorDto = Assertions.assertDoesNotThrow(
				() -> given().when().get("/by_device").then().statusCode(500).extract().as(ErrorDto.class));
		Assertions.assertNotNull(errorDto);
		Assertions.assertNotNull(errorDto.getExceptionMessage());
		Assertions.assertEquals("Error during csv parsing", errorDto.getError());
	}
}
