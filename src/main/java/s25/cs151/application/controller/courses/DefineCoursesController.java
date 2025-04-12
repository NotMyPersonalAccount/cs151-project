package s25.cs151.application.controller.courses;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.model.Course;
import s25.cs151.application.utils.DatabaseHelper;

public class DefineCoursesController extends DefineController<Course> {
    public DefineCoursesController() {
        super(new Course(), Forms::createCourseForm, DatabaseHelper::insertCourse);
    }
}
