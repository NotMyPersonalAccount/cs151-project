package s25.cs151.application.controller.timeslots;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.model.SemesterTimeSlot;

public class DefineSemesterTimeSlotsController extends DefineController<SemesterTimeSlot> {
    public DefineSemesterTimeSlotsController() {
        super(new SemesterTimeSlot());
    }
}