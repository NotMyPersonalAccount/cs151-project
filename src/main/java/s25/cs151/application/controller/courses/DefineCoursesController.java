package s25.cs151.application.controller.courses;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.model.Course;

public class DefineCoursesController extends DefineController<Course> {
    public DefineCoursesController() {
        super(new Course());
    }
}
