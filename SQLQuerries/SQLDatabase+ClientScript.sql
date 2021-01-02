CREATE DATABASE javaeeproject;

 CREATE TABLE clients (
	id int AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
    creationDate date NOT NULL,
    isDeleted boolean NOT NULL
);



INSERT INTO clients
		(name, description, creationDate, isDeleted)
VALUES ("Petooze", "Pet Store", "2020-11-20", false),
		("Teaaholic", "Everything about tea. Web Site.", "2020-11-20", false),
        ("Cozy Language", "Online language school", "2020-12-03", false);   