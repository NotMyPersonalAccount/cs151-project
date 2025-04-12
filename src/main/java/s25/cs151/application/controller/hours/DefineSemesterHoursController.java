package s25.cs151.application.controller.hours;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.model.SemesterHours;

public class DefineSemesterHoursController extends DefineController<SemesterHours> {
    public DefineSemesterHoursController() {
        super(new SemesterHours());
    }
}
