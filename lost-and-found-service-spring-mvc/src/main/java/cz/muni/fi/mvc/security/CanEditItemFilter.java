package cz.muni.fi.mvc.security;

import cz.muni.fi.api.facade.ItemFacade;
import cz.muni.fi.persistence.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;

@WebFilter(urlPatterns = {"/item/edit/*"})
public class CanEditItemFilter implements Filter {

    final static Logger log = org.slf4j.LoggerFactory.getLogger(CanEditItemFilter.class);

    @Autowired
    private ItemFacade itemFacade;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    private void response401(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("<html><body><h1>401 Unauthorized</h1> You are unauthorized for this page </body></html>");
    }

    private void response404(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().println("<html><body><h1>404 Item not found</h1> Given item doesn't exist </body></html>");
    }


    private String[] parseAuthHeader(String auth) {
        return new String(DatatypeConverter.parseBase64Binary(auth.split(" ")[1])).split(":", 2);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User) request.getSession().getAttribute("authenticated");

        String url = request.getRequestURL().toString();

        Long id = Long.valueOf(url.replaceAll("\\D+", "").substring(7));
        // ugly hack na 8080 a 165 z localhost a pa respectivelly



        // TODO: FIX

        log.debug(url);
        log.debug(id.toString());

        /*ItemDTO item = itemFacade.getItemById(id    );
        if (item == null) {
            response404(response);
            return;
        }

        log.debug(item.getOwner().toString());
        */

        //Boolean owner = Objects.equals(item.getOwner().getId(), user.getId());

        if (user.getIsAdmin()) { // change from admin to normal alter
            filterChain.doFilter(request, response);
        } else {
            response401(response);
        }
    }

    @Override
    public void destroy() {

    }
}
