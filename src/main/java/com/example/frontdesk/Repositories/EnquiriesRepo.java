package com.example.frontdesk.Repositories;

import com.example.frontdesk.entities.StudentEnquiries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnquiriesRepo extends JpaRepository<StudentEnquiries, Integer> {
}
