package controller;

import dao.DAO;
import dao.DAOImpl;
import entity.AccessLevel;
import entity.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Setter
public class SecondController implements Initializable {

    private DAO dao = new DAOImpl();

    @FXML
    private TextField idField;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passwordField;;
    @FXML
    private ComboBox<AccessLevel> accessLvlField;
    @FXML
    private TextField dateOfCreation;
    @FXML
    private TextField dateOfModification;
    @FXML
    private Button okButton;
    @FXML
    private Button cancelButton;

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        comboBoxAccessLvl();
    }

    public void comboBoxAccessLvl() throws IOException {
        ObservableList<AccessLevel> list = dao.getAccessLevelListDao();
        accessLvlField.setItems(list);
    }

    @FXML
    private void insertOrUpdateUser() throws IOException, SQLException {
        Stage stage = (Stage) okButton.getScene().getWindow();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        if (idField.getText().isEmpty()) {
            dao.insertUserDao(loginField.getText(), passwordField.getText(), accessLvlField.getValue().getId(), timestamp);
        } else {
            dao.updateUserDao(Long.parseLong(idField.getText()), loginField.getText(), passwordField.getText(), accessLvlField.getValue().getId(), timestamp);
        }
        stage.close();
    }


    public void preloadData(User user) {
        idField.setText(String.valueOf(user.getId()));
        loginField.setText(user.getLogin());
        passwordField.setText(user.getPassword());
        accessLvlField.setValue(user.getAccessLvl());
        dateOfCreation.setText(String.valueOf(user.getDateOfCreation()));
        dateOfModification.setText(String.valueOf(user.getDateOfModification()));
    }

    @FXML
    private void cancelButton() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
