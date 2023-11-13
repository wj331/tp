package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.timeslots.Timeslot;

/**
 * A ViewAvailableCommand instance which user can use to view available timeslot on a specified date
 */
public class ViewAvailableCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays all the available timeslot on given DATE.\n"
            + "Parameters: DATE (must be given in dd-MM-yyyy exactly).\n"
            + "Example: " + COMMAND_WORD + " /on 01-02-2024";
    private static Logger logger = Logger.getLogger("ViewAvailableCommandLogger");
    private Predicate<Timeslot> predicate;
    private Predicate<Appointment> apptPredicate;
    private LocalDate dateEntered;

    /**
     * Constructor for ViewAvailableCommand class
     * @param predicate A predicate instance for timeslot
     * @param apptPredicate A predicate instance for appointments
     * @param dateEntered Date which the user specified
     */
    public ViewAvailableCommand(Predicate<Timeslot> predicate, Predicate<Appointment> apptPredicate,
                                LocalDate dateEntered) {
        this.predicate = predicate;
        this.apptPredicate = apptPredicate;
        this.dateEntered = dateEntered;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.resetAvailableTimeSlot();
        model.updateFilteredAppointmentList(apptPredicate);
        ObservableList<Appointment> appointmentList = model.getFilteredAppointmentList();
        requireNonNull(appointmentList);
        Set<Integer> unavailableTimeslots = populateUnavailableTimeslot(appointmentList);
        addAvailableTimeslotsToModel(unavailableTimeslots, model);
        logger.log(Level.INFO,
                "ViewAvailableCommand:: current available timeslots: " + model.getAvailableTimeSlotList());

        boolean emptyList = model.getAvailableTimeSlotList().size() == 0;
        if (emptyList) {
            logger.log(Level.WARNING, "No available timeslots!");
            return new CommandResult(String.format(Messages.MESSAGE_NO_AVAILABLE_TIMESLOTS_OVERVIEW,
                    dateEntered, dateEntered, dateEntered));
        }
        // Return success command in command prompt
        return new CommandResult(String.format(Messages.MESSAGE_AVAILABLE_TIMESLOTS_FOUND_OVERVIEW,
                dateEntered, dateEntered, dateEntered));
    }

    /**
     * Adds unavailable timeslots to the empty hashset based on present appointments
     * @param appointmentList ObservableList of all appointments already made
     * @return Set of Integer of unavailable timings
     */
    public Set<Integer> populateUnavailableTimeslot(ObservableList<Appointment> appointmentList) {
        Set<Integer> unavailableTimeslots = new HashSet<>();
        for (int i = 0; i < appointmentList.size(); i++) {
            Appointment currAppt = appointmentList.get(i);
            unavailableTimeslots.add(currAppt.getDateTime().getHour());
        }
        logger.log(Level.INFO, "Finished populating unavailable timeslots!");
        return unavailableTimeslots;
    }

    /**
     * Adds available timeslots to the model
     * @param unavailableTimeslots Set of unavailable timings from previous method
     * @param model Current model we have
     */
    public void addAvailableTimeslotsToModel(Set<Integer> unavailableTimeslots, Model model) {
        int startTime = 9;
        int endTime = 18;
        for (int i = startTime; i < endTime; i++) {
            if (unavailableTimeslots.contains(i)) {
                continue;
            }
            Timeslot timeslot = new Timeslot(dateEntered, i);
            model.addAvailableTimeSlot(timeslot);
        }
        logger.log(Level.INFO, "Finished adding available timeslots to model!");
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewAvailableCommand)) {
            return false;
        }
        ViewAvailableCommand otherViewCommand = (ViewAvailableCommand) other;
        return predicate.equals(otherViewCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
