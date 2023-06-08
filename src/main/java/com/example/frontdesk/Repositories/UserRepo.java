package com.example.frontdesk.Repositories;

import com.example.frontdesk.entities.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserDetails, Integer> {
    public UserDetails findByEmail(String email);
    public UserDetails findByEmailAndPwd(String email, String pwd);
}
