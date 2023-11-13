---
layout: default.md
title: "Toh Pin Ren's Project Portfolio Page"
---

# Project: ClinicAssistant

## Overview
ClinicAssistant is a desktop application used by General Practitioner clinics' admin staff for patient contact management. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

## Summary of Contributions
Given below are my contributions to the project.

### Code contributed
Code contributed: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=tohpinren&breakdown=true)

### Enhancements implemented

**New Feature:** Implemented the `list_appt` command and created frontend for appointment list
- What it does: Enables users to see appointment list on the interface and list all appointments in the database through the `list_appt` command.
- Justification: The new frontend improves the user experience by allowing users to see the appointment list on the interface. The `list_appt` command allows users to list all appointments in the database easily.

**New Feature:** Implemented `find_appt` command by date or patient name
- What it does: Add on to the `find_appt` command to allow users to find appointments by date or patient name or both by adding parameters.
- Justification: This feature brings about enhanced user visibility, by providing a targeted way to view appointments associated with a specific person or date. Users can easily focus on the relevant information, streamlining their interaction with the application.

**New Feature:** Make appointment list always sorted by date
- What it does: Sorts the appointment list by date whenever the appointment list is displayed.
- Justification: This feature makes the interface more intuitive and user-friendly by always displaying the appointment list sorted by date which makes more sense to the user.

**New Feature:** Make `edit_appt` and `delete_appt` by index only
- What it does: Makes the `edit_appt` and `delete_appt` easier to use by only taking in the index of the appointment to be edited or deleted.
- Justification: This feature makes the commands more intuitive and user-friendly by making the `edit_appt` and `delete_appt` commands easier to use and less confusing for the user.

<br>

### Contributions to the UG
- Target audience
- Explanation for using the `list_appt` command
- Explanation for using the `find_appt` command
<br>

### Contributions to the DG
- Implementation of list feature and alternatives considered
- Use case of editing details of a patient
- Manual testing for editing a patient's details
- Planned enhancement to allow different doctors to have appointment slots at the same timing
- Planned enhancement to change the tab to the corresponding tab of the command
<br>

### Contributions to team-based tasks
- Set up GitHub organisation and tP repo.
- Set up GitHub Actions and codecov for continuous integration.
- Set up original version of the team website.
<br>

### Review/mentoring contributions
- Regularly reviewed teammates PR's, as well as highlighting of bugs and its appropriate fixes.
- Pull Requests reviewed: 19 [(Github)](https://github.com/AY2324S1-CS2103T-W09-3/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3Atohpinren)
<br>

### Contributions beyond the project team
- Found and reported bugs for [CS2103T-W13-4](https://github.com/AY2324S1-CS2103T-W13-4/tp/issues) during PE-D
<br>
