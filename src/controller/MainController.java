package controller;

import dao.DAO;
import dao.DAOImpl;
import entity.User;
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

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainController implements Initializable {

    DAO dao = new DAOImpl();
    User selectedUser = null;

    @FXML
    private TableView<User> tableView;
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
    private Button exitButton;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsers();
        getSelected();
    }

    public void showUsers() throws IOException {
        ObservableList<User> list = getUsersList();

        idColumn.setCellValueFactory(new PropertyValueFactory<User,Long>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        accessLvlColumn.setCellValueFactory(new PropertyValueFactory<User,Long>("accessLvl"));
        dateOfCreationColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDateTime>("dateOfCreation"));
        dateOfModificationColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDateTime>("dateOfModification"));

        tableView.setItems(list);
    }

    public ObservableList<User> getUsersList() throws IOException {
        return dao.getUsersListDao();
    }

    @FXML
    private void insertButton() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/secondPane.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Создать нового пользователя");

        stage.showAndWait();
        showUsers();
    }

    @FXML
    public void editButton() throws IOException {
        // Загрузчик для новой сцены
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/secondPane.fxml"));
        Parent parent = loader.load();

        // Передаем данные полей юзера в новую сцену
        SecondController controller = loader.getController();
        controller.preloadData(selectedUser);

        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Редактировать рользователя");

        // Ждем закрытия нового окна и обновляем список в текущем окне
        stage.showAndWait();
        showUsers();
    }

    @FXML
    private void deleteButton() throws IOException, SQLException {
        dao.deleteUserDao(selectedUser.getId());
        showUsers();
    }

    @FXML
    private void exitButton() throws IOException, SQLException {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void getSelected() {
        tableView.setOnMouseClicked(t -> {
            selectedUser = tableView.getSelectionModel().getSelectedItem();
        });
    }
}
