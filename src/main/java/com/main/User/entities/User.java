package com.main.User.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user_service")
public class User {

	@Id
	@Column(name = "ID")
	private String userId;
	
	private String name;
	
	private String email;
	
	private String about;
		
	@Transient
	private List<Rating> rating = new ArrayList<>();
}
