CREATE DATABASE jd2_homework5_6;

CREATE TABLE user_group (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, name VARCHAR (100));
CREATE TABLE user (id INT (11) NOT NULL PRIMARY KEY AUTO_INCREMENT, username VARCHAR (40), password VARCHAR (40), is_active TINYINT (1), age INT (11), user_group_id INT, FOREIGN KEY (user_group_id) REFERENCES user_group (id));
CREATE TABLE user_information( user_id INT (11) NOT NULL PRIMARY KEY, address VARCHAR (100), telephone VARCHAR (40), FOREIGN KEY (user_id) REFERENCES user (id) );

