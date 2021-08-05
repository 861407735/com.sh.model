package com.sh.model.ssologin;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ship.web.identify.authconfig.AuthAdvice;
import org.springframework.stereotype.Component;

import com.sh.model.ssologin.UserInfoResult.UserInfo;

@Component
public class SessionFilter implements Filter {

	@Autowired
	AuthHttpCheck authCheck;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		HttpServletRequest request1=(HttpServletRequest) request;
		HttpServletResponse response1=(HttpServletResponse) response;
		HttpSession session=request1.getSession();
		session.setAttribute(AuthAdvice.USERId,"1");
		session.setAttribute(AuthAdvice.NICKNAME,"李果");
//		if(session.getAttribute(AuthAdvice.USERId)==null) {
//			UserInfo userinfo=authCheck.getUserInfo(request1, response1);
//			if(userinfo == null) {
//				response1.sendRedirect(SsoConfig.casserver+"/jzsso/login.jsp?appServerIP=38.80.34.16&appName=gzzrxgj&casRedirectUrl=http://38.80.34.16:9999/publish/5ee48d645e26e60f5ef8739a");
//				return;
//			} else {
//				session.setAttribute(AuthAdvice.USERId, userinfo.getPcNumber());
//				session.setAttribute(AuthAdvice.NICKNAME, userinfo.getRealName());
//				chain.doFilter(request1, response1);
//			}
//		}
		chain.doFilter(request1, response1);
	}
}	