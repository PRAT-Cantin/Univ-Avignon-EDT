package com.example.edtunivavignon;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    public void resetDB() {
        try (Connection conn = this.connect()) {
            String sql;
            Statement statement;
            //sql = "DROP TABLE users";
            //statement = conn.createStatement();
            //statement.executeQuery(sql);

            //sql = "DROP TABLE events";
            //statement = conn.createStatement();
            //statement.executeQuery(sql);

             //sql = "CREATE TABLE users(userName VARCHAR(100), password VARCHAR(100), personalTimeTable VARCHAR(1000), darkMode BOOLEAN DEFAULT false, admin BOOLEAN)";
             //statement = conn.createStatement();
             //statement.executeQuery(sql);

             //sql = "CREATE TABLE events(userName VARCHAR(100), eventName VARCHAR(10000), rooms VARCHAR(10000), memo VARCHAR(10000), eventType VARCHAR(10000), eventColor VARCHAR(100), eventStart VARCHAR(100), eventEnd VARCHAR(100), teachers VARCHAR(10000), attendingGroups VARCHAR(10000), attendingPromotions VARCHAR(10000))";
             //statement = conn.createStatement();
             //statement.executeQuery(sql);

            //insert("Cantin","feur","https://edt-api.univ-avignon.fr/api/exportAgenda/tdoption/def502001eafd38c6be9b62798de135592ecdecec8d8f8c6dc24d4a29b99ab29b20b16c500d11e5fb9815a21952b048a697e9b8cd43bb905521c03c03793609b386e8fb17197b0ccafd5d21d3b2332e91120c702d6c26ed8",false);
            //insert("admin","admin","https://edt-api.univ-avignon.fr/api/exportAgenda/enseignant/def5020014cf744f63f7181931e243c5139c5d8427de488f3da5b30b52905edfe9de85e8da750e291f852c095f6fd05f93658cbbf3260bf1308a84c444accdb9ab8f67de5f5758e0b59200e3c78068a677fc5055644c4635",true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:src/main/resources/com/example/edtunivavignon/db/users.db");
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insert(String userName, String password, String icsURL,Boolean isAdmin) {
        String sql = "INSERT INTO users(userName,password,personalTimeTable,admin) VALUES(?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            pstmt.setString(3, icsURL);
            pstmt.setBoolean(4, isAdmin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectAll() {
        String sql = "SELECT * FROM events";
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getString("userName") +  "\t");
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
                user = new User(resultSet.getString("userName"),resultSet.getString("personalTimeTable"),resultSet.getBoolean("darkMode"),resultSet.getBoolean("admin"));
            }
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    public void addEvent(String userName, String eventName, String rooms, String memo, String eventType, String eventColor, LocalDateTime eventStart, LocalDateTime eventEnd, String teachers, String attendingGroups, String attendingPromotions) {
        String sql = "INSERT INTO events(userName, eventName, rooms, memo, eventType, eventColor, eventStart, eventEnd, teachers, attendingGroups, attendingPromotions) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userName);
            pstmt.setString(2, eventName);
            pstmt.setString(3, rooms);
            pstmt.setString(4, memo);
            pstmt.setString(5, eventType);
            pstmt.setString(6, eventColor);
            pstmt.setString(7, eventStart.format(formatter));
            pstmt.setString(8, eventEnd.format(formatter));
            pstmt.setString(9, teachers);
            pstmt.setString(10, attendingGroups);
            pstmt.setString(11, attendingPromotions);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Reservation> getReservations(String userName) {
        String query = "SELECT * FROM events WHERE userName='"+userName+"'";
        try (Connection connection = connect()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ArrayList<Reservation> customReservations = new ArrayList<>();
            Reservation reservation;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            while (resultSet.next()) {
                reservation = new Reservation();
                reservation.setMemo(resultSet.getString("memo"));
                reservation.setType(resultSet.getString("eventType"));
                reservation.setNameOfReservation(resultSet.getString("eventName"));
                reservation.setRooms(new ArrayList<>(List.of(resultSet.getString("rooms").split(";"))));
                reservation.setColor(resultSet.getString("eventColor"));
                reservation.setStart(LocalDateTime.parse(resultSet.getString("eventStart"),formatter));
                reservation.setEnd(LocalDateTime.parse(resultSet.getString("eventEnd"),formatter));
                reservation.setTeachers(new ArrayList<>(List.of(resultSet.getString("teachers").split(";"))));
                reservation.setAttendingGroups(new ArrayList<>(List.of(resultSet.getString("attendingGroups").split(";"))));
                reservation.setAttendingPromotions(new ArrayList<>(List.of(resultSet.getString("attendingPromotions").split(";"))));
                customReservations.add(reservation);
            }
            return customReservations;
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }
}
