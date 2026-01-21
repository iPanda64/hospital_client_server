# Hospital Client-Server System

This project is a comprehensive Hospital Management System designed to streamline various hospital operations. It follows a client-server architecture, with a JavaFX-based client application for the user interface and a Java-based server for handling business logic and database interactions. The system supports multiple user roles, each with a specific set of permissions and functionalities.

The application uses a MySQL database for data storage and a custom communication protocol for client-server interaction.

<table>
  <tr>
    <td><img src="screenshots/admin/Screenshot 2026-01-09 182650.png" width="200"/></td>
    <td><img src="screenshots/asistent/Screenshot 2026-01-09 181845.png" width="200"/></td>
    <td><img src="screenshots/doctor/Screenshot 2026-01-09 182036.png" width="200"/></td>
  </tr>
  <tr>
    <td><img src="screenshots/doctor/Screenshot 2026-01-09 182027.png" width="200"/></td>
    <td><img src="screenshots/other/Screenshot 2026-01-09 182520.png" width="200"/></td>
    <td><img src="screenshots/pacient/Screenshot 2026-01-09 182614.png" width="200"/></td>
  </tr>
</table>

## Features by User Role

### Administrator
*   Manage user accounts (Create, Read, Update, Delete).

### Doctor
*   View assigned patients.
*   View patient's medical history (consultations, prescriptions).
*   Create new consultations for patients.
*   Write and issue new prescriptions.
*   View personal data of patients.
*   View and manage appointments.

### Assistant
*   View all patients in the system.
*   Manage appointments (Create, approve, deny, delete).
*   View prescriptions for patients.
*   Generate invoices for consultations.

### Patient
*   View and manage personal appointments.
*   Create new appointment requests.
*   View personal medical history (consultations, prescriptions).
*   View and download invoices.

## Prerequisites

*   Java 11
*   Apache Maven
*   MySQL Server

## Installation

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/iPanda64/hospital_client_server.git
    cd hospital_client_server
    ```
2.  **Set up the database:**
    *   Make sure you have MySQL server running.
    *   Create a database named `hospital`.
    *   Import the `hospital-dump.sql` file into your `hospital` database.
        ```bash
        mysql -u root -p hospital < hospital-dump.sql
        ```
3.  **Configure the database connection (if necessary):**
    *   The database connection is configured in `Server/src/main/java/model/Repository/Repository.java`.
    *   The default settings are:
        *   URL: `jdbc:mysql://localhost:3306/hospital`
        *   User: `root`
        *   Password: `"root"`
    *   If your MySQL setup is different, you will need to update this file.
4.  **Build the project:**
    *   Build the server:
        ```bash
        cd Server
        mvn clean install
        cd ..
        ```
    *   Build the client:
        ```bash
        cd Client
        mvn clean install
        cd ..
        ```
5.  **Run the application:**
    *   Run the server:
        ```bash
        cd Server
        mvn exec:java
        ```
    *   In a new terminal, run the client:
        ```bash
        cd Client
        mvn javafx:run
        ```
