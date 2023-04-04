package controller.moim;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Reply;
import data.User;

@WebServlet("/views/reply")
public class ReplyController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
				//받아야와야 하는건 모임아이디, 멘트
			String moimId = req.getParameter("moimId"); //모임 아이디
			String ment = req.getParameter("ment");     // 작성내용
			User logonUser= (User)req.getSession().getAttribute("logonUser"); //유저 아이디
			
			SqlSessionFactory factory =(SqlSessionFactory)req.getServletContext().getAttribute("sqlSessionFactory");
			SqlSession sqlSession = factory.openSession(true);
			Reply replyId = sqlSession.selectOne("replys.findByMiomId", moimId);
			
			
			if(logonUser == null) {
				req.setAttribute("status", -1); // 로그인 하게 유도
			} else {
				//로그인 했을 때 맵으로 생성하여 자료를 가져온다.
				Map<String, Object> param = new HashMap<>(); 
				param.put("moimId", moimId);
				param.put("ment", ment);
				param.put("userId", logonUser.getId());
				
				int r = sqlSession.insert("replys.create", param);
			}
	
				sqlSession.close();
				
				return;
		
	}
}
