package controller;

import dao.DAO;
import dao.DAOImpl;
import entity.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

@Setter
public class SecondController {

    private DAO dao = new DAOImpl();

    @FXML
    private TextField idField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField accessLvlField;
    @FXML
    private TextField dateOfCreation;
    @FXML
    private TextField dateOfModification;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;


    @FXML
    private void insertOrUpdateUser() throws IOException, SQLException {
        Stage stage = (Stage) okButton.getScene().getWindow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (idField.getText().isEmpty()) {
            dao.insertUserDao(loginField.getText(), passwordField.getText(), Long.parseLong(accessLvlField.getText()), timestamp);
        } else {
            dao.updateUserDao(Long.parseLong(idField.getText()), loginField.getText(), passwordField.getText(), Long.parseLong(accessLvlField.getText()), timestamp);
        }

        stage.close();
    }


    public void preloadData(User user) {
        idField.setText(String.valueOf(user.getId()));
        loginField.setText(user.getLogin());
        passwordField.setText(user.getPassword());
        accessLvlField.setText(String.valueOf(user.getAccessLvl()));
        dateOfCreation.setText(String.valueOf(user.getDateOfCreation()));
        dateOfModification.setText(String.valueOf(user.getDateOfModification()));
    }

    @FXML
    private void cancelButton() throws IOException, SQLException {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
