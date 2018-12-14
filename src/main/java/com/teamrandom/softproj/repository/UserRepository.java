package com.teamrandom.softproj.repository;


import com.teamrandom.softproj.businessObject.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    @Query(value = "select u from User u join u.roles r where r.role='REVIEWER'")
    List<User> findReviewers();

    @Query(value = "select u from User u")
    List<User> findAllUsers();

    @Query(value = "select u from User u where u.id = :id")
    User getUserById(@Param("id") long id);

    @Query(value = "select email from User u where u.id = :id")
    String getEmailByUserId(@Param("id") long id);
}
