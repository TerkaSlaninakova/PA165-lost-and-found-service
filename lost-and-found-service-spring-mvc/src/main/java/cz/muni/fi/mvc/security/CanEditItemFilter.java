package cz.muni.fi.mvc.security;

import cz.muni.fi.api.dto.UserDTO;
import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 *
 * @auhor Jakub Polacek
 */
//@WebFilter(urlPatterns = {"/item/edit/*"})
public class CanEditItemFilter implements Filter {

    final static Logger log = org.slf4j.LoggerFactory.getLogger(CanEditItemFilter.class);


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.sendRedirect("/pa165/adminOnly");
    }


    private String[] parseAuthHeader(String auth) {
        return new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        UserDTO user = (UserDTO) request.getSession().getAttribute("authenticated");

        String url = request.getRequestURL().toString();

        Long id = Long.valueOf(url.replaceAll("\\D+", "").substring(7));
        // ugly hack na 8080 a 165 z localhost a pa respectivelly

        // TODO: FIX


        filterChain.doFilter(request, response);

    }

    @Override
    public void destroy() {

    }
}
