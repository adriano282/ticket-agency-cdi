package com.wflydevelopment.chapter4.controller;

import java.util.Optional;

import javax.enterprise.inject.Model;
import javax.inject.Inject;

import com.wflydevelopment.chapter4.boundary.TheatreBox;
import com.wflydevelopment.chapter4.entity.Seat;

@Model
public class Poller {
	@Inject
	private TheatreBox theatreBox;
	
	public boolean isPollingActive() {
		return areFreeSeatsAvailable();
	}
	
	private boolean areFreeSeatsAvailable() {
		final Optional<Seat> firstSeat =
				theatreBox.getSeats()
					.stream()
					.filter(seat -> !seat.isBooked())
					.findFirst();
		return firstSeat.isPresent();
	}
}

