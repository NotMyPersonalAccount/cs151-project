package s25.cs151.application.controller.timeslots;

import s25.cs151.application.controller.DefineController;
import s25.cs151.application.forms.Forms;
import s25.cs151.application.model.SemesterTimeSlot;
import s25.cs151.application.utils.DatabaseHelper;

public class DefineSemesterTimeSlotsController extends DefineController<SemesterTimeSlot> {
    public DefineSemesterTimeSlotsController() {
        super(new SemesterTimeSlot(), Forms::createSemesterTimeSlotForm, DatabaseHelper::insertSemesterTimeSlot);
    }
}