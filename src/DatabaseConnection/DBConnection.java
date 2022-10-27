package DatabaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection connection;
    private static String JDBC_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/library";

    public static void connect() {
        try {
            Class.forName(JDBC_Driver);
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            System.out.println("Successfully connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
