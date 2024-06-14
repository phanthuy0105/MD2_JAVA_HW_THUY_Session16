package ra.learn_session16.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    //Tạo kết nối và đóng kết nối
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_demo_db";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "Pooh241120@@";

    //Phương thức tạo đối tượng Connection kết nối từ ứng dụng Java đến CSDL MySQL
    public static Connection openConnection() {
        Connection conn = null;
        try {
            //1. Set Driver cho DriverManager để biết kết nối đến CSDL nào (MySQL)
            Class.forName(DRIVER);
            //2. Tạo đối tượng connection từ DriverManager
            conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }

    //Phương thức đóng kết nối (conn) và đối tượng CallableStatement
    public static void closeConnection(Connection conn, CallableStatement callSt){
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (callSt!=null){
            try {
                callSt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}