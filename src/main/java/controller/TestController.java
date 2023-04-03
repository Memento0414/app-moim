package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

@WebServlet("/test")
public class TestController extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 쿠키..? 브라우저에서 관리되는 것으로 데이터가 담긴 일종의 파일이라고 생각하면 된다.
		// 쿠키는 도메인값(쿠키 발행처)이 있는데
		// 브라우저는 웹요청을 보낼 때 발행처가 같다면 쿠키를 자동으로 전송하게 되어있다.
		// 백엔드쪽에서는 클라이언트가 보낸 쿠키들을 확인하는게 가능하다.

		Cookie[] cookies = req.getCookies(); // 가지고 오는 쿠키가 없다면 null
		// 사용자가 요청하는 것에서 쿠키를 뽑을 수 있음.

		if (cookies != null) {
			for (Cookie c : cookies) {
				System.out.println("Cookie ==> " + c.toString());

			}
		} else {
			System.out.println("=================");
			System.out.println("Not found cookie");
		}
		req.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(req, resp);
	}
}

//@Override
//protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//	//단방향 암호화
//	String message = "금요일 저녁은 뭘 먹어야 할까?";
//	// String pass = request.getParameter("pass");
//	
//	
//	for(int cnt =1; cnt<=5; cnt++) {
//		String hashed = BCrypt.hashpw(message, BCrypt.gensalt());
//		System.out.println(hashed);
//		
//		boolean f = message.equals(hashed);
//		System.out.println("String.equals = " + f);
//		
//		boolean ff =BCrypt.checkpw(message, hashed);
//		System.out.println("BCrypt.checkpw = " + ff);
//	}
//	
//	
//	
//}