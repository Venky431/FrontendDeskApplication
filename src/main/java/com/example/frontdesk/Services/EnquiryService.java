package com.example.frontdesk.Services;

import com.example.frontdesk.BindingClasses.DashBoardResponse;
import com.example.frontdesk.BindingClasses.EnquiryForm;
import com.example.frontdesk.BindingClasses.EnquirySearchCriteria;
import com.example.frontdesk.entities.StudentEnquiries;

import java.util.List;

public interface EnquiryService {
    public DashBoardResponse getDashboard(String username);
    public boolean addEnquiry(EnquiryForm form);
    public List<StudentEnquiries> getEnquiries(String username);
    public List<StudentEnquiries> filteredEnquiries(EnquirySearchCriteria search,String username);


    StudentEnquiries getEnquiry(int id);
}
