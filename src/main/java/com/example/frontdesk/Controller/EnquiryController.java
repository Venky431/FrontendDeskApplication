package com.example.frontdesk.Controller;

import com.example.frontdesk.BindingClasses.DashBoardResponse;
import com.example.frontdesk.BindingClasses.EnquiryForm;
import com.example.frontdesk.BindingClasses.EnquirySearchCriteria;
import com.example.frontdesk.Services.EnquiryService;
import com.example.frontdesk.entities.StudentEnquiries;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class EnquiryController {

    @Autowired
    private HttpSession session;

    @Autowired
    private EnquiryService enquiryService;

    @GetMapping("/dashboard")
    public String dashBoardPage(Model model){
        String userName= (String) session.getAttribute("userId");
        DashBoardResponse dashBoardResponse =  enquiryService.getDashboard(userName);
        model.addAttribute("dashBoard",dashBoardResponse);
        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(){
        session.invalidate();
        return "index";

    }
    @GetMapping("/enquiry")
    public String addEnquiryPage(Model model){
        EnquiryForm e= new EnquiryForm();
        if(session.getAttribute("enq")!=null){
            BeanUtils.copyProperties(session.getAttribute("enq"),e);
            session.removeAttribute("enq");
        }

        model.addAttribute("enquiryPage",e);
        return "add-enquiry";
    }

    @GetMapping("/enquiry/{id}")
    public String editEnquiryPage(@PathVariable("id") int id){

        StudentEnquiries s = enquiryService.getEnquiry(id);
        session.setAttribute("enq",s);
        session.setAttribute("enqId",id);
        return "redirect:/enquiry";
    }
    @PostMapping("/enquiry")
    public String addEnquiry(@ModelAttribute("enquiryPage") EnquiryForm form, Model model){
        System.out.println(form);
        enquiryService.addEnquiry(form);
        model.addAttribute("sucMsg","Enquiry added successfully");
        return "add-enquiry";
    }

    @GetMapping("/enquires")
    public String enquiriesPage(Model model){
        String userName= (String) session.getAttribute("userId");

        List<StudentEnquiries> enquiries = enquiryService.getEnquiries(userName);
        model.addAttribute("enquiries",enquiries);
        return "view-enquiries";
    }

    @GetMapping("/filtered-enquiries")
    public String filteredEnquiries(@RequestParam("cname") String cname, @RequestParam("mode") String mode,@RequestParam("status") String status,Model model ){
        EnquirySearchCriteria search = new EnquirySearchCriteria();
        search.setEnquiryStatus(status);
        search.setCourse(cname);
        search.setClassMode(mode);
        String username = (String) session.getAttribute("userId");
        System.out.println(search);
        List<StudentEnquiries> enquiries= enquiryService.filteredEnquiries(search,username);
        model.addAttribute("enquiries",enquiries);
        return "custom-enquiry";
    }

}
