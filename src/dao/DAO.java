package dao;

import entity.User;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface DAO {

    void executeQuery(String query);
    void insertUserDao(String login, String password, Long accesLvl, Timestamp dateOfCreation) throws IOException, SQLException;
    void updateUserDao(Long id, String login, String password, Long accesLvl, Timestamp dateOfModification) throws IOException, SQLException;
    ObservableList<User> getUsersListDao() throws IOException;
}
