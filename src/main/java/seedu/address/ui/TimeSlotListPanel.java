package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.timeslots.Timeslot;

/**
 * Panel containing the list of appointments.
 */
public class TimeSlotListPanel extends UiPart<Region> {
    private static final String FXML = "TimeSlotListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(TimeSlotListPanel.class);

    @FXML
    private ListView<Timeslot> timeslotsListView;

    @FXML
    private Label date;

    /**
     * Creates a {@code AppointmentListPanel} with the given {@code ObservableList}.
     */
    public TimeSlotListPanel(ObservableList<Timeslot> timeslotList) {
        super(FXML);
        timeslotsListView.setItems(timeslotList);
        timeslotsListView.setCellFactory(listView -> new TimeSlotListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Appointment} using a {@code AppointmentCard}.
     */
    class TimeSlotListViewCell extends ListCell<Timeslot> {
        @Override
        protected void updateItem(Timeslot timeslot, boolean empty) {
            super.updateItem(timeslot, empty);

            if (empty || timeslot == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new TimeSlotCard(timeslot).getRoot());
                date.setText("Available slots on " + timeslot.getDate().toString());
            }
            if (timeslotsListView.getItems().size() == 0) {
                date.setText("Timeslot tab deactivated");
            }
        }
    }
}
