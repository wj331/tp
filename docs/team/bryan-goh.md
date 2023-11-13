---
layout: default.md
title: "Bryan Goh's Project Portfolio Page"
---

# Project: ClinicAssistant
## Overview
ClinicAssistant is a desktop application used by General Practitioner clinics' admin staff for patient contact management. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.
## Summary of Contributions
Given below are my contributions to the project.


### Code contributed: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=Bryan-Goh&breakdown=true)

### Enhancements implemented:
**New Feature:** Implemented the Doctor class and introduced the add doctor feature(Pull 
 request [#106](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/106))
- What it does: Enables the user to effortlessly add a new doctor to the system by using the `add_doctor` command. User specifies key details such as name, age, email, phone number, address, and gender.
- Justification: This feature is extremely useful for clinics to have as not only does it keep the contact info of the doctors but it also lets people book appointments with specific doctors.
- Difficulty: Hard. Although the Doctor class is similar with the Person class it was challenging to implement how it was being stored, and how to read it from the json file. It was also difficult integrating this feature with all the existing ones like the edit add and delete appointment commands.

**New Feature:** Implemented the delete doctor feature(Pull
request [#106](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/106))
- What it does: Enables the user to easily delete a doctor by using the `delete_doctor` command. User specifies the index of the doctor in order todelete that doctor.
- Justification: This feature is useful in the case of accidentally adding a doctor with the wrong info and when a doctor is no longer working with the clinic.

**New Feature:** Implemented the delete appointment command (Pull
request [#52](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/52))
- What it does: Enables the user to delete appointments easily by using the `delete_appointment` command. User specifies the index of the appointment that needs to be deleted.
- Justification: This feature is extremely important to have because without the delete feature the amount of appointments will only continue to grow and outdated appointments will clutter up the system.

**Feature Enhancements:** Changed the implementation of the appointment class (Pull
request [#106](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/106))
- What it does: Enables the user to specify which doctor a patient wants to have an appointment with.
- Justification: This enhancement is extremely important to keep track of who is meeting who when booking a medical appointment.

**Feature Enhancements:** Changed the UI by adding a doctor tab (Pull
request [#106](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/106))
- What it does: Enables the user to easily switch between viewing doctors and viewing patients.
- Justification: This enhancement makes it so that the software can hold and show more information without sacrificing the readability of the information shown.

### Contributions to the UG:
* Updated the UG for the delete_appointment command.
* Added the UG for the add_doctor command.
* Added the UG for the delete_doctor command.
* Added the GUI explanation for the entire project at the start of the UG.
* Chose the markdown for Notes and Warnings in the UG.

### Contributions to the DG:
* Wrote the Appendix: Effort.
* Wrote future features of the feature edit doctor.
* Wrote the planned enhancements for case sensitivity.
* Made the diagram for the implementation of delete appointment.
* Updated the UI component of the dg.
* Updated the diagram for the UI component.
* Wrote the use cases for delete appointment.
* Wrote the use cases for add and delete doctor.
* Wrote the Manual test cases for add and delete doctor.
* Wrote the manual test cases for delete appointment.

### Contributions to team-based tasks:
* wrapped up and closed v1.2
* released the jar for v1.2
* released the jar for v1.2.1
* closed and wrapped up v1.3
* did the project demo for v1.2
* did the project demo for v1.3
* did the PE-D bug processing progress for v1.4
* gave deadlines for milestones v1.3 and v1.3b
* documenting the explanation of the GUI in the UG
* added planned enhancements in the DG

### Review/mentoring contributions:
* helped reviewed 12 PRs [PR](https://github.com/AY2324S1-CS2103T-W09-3/tp/pulls?q=is%3Apr+is%3Aclosed+reviewed-by%3A%40me)

### Contributions beyond the project team:
* found and reported critical and important bugs during the PE-D [PE-D](https://github.com/Bryan-Goh/ped/issues/created_by/Bryan-Goh)
