package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.facade.UserFacade;
import cz.muni.fi.persistence.entity.User;
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
@RequestMapping("/home")
public class HomeController {
    @Autowired
    UserFacade userFacade;

    @Autowired
    HttpSession session;

    @RequestMapping(method = RequestMethod.GET)
    public String home(Model model) {
        User user = User.class.cast(session.getAttribute("authenticated"));
        if (user != null) {
            model.addAttribute("authenticatedUser", user.getEmail());
        }
        return "/home";
    }

}
