package com.oracolo.impressions.server;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.oracolo.data.AsyncImpressionExtractor;
import com.oracolo.data.DeviceImpression;

import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class UniTest {

	@Inject
	AsyncImpressionExtractor asyncImpressionExtractor;
	public void fromCompletionStage(){
		Uni<Set<DeviceImpression>> uni = Uni.createFrom().completionStage(asyncImpressionExtractor.deviceImpressions());
	}
}
