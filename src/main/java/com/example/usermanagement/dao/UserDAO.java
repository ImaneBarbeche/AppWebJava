package com.example.usermanagement.dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import com.example.usermanagement.model.User;

import java.util.ArrayList;

public class UserDAO {

   private Connection connect() throws SQLException {
    try {
        Class.forName("org.sqlite.JDBC"); // Chargement explicite du driver (nécessaire avec Tomcat)

    } catch (ClassNotFoundException e) {
        throw new SQLException("Driver SQLite non trouvé", e);
    }

    // SQLite connection string
    String url = "jdbc:sqlite:C:/Users/Utilisateur/Documents/Simplon/Briefs et projets de formation/JAVA/usermanagement/src/main/resources/db/users.db";

    return DriverManager.getConnection(url);
}


    public UserDAO() { // constructeur qui permet de créer une table vide de base de données
        String sql = "CREATE TABLE IF NOT EXISTS users (id INTEGER PRIMARY KEY, name TEXT NOT NULL, email TEXT NOT NULL, phone TEXT, dateNaissance TEXT)";
        try (Connection conn = connect();
                Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> listAll() {
        List<User> users = new ArrayList<>();
        try (Connection conn = connect()) {

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                users.add(
                        new User(rs.getInt("id"),
                                rs.getString("name"),
                                rs.getString("email"),
                                rs.getString("phone"),
                                LocalDate.parse(rs.getString("dateNaissance"))));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void add(User u) {
        String sql = "INSERT INTO users (name, email, phone, dateNaissance) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getEmail());
            pstmt.setString(3, u.getPhone());
            pstmt.setString(4, u.getDateNaissance().toString());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
    String sql = "DELETE FROM users WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

}
