CREATE USER IF NOT EXISTS 'library_user'@'localhost' IDENTIFIED WITH mysql_native_password BY 'lib123';
GRANT ALL PRIVILEGES ON library_db.* TO 'library_user'@'localhost';
FLUSH PRIVILEGES;

CREATE DATABASE IF NOT EXISTS library_db;
USE library_db;

CREATE TABLE IF NOT EXISTS members (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE
);

CREATE TABLE IF NOT EXISTS books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    available BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS issued_books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT NOT NULL,
    book_id INT NOT NULL,
    issue_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    return_date DATE,
    FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (book_id) REFERENCES books(id)
);

INSERT INTO members (name, email) VALUES
('Ragini', 'ragini@gmail.com'),
('Rajitha', 'Rajitha@gmail.com');

INSERT INTO books (title, author) VALUES
('The Alchemist', 'Paulo Coelho'),
('Clean Code', 'Robert C. Martin');

INSERT INTO issued_books (member_id, book_id) VALUES (1, 1);
UPDATE books SET available = FALSE WHERE id = 1;

UPDATE issued_books SET return_date = CURRENT_DATE
WHERE member_id = 1 AND book_id = 1 AND return_date IS NULL;
UPDATE books SET available = TRUE WHERE id = 1;
