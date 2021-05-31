package connection;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectorMySQL {
    public Connection getConnection() throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream ("src\\resources\\database.properties")) {
            props.load(in);
        }
        String url = props.getProperty("url");
        String username = props.getProperty("username");
        String password = props.getProperty("password");

        try {
            Connection conn = DriverManager.getConnection(url,username,password);
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
