package com.example.frontdesk.Controller;

import com.example.frontdesk.BindingClasses.LoginForm;
import com.example.frontdesk.BindingClasses.SignUp;
import com.example.frontdesk.BindingClasses.UnlockForm;
import com.example.frontdesk.Services.UserService;
import com.example.frontdesk.Services.UserServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String loginPage(Model model){
        model.addAttribute("loginform", new LoginForm());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("loginform") LoginForm form, Model model){
        System.out.println(form);
         String status = userService.login(form);
         if(status.contains("success")){
             return "redirect:/dashboard";
         }
         else{
             model.addAttribute("errorMsg",status);
         }

        return "login";

    }


    @PostMapping("/signup")
    public String signUp(@ModelAttribute("user") SignUp form, Model model){
        boolean status = userService.signUp(form);
        if(status){
            model.addAttribute("successMsg","signup successfull mail sent to  your email");
        }
        else{
            model.addAttribute("errorMsg","invalid email");
        }
        return "signup";


    }

    @GetMapping("/signup")
    public String signUpPage(Model model){
        model.addAttribute("user",new SignUp());
        return "signup";
    }

    @GetMapping("/forgot")
    public String forgotPwdPage(){
        return "forgotPwd";
    }
    @PostMapping("/forgot")
    public String forgot(@RequestParam("email") String email , Model model){
         boolean status = userService.resetPassword(email);
         if(status){
             model.addAttribute("successMsg","Sent email with password to your mail address ");
         }
         else{
             model.addAttribute("errorMsg","please give valid mail address");
         }


        return "forgotPwd";

    }


    @GetMapping("/unlock")
    public String unlockPage(@RequestParam String email, Model model){
        UnlockForm unlock= new UnlockForm();
        unlock.setEmail(email);
        model.addAttribute("unlock",unlock);
        return "unlock";
    }
    @PostMapping("/unlock")
    public String unlock(UnlockForm form, Model model){

        if(form.getNewPassword().equals(form.getConfirmationPassword())){
             boolean status = userService.unlock(form);
             if(status){
                 model.addAttribute("successMsg","account unlocked successfully");
             }
             else{
                 model.addAttribute("errorMsg","temp password is wrong check your email");
             }
        }
        else{
            model.addAttribute("errorMsg","new password and confirmation password should be same");
        }
        model.addAttribute("unlock", form);
        return "unlock";

    }
}
