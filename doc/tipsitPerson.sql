CREATE DATABASE IF NOT EXISTS tipsit;
CREATE TABLE IF NOT EXISTS tipsit.person(id int(11) NOT NULL AUTO_INCREMENT, firstName varchar(50) NOT NULL, lastName varchar(50) NOT NULL,address text DEFAULT NULL,  passport varchar(50) DEFAULT NULL,  PRIMARY KEY (id)) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

INSERT INTO tipsit.person (id, firstName, lastName, address, passport) VALUES (1, 'Mathilde', 'Patrissi', 'Via....', NULL),(2, 'Mat', 'Pat', 'Via----', NULL);


CREATE USER 'mathy'@'%' IDENTIFIED BY 'mathy';
GRANT select ON tipsit.person TO 'mathy'@'%';