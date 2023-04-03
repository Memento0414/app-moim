package controller.moim;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import data.Moim;
import data.User;
import repository.Attendances;
import repository.Moims;

@WebServlet("/moim/join-task")
public class MoimJoinTaskController extends HttpServlet{
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		SqlSessionFactory factory=(SqlSessionFactory)req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
		
		String target = req.getParameter("target");  //모임id
		//모임 id에 해당하는 정보를 읽어온 후 (mybatis 방식으로 처리)
		
		Moim found = sqlSession.selectOne("moims.findById", target);
		
		//max, current 값을 비교해서 참가할 데이터를 넣어도 되는지 아닌지 판단해야함
		if(found.getCurrentPerson() >= found.getMaxPerson()) {
			resp.sendRedirect("/moims/detail?id=" + target);
			return;
		}
		
		User logonUser= (User)req.getSession().getAttribute("logonUser");
		//참석정보를 등록 시켜도 된다면... 이 모임 정보의 타입을 확인해서 private이면 status 1 로 등록 아니면 2로 등록
		//mybatis로 처리
		
		Map<String, Object> params = new HashMap<>();
		params.put("moimId", target); //모임아이디
		params.put("userId", logonUser.getId()); //userid
		params.put("status", found.getType().equals("public") ? 2 : 1);
	
		int r = sqlSession.insert("attendances.create", params);
		
		//public인 경우에는 이 해당모임의  current 값을 증가 // mybatis처리
		if(found.getType().equals("public")) {
			sqlSession.update("moims.updateCurrentPerson", target);
		}
		sqlSession.commit();
		sqlSession.close();
		
		resp.sendRedirect("/moims/detail?id=" + target);
		return;
		
		
		
		//처리 결과에 따라서 적절하게 응답할 수 있도록 유도
		
		
		
		
		
	}	

}
