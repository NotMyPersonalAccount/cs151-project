package s25.cs151.application.forms;

import com.dlsc.formsfx.model.structure.Field;
import com.dlsc.formsfx.model.structure.Form;
import com.dlsc.formsfx.model.structure.Group;
import com.dlsc.formsfx.model.util.BindingMode;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.model.validators.IntegerRangeValidator;
import com.dlsc.formsfx.view.controls.SimpleCheckBoxControl;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import s25.cs151.application.model.*;
import s25.cs151.application.utils.DatabaseHelper;

public class Forms {
    /**
     * Creates and binds values for a semester hours definition form
     */
    public static Form createSemesterHoursForm(SemesterHours semesterHours) {
        return Form.of(
                Group.of(
                        Field.ofSingleSelectionType(semesterHours.allSeasons, semesterHours.semester)
                                .label("Semester"),
                        Field.ofIntegerType(semesterHours.year)
                                .validate( IntegerRangeValidator.between(1000, 9999, "Four digits expected"))
                                .label("Year"),
                        Field.ofMultiSelectionType(semesterHours.allDays, semesterHours.days)
                                .label("Days")
                                .required("At least one day must be selected")
                                .render(new SimpleCheckBoxControl<>())
                )
        ).title("Define Semester Hours").binding(BindingMode.CONTINUOUS);
    }

    /**
     * Creates and binds values for a semester time slot definition form
     */
    public static Form createSemesterTimeSlotForm(SemesterTimeSlot timeSlot) {
        return Form.of(
                Group.of(
                        new TimeField(timeSlot.from).label("From"),
                        new TimeField(timeSlot.to).label("To")
                )
        ).title("Define Semester Time Slots");
    }

    /**
     * Creates and binds values for a course definition form
     */
    public static Form createCourseForm(Course course) {
        return Form.of(
                Group.of(
                        Field.ofStringType(course.courseCode)
                                .label("Course Code")
                                .required("Course Code is required"),
                        Field.ofStringType(course.courseName)
                                .label("Course Name")
                                .required("Course Name is required"),
                        Field.ofStringType(course.sectionNumber)
                                .label("Section Number")
                                .validate(CustomValidator.forPredicate(
                                        value -> value.matches("\\d+"), "Section Number must be an integer")
                                )
                                .required("Section Number is required")
                )
        ).title("Define Courses").binding(BindingMode.CONTINUOUS);
    }

    /**
     * Creates and binds value for a schedule definition form
     */
    public static Form createScheduleForm(Schedule schedule) {
        return Form.of(
                Group.of(
                        Field.ofStringType(schedule.name)
                                .label("Name")
                                .required("Name is required"),
                        Field.ofDate(schedule.date)
                                .label("Date")
                                .required("Date is required"),
                        Field.ofSingleSelectionType(new SimpleListProperty<>(FXCollections.observableList(DatabaseHelper.getAllSemesterTimeSlots())), schedule.timeSlot)
                                .select(0)
                                .label("Time Slot")
                                .required("Time Slot is required"),
                        Field.ofSingleSelectionType(new SimpleListProperty<>(FXCollections.observableList(DatabaseHelper.getAllCourses())), schedule.course)
                                .select(0)
                                .label("Course")
                                .required("Course is required"),
                        Field.ofStringType(schedule.reason)
                                .label("Reason"),
                        Field.ofStringType(schedule.comment)
                                .label("Comment")
                )
        ).title("Define Schedule").binding(BindingMode.CONTINUOUS);
    }
}
