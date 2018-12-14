package com.teamrandom.softproj.controller;

import com.teamrandom.softproj.businessObject.User;
import com.teamrandom.softproj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestController {

    @Autowired
    UserService userService;

    @RequestMapping(path = "/admin/users", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

//    @RequestMapping(value = "admin/user/{id}", method = RequestMethod.GET)
//    public User getUserById(@PathVariable("id") Long id){
//        return userService.findUserById(id);
//    }
}
