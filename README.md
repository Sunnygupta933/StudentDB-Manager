StudentDB-Manager
A Java Swing-based Student Database Management Application that allows adding, displaying, sorting, searching, modifying, and deleting student records with role-based access control.

Features
Add new student records

Display all records

Sort records by first name, last name, or major

Search records by Student ID, last name, or major

Modify existing student details

Delete individual or all records

Role-based UI access control (admin and user)

Prerequisites
Java Development Kit (JDK) 8 or above
Download here

MySQL Server
Download here

MySQL JDBC Connector (mysql-connector-java-x.x.x.jar)
Download here

IDE (Optional but recommended): IntelliJ IDEA, Eclipse, or NetBeans

Setup Instructions
1. Install MySQL Server
Download and install MySQL Server.

During installation, set up root user password (remember it; in code, it’s po09).

Start MySQL service.

2. Create Database and Table
Open MySQL command line or MySQL Workbench.

Run the following SQL commands to create the database and table:

sql
Copy
Edit
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
3. Add MySQL JDBC Connector to Your Project
Download the MySQL Connector/J .jar file.

Add the .jar to your project classpath:

In IntelliJ IDEA: File > Project Structure > Libraries > + > Select the .jar

In Eclipse: Right-click Project > Build Path > Add External Archives > Select the .jar

4. Update Database Credentials in dbConnect.java
Make sure the username, password, and URL in your dbConnect class match your MySQL setup:

java
Copy
Edit
private static final String USER = "root";  // your mysql username
private static final String PASS = "po09";  // your mysql password
private static final String URL = "jdbc:mysql://localhost:3306/studata?useSSL=false&serverTimezone=UTC";
5. Compile and Run the Application
Compile all .java files.

Run MainApp.java which launches the login GUI.

Login as:

Admin: username: admin, password: admin123

User: username: user, password: user123

6. Usage
As Admin, you can add, modify, delete, display, search, sort, and clear all student records.

As User, you have read-only access (display, search, sort) without modifying or deleting.

Project Structure
MainApp.java — Entry point for the application

LoginGUI.java — Login screen for users

RegisterGUI.java — User registration screen (if applicable)

AppGUI.java — Main student management GUI

dbConnect.java — Database connection class

Table.java — Helper class to build tables from ResultSet

License
This project is licensed under the MIT License. See LICENSE for details.

Contact
Created by Sunny Gupta

GitHub: https://github.com/Sunnygupta933
