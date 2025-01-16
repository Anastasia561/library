-- Table: book
CREATE TABLE book
(
    id               int PRIMARY KEY AUTO_INCREMENT,
    title            varchar(30) NOT NULL,
    author           varchar(50) NOT NULL,
    publication_year int         NOT NULL,
    isbn             varchar(20) NOT NULL UNIQUE,
    publisher_id     int         NOT NULL
);

-- Table: borrowing
CREATE TABLE borrowing
(
    id          int PRIMARY KEY AUTO_INCREMENT,
    borrow_date date NOT NULL,
    return_date date NULL,
    user_id     int  NOT NULL,
    copy_id     int  NOT NULL
);

-- Table: copy
CREATE TABLE copy
(
    id          int PRIMARY KEY AUTO_INCREMENT,
    copy_number int         NOT NULL UNIQUE,
    status      varchar(20) NOT NULL,
    book_id     int         NOT NULL
);

-- Table: librarian
CREATE TABLE librarian
(
    id              int PRIMARY KEY AUTO_INCREMENT,
    employment_date date        NOT NULL,
    position        varchar(20) NOT NULL,
    user_id         int         NOT NULL
);

-- Table: publisher
CREATE TABLE publisher
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    name         varchar(20) NOT NULL,
    address      varchar(30) NOT NULL,
    phone_number varchar(20) NOT NULL
);

-- Table: user
CREATE TABLE "user"
(
    id           int PRIMARY KEY AUTO_INCREMENT,
    name         varchar(20) NOT NULL,
    email        varchar(30) NOT NULL UNIQUE,
    phone_number varchar(20) NOT NULL,
    address      varchar(30) NOT NULL
);

-- foreign keys
-- Reference: book_publisher (table: book)
ALTER TABLE book
    ADD CONSTRAINT book_publisher FOREIGN KEY (publisher_id)
        REFERENCES publisher (id) ON DELETE RESTRICT;

-- Reference: borrowing_copy (table: borrowing)
ALTER TABLE borrowing
    ADD CONSTRAINT borrowing_copy FOREIGN KEY (copy_id)
        REFERENCES copy (id) ON DELETE RESTRICT;

-- Reference: borrowing_user (table: borrowing)
ALTER TABLE borrowing
    ADD CONSTRAINT borrowing_user FOREIGN KEY (user_id)
        REFERENCES "user" (id) ON DELETE RESTRICT;

-- Reference: copy_book (table: copy)
ALTER TABLE copy
    ADD CONSTRAINT copy_book FOREIGN KEY (book_id)
        REFERENCES book (id) ON DELETE RESTRICT;

-- Reference: librarian_user (table: librarian)
ALTER TABLE librarian
    ADD CONSTRAINT librarian_user FOREIGN KEY (user_id)
        REFERENCES "user" (id) ON DELETE RESTRICT;
