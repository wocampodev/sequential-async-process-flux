USE wordly;
DROP TABLE IF EXISTS word;
DROP TABLE IF EXISTS example;

CREATE TABLE `word` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `text` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=Aria;

LOAD DATA LOCAL INFILE './data.csv'
    IGNORE INTO TABLE word
    FIELDS
    TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 0 LINES
    (@dummy, text) SET text = UPPER(text);

CREATE TABLE `example` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL,
  `word_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB;

INSERT INTO example (name, word_id) VALUES
    ('Name 1', 1),
    ('Name 2', 1),
    ('Name 3', 1),
    ('Name 4', 3),
    ('Name 5', 3);
