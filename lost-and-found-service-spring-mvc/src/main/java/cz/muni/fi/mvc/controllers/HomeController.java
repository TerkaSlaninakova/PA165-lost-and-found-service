package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Augustin Nemec
 */

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private HttpSession session;

    @RequestMapping(value = {"/adminOnly"} ,method = RequestMethod.GET)
    public String droids(Model model) {

        return "/adminOnly";
    }


    @RequestMapping(value = {"", "/", "/home", "/home/*"} ,method = RequestMethod.GET)
    public String home(Model model) {
        UserDTO user = UserDTO.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            model.addAttribute("authenticatedUser", user.getEmail());
        }
        return "/home";
    }

}
