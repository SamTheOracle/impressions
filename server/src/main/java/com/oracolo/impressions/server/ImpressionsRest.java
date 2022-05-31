package com.oracolo.impressions.server;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.oracolo.data.AsyncImpressionExtractor;
import com.oracolo.data.DeviceImpression;

import io.smallrye.mutiny.Uni;

@Path("/impressions")
public class ImpressionsRest {

	@Inject
	AsyncImpressionExtractor asyncImpressionExtractor;

	@GET
	@Path("by_device")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Set<DeviceImpression>> byDevice(){
		return Uni.createFrom().completionStage(asyncImpressionExtractor.deviceImpressions());
	}


}
