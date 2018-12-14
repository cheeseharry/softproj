package com.teamrandom.softproj.component;

import java.util.List;

import com.teamrandom.softproj.businessObject.User;

public class Artifact {

	private String id;
	private Status status;
	private String contents;
	
	private List<Rating> ratings;
	private List<Review> reviews;
	
	private User submitter;
	private List<User> reviewers;
	private List<User> lecturers;
	
	
	
	
}
