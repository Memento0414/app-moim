package controller.moim;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.Moim;
import repository.Moims;

@WebServlet("/moim/search")
public class MoimSearchController extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String[] cates = req.getParameterValues("cate");
		System.out.println(Arrays.toString(cates));
		
		List<Moim> list = Moims.cateSearch(cates);
		req.setAttribute("list", list);
		
		
		req.getRequestDispatcher("/WEB-INF/views/moim/search.jsp").forward(req, resp);
		
	}
}
