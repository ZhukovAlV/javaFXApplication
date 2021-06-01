package dao;

import connection.ConnectorMySQL;
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


/*    public static void deleteUser(int id) throws SQLException, IOException, URISyntaxException {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM usersformyapp WHERE userID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        }
    }*/

/*    public static void updateUser(User user) throws SQLException, IOException {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE usersformyapp SET email = ?, firstName = ?, lastName = ? WHERE userID = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setInt(4, user.getId());

            preparedStatement.executeUpdate();
        }
    }*/

    public ObservableList<User> getUsersListDao() throws IOException {
        ObservableList<User> usersList = FXCollections.observableArrayList();
        Connection connection = connectorMySQL.getConnection();
        String query = "SELECT * FROM user";

        try (Statement st = connection.createStatement()) {
            ResultSet rs = st.executeQuery(query);
            User user = null;
            while(rs.next()) {
                user = new User(rs.getLong("id"),rs.getString("login"),
                        rs.getString("password"),rs.getLong("accesLvl"),
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

}
