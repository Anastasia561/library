-- publisher inserts

INSERT INTO publisher (name, address, phone_number)
VALUES ('Penguin Books', '123 Penguin St, New York', '123-456-7890'),
       ('HarperCollins', '456 Harper Ave, London', '987-654-3210'),
       ('Random House', '789 Random Rd, Boston', '555-123-4567');

-- book inserts

INSERT INTO book (title, author, publication_year, isbn, publisher_id)
VALUES ('The Great Gatsby', 'F. Scott Fitzgerald', 1925, '9780743273565', 1),
       ('To Kill a Mockingbird', 'Harper Lee', 1960, '9780061120084', 2),
       ('1984', 'George Orwell', 1949, '9780451524935', 3);

-- copy inserts

INSERT INTO copy (copy_number, status, book_id)
VALUES (1, 'Available', 1),
       (2, 'Borrowed', 1),
       (3, 'Available', 2),
       (4, 'Available', 2),
       (5, 'Available', 3),
       (6, 'Withdrawn', 3);

-- user inserts

INSERT INTO "user" (name, email, phone_number, address)
VALUES ('Alice Johnson', 'alice.johnson@example.com', '555-111-2222', '321 Elm St'),
       ('Bob Smith', 'bob.smith@example.com', '555-333-4444', '654 Oak Rd'),
       ('Charlie Brown', 'charlie.brown@example.com', '555-555-5555', '987 Pine Ave');

-- borrowing inserts

INSERT INTO borrowing (borrow_date, return_date, user_id, copy_id)
VALUES ('2025-01-01', '2025-01-10', 1, 1),
       ('2025-01-02', '2025-01-15', 2, 4),
       ('2025-01-05', NULL, 3, 6);

-- librarian inserts

INSERT INTO librarian (employment_date, position, user_id)
VALUES ('2020-06-01', 'Senior Librarian', 1),
       ('2022-09-15', 'Assistant Librarian', 2),
       ('2023-03-10', 'Library Manager', 3);
