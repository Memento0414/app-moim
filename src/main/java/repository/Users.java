package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.mindrot.jbcrypt.BCrypt;

import data.User;

public class Users {
	final static String url = "jdbc:oracle:thin:@192.168.4.22:1521:xe";
	final static String user = "C##MOIM";
	final static String password = "1q2w3e4r";

	public static int create(String id, String pass, String name, String avatar_Id) {

		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "INSERT INTO USERS VALUES(?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			String hashed = BCrypt.hashpw(pass, BCrypt.gensalt()); // 패스워드를 암호화하고 세팅
			ps.setString(2, hashed);
			ps.setString(3, name);
			ps.setString(4, avatar_Id);

			int r = ps.executeUpdate();

			conn.close();

			return r;

		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	public static User findById(String id) {
		try {
			Connection conn = DriverManager.getConnection(url, user, password);

			String sql = "SELECT USERS.*, AVATARS.URL AS AVATAR_URL FROM USERS JOIN AVATARS ON USERS.AVATAR_ID = AVATARS.ID WHERE USERS.ID=?";

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, id);

			ResultSet rs = ps.executeQuery();

			User one = null;

			if (rs.next()) {
				one = new User();
				one.setId(rs.getString("id"));
				one.setPass(rs.getString("pass"));
				one.setName(rs.getString("name"));
				one.setAvatarId(rs.getString("avatar_Id"));
				one.setAvatarUrl(rs.getString("avatar_Url"));
			}

			conn.close();
			return one;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
