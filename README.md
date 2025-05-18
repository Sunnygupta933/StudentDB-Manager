# StudentDB-Manager

A Java Swing desktop application for managing student records, integrated with a MySQL database. Supports full CRUD operations, sorting, searching, and role-based access control.

---

## Features

- Add, display, sort, search, modify, and delete student records.
- Role-based access control (`admin` and `user` roles).
- Simple, user-friendly Java Swing GUI.
- Data persistence using MySQL database.
  
---

## Prerequisites

1. **Java Development Kit (JDK) 8 or higher**  
   Download and install from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use OpenJDK.

2. **MySQL Server**  
   Download and install from [MySQL Official](https://dev.mysql.com/downloads/mysql/).

3. **MySQL JDBC Connector (mysql-connector-java)**  
   Download the latest version [here](https://dev.mysql.com/downloads/connector/j/).

4. **IDE (Optional but recommended):**  
   Use Eclipse, IntelliJ IDEA, or NetBeans for easy project management.

---

## Setup Instructions

### Step 1: Configure MySQL Database

- **Create Database and Table**

  1. Open MySQL Command Line or MySQL Workbench.
  2. Run the following commands to create the database and student table:

  ```sql
  CREATE DATABASE studata;
  USE studata;

  CREATE TABLE sdata (
      Student_ID VARCHAR(20) PRIMARY KEY,
      first_name VARCHAR(50),
      last_name VARCHAR(50),
      major VARCHAR(50),
      Phone VARCHAR(15),
      GPA VARCHAR(5),
      DOB DATE
  );
Alternatively:
You can use the included studata.sql file by importing it into MySQL.
mysql -u your_username -p studata < path/to/studata.sql




Step 2: Add MySQL JDBC Connector to Your Project
Add the downloaded mysql-connector-java-x.x.x.jar to your project's build path:

In Eclipse: Right-click project > Properties > Java Build Path > Libraries > Add External JARs > select jar

In IntelliJ: File > Project Structure > Modules > Dependencies > + > JARs or directories




Step 3: Configure Database Credentials
Open dbConnect.java and update the following constants if your MySQL username or password differ:

private static final String USER = "root";     // Your MySQL username
private static final String PASS = "po09";     // Your MySQL password



Ensure the URL points to the correct database:

private static final String URL = "jdbc:mysql://localhost:3306/studata?useSSL=false&serverTimezo





Step 4: Running the Application
Compile and run the MainApp.java file.

On launch, you'll see a login screen:

Admin Account:
Username: admin
Password: admin123
(Full access including add, modify, delete, clear all)

User Account:
Username: user
Password: user123
(View-only: Add/modify/delete disabled)






Project Structure
AppGUI.java — Main application GUI with all functionalities.
dbConnect.java — Handles database connection.
LoginGUI.java — Login screen with role-based access.
RegisterGUI.java — (If applicable) User registration screen.
Table.java — Helper class for displaying ResultSet data in tables.
MainApp.java — Main class to start the app.
studata.sql — SQL script to create database and table.





License
This project is licensed under the MIT License - see the LICENSE file for details.




Acknowledgments
MySQL Connector/J for JDBC driver
Java Swing for GUI components



Author
Sunny Gupta
Sunnygupta933





