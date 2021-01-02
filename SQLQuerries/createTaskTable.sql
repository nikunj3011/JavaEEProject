USE javaeeproject;

CREATE TABLE tasks (
	id int AUTO_INCREMENT PRIMARY KEY,
    taskname VARCHAR(50) NOT NULL,
    description VARCHAR(255) NULL,
	duration VARCHAR(50) NULL,
    creationDate date NOT NULL,
	lastUpdateDate date,
	isDeleted boolean default false
);

TRUNCATE TABLE tasks;

INSERT INTO tasks
	(taskname, description, duration, creationDate, lastUpdateDate)
VALUES 
("Dell R710", "Dell R710 Rack Mount Server Installation", "30", "2020-11-16","2020-11-16"),
("Red Hat Linux", "Red Hat Linux Installation", "120", "2020-11-16","2020-11-16"),
("Cisco Switch", "Cisco Switch Installation", "60", "2020-12-03", "2020-12-03"),
("Cat5e Cabling", "Cat5e Cabling Installation", "60", "2020-12-03", "2020-12-03"),
("HD Replacement", "Hard Drive Replacement", "60", "2020-12-03", "2020-12-03"),
("Floppy Disk Replacement", "Floppy Disk Replacement", "45", "2020-12-03", "2020-12-03");

SELECT * FROM tasks;