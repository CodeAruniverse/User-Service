package com.main.User.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.main.User.entities.Hotel;

@FeignClient(name = "HOTEL")
public interface HotelFeignService {

	@GetMapping("/hotel-service/{hotelId}")
	public Hotel getHotelById(@PathVariable String hotelId);
}
