package com.teamrandom.softproj.service;

import com.teamrandom.softproj.businessObject.*;
import com.teamrandom.softproj.repository.ReviewRepository;
import com.teamrandom.softproj.repository.RoleRepository;
import com.teamrandom.softproj.repository.UserRepository;
import com.teamrandom.softproj.role.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
@Service("reviewService")
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReviewRepository userRepository;
    
    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void uploadArtifact(){

    }
    
    public void saveReview(Review review){
        reviewRepository.save(review);
    }
}
