package filter;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@WebFilter(filterName = "Filter01",urlPatterns = "/*"/*该过滤器适用于所有资源*/)
public class Filter01 implements Filter {
  private Set<String> exclude=new HashSet<>();
  @Override
  public void init(FilterConfig filterConfig) throws ServletException {

  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    exclude.add("/login");
    HttpServletRequest request =(HttpServletRequest)req;

    String path=request.getRequestURI();
    if (exclude.contains(path)){
    }else {
      HttpSession session=request.getSession(false);
      JSONObject message=new JSONObject();
      if (session==null||session.getAttribute("currentUser")==null){
        message.put("message","您没有登录，请登录");
        res.getWriter().println(message);
        return;
      }
    }

  }

  @Override
  public void destroy() {

  }
}
