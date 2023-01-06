package pl.coderslab.entity;

import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.DbUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class UserDao {
    private static final String CREATE_USER_QUERY = "INSERT INTO users (username, email, password) VALUES (?,?,?)";
    private static final String READ_USER_QUERY = """
            SELECT id,username, email
            FROM users
            WHERE id = ?
            """;

    private static final String UPDATE_USER_QUERY = """
            UPDATE users SET username = ?, email = ?, password = ? WHERE id = ?
            """;
    private static final String DELETE_USER_QUERY = """
            DELETE FROM users WHERE id = ?
            """;

    private static final String FIND_ALL_USER_QUERY = """
            SELECT id ,username, email FROM users
            """;


    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public pl.coderslab.entity.User create(pl.coderslab.entity.User user) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement =
                    conn.prepareStatement(CREATE_USER_QUERY, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public pl.coderslab.entity.User read(int userId) {
        try (Connection conn = DbUtil.connect()) {

            pl.coderslab.entity.User user = new pl.coderslab.entity.User();
            PreparedStatement prepStmt = conn.prepareStatement(READ_USER_QUERY);
            prepStmt.setInt(1, userId);
            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                if (rs.getInt("id") != 0) {
                    user.setId(rs.getInt("id"));
                    user.setUserName(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    return user;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return null;


    }

    public void update(pl.coderslab.entity.User user) {
        try (Connection conn = DbUtil.connect()) {
            System.out.println();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Set new username");
            user.setUserName(scanner.nextLine());
            System.out.println("Set new email");
            user.setEmail(scanner.nextLine());
            System.out.println("Set new password");
            user.setPassword(scanner.nextLine());
            PreparedStatement statement = conn.prepareStatement(UPDATE_USER_QUERY);
            statement.setString(1, user.getUserName());
            statement.setString(2, user.getEmail());
            statement.setString(3, hashPassword(user.getPassword()));
            statement.setInt(4, user.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int userId) {
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(DELETE_USER_QUERY);
            statement.setInt(1, userId);
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<pl.coderslab.entity.User> findAll() {
        ArrayList<pl.coderslab.entity.User> list = new ArrayList<>();
        try (Connection conn = DbUtil.connect()) {
            PreparedStatement statement = conn.prepareStatement(FIND_ALL_USER_QUERY);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                pl.coderslab.entity.User user = new pl.coderslab.entity.User(rs.getInt(1), rs.getString(2), rs.getString(3));
                list.add(user);
                System.out.println(user.getId() + " " + user.getUserName() + " " + user.getEmail());
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


}
