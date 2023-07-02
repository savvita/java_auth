package db;

import com.google.common.base.Charsets;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import models.User;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

public class DB {
    private final String url;
    private final String username;
    private final String password;

    public DB(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    public boolean checkUser (String login, String password) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, this.password)){
                String query = "SELECT COUNT(*) FROM users WHERE login= ? AND password=?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, login);
                String encoded = getEncodedPassword(password);
                preparedStatement.setString(2, getEncodedPassword(password));
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    Integer res = resultSet.getInt(1);
                    return res == 1;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }

    public boolean addUser (String login, String password) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, this.password)){
                String query = "INSERT INTO users (login, password) VALUES (?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, login);
                preparedStatement.setString(2, getEncodedPassword((password)));
                int rowsInserted = preparedStatement.executeUpdate();
                return rowsInserted > 0;
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }

    public boolean checkUsername(String login) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, this.password)){
                String query = "SELECT COUNT(*) FROM users WHERE login= ?";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                preparedStatement.setString(1, login);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()) {
                    Integer res = resultSet.getInt(1);
                    return res == 0;
                }
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return false;
    }

    public Collection<User> getUsers () {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(url, username, this.password)){
                String query = "SELECT id, login FROM users";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();

                Collection<User> users = new ArrayList<>();

                while(resultSet.next()) {
                    users.add(new User(resultSet.getInt(1), resultSet.getString(2)));
                }
                return users;
            }
        }
        catch(Exception ex){
            System.out.println(ex);
        }

        return new ArrayList<User>(0);
    }

    private String getEncodedPassword(String password) throws NoSuchAlgorithmException {
        Hasher hasher = Hashing.sha256().newHasher();
        hasher.putString(password, Charsets.UTF_8);
        return hasher.hash().toString();
    }
}
