USE laboration1;

Task 1
CREATE TABLE IF NOT EXISTS successful_mission
AS
SELECT *
FROM moon_mission
WHERE outcome = 'Successful';

-- Task 2
START TRANSACTION;
ALTER TABLE successful_mission
    MODIFY mission_id INT AUTO_INCREMENT PRIMARY KEY;
COMMIT;

Task 3
START TRANSACTION;
SELECT REPLACE(operator, ' ', '')
FROM successful_mission;
COMMIT;

Task 4
START TRANSACTION;
DELETE
FROM successful_mission
WHERE launch_date >= '2010-01-01';
COMMIT;

Task 5
SELECT *,
       CONCAT(first_name, ' ', last_name)                            AS name,
       IF(SUBSTR(ssn, LENGTH(ssn) - 1, 1) % 2 = 0, 'female', 'male') AS gender
FROM account;

Task 6
START TRANSACTION;
DELETE
FROM account
WHERE SUBSTR(ssn, LENGTH(ssn) - 1, 1) % 2 = 0
  AND YEAR(STR_TO_DATE(CONCAT('19', SUBSTR(ssn, 1, 2), '-', SUBSTR(ssn, 3, 2), '-', SUBSTR(ssn, 5, 2)), '%Y-%m-%d')) <
      1970;
COMMIT;

Task 7
SELECT *

FROM account

SELECT 'male'                             AS gender,
       AVG(YEAR(CURDATE()) -
           YEAR(STR_TO_DATE(CONCAT('19', SUBSTR(ssn, 1, 2), '-', SUBSTR(ssn, 3, 2), '-', SUBSTR(ssn, 5, 2)),
                            '%Y-%m-%d'))) AS average_age
FROM account
WHERE SUBSTR(ssn, LENGTH(ssn) - 1, 1) % 2 = 1
UNION
SELECT 'female'                           AS gender,
       AVG(YEAR(CURDATE()) -
           YEAR(STR_TO_DATE(CONCAT('19', SUBSTR(ssn, 1, 2), '-', SUBSTR(ssn, 3, 2), '-', SUBSTR(ssn, 5, 2)),
                            '%Y-%m-%d'))) AS average_age
FROM account
WHERE SUBSTR(ssn, LENGTH(ssn) - 1, 1) % 2 = 0;

-- Book Store
USE bookstore;
SELECT *
FROM total_author_book_value;

CREATE