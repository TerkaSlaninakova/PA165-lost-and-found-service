package cz.muni.fi.mvc.security;

import cz.muni.fi.api.dto.UserDTO;
import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/user/*"})
public class CanAccessUsersFilter implements Filter {

    final static Logger log = org.slf4j.LoggerFactory.getLogger(CanAccessUsersFilter.class);

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

        UserDTO user = (UserDTO) request.getSession().getAttribute("authenticated");

        if (user.getIsAdmin()) {
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
