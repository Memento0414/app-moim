package filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.User;

@WebFilter({"/moim/create","/moim/join-task"})//하나 일때
//@WebFilter("{대상주소1, 대상주소2}") 2개 일 때 중괄호를 사용한다.
public class Authfilter extends HttpFilter{
	
	@Override
	protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		System.out.println("doFilter");
		String url = request.getRequestURI();
		System.out.println("doFilter =" +url);
		
		//if문을 걸어서 현재 요청을 보낸 사용자의 세션에 logon이 필터 동과 그게 아니면 
		//flow.jsp로 redirect 시키는 filter를 만들어 보자.
		
		
		boolean logon = (boolean)request.getSession().getAttribute("logon");
		User logonUser = (User)request.getSession().getAttribute("logonUser");

		if(logon && logonUser != null) {
			
			chain.doFilter(request, response); // 해당 필터를 통과 시키면서 다음 필터까지 도달할 수 있게 끔한다.
			
		} else {
			
			response.sendRedirect("/user/login?url="+url);
		}
		
		
		//필터의 역할은 요청하는 것에 대해 검증해주는 것을 
	}
}
