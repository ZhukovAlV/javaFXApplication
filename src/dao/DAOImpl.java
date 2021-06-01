package dao;

import connection.ConnectorMySQL;
import entity.AccessLevel;
import entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

//@Log4j
public class DAOImpl implements DAO {
    ConnectorMySQL connectorMySQL = new ConnectorMySQL();

    public void executeQuery(String query) {
        try (Connection conn = connectorMySQL.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(query);
        } catch (Exception e) {
       //     log.error("Error executeQuery: query", e);
            e.printStackTrace();
        }
    }

    public void insertUserDao(String login, String password, Long accesLvl, Timestamp dateOfCreation) throws IOException, SQLException {
        String query = "INSERT INTO user(login,password,accesLvl,dateOfCreation) VALUES (?, ?, ?, ?)";

        PreparedStatement preparedStatement = connectorMySQL.getConnection().prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setLong(3, accesLvl);
        preparedStatement.setTimestamp(4, dateOfCreation);

        preparedStatement.executeUpdate();
    }

    @Override
    public void updateUserDao(Long id, String login, String password, Long accesLvl, Timestamp dateOfModification) throws IOException, SQLException {
        String query =
                "UPDATE user " +
                "SET login = ?," +
                "password = ?," +
                "accesLvl = ?," +
                "dateOfModification = ?" +
                "WHERE id = ?";

        PreparedStatement preparedStatement = connectorMySQL.getConnection().prepareStatement(query);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        preparedStatement.setLong(3, accesLvl);
        preparedStatement.setTimestamp(4, dateOfModification);
        preparedStatement.setLong(5, id);

        preparedStatement.executeUpdate();
    }

    @Override
    public void deleteUserDao(Long id) throws IOException, SQLException {
        String query = "DELETE FROM user WHERE id = ?";

        PreparedStatement preparedStatement = connectorMySQL.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);

        preparedStatement.executeUpdate();
    }

    public ObservableList<User> getUsersListDao() throws IOException {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        Connection connection = connectorMySQL.getConnection();
        String query = "SELECT * FROM user";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            User user = null;
            while(rs.next()) {
                user = new User(rs.getLong("id"),rs.getString("login"),
                        rs.getString("password"),getAccessLevelDao(rs.getLong("accesLvl")),
                        (rs.getTimestamp("dateOfCreation") != null) ?
                                rs.getTimestamp("dateOfCreation").toLocalDateTime() : null,
                        (rs.getTimestamp("dateOfModification") != null) ?
                                rs.getTimestamp("dateOfModification").toLocalDateTime() : null);
                usersList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public AccessLevel getAccessLevelDao(Long id) throws IOException, SQLException {
        AccessLevel accessLevel = null;

        String query = "SELECT * FROM access_level WHERE id = ?";
        PreparedStatement preparedStatement = connectorMySQL.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);

        try (ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()) {
                accessLevel = new AccessLevel(rs.getLong("id"),rs.getString("title"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessLevel;
    }

    public ObservableList<AccessLevel> getAccessLevelListDao() throws IOException {
        ObservableList<AccessLevel> accessLevelList = FXCollections.observableArrayList();
        Connection connection = connectorMySQL.getConnection();
        String query = "SELECT * FROM access_level";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            AccessLevel accessLevel = null;
            while(rs.next()) {
                accessLevel = new AccessLevel(rs.getLong("id"),rs.getString("title"));
                accessLevelList.add(accessLevel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accessLevelList;
    }

    @Override
    public ObservableList<User> findByLogin(String login) throws IOException, SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();

        String query = "SELECT * FROM user WHERE login = ?";
        PreparedStatement preparedStatement = connectorMySQL.getConnection().prepareStatement(query);
        preparedStatement.setString(1, login);

        try (ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()) {
                userList.add(new User(rs.getLong("id"),rs.getString("login"),
                        rs.getString("password"),getAccessLevelDao(rs.getLong("accesLvl")),
                        (rs.getTimestamp("dateOfCreation") != null) ?
                                rs.getTimestamp("dateOfCreation").toLocalDateTime() : null,
                        (rs.getTimestamp("dateOfModification") != null) ?
                                rs.getTimestamp("dateOfModification").toLocalDateTime() : null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public ObservableList<User> findById(Long id) throws IOException, SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();

        String query = "SELECT * FROM user WHERE id = ?";
        PreparedStatement preparedStatement = connectorMySQL.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, id);

        try (ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()) {
                userList.add(new User(rs.getLong("id"),rs.getString("login"),
                        rs.getString("password"),getAccessLevelDao(rs.getLong("accesLvl")),
                        (rs.getTimestamp("dateOfCreation") != null) ?
                                rs.getTimestamp("dateOfCreation").toLocalDateTime() : null,
                        (rs.getTimestamp("dateOfModification") != null) ?
                                rs.getTimestamp("dateOfModification").toLocalDateTime() : null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    @Override
    public ObservableList<User> findByAccess(AccessLevel accessLevel) throws IOException, SQLException {
        ObservableList<User> userList = FXCollections.observableArrayList();

        String query = "SELECT * FROM user, access_level WHERE access_level.id = user.accesLvl AND access_level.id = ?";
        PreparedStatement preparedStatement = connectorMySQL.getConnection().prepareStatement(query);
        preparedStatement.setLong(1, accessLevel.getId());

        try (ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()) {
                userList.add(new User(rs.getLong("id"),rs.getString("login"),
                        rs.getString("password"),getAccessLevelDao(rs.getLong("accesLvl")),
                        (rs.getTimestamp("dateOfCreation") != null) ?
                                rs.getTimestamp("dateOfCreation").toLocalDateTime() : null,
                        (rs.getTimestamp("dateOfModification") != null) ?
                                rs.getTimestamp("dateOfModification").toLocalDateTime() : null));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}
