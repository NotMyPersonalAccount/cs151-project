package s25.cs151.application.utils;

import s25.cs151.application.model.Course;
import s25.cs151.application.model.SemesterHours;
import s25.cs151.application.model.SemesterTimeSlot;
import s25.cs151.application.model.Schedule;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:bookie_professor.db";

    protected static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Initializes all tables in the database.
     * @throws SQLException Thrown if query fails for any reason.
     */
    public static void initialize() throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            // Enable enforcement of foreign keys in SQLite
            stmt.execute("PRAGMA foreign_keys = ON");

            String initSemesterHoursQuery =
                    "CREATE TABLE IF NOT EXISTS semester_hours (" +
                    "   semester TEXT NOT NULL," +
                    "   year INTEGER NOT NULL," +
                    "   days TEXT NOT NULL," +
                    "   PRIMARY KEY (semester, year)" +
                    ")";
            stmt.execute(initSemesterHoursQuery);

            String initSemesterTimeSlots =
                    "CREATE TABLE IF NOT EXISTS semester_time_slots (" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "   from_time TEXT," +
                    "   to_time TEXT" +
                    ")";
            stmt.execute(initSemesterTimeSlots);

            String initCoursesQuery =
                    "CREATE TABLE IF NOT EXISTS courses (" +
                    "   course_code TEXT NOT NULL," +
                    "   course_name TEXT NOT NULL," +
                    "   section_number TEXT NOT NULL," +
                    "   PRIMARY KEY (course_code, course_name, section_number)" +
                    ")";
            stmt.execute(initCoursesQuery);

            String initSchedulesQuery =
                    "CREATE TABLE IF NOT EXISTS schedules (" +
                    "   id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "   name TEXT NOT NULL," +
                    "   date TEXT NOT NULL," +
                    "   time_slot_id INTEGER NOT NULL," +
                    "   course_code TEXT NOT NULL," +
                    "   course_name TEXT NOT NULL," +
                    "   section_number TEXT NOT NULL," +
                    "   reason TEXT," +
                    "   comment TEXT," +
                    "   FOREIGN KEY (time_slot_id)" +
                    "       REFERENCES semester_time_slots (id)" +
                    "           ON UPDATE CASCADE" +
                    "           ON DELETE CASCADE," +
                    "   FOREIGN KEY (course_code, course_name, section_number)" +
                    "       REFERENCES courses (course_code, course_name, section_number)" +
                    "           ON UPDATE CASCADE" +
                    "           ON DELETE CASCADE" +
                    ")";
            stmt.execute(initSchedulesQuery);
        }
    }

    /**
     * Inserts a semester time slot into the database
     */
    public static void insertSemesterTimeSlot(SemesterTimeSlot semesterTimeSlots) throws SQLException{
        String insertQuery = "INSERT INTO semester_time_slots (from_time, to_time) VALUES (?, ?)";
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(insertQuery)
        ) {
            stmt.setString(1, semesterTimeSlots.getFrom().format(TIME_FORMATTER));
            stmt.setString(2, semesterTimeSlots.getTo().format(TIME_FORMATTER));
            stmt.executeUpdate();
        }
    }

    /**
     * Loads semester time slots from the database.
     * @return a list of SemesterTimeSlot, or an empty list if the query fails.
     */
    public static List<SemesterTimeSlot> getAllSemesterTimeSlots() {
        List<SemesterTimeSlot> allSemesterTimeSlots = new ArrayList<>();

        String query = "SELECT * FROM semester_time_slots ORDER BY from_time, to_time";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalTime from = LocalTime.parse(rs.getString("from_time"), TIME_FORMATTER);
                LocalTime to = LocalTime.parse(rs.getString("to_time"), TIME_FORMATTER);

                SemesterTimeSlot semesterTimeSlot = new SemesterTimeSlot(id, from, to);
                allSemesterTimeSlots.add(semesterTimeSlot);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSemesterTimeSlots;
    }

    /**
     * Insert a semester hour into the database.
     * @param semesterHours The semester hours to save.
     * @throws SQLException Thrown if query fails for any reason, including if a row already exists with the
     *                      semester + year combination.
     */
    public static void insertSemesterHours(SemesterHours semesterHours) throws SQLException {
        String insertQuery = "INSERT INTO semester_hours (semester, year, days) VALUES (?, ?, ?)";
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(insertQuery)
        ) {
            stmt.setString(1, semesterHours.getSemester());
            stmt.setInt(2, semesterHours.getYear());
            stmt.setString(3, String.join(",", semesterHours.getDays()));
            stmt.executeUpdate();
        }
    }

    /**
     * Loads semester hours from the database, sorted by semester and year with most recent first.
     * @return a list of SemesterHours, or an empty list if the query fails.
     */
    public static List<SemesterHours> getAllSemesterHours() {
        List<SemesterHours> allSemesterHours = new ArrayList<>();

        String query =
                "SELECT * FROM semester_hours ORDER BY year DESC," +
                "   CASE semester" +
                "       WHEN 'Spring' THEN 3" +
                "       WHEN 'Summer' THEN 2" +
                "       WHEN 'Fall' THEN 1" +
                "       WHEN 'Winter' THEN 0" +
                "   END ";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String semester = rs.getString("semester");
                int year = rs.getInt("year");
                String days = rs.getString("days");

                SemesterHours semesterHours = new SemesterHours(semester, year, days.split(","));
                allSemesterHours.add(semesterHours);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSemesterHours;
    }

    /**
     * Inserts a course into the database
     */
    public static void insertCourse(Course course) throws SQLException {
        String insertQuery = "INSERT INTO courses (course_code, course_name, section_number) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(insertQuery)) {
            stmt.setString(1, course.getCourseCode());
            stmt.setString(2, course.getCourseName());
            stmt.setString(3, course.getSectionNumber());
            stmt.executeUpdate();
        }
    }

    /**
     * Loads courses from the database, sorted by course code in descending order.
     * @return a list of Course, or an empty list if the query fails.
     */
    public static List<Course> getAllCourses() {
        List<Course> allCourses = new ArrayList<>();

        String query = "SELECT * FROM courses ORDER BY course_code DESC";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("course_name");
                String sectionNumber = rs.getString("section_number");

                Course course = new Course(courseCode, courseName, sectionNumber);
                allCourses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allCourses;
    }

    /**
     * Inserts a schedule into the database
     */
    public static void insertSchedule(Schedule schedule) throws SQLException {
        String insertQuery = "INSERT INTO schedules (name, date, time_slot_id, course_code, course_name, section_number, reason, comment) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(insertQuery)
        ) {
            stmt.setString(1, schedule.getName());
            stmt.setString(2, schedule.getDate().toString());
            stmt.setInt(3, schedule.getTimeSlot().getId());
            stmt.setString(4, schedule.getCourse().getCourseCode());
            stmt.setString(5, schedule.getCourse().getCourseName());
            stmt.setString(6, schedule.getCourse().getSectionNumber());
            stmt.setString(7, schedule.getReason());
            stmt.setString(8, schedule.getComment());
            stmt.executeUpdate();
        }
    }

    /**
     * Deletes a schedule from the database
     */
    public static void deleteSchedule(Schedule schedule) throws SQLException {
        String deleteQuery = "DELETE FROM schedules WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(deleteQuery)
        ) {
            stmt.setInt(1, schedule.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Updates a schedule in the database
     */
    public static void updateSchedule(Schedule oldSchedule, Schedule newSchedule) throws SQLException {
        String updateQuery = "UPDATE schedules SET name = ?, date = ?, time_slot_id = ?, course_code = ?, course_name = ?, section_number = ?, reason = ?, comment = ? WHERE id = ?";
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(updateQuery)
        ) {
            Course newCourse = newSchedule.getCourse();

            stmt.setString(1, newSchedule.getName());
            stmt.setString(2, newSchedule.getDate().toString());
            stmt.setInt(3, newSchedule.getTimeSlot().getId());
            stmt.setString(4, newCourse.getCourseCode());
            stmt.setString(5, newCourse.getCourseName());
            stmt.setString(6, newCourse.getSectionNumber());
            stmt.setString(7, newSchedule.getReason());
            stmt.setString(8, newSchedule.getComment());
            stmt.setInt(9, oldSchedule.getId());
            stmt.executeUpdate();
        }
    }

    /**
     * Loads schedules from the database, sorted by date and time in ascending order.
     * @return a list of Schedule, or an empty list if the query fails.
     */
    public static List<Schedule> getAllSchedules() {
        List<Schedule> allSchedules = new ArrayList<>();

        String query = "SELECT * FROM schedules INNER JOIN semester_time_slots ON semester_time_slots.id = schedules.time_slot_id ORDER BY date DESC, from_time DESC, to_time DESC";
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(query);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                LocalDate date = LocalDate.parse(rs.getString("date"));
                int timeSlotId = rs.getInt("time_slot_id");
                String courseCode = rs.getString("course_code");
                String courseName = rs.getString("course_name");
                String sectionNumber = rs.getString("section_number");
                String reason = rs.getString("reason");
                String comment = rs.getString("comment");
                LocalTime fromTime = LocalTime.parse(rs.getString("from_time"), TIME_FORMATTER);
                LocalTime toTime = LocalTime.parse(rs.getString("to_time"), TIME_FORMATTER);

                Course course = new Course(courseCode, courseName, sectionNumber);
                SemesterTimeSlot timeSlot = new SemesterTimeSlot(timeSlotId, fromTime, toTime);
                allSchedules.add(new Schedule(id, name, date, timeSlot, course, reason, comment));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return allSchedules;
    }
}

