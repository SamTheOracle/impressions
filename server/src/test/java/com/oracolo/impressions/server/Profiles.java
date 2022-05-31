package com.oracolo.impressions.server;

import java.util.Collections;
import java.util.Map;

import io.quarkus.test.junit.QuarkusTestProfile;

public class Profiles {

	public static class CorrectCsvProfile implements QuarkusTestProfile {
		@Override
		public Map<String, String> getConfigOverrides() {
			return Collections.singletonMap("impressions.file-path", "src/test/resources/dataset.csv");
		}
	}

	public static class IncorrectCsvProfile implements QuarkusTestProfile {
		@Override
		public Map<String, String> getConfigOverrides() {
			return Collections.singletonMap("impressions.file-path", "src/test/resources/incorrect-dataset.csv");
		}
	}
}
