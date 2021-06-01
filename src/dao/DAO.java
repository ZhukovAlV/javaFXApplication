package dao;

import entity.AccessLevel;
import entity.User;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public interface DAO {

    void executeQuery(String query);
    ObservableList<User> getUsersListDao() throws IOException;
    void insertUserDao(String login, String password, Long accesLvl, Timestamp dateOfCreation) throws IOException, SQLException;
    void updateUserDao(Long id, String login, String password, Long accesLvl, Timestamp dateOfModification) throws IOException, SQLException;
    void deleteUserDao(Long id) throws IOException, SQLException;
    ObservableList<AccessLevel> getAccessLevelListDao() throws IOException;

}
