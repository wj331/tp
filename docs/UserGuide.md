---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---

# ClinicAssistant User Guide

ClinicAssistant is a **desktop app for managing patients' records, optimized for use via a  Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).
ClinicAssistant requires a lot of typing of various prefixes to add, list or store patients or appointments accordingly.

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `clinicassistant.jar` from [here](https://github.com/AY2324S1-CS2103T-W09-3/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your ClinicAssistant.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar clinicassistant.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all patients in the ClinicAssistant.

   * `add /n John Doe /p 98765432 /e johnd@example.com /g M /age 22 /eth Chinese /ic T1234567E /a 311, Clementi Ave 2, #02-25 /t allergic to the sun /t history of knee problems` :
   Adds a patient named `John Doe` to ClinicAssistant the specified details such as male `M`, ethnicity `Chinese` and IC number `T1234567E`.

   * `delete 3` : Deletes the 3rd patient shown in the current list of ClinicAssistant.
   
   * `appt /for 1 /doc 1 /d description details /on 02-01-2024 12:00` : Adds an appointment with specified Doctor index, description and time to the patient identified with `INDEX` 1 in the list.

   * `view /on 02-01-2024` : Displays all available timeslots that can be booked for an appointment on 02 Jan 2024.

   * `clear` : Deletes all patients.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add /n NAME`, `NAME` is a parameter which can be used as `add /n John Doe`.

* Items in square brackets are optional.<br>
  e.g `/n NAME [/t TAG]` can be used as `/n John Doe /t friend` or as `/n John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* All commands are case-sensitive. <br>
  e.g. `delete 1` will work, but `Delete 1` or `DELETE 1` will not work.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
</box>


### Adding a person: `add`

Adds a patient to the database. A patient has a **name, phone number, email, gender, age, ethnicity, IC** and **address**.

Format: `add /n NAME /p PHONE_NUMBER /e EMAIL /g GENDER /a AGE /e ETHNICITY /ic NRIC /a ADDRESS [/t TAG]…​`

Examples:
* `add /n John Doe /p 91234567 /e john@gmail.com /g M /a 22 /e Chinese /ic T1234567G /a Clementi /t allergic to pollen`

### Adding a doctor: `add_doctor`

**Note:**
* Make sure the details of the doctor is correct as there is a limitation where you can not edit the details of a doctor yet
* If you happen to add a Doctor with the wrong information do not worry as you can just delete the doctor by its index and add it again with the correct information this time
* If you want to edit a doctor's details but that doctor already has appointment you need to make sure that the doctor has no more appointment this is because if you delete a doctor with appointments those appointments will deleted as well

Adds a doctor to the database. A doctor has a **name, phone number, email, gender, age, and **address**.

Format: `add_doctor /n NAME /p PHONE_NUMBER /e EMAIL /g GENDER /age AGE /a ADDRESS`

Examples:
* `add_doctor /n Dr Lee /p 81824444 /e drlee@gmail.com /g M /age 30 /a 901 Shelby Drive`

### Adding an appointment: `appt`

Adds a new appointment for a specific patient at index.

Format: `appt /for INDEX /doc INDEX /d DESCRIPTION /on DATETIME`

**Note:**
* Doctor index must be valid, i.e. there must also be a doctor added first before an Appointment can be made
* Upcoming date of this new appointment must be included.
* `DATETIME` is in the format dd-MM-yyyy HH:mm e.g. 01-01-2024 12:00.
* `DATETIME` must be a date after the **current** time
* There can only be 1 appointment per timeslot, regardless of which doctor the appointment is associated to. i.e. 2 doctors cannot have appointments on the same timeslot. This is a known issue and is included in our future implementations
* An appointment must have all fields to work. For example, `add appt`, `add appt /for 3`, `add appt /on 2023-09-17` will not work as they have missing fields.

Examples:
* `appt /for 6 /doc 1 /d Blood test /on 01-01-2024 12:00`

### Listing all patients : `list`

Shows a list of all patients in Clinic Assistant.

Format: `list`

### Listing all appointments : `list_appt`

Shows a list of all appointments in Clinic Assistant.

Format: `list_appt`

### Editing a patient: `edit`

Edits an existing patient from the Clinic Records.

Format: `edit INDEX [/n NAME] [/p PHONE] [/e EMAIL] [/g GENDER] [/age AGE] [/eth ETHNIC] [/ic NRIC] [/a ADDRESS] [t/TAG]…​`

* Edits the patient at the specified `INDEX`. The index refers to the index number shown in the displayed patient list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.

Examples:
* `edit 1 /p 91234567 /e johndoe@example.com` edits the phone number and email address of the 1st person to be 91234567 and johndoe@example.com respectively.
* `edit 2 /n Betsy Crower` edits the name of the 2nd person to be Betsy Crower and clears all existing tags.

### Editing an appointment: `edit_appt`

Edits an existing appointment from the Clinic Records.

Format: `edit_appt INDEX [/d DESCRIPTION] [/on DATETIME]`

* Edits the appointment at the specified `INDEX`. The index refers to the index number shown in the displayed appointment list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

**Note** Currently does **not** support editing of Doctors, planned for future implementations

Examples:
* `edit_appt 1 /d changed to x-ray scan` edits the description of the appointment of 1st appointment.
* `edit_appt 2 /on 05-02-2024 12:00` edits the date of the 2nd appointment to be on 05-02-2024 12:00.

### Deleting a patient : `delete`

Deletes the specified patient from the Clinic Records.

Format: `delete INDEX`

* Deletes the patient at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, 4, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd patient in ClinicAssistant.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.

### Deleting an appointment : `delete_appt`

Deletes the appointment at the specified index of the appointment shown on the list.

Format: `delete_appt INDEX`

* The command is case-sensitive. E.g. Delete_appt INDEX will not work
* INDEX must be a positive integer, starting from 1
* Acceptable values for INDEX is a single integer that is within the number of appointments

### Deleting a doctor : `delete_doctor`

**Note:**
* Make sure that the doctor you are about to delete does not have any appointments
* This is because if you delete a doctor that has appointments those appointments will be deleted from clinic assistant as well and all data will be lost

Deletes the specified doctor from the Clinic Records.

Format: `delete_doctor INDEX`

* Deletes the doctor at the specified `INDEX`.
* The index refers to the index number shown in the displayed doctor list.
* The index **must be a positive integer** 1, 2, 3, 4, …​

Examples:
* `delete_doctor 2` deletes the 2nd doctor in ClinicAssistant.

Examples:
*  `delete_doctor 4` Deletes the fourth doctor of the list shown
*  `delete_doctor 1` Deletes the first doctor of the list shown

### Finding patient details by name: `find`

Finds patients whose name contains the given keyword.

Format: `find KEYWORD`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Only the name is searched.

Examples:
* `find John` returns `john` and `John Doe`

### Finding patient details by nric: `find_nric`

Finds the patient who has the given NRIC.

Format: `find_nric NRIC`

* The search is not case-sensitive. ie the NRIC `T1234567E` is equivalent to the NRIC `t1234567e`.
* The inputted NRIC must be valid. The NRIC must start with 'S' or 'T', and contain 7 digits between the 2 alphabets.
  * ie v1234567e and s123456e are invalid NRICs

Examples:
* `find_nric T0123456G` returns the patient with NRIC `T0123456G`
* `find_nric s0123456h` returns the patient with NRIC `S0123456H`

### Finding appointment by patient name or date: `find_appt`

Finds appointments of a patient whose name contains the given keyword and/or appointment falls on the date given.

Format: `find_appt [/n KEYWORDS] [/on DATE]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* Date is in the format dd-MM-yyyy e.g. 01-01-2024.

**Note:**
Although both fields are optional, at least 1 must be given. e.g. `find_appt` with no field inputs will not work

Examples:
* `find_appt /n John /on 01-01-2024 ` returns appointments of patient with name `John` that falls on 1 Jan 2024.

### Viewing available timeslots : `view`

Displays all **available** timeslots on the specified date by the user.

Format: `view /on DATE`

* Displays all available timeslots that can be booked on the `DATE`.
* Timeslots for appointments are fixed at 1 hour each, **starting from 9AM to 5PM**
* Any Timeslot that is displayed after calling `view /on DATE` can be booked

Examples:
* `view /on 02-01-2024` shows all available timeslots that can be booked on 02 Jan 2024.
* If timeslot `10 AM` is displayed, one can proceed to book an appointment for that `DATE` at `10 AM`

**Note:**
* There should not be any timings after `DATE`. e.g. `dd-MM-yyyy 18:00` will return an error.
* The `DATE` **must be a valid date on the calendar in the form `dd-MM-yyyy` exactly**
* The `DATE` entered should not be a present or future date.
* The `DATE` should be entered in exactly `dd-MM-yyyy` format separated by a `-` and not anything else such as `/`…​

### Clearing all patients : `clear`

Clears all patients from ClinicAssistant.

Format: `clear`

### Exiting the program : `exit`

Exits ClinicAssistant.

Format: `exit`

### Saving the data

ClinicAssistant data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

ClinicAssistant data are saved automatically as a JSON file `[JAR file location]/data/clinicassistant.json`. Advanced users are welcome to update data directly by editing that data file.

**Caution:**
If your changes to the data file makes its format invalid, ClinicAssistant will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.
</box>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous ClinicAssistant home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action     | Format, Examples
-----------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------
**Add**    | `add /n NAME /p PHONE_NUMBER /e EMAIL /g GENDER /age AGE /eth ETHNICITY /ic IC /a ADDRESS [t/TAG]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com /g M /age 22 /eth Chinese /ic T1234567E a/123, Clementi Rd, 1234665 t/friend t/colleague`
**Add Appointment**  | `appt /for INDEX /d details /on DATE & TIME` <br> e.g., `appt /for 1 /d orthopaedic /on 02-01-2024 12:00`
**Edit**   | `edit INDEX [/n NAME] [/p PHONE] [/e EMAIL] [/g GENDER] [/age AGE] [/eth ETHNIC] [/ic NRIC] [/a ADDRESS] [t/TAG]…​` <br> e.g., `edit 1 /a 3 NUS Computing Drive`
**Edit Appointment** | `edit_appt [/d DESCRIPTION] [/on DATETIME]` <br> e.g., `edit_appt 1 /d changed to X-ray scan`
**Clear**  | `clear`
**Delete** | `delete INDEX`<br> e.g., `delete 3`
**Delete Appointment** | `delete_appt INDEX` <br> e.g., `delete_appt 3`
**Find**   | `find KEYWORD`<br> e.g., `find john`
**Find by NRIC**   | `find_nric NRIC` <br> e.g., `find_nric T1234567E`
**Find Appointment** | `find_appt [/n KEYWORD] [/on DATE]`<br>e.g., `find_appt /n John /on 01-01-2024 12:00`
**List**   | `list`
**View**   | `view /on DATE`<br> e.g., `view /on 02-01-2024`
**Help**   | `help`
