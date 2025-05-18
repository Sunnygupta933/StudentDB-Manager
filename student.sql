CREATE DATABASE studata;
USE studata;
CREATE TABLE sdata (Student_ID VARCHAR(10) NOT NULL,first_name VARCHAR(30) NOT NULL,last_name VARCHAR(30) NOT NULL,major VARCHAR(50) NOT NULL,Phone VARCHAR(12),GPA VARCHAR(5) NOT NULL,DOB VARCHAR(30),UNIQUE KEY (Student_ID));
INSERT INTO sdata VALUES('001','Sankar','Nagarapu','EEE','9398068299','7.0','2002-06-24'),('002','Dharani','Nagarapu','CSE','9133501412','8.0','2004-06-05');
CREATE TABLE users (username VARCHAR(30) PRIMARY KEY, password VARCHAR(30) NOT NULL, role VARCHAR(10) NOT NULL -- values: 'admin' or 'user'
);
INSERT INTO users VALUES ('admin', 'admin123', 'admin');
INSERT INTO users VALUES ('user1', 'user123', 'user');