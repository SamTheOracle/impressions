package com.oracolo.impressions.server;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.oracolo.data.AsyncImpressionExtractor;
import com.oracolo.impressions.server.dto.ErrorDto;

import io.smallrye.mutiny.Uni;

@Path("/impressions")
public class ImpressionsRest {

	@Inject
	AsyncImpressionExtractor asyncImpressionExtractor;

	@GET
	@Path("by_device")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Response> byDevice() {
		return Uni.createFrom().completionStage(asyncImpressionExtractor.deviceImpressions()).map(
				deviceImpressions -> Response.ok(deviceImpressions).build()).onFailure().recoverWithItem(
				throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDto(throwable.getMessage())).build());
	}

	@GET
	@Path("by_hour")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Response> byHourOfDay() {
		return Uni.createFrom().completionStage(asyncImpressionExtractor.hourOfDayImpressions()).map(
				hourOfDayImpressions -> Response.ok(hourOfDayImpressions).build()).onFailure().recoverWithItem(
				throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDto(throwable.getMessage())).build());
	}

	@GET
	@Path("by_dayofweek")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Response> byDayOfWeek() {
		return Uni.createFrom().completionStage(asyncImpressionExtractor.dayOfWeekImpressions()).map(
				dayOfWeekImpressions -> Response.ok(dayOfWeekImpressions).build()).onFailure().recoverWithItem(
				throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDto(throwable.getMessage())).build());
	}

	@GET
	@Path("by_dayofmonth")
	@Produces(MediaType.APPLICATION_JSON)
	public Uni<Response> byDayOfMonth() {
		return Uni.createFrom().completionStage(asyncImpressionExtractor.dayOfMonthImpressions()).map(
				dayOfMonthImpressions -> Response.ok(dayOfMonthImpressions).build()).onFailure().recoverWithItem(
				throwable -> Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorDto(throwable.getMessage())).build());
	}

}
