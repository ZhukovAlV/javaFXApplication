package controller;

import connection.ConnectorMySQL;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class AddController {

    ConnectorMySQL connectorMySQL = new ConnectorMySQL();

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField accessLvlField;

    @FXML
    private void insertUser() throws IOException, SQLException {
        String query =
                "insert into user(login,password,accesLvl) values("
                + "'" + loginField.getText() + "','" + passwordField.getText() + "',"
                + accessLvlField.getText()+")";
        executeQuery(query);
        //connectorMySQL.getConnection().close();
    }

    public void executeQuery(String query) throws IOException {
        Connection conn = connectorMySQL.getConnection();
        try (Statement st = conn.createStatement();) {
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
