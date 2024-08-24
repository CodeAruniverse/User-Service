package com.main.User.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.main.User.entities.Hotel;
import com.main.User.entities.Rating;
import com.main.User.entities.User;
import com.main.User.exception.ResourceNotFoundException;
import com.main.User.external.HotelFeignService;
import com.main.User.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelFeignService hotelFeignService;
	
	
	@Override
	public List<User> saveUsers(List<User> users) {
	    List<User> savedUsers = new ArrayList<>();
	    
	    for (User user : users) {
	        String uuid = UUID.randomUUID().toString();
	        user.setUserId(uuid);
	    }
	    
	    savedUsers = this.userRepo.saveAll(users);
	    return savedUsers;
	}


	@Override
	public List<User> getAllUser() {
		return this.userRepo.findAll();
	}

	@Override
	public User getUserById(String userId) {
		// Retrieve the user from the repository, throwing an exception if not found
		User user = this.userRepo.findById(userId)
		    .orElseThrow(() -> new ResourceNotFoundException("User not found"));

		// Fetch rating details for the user
		Rating[] ratingOfUser;
		try {
		    ratingOfUser = this.restTemplate.getForObject("http://RATING/rating-service/user/" + user.getUserId(), Rating[].class);
		    if (ratingOfUser == null) {
		        throw new ResourceNotFoundException("Ratings not found for user");
		    }
		} catch (RestClientException e) {
		    throw new ServiceException("Error fetching ratings from rating service", e);
		}

		// Convert the array of ratings to a list
		List<Rating> ratings = Arrays.asList(ratingOfUser);

		// Fetch hotel details for each rating and set it in the rating
		List<Rating> ratingList = ratings.stream().map(rating -> {
		    Hotel hotel = hotelFeignService.getHotelById(rating.getHotelId());
		    rating.setHotel(hotel);
		    return rating;
		}).collect(Collectors.toList());

		// Set the ratings in the user object
		user.setRating(ratingList);

		return user;

	}

}
