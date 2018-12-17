package cz.muni.fi.mvc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author Augustin Nemec
 */

@Controller
@RequestMapping("/logout")
public class LogoutController {
    @Autowired
    private HttpSession session;

    @RequestMapping(value="/logout", method= RequestMethod.GET)
    public String logout(RedirectAttributes redirect, HttpServletRequest request, HttpServletResponse response) {

        session.removeAttribute("authenticated");
        return "redirect:/";
    }
}
