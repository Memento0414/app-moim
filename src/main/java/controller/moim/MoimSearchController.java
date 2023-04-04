package controller.moim;

import java.io.IOException;
import java.util.Arrays;
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

import data.Moim;
import repository.Moims;

@WebServlet("/moim/search")
public class MoimSearchController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] cates = req.getParameterValues("cate");
	//	System.out.println(Arrays.toString(cates));
		
		//=====사용자가 페이지 값을 하지 않을때 default을 처리.============= 
		int p;
		if(req.getParameter("page") ==null) {
			p = 1;
		} else {
			p = Integer.parseInt(req.getParameter("page"));
		}
		
		
		SqlSessionFactory factory = (SqlSessionFactory)req.getServletContext().getAttribute("sqlSessionFactory");
		SqlSession sqlSession = factory.openSession();
		Map<String, Object> map = new HashMap<>();
		map.put("a", p*6-5);
		map.put("b", 6*p);
		map.put("arr", cates);
		
//		List<Moim> list = sqlSession.selectList("moims.findSomeByCates", cates);
		List<Moim> list = sqlSession.selectList("moims.findSomebyAtoBInCates", map);
		//List<Moim> list = Moims.findByCate(cates);
		
		int total = sqlSession.selectOne("moims.countDatas", cates);
		sqlSession.close();
		
		req.setAttribute("list", list);
		
		int lastPage = total / 6 + (total % 6 > 0 ? 1 : 0);
		
		
		int last = (int)Math.ceil(p/5.0)*5;	
		int start = last -4;
		
		last = last > lastPage ? lastPage : last;
		
		req.setAttribute("start", start);
		req.setAttribute("last", last);
		
	
		boolean existPrev = p >= 6;
		boolean existNext = lastPage > last;
		req.setAttribute("existPrev", existPrev);
//		System.out.println(exsitPrev);
		req.setAttribute("existNext", existNext);
//		System.out.println(exsitNext);
		
	    
		
		req.getRequestDispatcher("/WEB-INF/views/moim/search.jsp").forward(req, resp);
		
	}
}
