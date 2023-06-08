package com.example.frontdesk.entities;


import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="AIT_USER_DTLS")
public class UserDetails {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userId;
    private String name;
    private String email;
    private Long phoneNumber;
    private String pwd;
    private  String status;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<StudentEnquiries> enquiryList;

    @Override
    public String toString() {
        return "UserDetails{" +
                "userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", pwd='" + pwd + '\'' +
                ", status='" + status + '\'' +
                ", enquiryList=" + enquiryList +
                '}';
    }
}
