package s25.cs151.application.controller.hours;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.model.SemesterHours;
import s25.cs151.application.utils.DatabaseHelper;

public class DefineSemesterHoursController extends DefineController<SemesterHours> {
    public DefineSemesterHoursController() {
        super(new SemesterHours(), Forms::createSemesterHoursForm, DatabaseHelper::insertSemesterHours);
    }
}
