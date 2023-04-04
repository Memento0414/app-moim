package repository;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import data.Moim;

public class Moims {
	final static String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	final static String user = "C##MOIM";
	final static String password = "1q2w3e4r";

	public static int create(String id, String managerId, String event, String type, String cate, String description,
			int maxPerson, String beignDate, String endDate) {

		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO MOIMS VALUES(?,?,?,?,?,?,?,1, TO_DATE(?, 'YYYY-MM-DD HH24:MI'), TO_DATE(?, 'YYYY-MM-DD HH24:MI'), NULL)";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id); // uuid를 subString 해서 받아 올 거임
			ps.setString(2, managerId); // session으로 받아올거임
			ps.setString(3, event);
			ps.setString(4, type);
			ps.setString(5, cate);
			ps.setString(6, description);
			ps.setInt(7, maxPerson);
			ps.setString(8, beignDate);
			ps.setString(9, endDate);

			int r = ps.executeUpdate();

			conn.close();

			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static List<Moim> findLastest() {
		// 모임 기간이 임박한 모임 3개를 보여주는 메서드
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT * FROM(SELECT * FROM MOIMS WHERE BEGIN_DATE > SYSDATE ORDER BY BEGIN_DATE - SYSDATE) WHERE ROWNUM <= 3";

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			List<Moim> list = new ArrayList<>();

			while (rs.next()) {
				Moim moim = new Moim();
				moim.setId(rs.getString("ID"));
				moim.setManagerId(rs.getString("MANAGER_ID"));
				moim.setEvent(rs.getString("EVENT"));
				moim.setType(rs.getString("TYPE"));
				moim.setCate(rs.getString("CATE"));
				moim.setDescription(rs.getString("DESCRIPTION"));
				moim.setMaxPerson(rs.getInt("MAX_PERSON"));
				moim.setCurrentPerson(rs.getInt("CURRENT_PERSON"));
				moim.setBeginDate(rs.getDate("BEGIN_DATE"));
				moim.setEndDate(rs.getDate("END_DATE"));
				moim.setFinalCost(rs.getInt("FINAL_COST"));
				list.add(moim);
			}

			conn.close();
			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Moim findById(String id) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT MOIMS.*, USERS.NAME AS MANAGER_NAME , AVATARS.URL AS MANAGER_URL " 
					+ "FROM MOIMS "
					+ "JOIN USERS ON MOIMS.MANAGER_ID = USERS.ID " + "JOIN AVATARS ON AVATARS.ID = USERS.AVATAR_ID "
					+ "WHERE MOIMS.ID = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();
			Moim moim = new Moim();
			if (rs.next()) {
				
				moim.setId(rs.getString("ID"));
				moim.setManagerId(rs.getString("MANAGER_ID"));
				moim.setEvent(rs.getString("EVENT"));
				moim.setCate(rs.getString("CATE"));
				moim.setType(rs.getString("TYPE"));
				moim.setDescription(rs.getString("DESCRIPTION"));
				moim.setMaxPerson(rs.getInt("MAX_PERSON"));
				moim.setCurrentPerson(rs.getInt("CURRENT_PERSON"));
				moim.setBeginDate(rs.getDate("BEGIN_DATE"));
				moim.setEndDate(rs.getDate("END_DATE"));
				moim.setFinalCost(rs.getInt("FINAL_COST"));

				moim.setManagerName(rs.getString("MANAGER_NAME"));
				moim.setManagerAvatarUrl(rs.getString("MANAGER_URL"));
			}
			conn.close();
			
			return moim;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Moim> cateSearch(String[] cates) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT * FROM MOIMS WHERE BEGIN_DATE > SYSDATE ";

			if (cates == null) {
				sql += " ORDER BY BEGIN_DATE";
			} else {
				switch (cates.length) {
				case 1 -> sql += " AND CATE IN (?)";
				case 2 -> sql += " AND CATE IN (?, ?)";
				case 3 -> sql += " AND CATE IN (?, ?, ?)";
				case 4 -> sql += " AND CATE IN (?, ?, ?, ?)";
				case 5 -> sql += " AND CATE IN (?, ?, ?, ?, ?)";
				case 6 -> sql += " AND CATE IN (?, ?, ?, ?, ?, ?)";
				case 7 -> sql += " AND CATE IN (?, ?, ?, ?, ?, ?, ?)";
				}
				sql += " ORDER BY BEGIN_DATE";
			}
			PreparedStatement ps = conn.prepareStatement(sql);
			
			if (cates != null) {
				for (int i = 0; i < cates.length; i++) {
					ps.setString(i + 1, cates[i]);
				}
			}

			ResultSet rs = ps.executeQuery();
			List<Moim> list = new LinkedList<>();
			
			while (rs.next()) {
				Moim moim = new Moim();
				moim.setId(rs.getString("ID"));
				moim.setManagerId(rs.getString("MANAGER_ID"));
				moim.setEvent(rs.getString("EVENT"));
				moim.setCate(rs.getString("CATE"));
				moim.setType(rs.getString("TYPE"));
				moim.setDescription(rs.getString("DESCRIPTION"));
				moim.setMaxPerson(rs.getInt("MAX_PERSON"));
				moim.setCurrentPerson(rs.getInt("CURRENT_PERSON"));
				moim.setBeginDate(rs.getDate("BEGIN_DATE"));
				moim.setEndDate(rs.getDate("END_DATE"));
				moim.setFinalCost(rs.getInt("FINAL_COST"));

				list.add(moim);
			}
			conn.close();

			return list;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}
		public static int attendPerson(String id) {
			try {
				Connection conn = DriverManager.getConnection(url, user, password);

				String sql = "UPDATE MOIMS SET CURRENT_PERSON = CURRENT_PERSON + 1 WHERE ID =?";
				
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, id);
				
				int r = ps.executeUpdate();
				
				conn.close();
				
				return r;
				
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
}
