package s25.cs151.application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    public final StringProperty courseCode = new SimpleStringProperty("");
    public final StringProperty courseName = new SimpleStringProperty("");
    public final StringProperty sectionNumber = new SimpleStringProperty("");

    public Course() {
    }

    public Course(String courseCode, String courseName, String sectionNumber) {
        this.courseCode.set(courseCode);
        this.courseName.set(courseName);
        this.sectionNumber.set(sectionNumber);
    }

    public String getCourseCode() {
        return this.courseCode.get();
    }

    public String getCourseName() {
        return this.courseName.get();
    }

    public String getSectionNumber() {
        return this.sectionNumber.get();
    }

    public String toString() {
        return this.getCourseCode() + "-" + this.getSectionNumber();
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course course)) return false;

        return this.getCourseCode().equals(course.getCourseCode()) &&
                this.getCourseName().equals(course.getCourseName()) &&
                this.getSectionNumber().equals(course.getSectionNumber());
    }
}
