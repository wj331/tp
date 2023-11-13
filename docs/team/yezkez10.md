---
layout: default.md
title: "Joy Foo's Project Portfolio Page"
---

## Project: ClinicAssistant

### Overview

ClinicAssistant is a desktop application used by General Practitioner clinics' admin staff for patient contact management. The user interacts with it using a CLI, and it has a GUI created with JavaFX. It is written in Java, and has about 10 kLoC.

### Summary of Contributions

Given below are my contributions to the project.

**Code contributed**: [RepoSense link](https://nus-cs2103-ay2324s1.github.io/tp-dashboard/?search=yezkez10&breakdown=true)

**Feature Enhancement:** Added the NRIC field to Patient (Pull request [#39](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/39))
- What it does: attributes a unique NRIC to every patient
- Justification: This allows the user to identify each patient by their unique NRIC.
This is a better mode of identification than name since patients can have the same name, 
but no two patients can have the same NRIC.
- Highlights: The implementation of this enhancement affected existing commands and components. 
Its implementation required changes to other commands, parsers, testcases and the GUI.
This process required an in-depth understanding of the architecture and design.


**New Feature:** Added Find By NRIC feature (Pull request [#93](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/93))
  - What it does: Allows user to find patient details just by keying in their NRIC.
  - Justification: Every patient has a unique NRIC, hence this command will return at most 1 patient's details.
This is more helpful than the find by name feature since the latter could return more than 1 patient's details. 


**Enhancements implemented:**
- Updated GUI formatting (Pull request [#111](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/111))
  - What it does: Separates layout into 2 sections -- one containing the `Patients` and `Doctors` tabs, 
  the other containing the `Appointments` and `Timeslots` tabs.
  - Justification: Organises information into their own compartments. Reduces the clutter of information shown to users. 
- Polished styling and GUI appearance


**Enhancements to existing features:**
- Updated test cases to increase coverage for existing features (Pull requests [#39](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/39),
  [#93](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/93), 
  [#174](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/174))
- Wrote additional tests for existing features to increase coverage
- Contributed to resolving bugs found in PED (Pull request [#171](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/171))


**Contributions to Documentation:**
* User Guide: Added and updated documentation for the following (Pull request [#185](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/185))
    * Product overview.
    * The usage of features `add`, `list`, `edit`, `delete`, `find`, `find_nric`.
    * Contributed to adding screenshots and graphics.
    * Overall vetting of the formatting and quality of the UG.
      
* Developer Guide: Added and updated documentation for the following (Pull request [#221](https://github.com/AY2324S1-CS2103T-W09-3/tp/pull/221))
    * The components `Logic`, `Storage`, `Model`.
    * The implementation of features `add`, `find`, `edit`, `delete`.
    * The testing of features `add` and `find`.
  .* Overall vetting of the formatting and quality of the DG


**Community**:
* Reviewed and merged teammates' PRs.
* Reported bugs and suggestions for other teams in the class during PE-D.


**Project management**:
- Managed release [v1.3](https://github.com/AY2324S1-CS2103T-W09-3/tp/releases/tag/v1.3) on Github.


**Contributions beyond the project scope:**
- Worked with the team to come up with an 18-minute product pitch, 
to market our product to a targeted audience. 
