package com.example.frontdesk.entities;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name="AIT_STUDENT_ENQUIRIES")
public class StudentEnquiries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer enquiryId;
    private String name;
    private Long contactNo;
    private String classMode;
    private String course;
    private  String enquiryStatus;
    @CreationTimestamp
    private LocalDate createdDate;
    @UpdateTimestamp
    private LocalDate updatedDate;
    @ManyToOne
    @JoinColumn(name="user_Id")
    private UserDetails user;

    @Override
    public String toString() {
        return "StudentEnquiries{" +
                "enquiryId=" + enquiryId +
                ", name='" + name + '\'' +
                ", contactNo=" + contactNo +
                ", classMode='" + classMode + '\'' +
                ", course='" + course + '\'' +
                ", enquiryStatus='" + enquiryStatus + '\'' +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                ", user=" + user +
                '}';
    }
}
