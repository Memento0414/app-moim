package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.Avatar;

public class Avatars {
	final static String user = "C##MOIM";
	final static String pass = "1q2w3e4r";
	final static String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	
	
	public static List<Avatar>findAll() {
		
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			
			String sql ="SELECT * FROM AVATARS";
			
			PreparedStatement ps = conn.prepareStatement(sql);
		
	
			ResultSet rs = ps.executeQuery();
			
			List<Avatar> list = new ArrayList<>();
			
			while(rs.next()) {
				Avatar one = new Avatar();
				
				one.setId(rs.getString("ID"));
				one.setUrl(rs.getString("URL"));
				
				list.add(one);
				
			}
			
			conn.close();
			
		return list;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
