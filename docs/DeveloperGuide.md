---
layout: default.md
title: "Developer Guide"
pageNav: 3
---

# ClinicAssistant Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

Base code adapted from [AY23/24 CS2103T Github](https://github.com/nus-cs2103-AY2324S1/tp)

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

<div style="page-break-after: always;"></div>

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2324S1-CS2103T-W09-3/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>
<br>
The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `DoctorlistPanel`, `AppointmentListPanel`, `TimeSlotListPanel`, etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2324S1-CS2103T-W09-3/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2324S1-CS2103T-W09-3/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person`, `Appointment`, `Doctor`, and `TimeSlot` object residing in the `Model`.

<div style="page-break-after: always;"></div>

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

<div style="page-break-after: always;"></div>

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>

<div style="page-break-after: always;"></div>

### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add feature

#### Implementation

The add feature allows users to add a patient, doctor or appointment to the clinic records.

This addition is done using `AddCommand`, `AddDoctorCommand` and `AddAppointmentCommand`.

Given below is an example usage scenario for `AddCommand` and how the mechanism behaves at each step.

Step 1. The user launches the application. All patients are shown on the application as an indexed list.
Step 2. The user executes `add n/David …` to add a new patient. <br>
Step 3. The `AddCommandParser` checks if the fields are valid, and creates an `AddCommand` object. <br>
Step 4. This `AddCommand` checks if adding the new Patient will cause duplicate Patients. <br>
Step 5. The `AddCommand` calls `Model#addPerson()`, which adds the patient to the UniquePersonList in ModelManager. <br>
Step 6. The application will then save the patient into the `UniquePersonList` and display the patient added. <br>

The sequence diagram below shows how the add command works: <br>
<puml src="diagrams/AddCommand.puml" alt="AddCommand" />

#### Design considerations:
**Aspect: How a Patient is going to be saved:**

**Alternative 1 (current choice)**: Patient is its own class containing detailed patient information.
* Pros: Organised using Object-Oriented Paradigms.
* Cons: Bugs may be tedious to trace.

**Alternative 2:** Patient is just a String with his/her name saved inside an ArrayList.
* Pros: Will be much simpler to implement and maintain. <br>
* Cons: Does not mirror real-life scenarios where patients are identified by their unique NRICs,
  or when patient information needs to be searched for.

**Aspect: Storage of Appointments**

**Alternative 1 (current choice):** Appointment is its own class containing detailed information on the appointment.
* Pros: Aligned with the approach for patients. Easier to build upon for future implementations.
* Cons: May introduce complexities and higher lines of code.

**Alternative 2:** Appointments as strings in an ArrayList.
* Pros: Simpler implementation.
* Cons: Less adaptable for future developments involving the Appointment Class.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### Edit feature

#### Implementation

The edit feature allows users to edit a patient or appointment.
The edit commands make use of the given index identify the patient or appointment to be edited.
The editing is done through `EditCommand` and `EditAppointmentCommand`.

Given below is an example usage scenario for `EditCommand` and how the mechanism behaves at each step.

Step 1. The user launches the application. All patients are shown on the application as an indexed list.

Step 2. The user executes `edit 1 /n John` to edit te first person in the list to have the name `John`.
The `EditCommandParser` creates an `EditPersonDescriptor` for the `EditCommand`.

Step 3. The `EditCommand` retrieves the old patient from the `Model`
and creates the edited `Patient` using the `EditPersonDescriptor`.

Step 4. The `EditCommand` checks if the fields are valid and if replacing the edited `Person` will cause duplicate `Person`s.

Step 5. The `EditCommand` calls `Model#setPerson`, which replaces the patient in the `UniquePersonList` in `ModelManager`.

The sequence diagram below shows how the `EditXXXCommandParser` works:

<puml src="diagrams/EditCommandParserSeqDiagram.puml" alt="EditCommandParserSeqDiagram" />

The seqence diagram below shows how the `EditXXXCommand` works:

<puml src="diagrams/EditCommandSeqDiagram.puml" alt="EditCommandSeqDiagram" />

The `EditAppointmentCommand` works similarly to the `EditCommand` as described in the example and diagram above.
The only difference is the parameters needed for this command.
The user can either edit the date or description of an appointment.
This difference happens in Step 2: <br>
The user executes the `edit_appt 1 /d changed to x-ray scan /on 01-01-2024 09:00` command
to change the description and date of the first appointment on the list.


#### Design considerations:

**Aspect: How Appointments are going to be edited:**

* **Alternative 1 (current choice):** Appointment is Edited based on its Index in the overall Appointment List
    * Pros: One centralized Appointment List for all Edit Appointment operations
    * Cons: Harder to implement

* **Alternative 2:** An additional Index field of the Patients Index is to be given, followed by his/her Appointment to edit
    * Pros: Appointment to be edited is specified to the specific Patient index input
    * Cons: Harder for the user to visualise which Appointment he is going to edit

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### Delete Patient feature

#### Implementation

The delete feature is now separated for patients, doctors and appointments.
Deleting a patient and doctors delete their corresponding appointments.

The delete command deletes a patient, doctor or appointment identified by their index in the displayed list.
The deletion is done through the `deletePerson`, `deleteDoctor` and `deleteAppointment` functions in `ModelManager`.

Given below is an example usage scenario for `DeleteCommand` and how this delete mechanism behaves.

Step 1. The user launches the application. All patients are shown on the application as an indexed list.

Step 2. The user executes `delete 3` command to delete the third patient in the displayed list.
The `DeleteCommandParser` creates a `DeleteCommand`.

Step 3. The execution of the `DeleteCommand` calls `Model#deletePerson(personToDelete)` to delete the person from the filtered list of patients.

The following sequence diagram shows how the `DeleteCommand` class works.

<puml src="diagrams/DeleteCommandDiagram.puml" alt="DeleteCommand UML"/>

<box type="info" seamless>

**Note:**
* The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
* The command will not execute for any index that is **out-of-bounds**.
</box>

#### Design considerations:

**Aspect: How convenient it is for clinic staff to delete:**

* **Alternative 1 (current choice):** Delete based on `INDEX` shown on the present list
    * Pros: Intuitive and easy for clinic staff to use.
    * Cons: List index may change when staff first use `list` or `find`.

* **Alternative 2:** Delete based on `name` of patient
    * Pros: Will be more accurate when deleting a patient.
    * Cons: Typing out patient names has higher room for error and takes a longer time.
    * Cons: User will face issues when trying to delete patients with the same name.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### Delete Appointment feature

#### Implementation

This delete command deletes an appointment identified by its index in the displayed list.
This deletion is done through the `deleteAppointment` function in `ModelManager`.
Its mechanism is facilitated by `DeleteAppointmentCommand`, `UniqueAppointmentList` and the `LogicManager` Class.
This deletion mechanism is largely similar to the delete patient mechanism,
with the only difference being that the appointment is to be deleted from the Patient and Doctor classes.

Given below is an example usage scenario for `DeleteAppointmentCommand` and how this delete mechanism behaves.

Step 1. The user launches the application. All patients are shown on the application as an indexed list.

Step 2. The user executes `delete_appt 3` command to delete the third appointment in the displayed list.
The `DeleteAppointmentCommandParser` creates a `DeleteAppointmentCommand`.

Step 3. The execution of the `DeleteAppointmentCommand` retrieves the third appointment in the list of unique appointments.

Step 4. The methods `deletePatientAppointment` and `deleteDoctorAppointment` in the `DeleteCommand` class are called.
The appointment linked to the corresponding doctor and patient is removed.

Step 5. The execution of the `DeleteAppointmentCommand` calls `Model#deleteAppointment(appointmentToDelete)` to delete the person from the filtered list of patients.

The following sequence diagram shows how the `DeleteAppointmentCommand` class works.

<puml src="diagrams/DeleteAppointments.puml" alt="DeleteAppointment" />

<box type="info" seamless>

**Note:**
* The lifeline for `DeleteAppointmentCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
* The command will not execute for any index that is **out-of-bounds**.
  </box>


#### Design considerations:

**Aspect: How Appointments are being deleted:**

* **Alternative 1 (current choice):** Appointment is being stored in 3 different places inside a patient object, a doctor object, and the `UniqueAppointmentList` and are being deleted once at each object.
    * Pros: Easy to understand
    * Cons: Can be easily filled with bugs and takes a lot of effort to implement.

* **Alternative 2:** have only one List of Appointments and by deleting it there everywhere else appointment will be deleted.
    * Pros: Will be harder for bugs to happen.
    * Cons: Will be harder to implement.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### List feature

#### Implementation

The list appointments feature is added on from the `Appointment` class. The `list_appt` command will list out all
appointments in the Clinic Assistant database.

Firstly, to implement the list appointments command, we have to edit the current frontend to add a new Panel to display
the appointments. This is done by adding a new `AppointmentListPanel` class to the `seedu.address.ui` package. This
class is then added to the `MainWindow` class to display the appointments. The `AppointmentCard` class is also added to
display the individual appointments inside the `AppointmentListPanel`. In addition, the corresponding `fxml` files are
also added to the `view` folder to display the appointments.

To implement the backend of the list appointments command, we first have to store the appointments in the database.
Like the implementation of storing of `Person`, we keep a `UniqueAppointmentList` in the `Model` class to store the
appointments. The `UniqueAppointmentList` class is similar to the `UniquePersonList` class, except that it stores
`Appointment` objects instead of `Person` objects. What we show on the frontend is the filtered list of appointments
from the `UniqueAppointmentList` class.

A `ListAppointmentCommand` class is then created to handle the `list_appt` command. This class is similar to the
`ListCommand` class, except that it handles the `Appointment` objects instead of `Person` objects. The
`ListAppointmentCommand` class will then be called by the `LogicManager` class to execute the `list_appt` command.
What this command does is that it changes the predicate to the filtered list of appointments to show all the
appointments in the database.

#### Alternatives considered

**Aspect: How to store and maintain list of appointments:**

* **Alternative 1 (current choice):** Save the list of appointments as a `UniqueAppointmentList` and filter it when needed.
    * Pros: Easy to implement - similar to the current implementation of `Person`
    * Cons: More memory is used to store the list of appointments

* **Alternative 2:** Get the list of appointments by parsing through each `Person` object in the `UniquePersonList`
    * Pros: Will use less memory
    * Cons: Will be slower because we have to iterate through all the `Person` objects to get the appointments each time

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### Find feature

#### Implementation

The find feature is now separated for the patients and appointments.

The `FindCommand`, `FindByNricCommand` and `FindAppointmentsCommand` make use of predicates to filter the list of patients and 
appointments. 
Each command parser parses the field prefixes and the inputs keyed in by the user. 
The command is then created, with the predicates passed in as parameters. 
This predicate is used to update the filtered list of patients or appointments.

Given below is an example usage scenario for `FindCommand` and how the find mechanism behaves at each step.

Step 1. The user launches the application. 
The filtered patients list is initialized with an “always true” predicate for all the patient fields 
and all patients are shown to the user as an indexed list on the patient list panel. <br>
Step 2. The user executes the `find Alex` command to find all patients with the name field containing “Alex”. <br>
Step 3. The keyword "Alex" is parsed by the `FindCommandParser`, and a `FindCommand` is created, together with a `NameContainsKeywordPredicate`. <br> 
Step 4. The `FindCommand` calls `Model#updateFilteredPatientList(predicate)` to set the `predicate` of the filtered patients list to the new predicate created by the command. 
The application displays the list of patients with names containing “Alex” on the patient list panel.

The following sequence diagram shows how the `FindCommand` works:

<puml src="diagrams/FindCommandSequenceDiagram.puml" alt="FindCommand UML"/>

The `FindAppointmentsCommand` works similarly to the `FindCommand` as described in the example and diagram above.
The only difference is the parameters needed for this command. 
The user can either find appointments by a patient's name, or the date of an appointment.
This difference happens in Step 2: <br>
The user executes the `find_appt /on 01-01-2024` command to find all appointments on Jan 01, 2024.
The user can also execute `find_appt /n Alex` to find all appointments for Alex.

#### Alternatives considered
**Aspect: Implementation and usability**

* **Alternative 1 (current choice):** Create separate command for finding patients using different fields, and for finding appointments.
    * Pros: Is a more organised way of searching for patients and appointments.
    * Cons: Harder to implement, since we need to create various commands, parsers and predicates.

* **Alternative 2:** Create only one command for finding patients and appointments using the command word `find`.
    * Pros: Easier to implement.
    * Cons: Too many prefixes to type in one command if we want to find by multiple fields, which can make the command very long.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

### View Available Timeslots feature

#### Implementation

The view available timeslots feature displays all available timeslots on the specific date. 
This mechanism is facilitated by the `UniqueTimeslotList` and `Timeslot` class.
A `view` command input takes in a `date` and displays all available timeslots for that `date`. This is mainly used by users
to identify available timeslots instantly which they can use to book appointments on.

Given below is an example usage scenario for `ViewAvailableCommand` and how view available timeslots mechanism behaves.

Step 1. The user launches the application. Patients and appointments already exist in the database and shown on the indexed list.

Step 2. The user executes `view /on 02-01-2024` command to view all available timeslots in the displayed list for 02 Jan 2024.
The `ViewAvailableCommandParser` creates a `ViewAvailableCommand`.

Step 3. The execution of the `ViewAvailableCommand` retrieves all available timeslots on 02 Jan 2024 in the Timeslots tab.

Step 4. The methods `populateUnavailableTimeslot` and `addAvailableTimeslotsToModel` in the `ViewAvailableCommand` class are called.
The available timeslots not in the appointment list is added to the `UniqueTimeslotList`.

Step 5. The execution of the `ViewAvailableTimeslot` calls `Model#updateFilteredAvailableTimeslot` to update the new list of available timeslots in the Timeslots tab.

<puml src="diagrams/ViewAvailableDiagram.puml" alt="ViewAvailableCommand UML" width = "100000" />

**Note:**
* The lifeline for `ViewAvailableCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
* As method names for this feature is a little long, due to the limitation of PlantUML, kindly zoom in to have a better view of this sequence diagram.
  </box>

#### Design considerations:

**Aspect: What Timeslots will be added:**

* **Alternative 1 (current choice):** Loop through all appointments in the appointment list and only add timeslots which are available
    * Pros: Allows to retrieve the latest appointments and add available timeslots accurately.
    * Cons: Increased coupling between timeslots and appointment.

* **Alternative 2:** Add the timeslots taken by appointments directly
    * Pros: Easier to implement as we only need to get time from appointments.
    * Cons: Harder for user to visualise exactly which timeslot is available and can be used to book appointments.

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile: Clinic Assistants**

* has a need to manage a significant number of patients
* prefer desktop apps over other types
* can type fast
* prefers typing to mouse interactions

**Value proposition**: manage contacts faster than traditional pen-and-paper record taking


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                      | I want to …​                                | So that I can…​                                        |
|----------|------------------------------|---------------------------------------------|--------------------------------------------------------|
| `* * *`  | busy clinic assistant        | register new patients to the database       | reduce the use of physical documents and storage costs |
| `* *`    | clinic assistant             | delete patient records                      | keep our records up-to-date                            |
| `* * *`  | clinic assistant             | add new appointments to specific patients   | keep track of their appointments                       |
| `* * *`  | busy clinic assistant        | delete a specific appointment               | update frequent cancellations of appointments          |
| `* *`    | clinic assistant             | view all the patient records                | not need to memorise all the patients of the clinic    |
| `* * *`  | overwhelmed clinic assistant | view a specific patient's details           | save time                                              |
| `* *`    | clinic assistant             | edit an appointment                         | adjust changes to appointments                         |
| `* * *`  | clinic assistant             | keep track of a patient's medical history   | be more conscious of patients' conditions              |
| `* * *`  | stressed clinic assistant    | register new doctors to the database easily | reduce workload whenever a new doctor joins the clinic |
| `* *`    | clinic assistant             | delete a specific doctor                    | keep the doctor list up to date                        |
| `* *`    | busy clinic assistant        | view all available timeslots on a date      | easily filter for available time to book appointments  |
| `*`      | clinic assistant             | edit a doctor's information                 | update any changes to doctors in the clinic            |
| `* *`    | clinic assistant             | edit a patient's information                | update any changes to patients in the clinic           |

<div style="page-break-after: always;"></div>

### Use cases

(For all use cases below, the **System** is the `ClinicAssistant` and the **Actor** is the `clinic assistant`, unless specified otherwise)

**Use case 1: Add a patient**

**MSS**

1.  New patient visits the clinic
2.  Patient is not in the clinic database
3.  Clinic staff adds this new patient into the clinic database
4.  ClinicAssistant shows a confirmation message

    Use case ends.

**Extensions**

* 3a. The given input is invalid.

    * 3a1. ClinicAssistant shows an error message.

      Use case resumes at step 3.

**Use case 2: List all patients**

**MSS**

1.  Clinic staff wants to see all patient records.
2.  Clinic staff requests to list patients.
3.  ClinicAssistant shows a list of patients.

    Use case ends.

**Use case 3: List a specific patient**

**MSS**

1. Patient visits the clinic.
2. Clinic staff needs the information of this specific patient.
3. Clinic staff inserts the patient's details.
4. ClinicAssistant retrieves the patient's information for the clinic staff.

   Use case ends.

**Extensions**

* 3a. The patient cannot be found.
    * 3a1. ClinicAssistant shows an error message.

      Use case resumes at step 3.

**Use case 4: Edit details of a specific patient**

**MSS**

1. Patient visits the clinic.
2. Patient informs clinic staff of changes to his/her details.
3. Clinic staff searches for the patient in Clinic Assistant (by name or NRIC).
4. Clinic staff edits the patient's details in Clinic Assistant.
5. ClinicAssistant shows a confirmation message.

   Use case ends.

**Extensions**

* 3a. The patient cannot be found.
    * 3a1. ClinicAssistant shows an error message.
    * 3a2. Clinic staff checks if the name or NRIC is correct.
    * 3a3. Clinic staff searches for the patient again.

      Use case resumes at step 4.

* 4a. The given input to edit the patient's details is invalid.
    * 4a1. ClinicAssistant shows an error message.
    * 4a2. Clinic staff checks and enters the correct input.

      Use case resumes at step 5.

**Use case 5: Delete a patient** <br>
**Actor:** Clinic staff <br>
**MSS**

1. User requests to list all patients.
2. ClinicAssistant returns a list of all patients from the database.
3. User requests to delete a specific person in the list with his index.
4. ClinicAssistant deletes the person from the database.

   Use case ends.

**Extensions**
* 3a. The input index is invalid.
    * 3a1. ClinicAssistant shows an error message.
    * 3a2. User enters new index.
    * Steps 3a1-3a2 repeated until the index entered is correct.

      Use case resumes at step 4.

**Use case 6: Viewing available timeslots to book appointment** <br>
**Actor:** Clinic staff, Patient <br>
**MSS**

1. User needs to book an appointment for a patient.
2. User chooses a date and enters it.
3. ClinicAssistant returns a list of available timeslots on that date.
4. User finds an available timeslot from the given list.
5. User proceeds to book an appointment for the patient on that specific date and timeslot

   Use case ends.

**Extensions**
* 2b. Date entered is invalid.
    * 2b1. ClinicAssistant shows an error message and requests for correct date.
    * 2b2. User enters a new date.
    * Steps 2b1 - 2b2 are repeated until date entered is correct.

      Use case resumes at step 3.

* 3a. ClinicAssistant returns an empty list of available timeslots.
    * 3a1. User now has to enter a new date .
    * Step 3a1 is repeated until date entered has list of at least 1 available timeslot.

      Use case resumes at step 4.

**Use case 6: Add a doctor** <br>
**Actor:** Clinic staff, Doctor <br>
**Preconditions:** there is no doctor with the exact same name already in the system. <br>
**MSS**

1. A doctor joins the clinic.
2. Doctor is not in ClinicRecords.
3. Clinic staff asks the doctor for the required information.
4. Clinic staff enters the add doctor command with the information.
5. ClinicAssistant adds this new doctor into the ClinicRecords.
6. ClinicAssistant shows a confirmation message.

   Use case ends.

**Extensions:**
* 4a. ClinicAssistant detects an error in the entered information.
    * 4a1. ClinicAssistant shows error message.
    * 4a2. Clinic staff enters new information to the add doctor command.
    * Steps 4a1 and 4a2 are repeated until the information required is entered correctly.
    
      Use Case resumes at step 5.

**Use case 7: Delete a doctor** <br>
**Actor:** Clinic staff, Doctor(multiple doctors) <br>
**Preconditions:** at least one Doctor is in the system. <br>

**MSS**

1. A doctor chooses to leave the clinic.
2. Clinic staff asks the doctor's required information.
3. Clinic staff lists all the doctor in the system.
4. Clinic staff searches for the correct index of the doctor.
5. Clinic staff enter the delete doctor command.
6. ClinicAssistant deletes the doctor from the system and shows the success message.

   Use case ends.

**Extensions:**
* 5a. ClinicAssistant detects an error in the entered index.
    * 5a1. ClinicAssistant shows error message.
    * 5a2. Clinic staff enters a new delete doctor command with a different index.
    * Steps 5a1 and 5a2 are repeated until the index entered is correct.
  
      Use Case resumes at step 6.

**Use case 8: Delete an appointment** <br>
**Actor:** Clinic staff, Patient <br>
**Preconditions:** at least one Doctor is in the system, the Patient is already in the system, the appointment exists in the system. <br>

**MSS**

1. A patient wants to cancel an appointment.
2. Clinic staff asks for the patient's name and appointment date.
3. Clinic staff <u>enters the find appointment command</u>.
4. ClinicAssistant shows the list of appointments for the find appointment command.
5. Clinic staff asks the patient for the time of appointment.
6. Clinic staff searches for the right appointment.
7. Clinic staff enters the delete appointment command.
8. ClinicAssistant deletes the appointment and shows the success message.

   Use case ends.

**Extensions:**
* 7a. ClinicAssistant detects an error in the entered index.
    * 7a1. ClinicAssistant shows error message.
    * 7a2. Clinic staff enters a new delete appointment command with a different index.
    * Steps 7a1 and 7a2 are repeated until the index entered is correct.

      Use Case resumes at step 8.

<div style="page-break-after: always;"></div>

### Non-Functional Requirements

1.  System should be able to find a patient's information in less than 2 seconds
2.  System should be able to hold up to 1000 patients without a noticeable sluggishness in performance for typical usage
3.  System should be able to view available timeslots on a valid date in about 2 seconds
4.  User-friendly and easy to use without much guidance
5.  System works on both 32-bit and 64-bit environments
6.  System should be usable by a beginner with basic computer knowledge
7.  System uses extremely secure security software to keep patient data safe and secure
8.  System supports the use of English in both UK and US languages


### Glossary
* **GUI**: Graphical User Interface, a visual display which the user sees and interacts with
* **API**: Application Programming Interface, functions that allow created applications to access the features of an operating system, application or other service


--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### Adding a Patient
1. Adding a patient <br>
   1. Prerequisite: None
   1. Test case: `add /n Drizzy /p 99090909 /e drake@gmail.com /g F /age 18 /eth Chinese /ic T0123456E /a 901 Shelby Dr` <br>
   Expected: Given patient is added to the database. Details of the added patient shown in the status message.
   1. Test case: `add Drizzy /p 99090909` <br>
   Expected: Given patient is not added. Error details shown in status message.
   3. Other incorrect add commands to try: any commands where any of the mandatory parameters are missing. <br>
   Expected: Similar to previous.

### Editing a Patient

1. Editing a patient's details in the `Patients` list

    1. Prerequisites: At least 1 patient in the `Patients` list. <br>
    Done by listing all patients using the `list` command. <br>
    Or by finding specific patients by name using the `find` command. <br>
    Or by finding specific patients by NRIC using the `find_nric` command. 
    If there are currently no patients in Clinic Assistant, use `add` command to add a patient into the list.

    2. Test case: `edit 1 /p 91234567 /e johndoe@example.com`<br>
       Expected: The phone number and email of the first person in the `Patients` list is updated to the new values. Details of the updated patient is shown in the status message.

    3. Test case: `edit 0`<br>
       Expected: No patient's details is updated. Error details shown in the status message for invalid command format.
    
    4. Test case: `edit 1 /p 12345678`<br>
       Expected: The phone number of the first person in the `Patients` list is not updated. Error details shown in the status message for valid phone number inputs.

    5. Other incorrect edit commands to try: `edit`, `edit x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to test case in step 3.

### Deleting a Patient

1. Deleting a patient from `Patients` list

    1. Prerequisites: List all patients using the `list` command. At least 1 patient in the list. Use `add` to add a patient into the list. 
   
    2. Test case: `delete 1`<br>
        Expected: First patient is deleted from the list. Details of the deleted contact shown in the status message.
   
    3. Test case: `delete 0`<br>
    Expected: No patient is deleted. Error details shown in the status message.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### Adding a Doctor
3. Adding a Doctor to the `Doctor` List.

   1. Prerequisites: Make sure to have the Doctor List Panel on the screen by clicking the Doctors Tab.
   
   2. Test case: `add_doctor /n Dr Lee /p 91236789 /e lee@gmail.com /g M /age 30 /a Prince Gearge Park ` <br>
      Expected: The Doctor will show in the Doctor List in the order of first added to last added. Success message will be shown in the output display.
   
   3. Test case: `add_doctor`
      Expected: No Doctor will be added and a error message will be shown at the output display.

   4. Other incorrect add_doctor commands to try: `add_doctor /n John `, `add_doctor /age 100 `, `...` (where you are missing one of the syntax)<br>
      Expected: Similar to previous.

### Deleting a Doctor

1. Deleting a doctor from `Doctor` list

    1. Prerequisites: List all doctors by clicking on the Doctors tab. At least 1 doctor in the list. Use `add_doctor` to add a doctor into the list.

    2. Test case: `delete_doctor 1`<br>
       Expected: First doctor is deleted from the list. Details of the deleted doctor is shown in the output display.

    3. Test case: `delete_doctor INDEX` (where INDEX is larger than the number of doctors in the list)<br>
       Expected: No doctor is deleted. Error message `The doctor index provided is invalid` will be shown in the output display. 
   
    4. Test case: `delete_doctor 0`<br>
       Expected: No doctor is deleted. Error details shown in the output display.
   
    5. Other incorrect delete commands to try: `delete_doctor`, `delete_doctor x`, `...` (where x is not a positive integer)<br>
       Expected: Similar to previous.

### Adding an Appointment

1. Deleting an appointment from `Appointment` list

    1. Prerequisites: At least 1 Doctor and 1 Patient must be present in the clinic records. Use `add_doctor` & `add` to add a Doctor and Patient respectively into the clinic records.

    2. Test case: `appt /for 1 /doc 1 /d x-ray scan /on 02-01-2024 12:00`<br>
       Expected: An appointment is added to the list. Details of the added appointment is shown in the output display.

    3. Test case: `appt /for 1 /doc 1 /d x-ray scan /on tuesday` (where `DATE_TIME` is invalid)<br>
       Expected: No appointment is added and an error message will be shown in the output display.

    4. Test case: `appt`<br>
       Expected: No appointment is added. Error details shown in the output display.

    5. Other incorrect delete commands to try: `appt /for 0`, `appt /for 1 /on 01-01-2024 12:00`<br>
       Expected: Similar to previous invalid cases.

### Deleting an Appointment

1. Deleting an appointment from `Appointment` list

    1. Prerequisites: List all appointments by clicking on the Appointments tab and use the `list_appt` command. At least 1 appointment in the list. Use `appt` to add an appointment into the list.

    2. Test case: `delete_appt 1`<br>
       Expected: First appointment is deleted from the list. Details of the deleted appointment is shown in the output display.
   
    3. Test case: `delete_appt INDEX` (where `INDEX` is larger than the number of appointments in the list)<br>
       Expected: No appointment is deleted. Error message `The appointment index provided is invalid` will be shown in the output display. 
   
    4. Test case: `delete_appt 0`<br>
       Expected: No appointment is deleted. Error details shown in the output display.

    5. Other incorrect delete commands to try: `delete_appt`, `delete_appt x`, `...` (where x is not a positive integer)<br>
       Expected: Similar to previous.

### View Available Timeslots

1. Viewing available timeslots on a given `DATE`

    1. Prerequisites: None but good to have some appointments made using `appt`

    2. Test case: `view /on 02-01-2024`<br>
       Expected: Displays all available timeslots under the timeslot tab. Success message and `DATE` shown in the status message.

    3. Test case: `view /on 02/02/2024`<br>
       Expected: No timeslots will be displayed. Error details shown in the status message.

    4. Other incorrect view commands to try: `view`, `view /on x`, `...` (where x is past date)<br>
       Expected: Similar to previous.

### Find Patients or Appointments

1. Finding patients using their name
    2. Prerequisite: None but good to have some patients in the list.
    3. Test case: `find Alex` <br>
       Expected: Patient list shows only the patients with the name `Alex`.
    4. Test case: `find 01-01-2024` <br>
       Expected: No patients found. Error details shown in the status message.
    5. Test case: `find T1234567E` <br>
       Expected: Similar to previous.
8. Finding patients using their NRIC
    1. Test case: `find_nric T1234567E` <br>
       Expected: Patient list shows the one patient with the given NRIC.
    2. Test case: `find_nric x12356e` <br>
       Expected: No patients found. Error details shown in the status message.
11. Finding appointments
    1. Test case: `find_appt /on 01-01-2024` <br>
       Expected: Appointment list shows all appointments on Jan 01, 2024.
    2. Test case: `find_appt /n Alex` <br>
       Expected: Appointment list shows all appointments for Alex.
    3. Test case: `find_appt /on 02/02/2024`<br>
       Expected: No appointments will be displayed. Error details shown in the status message.`

### Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix: Planned Enhancements**
In the near future, we hope to be able to enhance our application as stated below.

### Make the name of the Doctor not case sensitive

#### Implementation

Currently when the system receives an add_doctor command they only check whether the string of the name is exactly the same or not.<br>
In the feature a good enhancement would be to make this check not case sensitive to follow the real world better.<br>
This can be done by improving the isSameDoctor feature and replacing the current Name.equal method with a method that considers capital letter also.

### Design consideration

**Aspect: How to check without case sensitivity**

* Create a method to check whether the letters are the same by checking both the letters lower case and upper case char value
    * Pros: can make sure the letters are not affected by case sensiticity during checks
    * Cons: will be slightly tedious to implement.
  
### Allow different doctors to have appointment slots at the same timing

#### Implementation

Currently, different doctors cannot have the appointment slots at the same timing. This is done with the assumption that the GP clinic will only have one doctor and one room at any one time.
However, in the future, we hope to be able to allow different doctors to have appointment slots at the same timing. This will allow the clinic to have multiple doctors and rooms at the same time.

This enhancement will allow the user to add appointments with the same timing as long as the doctor is different.

The `view` Timeslots command will also be updated to show the available timeslots for all doctors.

To implement this, there has to be a check to ensure that the appointment timing does not clash with any other appointments for the same doctor when adding/editing appointments.

#### Design consideration:

**Aspect: How to ensure each doctor can only have 1 appointment for timeslot:**

* When a new appointment is added or edited, check if the appointment timing clashes with any other appointments for the same doctor.
    * Pros: Easy to implement.
    * Cons: Additional check required when adding/editing appointments.


### Change the tab to the corresponding tab of the command

#### Implementation

Currently, when a command is used, the tab the user is on will not switch to the corresponding tab of the command. E.g. if the user is on the `Doctors` tab, entering the `list` command
will not switch the user to the `Patients` tab. In the future, we hope to be able to implement this feature such that the user will be switched to the corresponding tab of the command.

This applies for all commands such as `list_appt`, `add`, `add_doctor`, `edit`, `edit_appt`, `delete`, `delete_doctor`, `delete_appt`, `find`, `find_nric`, `find_appt`, `view`, `appt`, `edit_appt` and `delete_appt`.

This enhancement will allow the user to be switched to the corresponding tab of the command.

This enhancement will improve the user experience as the user will not have to manually switch to the corresponding tab of the command.

#### Design consideration:

**Aspect: How to implement changing of tabs:**

* Create a method to switch to the corresponding tab of the command and call this method whenever a command is used.
    * Pros: Easy to implement.
    * Cons: Additional method required to switch tabs.


### Edit Appointment to include editing of Doctor details

#### Implementation

This enhancement will let the user edit the appointments associated doctor.

This edit command will take in a parameter INDEX which is a positive integer which references to the index of doctors shown on the screen.

Furthermore, it will take in information that the specified doctor's information will be changed to.
This will then change the doctor associated with the appointment the user is editing.

#### Design consideration:

**Aspect: How the doctor is going to be edited:**

* Edit associated doctor based on INDEX shown on the present doctor list. e.g., `edit_appt /doc 2` will edit the doctor associated to the appointment to the second doctor displayed in the Doctor list.
    * Pros: Intuitive for clinic assistants to use
    * Cons: Might be difficult to implement

### Display date in Timeslot tab even on first view call

#### Implementation

Currently, the date in the timeslot tab only appears after calling view command more than once. This is done in our previous release under the assumption that the first call of the command is not ambiguous.

However, in the future, we will change it to instantly display the date whenever we call the view command. This will help to improve user experience, increasing their confidence since date is always displayed.

This enhancement will enhance the user experience when using the view command and the timeslots tab.

To implement this, there needs to be a logic change in the front end of the view available command.

#### Design consideration:

**Aspect: How to ensure user is certain of the date of the available timeslots they are viewing:**

* Whenever a view command is called, display the date at the top of the timeslot tab, even for the first call.
    * Pros: Helps to make the user experience more smooth.
    * Cons: Difficult to implement.

### Change duration of each timeslot to smaller durations

#### Implementation

Currently, the duration of every timeslot is exactly 1 hour. This is done under the assumption that most appointments take 1 hour and to provide doctors with sufficient rest.

However, in the future, we will change it to variable timeslots which clinic assistants can choose depending on the type of appointment. This will help to improve efficiency of the clinic to be able to treat more patients in a day.

To implement this, there needs to be a back end change in the view available command where timeslots added will be in intervals of different durations.

#### Design consideration:

**Aspect: How to ensure clinic can cater to people of different appointment types:**

* Whenever a view command is called, display all time intervals which the doctor is free.
    * Pros: Clinic assistant can view the time interval and book the appointment as necessary.
    * Cons: Difficult to implement.
<div style="page-break-after: always;"></div>

## **Appendix: Future Features**

### Edit Doctor

#### Implementation

This enhancement will let the user edit details of the doctor inside the clinic assistant without deleting or interfering with the appointments that doctor has.

This edit command will take in a parameter INDEX which is a positive integer which references to the index of doctors shown on the screen.

Furthermore, it will take in information that the specified doctor's information will be changed to.
This will create a new Doctor Object and transfer over all the information that isnt specified in the edit command to be the same as the original doctor.

#### Design consideration:

**Aspect: How the doctor object is going to be edited:**

* You can make it so that you change the value of the variables inside the original doctor
    * Pros: save space and improve space and time complexity
    * Cons: Risk introducing unexpected bug as Doctor is no longer immutable

--------------------------------------------------------------------------------------------------------------------
<div style="page-break-after: always;"></div>

## **Appendix: Effort**

The effort required for our team to finish this team project from start to finish was quite substantial. We faced many difficulties at every step and milestone, but in the end we overcame those challenges and prevailed.
The difficulty of this team project was quite high as there are a lot of unexpected challenges that came up. In the first few weeks it was quite smooth as we only had to figure out what kind of product we wanted to make and who we wanted to make it for.
Afterwards is where things get more challenging it turns out that refactoring existing code was more difficult than it seemed. An example, the Person Object needed to be changed to hold more information like nric.
This causes many of the existing test cases from AB3 to break. Another challenge was when we added another entity type class called Appointment. At first, it may seem like it would not be that hard to implement Appointment by referencing the code for the Person Object.
But when we arrived at the storage implementation we were stuck for quite a while. It was quite a challenge to figure out how we could store and reap the Appointment class reliably.

<br>

Although those experiences was frustrating at times, when we finally managed to finish a section, we felt a sense of achievement every single time.
One of these moments is when we managed to figure out how to read and write from the json file when we added Appointments to the project. This achievement was one of the bigger milestone that we crossed  that helped with the implementations of future features.
For example, When the Doctor Object was added to the project it was definitely smoother as we already had experience before hand with appointments. This makes the implementation of Doctor slightly faster as we had overcame these challenges before.
Although the Doctor Object also came with challenges that are unique. This is because the Doctor Object has a deep interaction with the Appointment Objects making it a three-way connection between the three classes. This causes a lot of problems and bugs that took a lot of effort and was very difficult to solve due to the increasingly complex code.

<br>

Another unique challenge was the GUI of the project. Most of us are not familiar with javafx before this, therefore we encountered difficulties not only in coding the gui, but also coming up with the design of it.
We wanted to make it not too cluttered without sacrificing any of the feature we wanted to add. We then went through multiple iterations to figure out what was the best way to get the most out of the UI. We accomplished these eventually by using tab Panes that let
us have a good amount of information stored in the project without sacrificing the readability of it.

<br>

Testing and Debugging was one of the biggest challenges we encountered throughout this project. Debugging was extremely time-consuming and so were finding bugs. We learned that we had quite a lot of room for improvement during the PE-D. This
demo helped us realise things that we have overlooked before as a team. This was an extremely important experience for us as we were forced to not only fix the bugs each member is responsible for, but we also had to cooperate in debugging and testing each other's code. This
part of the project might be the most difficult and time-consuming one as it took a lot of hours to finish and not a lot of time to spare. The moment when we realise that we had fixed most of the issues found during and after the PE-D was one of the biggest achievement in this project, as it signifies the end of this team project.

In conclusion, We learned a lot from this team project. Whether that was teamwork, coding, and time management. We struggled a lot at almost every step of the way, but we prevailed and came out of it as better programmers.
We learned a lot about various programming and software engineering practices and techniques like, testing, uml diagrams, user stories, defensive coding. By using everything that we learned this past semester we are able to come up with a good product with important features that prioritized the user's needs first.
