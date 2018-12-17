package cz.muni.fi.mvc.security;

import org.slf4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

/**
 *
 * @author Augustin Nemec
 */

@WebFilter(urlPatterns = {"/item/*", "/category/*", "/user/*", "/location/*"})
public class ProtectFilter implements Filter {
    final static Logger log = org.slf4j.LoggerFactory.getLogger(ProtectFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader("WWW-Authenticate", "Basic realm=\"type email and password for admin user\"");
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> You are unauthorized for this page </body></html>");
    }

    private String[] parseAuthHeader(String auth) {
        return new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession().getAttribute("authenticated") == null) {
            response.sendRedirect("/pa165/");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
