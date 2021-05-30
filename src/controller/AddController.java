package controller;

import dao.DAO;
import dao.DAOImpl;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class AddController {

    private DAO dao = new DAOImpl();

    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField accessLvlField;
    @FXML
    private Button insertUserButton;

    @FXML
    private void insertUser() throws IOException, SQLException {
        Stage stage = (Stage) insertUserButton.getScene().getWindow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        dao.insertUserDao(loginField.getText(), passwordField.getText(), Long.parseLong(accessLvlField.getText()), timestamp);
        stage.close();
    }
}
