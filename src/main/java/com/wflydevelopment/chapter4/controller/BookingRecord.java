package com.wflydevelopment.chapter4.controller;

import java.io.Serializable;

import javax.enterprise.event.Observes;

import com.wflydevelopment.chapter4.annotations.NamedView;
import com.wflydevelopment.chapter4.entity.Seat;

@NamedView
public class BookingRecord implements Serializable {
	private int bookedCount = 0;
	
	public int getBookedCount() {
		return bookedCount;
	}
	
	public void bookEvent(@Observes Seat bookedSeats) {
		bookedCount++;
	}
}
