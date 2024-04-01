package com.example.edtunivavignon;

import java.sql.*;

public class UserDB {

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/example/edtunivavignon/db/users.db");
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                String sql = "CREATE TABLE users(userName VARCHAR(100), password VARCHAR(100), personalTimeTable VARCHAR(1000), darkMode BOOLEAN DEFAULT false)";
                Statement statement = conn.createStatement();
                statement = conn.createStatement();
                //statement.executeQuery(sql);
                //insert("Cantin","feur","https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def502001eafd38c6be9b62798de135592ecdecec8d8f8c6dc24d4a29b99ab29b20b16c500d11e5fb9815a21952b048a697e9b8cd43bb905521c03c03793609b386e8fb17197b0ccafd5d21d3b2332e91120c702d6c26ed8");
                //insert("admin","admin","https://edt-api.univ-avignon.fr/api/exportAgenda/enseignant/def5020014cf744f63f7181931e243c5139c5d8427de488f3da5b30b52905edfe9de85e8da750e291f852c095f6fd05f93658cbbf3260bf1308a84c444accdb9ab8f67de5f5758e0b59200e3c78068a677fc5055644c4635");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String userName, String password, String icsURL) {
        String sql = "INSERT INTO users(userName,password,personalTimeTable) VALUES(?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            pstmt.setString(3, icsURL);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAll() {
        String sql = "SELECT * FROM users";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("userName") +  "\t" +
                        rs.getString("password") + "\t");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public User getUser(String userName, String password) {
        String query = "SELECT * FROM USERS WHERE userName='"+userName+"' AND password='"+password+"'";
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            User user = null;
            while (resultSet.next()) {
                user = new User(resultSet.getString("userName"),resultSet.getString("personalTimeTable"),resultSet.getBoolean("darkMode"));
            }
            return user;
        } catch (SQLException e) {
            return null;
        }
    }
}
