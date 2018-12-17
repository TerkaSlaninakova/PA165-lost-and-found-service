package cz.muni.fi.mvc.security;

import cz.muni.fi.persistence.entity.User;
import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/category/edit/*", "/category/delete/*", "/category/new/*", "/category/create/*"})
public class CanManageCategoriesFilter implements Filter {

    final static Logger log = org.slf4j.LoggerFactory.getLogger(CanManageCategoriesFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> You are unauthorized for this page </body></html>");
    }

    private String[] parseAuthHeader(String auth) {
        return new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute("authenticated");

        if (user.getIsAdmin()) { // change from admin to normal alter
            filterChain.doFilter(request, response);
        } else {
            response401(response);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
