package org.com.drag.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.com.drag.common.util.Constants;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by cdyoue on 2016/7/11.
 */
public class LoginInterceptor implements HandlerInterceptor {
    // 排除字符串数组
    private String[] excludeUrls;
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        //HttpSession session = request.getSession();
        Subject currentUser = SecurityUtils.getSubject(); 
        Session session = currentUser.getSession();
        // 设置编码
        response.setContentType("text/html;charset=UTF-8");
        // 获取请求路径
        String requestUrl = request.getRequestURI();
        String urlString = requestUrl.substring(requestUrl.lastIndexOf("/"));
        // 匹配放行url
        for (int i = 0; excludeUrls != null && i < excludeUrls.length; i++) {
            if (urlString.equals(excludeUrls[i])) {
                return true;
            }
        }
        if(session.getAttribute(Constants.USER_KEY) == null) {
            String contextPath = request.getContextPath();
            String path = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath+"/";
            String XRequested =request.getHeader("X-Requested-With");
            if("XMLHttpRequest".equals(XRequested)){
                response.getWriter().write("AjaxSessionTimeout");
            }else{
                response.sendRedirect(path);
            }
            return false;
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

}
