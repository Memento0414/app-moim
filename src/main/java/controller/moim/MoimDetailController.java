package controller.moim;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Attendance;
import data.Moim;
import data.Reply;
import data.User;
import repository.Attendances;
import repository.Moims;
import repository.Users;

@WebServlet("/moim/detail")
public class MoimDetailController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	//	req.setCharacterEncoding("utf-8");
		String id= req.getParameter("id"); // 아이디 파라미터로 뽑아오고
		String moimId = req.getParameter("moimId");
		Moim moim = Moims.findById(id);   // 모임에서 아이디 가져온다.

		if(moim == null) {                             
			resp.sendRedirect("/moim/search?error=1");
			return;
		}
		req.setAttribute("moim", moim);

		
		User manager = Users.findById(moim.getManagerId()); 
		req.setAttribute("manager", manager);
	
		List<Attendance> list = Attendances.findByMoimId(id); // Attendances에서 정보를 추출
		//==============================================================================
		//---모임 댓글 가져오기 처리
		SqlSessionFactory factory = (SqlSessionFactory)req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
		List<Reply> replys =sqlSession.selectList("replys.findByMoimId", id);
		sqlSession.close();
		
		
		req.setAttribute("replys", replys);
		//=============================================================================
	
		for(Attendance a : list) {
			User found = Users.findById(a.getUserId());
			a.setUserAvatarUrl(found.getAvatarUrl());
			a.setUserName(found.getName());
		}
		req.setAttribute("attendances", list);
				
		User logonUser = (User)req.getSession().getAttribute("logonUser");
		if(logonUser == null) {
			req.setAttribute("status", -1);
		}else {
			//===================
			int status = Attendances.findUserStatusInMoim(id, logonUser.getId());
			req.setAttribute("status", status);
		}
		
	
		
		// 뷰로 넘기는 작업은 패스
		
		req.getRequestDispatcher("/WEB-INF/views/moim/detail.jsp?id=" +moimId).forward(req, resp);
		
	}
}
