package s25.cs151.application.utils;

import s25.cs151.application.model.Course;
import s25.cs151.application.model.SemesterHours;
import s25.cs151.application.model.SemesterTimeSlot;
import s25.cs151.application.model.Schedule;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class DatabaseHelper {
    private static final String DB_URL = "jdbc:sqlite:bookie_professor.db";

    /**
     * Initializes all tables in the database.
     * @throws SQLException Thrown if query fails for any reason.
     */
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
                    "   comment TEXT" +
                            ")";
            stmt.execute(initSchedulesQuery);


        }
    }

    /**
     * Inserts a semester time slot into the database
     */
    public static void insertSemesterTimeSlot(SemesterTimeSlot semesterTimeSlots) throws SQLException{
        String insertQuery = "INSERT INTO semester_time_slots (from_time, to_time) VALUES (?, ?)";
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm");
        try (
                Connection conn = DriverManager.getConnection(DB_URL);
                PreparedStatement stmt = conn.prepareStatement(insertQuery)
        ) {
            stmt.setString(1, semesterTimeSlots.getFrom().format(dateFormat));
            stmt.setString(2, semesterTimeSlots.getTo().format(dateFormat));
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
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("HH:mm");

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("id");
                LocalTime from = LocalTime.parse(rs.getString("from_time"), dateFormat);
                LocalTime to = LocalTime.parse(rs.getString("to_time"), dateFormat);

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

    public static List<Schedule> getAllSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        String query = "SELECT * FROM schedules";

        // Build lookup maps
        Map<Integer, SemesterTimeSlot> timeSlotMap = new HashMap<>();
        for (SemesterTimeSlot slot : getAllSemesterTimeSlots()) {
            timeSlotMap.put(slot.getId(), slot);
        }

        Map<String, Course> courseMap = new HashMap<>();
        for (Course course : getAllCourses()) {
            String key = course.getCourseCode() + "-" + course.getSectionNumber();
            courseMap.put(key, course);
        }

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
                String sectionNumber = rs.getString("section_number");
                String reason = rs.getString("reason");
                String comment = rs.getString("comment");

                SemesterTimeSlot timeSlot = timeSlotMap.get(timeSlotId);
                Course course = courseMap.get(courseCode + "-" + sectionNumber);

                if (timeSlot != null && course != null) {
                    schedules.add(new Schedule(id, name, date, timeSlot, course, reason, comment));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return schedules;
    }




}

