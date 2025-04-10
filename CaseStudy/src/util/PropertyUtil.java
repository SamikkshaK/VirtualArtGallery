package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtil {

    public static String getConnectionString(String fileName) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(fileName);
        props.load(fis);

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        return url + "?user=" + user + "&password=" + password;
    }

    public static String getDriverClass(String fileName) throws IOException {
        Properties props = new Properties();
        FileInputStream fis = new FileInputStream(fileName);
        props.load(fis);
        return props.getProperty("db.driver");
    }
}
