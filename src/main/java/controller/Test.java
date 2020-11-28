package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import utility.User;
import utility.spring.Bean;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Test {
    @RequestMapping("/login")
    public String login(Model model)
    {
        User user= (User)Bean.getBean("beans.xml","user",User.class);
        System.out.println(user);
        return "com";
    }



}
