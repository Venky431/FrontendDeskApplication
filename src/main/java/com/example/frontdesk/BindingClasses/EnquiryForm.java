package com.example.frontdesk.BindingClasses;

import lombok.Data;

@Data
public class EnquiryForm {
    private String name;
    private Long contactNo;
    private String classMode;
    private  String course;
    private String enquiryStatus;
}
