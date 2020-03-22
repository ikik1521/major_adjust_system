package cn.scuec.major_adjust_system.interceptor;

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
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object arg2) throws Exception {
		
		
		HttpSession session=httpServletRequest.getSession();
		String homeUrl = httpServletRequest.getContextPath();
		//String homeUrl = "";
		
		if (session.getAttribute("USER_INFO") == null) {
           
         	// ����� ajax ���������� session ״̬ ��CONTEXTPATH ��·��ֵ
         	// �����ajax������Ӧͷ���У�x-requested-with
            if (httpServletRequest.getHeader("x-requested-with") != null && httpServletRequest.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
                httpServletResponse.setHeader("sessionstatus", "TIMEOUT");
                
                //����nginxʱҪɾ�� homeUrl
                httpServletResponse.setHeader("CONTEXTPATH", homeUrl+"/index.html");
                // FORBIDDEN��forbidden��Ҳ���ǽ�ֹ��403
                httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN); 
                return false;
            }else{
                // ������� ajax ������ֱ����ת����
                httpServletResponse.sendRedirect(homeUrl+"/index.html");
               
            }
            return false;
        }
        return true;
	}

	//����������ajax�����ض�������Ĵ�����
    public void redirect(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //��ȡ��ǰ�����·��
        String basePath = request.getScheme() + "://" + request.getServerName() + ":"  + request.getServerPort()+request.getContextPath();
        //String basePath = "";
        //���request.getHeader("X-Requested-With") ���ص���"XMLHttpRequest"˵������ajax������Ҫ���⴦�� ����ֱ���ض���Ϳ�����
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            //����ajax�����ض���
            response.setHeader("REDIRECT", "REDIRECT");
            //����ajax���ض����·��,����nginxʱҪɾ�� homeUrl
            response.setHeader("CONTENTPATH", basePath+"/index.html");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }else{
            response.sendRedirect( basePath+"/index.html");
        }
    }
}
