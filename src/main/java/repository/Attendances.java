package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import data.Attendance;
import data.Avatar;

public class Attendances {
	final static String user = "C##MOIM";
	final static String pass = "1q2w3e4r";
	final static String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";

	public static List<Attendance>findByMoimId(String moimId) {
		
		
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			
			String sql ="SELECT * FROM ATTENDANCES WHERE MOIM_ID=?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, moimId);
	
			ResultSet rs = ps.executeQuery();
			
			List<Attendance> list = new ArrayList<>();
			
			while(rs.next()) {
				Attendance a = new Attendance();
				a.setId(rs.getInt("ID"));
				a.setMoimId(rs.getString("MOIM_ID"));
				a.setUserId(rs.getString("USER_ID"));
				a.setStatus(rs.getInt("STATUS"));
		
				
				list.add(a);
				
			}
			
			conn.close();
			
		return list;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}

	public static int joinDao (String moimId, String userId, int status) {
		
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			
			String sql = "INSERT INTO ATTENDANCES VALUES(ATTENDANCES_SEQ.NEXTVAL, ?, ? , ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, moimId);
			ps.setInt(3, status);
			
			
			int r = ps.executeUpdate();
			
			conn.close();
			
			return r;
			
		}catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		
	}
	
	//모임이 참여한지 여부를 확인하는 메서드 
	
	public static int findUserStatusInMoim(String moimId, String userId) {
		
		try {
			Connection conn = DriverManager.getConnection(url, user, pass);
			
			String sql ="SELECT STATUS FROM ATTENDANCES WHERE MOIM_ID=? AND USER_ID= ?";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, moimId);
			ps.setString(2, userId);
			
			ResultSet rs = ps.executeQuery();
			
			int status;
			
			if(rs.next()) {
		
				status= rs.getInt("STATUS");
	
			} else {
				status = 0;
			}
			return status;
			
		} catch (Exception e) {
			e.printStackTrace();
			return -9;
		}
	}
	
}
