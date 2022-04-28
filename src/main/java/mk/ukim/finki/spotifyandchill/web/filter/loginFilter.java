package mk.ukim.finki.spotifyandchill.web.filter;


import mk.ukim.finki.spotifyandchill.model.User;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class loginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        User user = (User)request.getSession().getAttribute("user");

        String path = request.getServletPath();
        System.out.println("path: "+ path );

        if (!"/".equals(path) &&
                !"/css/style.css".equals(path) &&
                user==null &&
                !"/aboutUs".equals(path) &&
        !"/responseFromSpotify".equals(path)){
            response.sendRedirect("/");
        } else {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }

    @Override
    public void destroy() {

    }
}
