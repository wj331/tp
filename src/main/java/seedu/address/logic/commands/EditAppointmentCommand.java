package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddAppointmentCommand.MESSAGE_DUPLICATE_APPOINTMENT_DOCTOR;
import static seedu.address.logic.commands.AddAppointmentCommand.MESSAGE_SAME_APPOINTMENT_TIME;
import static seedu.address.logic.commands.AddAppointmentCommand.MESSAGE_TIMESLOT_TAKEN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPTS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.timeslots.Timeslot;


/**
 * Edits the details of an existing person in the address book.
 */
public class EditAppointmentCommand extends Command {

    public static final String COMMAND_WORD = "edit_appt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Edits the specified appointment identified "
            + "by the index number used in the displayed appointments list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]"
            + "[" + PREFIX_DATE + "DATE_TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "01-01-2024 09:00";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Newly edited appointment |%1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided: "
            + "[" + PREFIX_DESCRIPTION + "DESCRIPTION]"
            + "[" + PREFIX_DATE + "DATE_TIME]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "01-01-2024 00:00";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists for the patient.";
    private final Index appointmentIndex;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * @param editPersonDescriptor details to edit the person with
     */
    public EditAppointmentCommand(Index apptIndex, EditAppointmentDescriptor editPersonDescriptor) {
        requireNonNull(apptIndex);
        requireNonNull(editPersonDescriptor);

        this.appointmentIndex = apptIndex;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> appointmentList = model.getFilteredAppointmentList();
        List<Doctor> doctorList = model.getFilteredDoctorList();

        int zeroBasedAppointmentIndex = appointmentIndex.getZeroBased();
        if (zeroBasedAppointmentIndex >= appointmentList.size() || zeroBasedAppointmentIndex < 0) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }
        // Create edited appointment
        Appointment appointmentToEdit = appointmentList.get(zeroBasedAppointmentIndex);
        Person patient = appointmentToEdit.getPerson();
        Doctor targetDoctor = getDoctor(doctorList, new Name(appointmentToEdit.getName()));

        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor,
                patient, targetDoctor.getName().toString());

        for (Doctor doctor : doctorList) {
            if (doctor.equals(targetDoctor)) {
                // skip if current doctor is target doctor
                continue;
            }
            // checks if a doctor has an appointment on that timeslot already
            if (doctor.hasAppointmentOnTimeslot(editedAppointment.getDateTime())) {
                throw new CommandException(String.format(MESSAGE_TIMESLOT_TAKEN, doctor.getName()));
            }
        }
        // if you are editing JUST time
        if (patient.hasAppointmentOnTimeslot(editedAppointment)
                && editAppointmentDescriptor.getDescription().isEmpty()) {
            throw new CommandException(MESSAGE_SAME_APPOINTMENT_TIME);
        }
        if (appointmentToEdit.isSameAppointment(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
        // JUST editing time of appointment
        if (targetDoctor.hasAppointment(editedAppointment) && editAppointmentDescriptor.getDescription().isEmpty()) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT_DOCTOR);
        }
        if (!editAppointmentDescriptor.getDescription().isEmpty() && targetDoctor.hasAppointment(editedAppointment)
                && !editAppointmentDescriptor.getDateTime().isEmpty()) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT_DOCTOR);
        }

        // Update appointment in patient
        int appointmentIndexInPatient = patient.getAppointments().indexOf(appointmentToEdit);
        patient.editAppointment(appointmentIndexInPatient, editedAppointment);
        // update appointment in Doctor
        targetDoctor.editAppointment(appointmentToEdit, editedAppointment);

        // Add available timeslot and remove previous timeslot IF model list same date
        updateModelTimeslotList(model, appointmentToEdit, editedAppointment);
        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS,
                Messages.formatAppointment(editedAppointment)));
    }

    /**
     * Updates the Timeslot list currently displayed in the model
     * @param model The model we are facing
     * @param appointmentToEdit Appointment instance we are editing from
     * @param editedAppointment Appointment instance we want to edit to
     */
    private void updateModelTimeslotList(Model model, Appointment appointmentToEdit, Appointment editedAppointment) {
        if (!(model.getAvailableTimeSlotList().size() == 0)) {
            LocalDate currDate = model.getAvailableTimeSlotList().get(0).getDate();
            LocalDate prevApptDate = appointmentToEdit.getDateTime().toLocalDate();
            LocalDate apptDate = editedAppointment.getDateTime().toLocalDate();
            if (prevApptDate.equals(currDate)) {
                Timeslot timeslotToAdd = new Timeslot(prevApptDate, appointmentToEdit.getDateTime().getHour());
                model.addAvailableTimeSlot(timeslotToAdd);
            }
            if (apptDate.equals(currDate)) {
                Timeslot timeslotToRemove = new Timeslot(editedAppointment.getDateTime().toLocalDate(),
                        editedAppointment.getDateTime().getHour());
                model.removeAvailableTimeSlot(timeslotToRemove);
            }
        }
    }

    /**
     * Returns Doctor of the doctorList by the name of the doctor.
     */
    public Doctor getDoctor(List<Doctor> doctorList, Name doctorName) {
        Doctor targetDoctor = null;
        for (Doctor doctor : doctorList) {
            if (doctor.getName().equals(doctorName)) {
                targetDoctor = doctor;
                break;
            }
        }
        if (targetDoctor == null) {
            throw new RuntimeException();
        }
        return targetDoctor;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment apptToEdit, EditAppointmentDescriptor editApptDesc,
                                                       Person editedPerson, String doctorName) {
        assert apptToEdit != null;

        String updatedDescription = editApptDesc.getDescription().orElse(apptToEdit.getDescription());
        LocalDateTime updatedDateTime = editApptDesc.getDateTime().orElse(apptToEdit.getDateTime());

        return new Appointment(updatedDescription, updatedDateTime, editedPerson, doctorName);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAppointmentCommand)) {
            return false;
        }

        EditAppointmentCommand otherEditAppointmentCommand = (EditAppointmentCommand) other;
        return appointmentIndex.equals(otherEditAppointmentCommand.appointmentIndex)
                && editAppointmentDescriptor.equals(otherEditAppointmentCommand.editAppointmentDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("appointmentIndex", appointmentIndex)
                .add("editAppointmentDescriptor", editAppointmentDescriptor)
                .toString();
    }

    /**
     * Stores the Appointment to edit the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient.
     */
    public static class EditAppointmentDescriptor {
        private String description;
        private LocalDateTime dateTime;
        private Person patient;

        public EditAppointmentDescriptor() {}

        /**
         * Copy constructor.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setDescription(toCopy.description);
            setDateTime(toCopy.dateTime);
            setPatient(toCopy.patient);
        }
        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(description, dateTime);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
        }

        public Optional<LocalDateTime> getDateTime() {
            return Optional.ofNullable(dateTime);
        }

        public void setPatient(Person patient) {
            this.patient = patient;
        }

        public Optional<Person> getPatient() {
            return Optional.ofNullable(patient);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentCommand.EditAppointmentDescriptor)) {
                return false;
            }
            EditAppointmentCommand.EditAppointmentDescriptor otherAppointmentDescriptor =
                    (EditAppointmentCommand.EditAppointmentDescriptor) other;
            return Objects.equals(description, otherAppointmentDescriptor.description)
                    && Objects.equals(dateTime, otherAppointmentDescriptor.dateTime);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("description", description)
                    .add("dateTime", dateTime)
                    .toString();
        }
    }
}
