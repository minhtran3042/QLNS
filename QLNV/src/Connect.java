import java.sql.Connection;// import thư viện 
import java.sql.DriverManager;

public class Connect {
public Connection getConnection() { //import thư viện Connection
	Connection conn = null;
	try {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//tên JDBC
		conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=JavaBTGK;","sa","123456"); //kết nối sql tên database tên user và mật khẩu
		if(conn != null) {
			System.out.println("ket noi thanh cong");//báo khi kết quả trả về khác null
		}
	} catch(Exception ex) {
		System.out.println(ex.toString()); //báo khi có lỗi
	}
	return conn;
}
}
