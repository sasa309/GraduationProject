package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	 static String loginUserDataFilePath = System.getProperty("user.dir")+"\\src\\main\\java\\Properties\\loginUserData.properties";
	 static public Properties userLoginData = LoadJavaProperties(loginUserDataFilePath);
	 
	 static String registerUserDataFilePath = System.getProperty("user.dir")+"\\src\\main\\java\\Properties\\registerUserData.properties";
	 static public Properties userRegisterData = LoadJavaProperties(registerUserDataFilePath);
	
	private static Properties LoadJavaProperties(String filePath) {
		Properties pro = new Properties();
		
		try {
			FileInputStream stream = new FileInputStream(filePath);
			try {
				pro.load(stream);
			} catch (IOException e) {
				System.out.println("Error Occupied "+e.getMessage());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error Occupied "+e.getMessage());
		}
		
		return pro;
	}
}
