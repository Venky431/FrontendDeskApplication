package com.example.frontdesk.Repositories;

import com.example.frontdesk.entities.Courses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepo extends JpaRepository<Courses,Integer > {
}
