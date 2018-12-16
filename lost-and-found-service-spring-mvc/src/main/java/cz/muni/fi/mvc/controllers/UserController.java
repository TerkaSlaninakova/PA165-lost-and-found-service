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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserFacade userFacade;

    @Autowired
    private HttpSession session;

    @RequestMapping(value = {"", "/", "/all", "list"}, method = RequestMethod.GET)
    public String list(Model model) {

        User loggedUser = User.class.cast(session.getAttribute("authenticated"));
        if (loggedUser != null) {
            model.addAttribute("authenticatedUser", loggedUser.getEmail());
        }

        model.addAttribute("users", userFacade.getAllUsers());
        return "user/list";
    }
}
