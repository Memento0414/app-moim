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
import repository.Users;

@WebServlet("/moim/add-reply-task")
public class MoimAddReplyTaskController extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		//받아야와야 하는건 모임아이디, 멘트
		String moimId = req.getParameter("moimId"); //모임 아이디
		String ment = req.getParameter("ment");     // 작성내용
		User writer= (User)req.getSession().getAttribute("logonUser"); //유저 아이디
		
		SqlSessionFactory factory =(SqlSessionFactory)req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
	//	Reply replyId = sqlSession.selectOne("replys.findByMiomId", moimId);
		
		
		
			//로그인 했을 때 맵으로 생성하여 자료를 가져온다.
			Map<String, Object> param = new HashMap<>(); 
			param.put("writer", writer.getId());
			param.put("ment", ment);
			param.put("moimId", moimId);
			System.out.println(moimId);
			
			int r = sqlSession.insert("replys.create", param);
		//=================================================
//			Reply reply = new Reply();
//			reply.setWriter(writer.getId());
//			reply.setId(ment);
//			reply.setMoimId(moimId);

			
			sqlSession.commit();
			sqlSession.close();
			
	
			
			resp.sendRedirect("/moim/detail?id= " + moimId);
	
	}
	
		
	
}
