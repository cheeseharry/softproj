package com.teamrandom.softproj.controller;

import com.teamrandom.softproj.businessObject.*;
import com.teamrandom.softproj.repository.RoleRepository;
import com.teamrandom.softproj.repository.UserRepository;
import com.teamrandom.softproj.role.Role;
import com.teamrandom.softproj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/reviewer")
public class ReviewerController {

    @Autowired
    UserService userService;
    
    @Autowired
    ReviewService reviewService;
    
    @Autowired
    ArtifactService artifactService;

    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UserRepository artifactRepository;

    @Autowired
    TeamService teamService;

    @Autowired
    RoleRepository roleRepository;

    @RequestMapping(value="/associatedArtifacts", method = RequestMethod.GET)
    public ModelAndView home(@ModelAttribute("alertMessage") String alertMessage){
        ModelAndView modelAndView = new ModelAndView();
        User user = getCurrentUser();
        if (user == null) modelAndView.setViewName("login");
        else {
            modelAndView.addObject("alertMessage", alertMessage);
            modelAndView.addObject("user_roles", user.getRoles());
            modelAndView.addObject("user", user);
            List<User> reviewers = userService.getReviewers();
            List<Artifact> allArtifacts = artifactService.getAllArtifact();
            modelAndView.addObject("files", allArtifacts);
            modelAndView.addObject("reviewers", reviewers);
            modelAndView.addObject("submitter", roleRepository.findByRole("SUBMITTER"));
            modelAndView.addObject("adminRole", roleRepository.findByRole("ADMIN"));
            modelAndView.addObject("reviewerRole", roleRepository.findByRole("REVIEWER"));
            modelAndView.setViewName("/reviewer/associatedArtifacts");
        }
        return modelAndView;
    }
    
    @RequestMapping(value="/writeReview")
    public ModelAndView writeReview(@ModelAttribute("alertMessage") String alertMessage){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/reviewer/writeReview");
        return modelAndView;
    }
    
    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userService.findUserByUsername(auth.getName());
    }
}
