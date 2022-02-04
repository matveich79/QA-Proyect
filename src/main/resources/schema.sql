DROP TABLE IF EXISTS `character_list`;

CREATE TABLE character_list (
	id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(20),
	type VARCHAR(20),
	age INT,
	conflict VARCHAR(20)
);
