package controller;

import entity.User;
import javafx.collections.FXCollections;
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
    private TableColumn<User, LocalDate> dateOfModificationColumn;
    @FXML
    private Button insertButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showUsers();
    }

    public void showUsers() {
        ObservableList<User> list = getUsersList();

        idColumn.setCellValueFactory(new PropertyValueFactory<User,Long>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<User,String>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<User,String>("password"));
        accessLvlColumn.setCellValueFactory(new PropertyValueFactory<User,Long>("accessLvl"));
        dateOfCreationColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDateTime>("dateOfCreation"));
        dateOfModificationColumn.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("dateOfModification"));

        TableView.setItems(list);
    }

    public ObservableList<User> getUsersList(){
        ObservableList<User> usersList = FXCollections.observableArrayList();
        Connection connection = getConnection();
        String query = "SELECT * FROM user";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            User user = null;
            while(rs.next()) {
                user = new User(rs.getLong("id"),rs.getString("login"),
                        rs.getString("password"),rs.getLong("accesLvl"),
                        rs.getTimestamp("dateOfCreation").toLocalDateTime(),
                        rs.getDate("dateOfModification").toLocalDate());
                usersList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersList;
    }

    @FXML
    private void insertButton() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/secondPane.fxml"));

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Главное окно");
        stage.show();
       // String query = "insert into books values("+idField.getText()+",'"+titleField.getText()+"','"+authorField.getText()+"',"+yearField.getText()+","+pagesField.getText()+")";
/*        String query = "Select 1";
        executeQuery(query);
        showUsers();*/
    }

    public Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/data","admin","admin");
            return conn;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public void executeQuery(String query) {
        Connection conn = getConnection();
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
