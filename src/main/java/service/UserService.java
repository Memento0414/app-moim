package service;

public class UserService {
	public static boolean volume(String id, String pass, String name) {
		
		//로그인 과정에서 아이디, 비번, 이름 등의 제약 조건을 설정
		if(!id.matches("[a-z][a-z0-9]+")) {
			return false;
		} 
		if(pass.length() < 4) {
			return false;
		}
		if(name.length()< 2) {
			return false;
		}
		return true;
	}
}
