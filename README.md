Features
 StudentDB-Manager- Add new student records- Display all records- Sort records by first name, last name, or major- Search records by Student ID, last name, or major- Modify existing student details- Delete individual or all records- Role-based UI access control (admin and user)
 Prerequisites- Java Development Kit (JDK) 8 or above- MySQL Server- MySQL JDBC Connector (mysql-connector-java-x.x.x.jar)- IDE (IntelliJ IDEA, Eclipse, or NetBeans recommended)
 1. Install MySQL Server
 Download and install MySQL Server. Set up a root password (default: po09).
 Ensure MySQL service is running.
 2. Create Database and Table
 Run these SQL commands in MySQL Workbench or CLI:
 CREATE DATABASE studata;
 USE studata;
 CREATE TABLE sdata (
    Student_ID VARCHAR(20) PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    major VARCHAR(50),
    Phone VARCHAR(15),
    GPA VARCHAR(5),
 StudentDB-Manager
    DOB DATE
 );
 3. Add JDBC Connector
 Download mysql-connector-java.jar and add it to your project:- IntelliJ: File > Project Structure > Libraries > + > Select .jar- Eclipse: Right-click Project > Build Path > Add External Archives
 4. Update Credentials in dbConnect.java
 Ensure the following values in dbConnect.java:
 USER = "root"
 PASS = "po09"
 URL = "jdbc:mysql://localhost:3306/studata?useSSL=false&serverTimezone=UTC"
 5. Compile and Run
 Compile all .java files. Run MainApp.java to launch GUI.
 Login Credentials:
 Admin -> Username: admin | Password: admin123
 User  -> Username: user  | Password: user123
 6. Project Structure- MainApp.java: Entry point- LoginGUI.java: Login UI- RegisterGUI.java: Register UI- AppGUI.java: Main dashboard- dbConnect.java: DB connection- Table.java: ResultSet to JTable utility
 License
StudentDB-Manager
 MIT License. See LICENSE file for more info.
 Contact
 Created by Sunny Gupta
 GitHub: https://github.com/Sunnygupta933
