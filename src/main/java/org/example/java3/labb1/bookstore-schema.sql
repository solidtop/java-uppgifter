DROP DATABASE IF EXISTS bookstore;
CREATE DATABASE bookstore;

USE bookstore;

CREATE TABLE language
(
    id   INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL
);

CREATE TABLE author
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    birth_date DATE
);

CREATE TABLE book
(
    isbn             VARCHAR(13) PRIMARY KEY,
    title            VARCHAR(255),
    author_id        INT NOT NULL,
    language_id      INT NOT NULL,
    price            DECIMAL(10, 2),
    publication_date DATE,
    FOREIGN KEY (author_id) REFERENCES author (id),
    FOREIGN KEY (language_id) REFERENCES language (id)
);

CREATE TABLE bookstore
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    store_name VARCHAR(255),
    city       VARCHAR(50)
);

CREATE TABLE inventory
(
    store_id INT,
    isbn     CHAR(13),
    amount   INT,
    PRIMARY KEY (store_id, isbn),
    FOREIGN KEY (store_id) REFERENCES bookstore (id),
    FOREIGN KEY (isbn) REFERENCES book (isbn)
);

-- Insert data into tables
INSERT INTO language (name)
VALUES ('English'),
       ('Swedish'),
       ('Spanish');

INSERT INTO author (first_name, last_name, birth_date)
VALUES ('J.R.R', 'Tolkien', '1973-09-02'),
       ('Stephen', 'King', '1947-09-21'),
       ('William', 'Shakespeare', '1564-04-23');

INSERT INTO book (isbn, title, author_id, language_id, price, publication_date)
VALUES ('9788845292613', 'The Lord of the Rings', 1, 1, 424.00, '1954-07-29'),
       ('9780385121675', 'The Shining', 2, 2, 462.00, '1977-01-28'),
       ('9789170379673', 'Hamlet', 3, 3, 89.00, '1604-05-01');

INSERT INTO bookstore (store_name, city)
VALUES ('Trippolo', 'Västervik'),
       ('Bok-Galleriet', 'Norrköping');

INSERT INTO inventory (store_id, isbn, amount)
VALUES (1, '9788845292613', 50),
       (1, '9780385121675', 30),
       (2, '9788845292613', 40),
       (2, '9780385121675', 20),
       (2, '9789170379673', 10);

-- Create a view for summarised data
CREATE VIEW total_author_book_value AS
SELECT CONCAT(author.first_name, ' ', author.last_name) AS name,
       YEAR(CURRENT_DATE()) - YEAR(author.birth_date)   AS age,
       COUNT(DISTINCT book.title)                       AS book_title_count,
       SUM(book.price * inventory.amount)               AS inventory_value
FROM author
         JOIN book ON author.id = book.author_id
         LEFT JOIN inventory ON book.isbn = inventory.isbn
GROUP BY author.id;

-- Create Users
CREATE USER IF NOT EXISTS 'dev'@'%' IDENTIFIED BY 'dev';
CREATE USER IF NOT EXISTS 'web'@'%' IDENTIFIED BY 'web';

-- Grant Permissions
GRANT CREATE, DROP , SELECT, INSERT, UPDATE, DELETE ON bookstore.* TO 'dev'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON bookstore.* TO 'web'@'%';