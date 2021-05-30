package controller;

import dao.DAO;
import dao.DAOImpl;
import entity.User;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    @FXML
    private TableView<User> TableView;
    @FXML
    private TableColumn<User, Long> idColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, Long> accessLvlColumn;
    @FXML
    private TableColumn<User, LocalDateTime> dateOfCreationColumn;
    @FXML
    private TableColumn<User, LocalDateTime> dateOfModificationColumn;
    @FXML
    private Button insertButton;

    DAO dao = new DAOImpl();

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsers();
        /*        ObservableList<User> ob = getUsersList();
        ob.addListener((ListChangeListener<User>) change -> {
            try {
                showUsers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });*/
    }

    public void showUsers() throws IOException {
        ObservableList<User> list = getUsersList();

        idColumn.setCellValueFactory(new PropertyValueFactory<User,Long>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        accessLvlColumn.setCellValueFactory(new PropertyValueFactory<User,Long>("accessLvl"));
        dateOfCreationColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDateTime>("dateOfCreation"));
        dateOfModificationColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDateTime>("dateOfModification"));

        TableView.setItems(list);
    }

    public ObservableList<User> getUsersList() throws IOException {
        return dao.getUsersListDao();
    }

    @FXML
    private void insertButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/secondPane.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Работа с пользователем");
        stage.show();
    }
}
