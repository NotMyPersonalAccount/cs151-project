package s25.cs151.application;

import javafx.collections.FXCollections;
import s25.cs151.application.model.SemesterHours;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:bookie_professor.db";

    public static void initialize() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String initSemesterHoursQuery =
                    "CREATE TABLE IF NOT EXISTS semester_hours (" +
                    "   semester TEXT NOT NULL," +
                    "   year INTEGER NOT NULL," +
                    "   days TEXT NOT NULL," +
                    "   PRIMARY KEY (semester, year)" +
                    ")";
            stmt.execute(initSemesterHoursQuery);
        }
    }

    public static void insertSemesterHours(SemesterHours semesterHours) throws SQLException {
        String insertQuery = "INSERT INTO semester_hours (semester, year, days) VALUES (?, ?, ?)";
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(insertQuery)
        ) {
            stmt.setString(1, semesterHours.semester.getValue());
            stmt.setInt(2, semesterHours.year.getValue());
            stmt.setString(3, String.join(",", semesterHours.days.getValue()));
            stmt.executeUpdate();
        }
    }

    public static List<SemesterHours> getAllSemesterHours() {
        List<SemesterHours> allSemesterHours = new ArrayList<>();

        String sql = "SELECT * FROM semester_hours ORDER BY year DESC, semester DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String semester = rs.getString("semester");
                int year = rs.getInt("year");
                String days = rs.getString("days");

                SemesterHours semesterHours = new SemesterHours();
                semesterHours.semester.setValue(semester);
                semesterHours.year.setValue(year);
                semesterHours.days.setValue(FXCollections.observableArrayList(days.split(",")));

                allSemesterHours.add(semesterHours);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSemesterHours;
    }
}

