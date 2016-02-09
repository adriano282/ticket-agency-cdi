package com.wflydevelopment.chapter4.control;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.Timer;
import javax.ejb.TimerService;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.wflydevelopment.chapter4.boundary.TheatreBox;
import com.wflydevelopment.chapter4.entity.Seat;

@Stateless
public class AutomaticSellerService {
	
	@Inject
	private Logger logger;
	
	@Inject
	private TheatreBox theatreBox;
	
	@Resource
	private TimerService timerService;
	
	@Schedule(hour = "*", minute = "*/1", persistent = false)
	public void automaticCustomer() {
		final Optional<Seat> seatOptional = findFreeSeat();
		if (!seatOptional.isPresent()) {
			cancelTimers();
			logger.info("Scheduler gone!");
			return;
		}
		
		final Seat seat = seatOptional.get();
		
		theatreBox.buyTicket(seat.getId());
		
		
		logger.info("Somebody just bookedd seat number " + seat.getId());
	}
	
	private Optional<Seat> findFreeSeat() {
		final Collection<Seat> list = theatreBox.getSeats();
		return list.stream()
				.filter(seat -> !seat.isBooked())
				.findFirst();
	}
	
	private void cancelTimers() {
		for (Timer timer : timerService.getTimers())
			timer.cancel();
	}
}
