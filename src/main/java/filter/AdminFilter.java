package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "adminFilter",urlPatterns = "/admin/*")
public class AdminFilter implements Filter
{
    @Override
    public void init(FilterConfig fc) throws ServletException
    {
    }

    @Override
    public void destroy()
    {
    }

    @Override
    public void doFilter(ServletRequest req
            , ServletResponse resp
            , FilterChain chain)throws IOException, ServletException
    {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        HttpSession session = request.getSession();

        String uri = request.getRequestURI();

        if(uri.contains("login")){
            chain.doFilter(req, resp);
            return;
        }

        Object level=session.getAttribute("level");
        if (level==null|| Integer.parseInt(level.toString()) != 3) {

            // ?号后面的部分也得带上
            String query = request.getQueryString();
            if (query != null && query.length() > 0)
                uri += "?" + query;

            //MVC请求： 返回302重定向
            response.sendRedirect(request.getContextPath()
                    + "/admin/login");
            return;
        }


        chain.doFilter(req, resp);
    }


}
