package listener;

import java.io.InputStream;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
@WebListener
public class MoimInitializer implements ServletContextListener{
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			//이전 방식의 DAO에서 필요한 코드니까 살려두고.....
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("[SERVER] ojdbc library load success");
		//============================================================================================
			//mybatis 선언부분
			String resource = "mybatis/config.xml"; //mybatis 경로
			
			InputStream inputStream = Resources.getResourceAsStream(resource);
			
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			
			System.out.println(sqlSessionFactory);
			
			sce.getServletContext().setAttribute("sqlSessionFactory", sqlSessionFactory); 
    	//============================================================================================	
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("[SERVER] ojdbc library load failed");
			System.exit(0);
		}
		//오타나 라이브러리가 제대로 지정 안되면 톰캣은 실행되지 않는다.
	}
}
