The __Praktikumsplaner__ offers a way to organize junior staff and their internships during their vocational training.

---
## About the project

The Praktikumsplaner, a project launched in collaboration between the Competence Center of Software Engineering (CCSE) and the InnovationLab, aims to support the training management in the allocation of junior staff.
It offers trainers the opportunity to report their available internships to the training management.
They can then use the application to assign junior staff to the reported internships using drag-and-drop functionality.
The internship planner helps to maintain a good overview in order to assign junior staff to the most suitable positions.


```mermaid
sequenceDiagram
    actor ÖA as Örtliche Ausbilder*innen
    actor ÖAL as Örtliche Ausbildungsleitung
    actor POR as Personal- und Organisationsreferat
    actor NWK as Nachwuchskräfte
    ÖAL->>ÖA: Notification, that internships can be reported
    rect rgb(0, 90, 159)
    ÖA->>ÖAL: Report internships
    ÖAL->>ÖAL: Add junior staff information to the system
    ÖAL->>ÖAL: Assign each junior staff to a suitable position
    end
    ÖAL->>POR: Sends assignment table for checking
    POR->>ÖAL: Sends feedback for assignments
    rect rgb(0, 90, 159)
    ÖAL->>ÖAL: Adjusts assignment if necessary
    ÖAL->>ÖA: Sends information about the assigned junior staff by e-mail
    end
    POR ->> NWK: Sends information about the assigned internship position
```
Here you can see the overall process in which the Praktikumsplaner takes place. The area marked in blue is where the Praktikumsplaner provides direct support.


---
![Screenshot of the assignment page of the Praktikumsplaners with sample data](/Screenshot_Praktikumsplaner.png)
Here you can see a screenshot of the assignment part of the application

## Technical details

The Praktikumsplaner was developed with the [reference architecture](https://opensource.muenchen.de/publish.html#refarch) of the City of Munich.
Java Spring Boot is used in the backend and TypeScript and [Vue.js](https://opensource.muenchen.de/software/vuejs.html) in the frontend.  
The application is operated in the data center of the City of Munich on [Openshift](https://opensource.muenchen.de/software/openshift.html).