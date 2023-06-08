package com.example.frontdesk.Services;

import com.example.frontdesk.BindingClasses.DashBoardResponse;
import com.example.frontdesk.BindingClasses.EnquiryForm;
import com.example.frontdesk.BindingClasses.EnquirySearchCriteria;
import com.example.frontdesk.Repositories.EnquiriesRepo;
import com.example.frontdesk.Repositories.UserRepo;
import com.example.frontdesk.entities.StudentEnquiries;
import com.example.frontdesk.entities.UserDetails;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnquiryServiceImpl implements  EnquiryService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HttpSession session;

    @Autowired
    private EnquiriesRepo enquiriesRepo;

    @Override
    public DashBoardResponse getDashboard(String username) {
        UserDetails userDetails = userRepo.findByEmail(username);
        List<StudentEnquiries> enquiries = userDetails.getEnquiryList();
        Integer total = enquiries.size();
        Integer lost = Math.toIntExact(enquiries.stream().filter(e -> e.getEnquiryStatus().equals("Lost")).count());
        Integer enrolled = Math.toIntExact(enquiries.stream().filter(e -> e.getEnquiryStatus().equals("Enrolled")).count());
        DashBoardResponse dashBoardResponse = new DashBoardResponse();
        dashBoardResponse.setEnrolled(enrolled);
        dashBoardResponse.setTotalEnquiries(total);
        dashBoardResponse.setLostCnt(lost);
        return dashBoardResponse;
    }

    @Override
    public boolean addEnquiry(EnquiryForm form) {
        UserDetails userEntity = userRepo.findByEmail((String) session.getAttribute("userId"));
        StudentEnquiries s = new StudentEnquiries();
        BeanUtils.copyProperties(form, s);
        if(session.getAttribute("enqId")!=null){
            s.setEnquiryId((Integer) session.getAttribute("enqId"));
            session.removeAttribute("enqId");
        }
        s.setUser(userEntity);
        enquiriesRepo.save(s);
        return true;
    }

    @Override
    public List<StudentEnquiries> getEnquiries(String username) {
        return userRepo.findByEmail(username).getEnquiryList();
    }

    @Override
    public List<StudentEnquiries> filteredEnquiries(EnquirySearchCriteria search,String username) {
        List<StudentEnquiries> enquiries= userRepo.findByEmail(username).getEnquiryList();
        if(null!=search.getCourse() && !"".equals(search.getCourse())){
            enquiries= enquiries.stream().filter(e->e.getCourse().equals(search.getCourse())).collect(Collectors.toList());
        }
        if(null!=search.getEnquiryStatus() && !"".equals(search.getEnquiryStatus())){
            enquiries= enquiries.stream().filter(e->e.getEnquiryStatus().equals(search.getEnquiryStatus())).collect(Collectors.toList());
        }
        if(null!=search.getClassMode() && !"".equals(search.getClassMode())){
            enquiries= enquiries.stream().filter(e->e.getClassMode().equals(search.getClassMode())).collect(Collectors.toList());
        }
        return enquiries;
    }

    @Override
    public StudentEnquiries getEnquiry(int id) {
        Optional<StudentEnquiries> s= enquiriesRepo.findById(id);
        if(s.isPresent()){
            return s.get();
        }
        return null;
    }
}
