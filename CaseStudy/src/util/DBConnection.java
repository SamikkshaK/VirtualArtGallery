package util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String fileName = "src/db.properties";

    public static Connection getDbConnection() {
        Connection con = null;
        String connString=null;

        try {
            connString = PropertyUtil.getConnectionString(fileName);
        } catch (IOException e) {
            System.out.println("Could not load DB properties file.");
            e.printStackTrace();
        }
        if(connString!=null) {
        	try {
        		con=DriverManager.getConnection(connString);
        } 
        	catch (SQLException e) {
            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
        }

        return con;
    }
}
