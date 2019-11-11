package pl.rpgprojekt.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.rpgprojekt.dao.UserDao;
import pl.rpgprojekt.entities.User;

@Controller
@RequestMapping(produces = "text/html;charset=UTF-8")
public class HomeController {

    @Autowired
    private UserDao userDao;

    @GetMapping("/")
    public String home () {
        return "home";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm () {
        return "registerForm";
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister (@RequestParam String username,
                                   @RequestParam String password,
                                   BCryptPasswordEncoder bCryptPasswordEncoder) {
        User user = new User(username, bCryptPasswordEncoder.encode(password) );
        userDao.create(user);
        return "Udało się zarejestrować";

    }

}