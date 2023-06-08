package com.example.frontdesk.Services;

import com.example.frontdesk.BindingClasses.LoginForm;
import com.example.frontdesk.BindingClasses.SignUp;
import com.example.frontdesk.BindingClasses.UnlockForm;
import com.example.frontdesk.Repositories.UserRepo;
import com.example.frontdesk.UtilClasses.EmailUtil;
import com.example.frontdesk.UtilClasses.PwdUtils;
import com.example.frontdesk.entities.UserDetails;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.stream.Stream;

@Service
public class UserServiceImple implements UserService{
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private HttpSession session;
    @Override
    public boolean signUp(SignUp form) {
        //TODO: convert form data to entity
        UserDetails e= userRepo.findByEmail(form.getEmail());
        if(e!=null){
            return false;
        }
        UserDetails entity= new UserDetails();

        BeanUtils.copyProperties(form,entity);
        //TODO: generate random password and add it to object
        String tempPassword= PwdUtils.generatePassword();
        entity.setPwd(tempPassword);
        //TODO: add default status
        entity.setStatus("LOCKED");
        //TODO: add entity to repo;
        userRepo.save(entity);

        //TODO : send mail for unlock with link
        String to = form.getEmail();
        String subject="Activation Mail for AshokIT";
        StringBuffer sb= new StringBuffer();
        sb.append("<h5>temporary password for your account is below</h5>");
        sb.append("<p>Temporary password is : "+tempPassword+"</p>");
        sb.append("<br/>");
        sb.append("<p>click on below link to unlock account</p>");
        sb.append("<a href=\"http://localhost:8080/unlock?email="+to+"\">Unlock account Link</a>");
        emailUtil.sendMessage(to,subject,sb.toString());
        return true;
    }

    @Override
    public String login(LoginForm form) {
        UserDetails entity= userRepo.findByEmailAndPwd(form.getUsername(), form.getPassword());
        if(entity==null){
            return "Invalid credentials";
        }
        else if(entity.getStatus().equals("LOCKED")){
            return "account is locked";
        }
        else{
            session.setAttribute("userId",form.getUsername());
            return "success";
        }
    }

    @Override
    public boolean resetPassword(String userEmail) {
        UserDetails entity = userRepo.findByEmail(userEmail);
        if(entity==null){
            return false;
        }

        String subject="Forgot Password Mail ";
        StringBuffer sb= new StringBuffer();
        sb.append("<h5> password for your account is below</h5>");
        sb.append("<p>Password is : "+entity.getPwd()+"</p>");
        sb.append("<br/>");
        sb.append("<p>click on below link to login </p>");
        sb.append("<a href=\"http://localhost:8080/login\">Link to login</a>");
        emailUtil.sendMessage(userEmail,subject,sb.toString());

        return true;
    }

    @Override
    public boolean unlock(UnlockForm form) {
        UserDetails entity=userRepo.findByEmail(form.getEmail());
        if(form.getTempPassword().equals(entity.getPwd())){
            entity.setStatus("Unlocked");
            entity.setPwd(form.getNewPassword());
            userRepo.save(entity);
            return true;
        }
        return false;
    }
}
