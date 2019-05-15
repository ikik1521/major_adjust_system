package cn.sucec.major_adjust_system.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor{

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method  stub
		
	}
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		
		HttpSession session=arg0.getSession();
		if(session.getAttribute("USER_INFO") != null){
			return true;
		}else {
			
			redirect(arg0, arg1); 
            return false;
		}
		
	}

	//����������ajax�����ض�������Ĵ�����
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //��ȡ��ǰ�����·��
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
        //���request.getHeader("X-Requested-With") ���ص���"XMLHttpRequest"˵������ajax������Ҫ���⴦�� ����ֱ���ض���Ϳ�����
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            //����ajax�����ض���
            response.setHeader("REDIRECT", "REDIRECT");
            //����ajax���ض����·��
            response.setHeader("CONTENTPATH", basePath+"/login.html");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else{
            response.sendRedirect(basePath + "/login.html");
        }
    }
}
