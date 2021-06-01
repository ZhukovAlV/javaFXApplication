package dao;

import entity.AccessLevel;
import entity.User;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface DAO {

    void executeQuery(String query);
    ObservableList<User> getUsersListDao() throws IOException;
    void insertUserDao(String login, String password, Long accesLvl, Timestamp dateOfCreation) throws IOException, SQLException;
    void updateUserDao(Long id, String login, String password, Long accesLvl, Timestamp dateOfModification) throws IOException, SQLException;
    void deleteUserDao(Long id) throws IOException, SQLException;
    ObservableList<AccessLevel> getAccessLevelListDao() throws IOException;
    ObservableList<User> findByLogin(String login) throws IOException, SQLException;
    ObservableList<User> findById(Long id) throws IOException, SQLException;
    ObservableList<User> findByAccess(AccessLevel accessLevel) throws IOException, SQLException;
}
