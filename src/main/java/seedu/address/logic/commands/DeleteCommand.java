package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Person;
import seedu.address.model.timeslots.Timeslot;

/**
 * Deletes a patient identified using its displayed index in the clinic records.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the patient with the index number used in the clinic records.\n"
            + "Parameters: INDEX (must be a positive integer in the list)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PATIENT_SUCCESS = "Deleted Person: %1$s from clinic records";
    private static Logger logger = Logger.getLogger("DeleteCommandLogger");
    private final Index targetIndex;

    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();
        List<Doctor> doctorList = model.getFilteredDoctorList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        assert personToDelete != null : "Person to delete is null!";
        model.deletePerson(personToDelete);

        ArrayList<Appointment> patientAppointments = personToDelete.getAppointments();
        for (Appointment appointment : patientAppointments) {
            model.deleteAppointment(appointment);

            Doctor targetDoctor = targetDoctor(doctorList, appointment);
            int appointmentIndex = targetDoctor.getAppointments().indexOf(appointment);
            targetDoctor.deleteAppointment(appointmentIndex);

            updateModelTimeslotList(model, appointment);
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PATIENT_SUCCESS, Messages.format(personToDelete)));
    }

    private void updateModelTimeslotList(Model model, Appointment appointment) {
        if (model.getAvailableTimeSlotList().size() > 0) {
            logger.log(Level.INFO, "Updating Timeslot List!");
            LocalDate currDate = model.getAvailableTimeSlotList().get(0).getDate();
            LocalDate apptDate = appointment.getDateTime().toLocalDate();

            if (apptDate.equals(currDate)) {
                Timeslot timeslotToAdd = new Timeslot(apptDate, appointment.getDateTime().getHour());
                model.addAvailableTimeSlot(timeslotToAdd);
            }
        }
    }

    /**
     * Returns the doctor that contains the given appointment.
     */
    public Doctor targetDoctor(List<Doctor> doctorList, Appointment appointment) {
        Doctor targetDoctor = null;
        for (Doctor doctor : doctorList) {
            if (doctor.hasAppointment(appointment)) {
                targetDoctor = doctor;
                break;
            }
        }
        if (targetDoctor == null) {
            throw new RuntimeException();
        }
        return targetDoctor;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
