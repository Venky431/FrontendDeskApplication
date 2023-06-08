package com.example.frontdesk.Services;

import com.example.frontdesk.BindingClasses.LoginForm;
import com.example.frontdesk.BindingClasses.SignUp;
import com.example.frontdesk.BindingClasses.UnlockForm;

public interface UserService {
    public boolean signUp(SignUp form);
    public String login(LoginForm form);
    public boolean resetPassword(String userEmail);
    public boolean unlock(UnlockForm form);
}
