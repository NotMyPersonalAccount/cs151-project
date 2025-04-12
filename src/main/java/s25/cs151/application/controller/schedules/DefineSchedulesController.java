package s25.cs151.application.controller.schedules;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.model.Schedule;

public class DefineSchedulesController extends DefineController<Schedule> {
    public DefineSchedulesController() {
        super(new Schedule());
    }
}
