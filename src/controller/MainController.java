package controller;

import dao.DAO;
import dao.DAOImpl;
import entity.AccessLevel;
import entity.User;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    private TextField findByLogin;
    @FXML
    private TextField findById;
    @FXML
    private ComboBox findByAccess;
    @FXML
    private TableColumn<User, Long> idColumn;
    @FXML
    private TableColumn<User, String> loginColumn;
    @FXML
    private TableColumn<User, String> passwordColumn;
    @FXML
    private TableColumn<User, AccessLevel> accessLvlColumn;
    @FXML
    private TableColumn<User, LocalDateTime> dateOfCreationColumn;
    @FXML
    private TableColumn<User, LocalDateTime> dateOfModificationColumn;
    @FXML
    private Button exitButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsers(getUsersList());
        findByAccess.setItems(dao.getAccessLevelListDao());
        getSelected();
        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }

    public void showUsers(ObservableList<User> list) {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        accessLvlColumn.setCellValueFactory(new PropertyValueFactory<>("accessLvl"));
        dateOfCreationColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfCreation"));
        dateOfModificationColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfModification"));

        tableView.setItems(list);
    }

    public ObservableList<User> getUsersList() throws IOException {
        return dao.getUsersListDao();
    }

    public void findByLoginList() throws IOException, SQLException {
        if (!findByLogin.getText().isEmpty())
            showUsers(dao.findByLogin(findByLogin.getText()));
        else showUsers(getUsersList());
        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }

    public void findByIdList() throws IOException, SQLException {
        if (!findById.getText().isEmpty())
            showUsers(dao.findById(Long.parseLong(findById.getText())));
        else showUsers(getUsersList());
        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }

    public void findByAccessList() throws IOException, SQLException {
        if (findByAccess.getValue() != null)
            showUsers(dao.findByAccess((AccessLevel)findByAccess.getValue()));
        else showUsers(getUsersList());
        editButton.setVisible(false);
        deleteButton.setVisible(false);
    }
    @FXML
    private void insertButton() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/secondPane.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Создать нового пользователя");

        stage.showAndWait();
        showUsers(getUsersList());
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
        showUsers(getUsersList());
    }

    @FXML
    private void helpInfo() throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/info.fxml"));

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Информация");
        stage.show();
    }

    @FXML
    private void deleteButton() throws IOException, SQLException {
        dao.deleteUserDao(selectedUser.getId());
        showUsers(getUsersList());
    }

    @FXML
    private void exitButton() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void getSelected() {
        tableView.setOnMouseClicked(t -> {
            selectedUser = tableView.getSelectionModel().getSelectedItem();
            editButton.setVisible(true);
            deleteButton.setVisible(true);
        });
    }
}
