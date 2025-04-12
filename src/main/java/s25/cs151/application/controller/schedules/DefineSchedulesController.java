package s25.cs151.application.controller.schedules;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.model.Schedule;
import s25.cs151.application.utils.DatabaseHelper;

public class DefineSchedulesController extends DefineController<Schedule> {
    public DefineSchedulesController() {
        super(new Schedule(), Forms::createScheduleForm, DatabaseHelper::insertSchedule);
    }
}
