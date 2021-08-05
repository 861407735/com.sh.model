package com.sh.model.ssologin;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.sh.model.common.HttpUtil;
import com.sh.model.ssologin.UserInfoResult.UserInfo;

@Component
public class AuthHttpCheck {

	@Autowired
	HttpUtil httpUtil;

//	public UserInfo getUserInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		Cookie[] cookies = request.getCookies();
//		// 判断是否已经登陆已经登陆
//		if (cookies != null && cookies.length > 0) {
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals("ssoTicket") && cookie.getValue() != null) {
//					String url = SsoConfig.casserver + "/jzsso/ticket/userInfo/" + cookie.getValue() + "/" + SsoConfig.serverip+ "/" + request.getRemoteHost() + "/" + SsoConfig.appname;
//					String result = httpUtil.httpGetRequest(url);
//					UserInfoResult portInfo = JSONObject.parseObject(result, UserInfoResult.class);  //result 解析为一个Json对象，类型是UserInfoResult.class
//					if (portInfo.getCode().equals("12")) {
//						UserInfo userInfoBean = portInfo.getUserInfo();
//						return userInfoBean;
//					}
//				}
//			}
//		}
//		//通过ticket获取
//		String url = SsoConfig.casserver+"/jzsso/ticket/findTicket/" + SsoConfig.serverip + "/"+ request.getRemoteHost() + "/" + SsoConfig.appname;
//		String result = httpUtil.httpGetRequest(url);
//		Ticket ticket = JSONObject.parseObject(result, Ticket.class); //获取ticket
//		if (ticket != null && ticket.getCode().equals("07")) {
//			String url1 = SsoConfig.casserver + "/jzsso/ticket/userInfo/" + ticket.getSsoTicket() + "/" + SsoConfig.serverip+ "/" + request.getRemoteHost() + "/" + SsoConfig.appname;
//			String result1 = httpUtil.httpGetRequest(url1);
//			UserInfoResult portInfo = JSONObject.parseObject(result1, UserInfoResult.class);
//			if (portInfo.getCode().equals("12")) {
//				UserInfo userInfoBean = portInfo.getUserInfo();
//				Cookie cook=new Cookie("ssoTicket", ticket.getSsoTicket());
//				response.addCookie(cook);
//				return userInfoBean;
//			}
//		}
//		return null;
//	}
}
