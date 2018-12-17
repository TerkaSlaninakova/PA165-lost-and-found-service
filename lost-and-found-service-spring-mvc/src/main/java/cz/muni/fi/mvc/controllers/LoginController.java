package cz.muni.fi.mvc.controllers;

import cz.muni.fi.api.dto.ItemDTO;
import cz.muni.fi.api.dto.UserDTO;
import cz.muni.fi.api.facade.UserFacade;
import cz.muni.fi.persistence.entity.User;
import cz.muni.fi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpSession;

/**
 *
 * @author Augustin Nemec
 */

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private HttpSession session;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String list(Model model) {
        return "/login";
    }

    @RequestMapping(value="/check", method= RequestMethod.POST)
    public String Check(Model model,
                        @RequestParam("email") String email,
                        @RequestParam("password") String password,
                        RedirectAttributes redirectAttributes,
                        UriComponentsBuilder uriBuilder
                        ) {
        User user = userService.getUsersByEmail(email);

        if (user == null) {
            redirectAttributes.addFlashAttribute(
                    "alert_warning",
                    "Incorrect e-mail or password.");
            return "redirect:" + uriBuilder.path("/login").build().toUriString();
        }

        else if (userService.authenticate(user, password)) {
            model.addAttribute("authenticatedUser", user.getEmail());
            session.setAttribute("authenticated", user);

            redirectAttributes.addFlashAttribute(
                    "alert_success",
                    "Successful login.");
            return "redirect:" + uriBuilder.path("/home").build().toUriString();
        }
        else {
            redirectAttributes.addFlashAttribute(
                    "alert_danger",
                    "Unknown error while logging in.");
            return "redirect:" + uriBuilder.path("/login").build().toUriString();
        }

    }
}
