-- foreign keys
ALTER TABLE borrowing
    DROP CONSTRAINT borrowing_copy;
ALTER TABLE borrowing
    DROP CONSTRAINT borrowing_user;
ALTER TABLE copy
    DROP CONSTRAINT copy_book;
ALTER TABLE librarian
    DROP CONSTRAINT librarian_user;
ALTER TABLE book
    DROP CONSTRAINT book_publisher;

DROP TABLE borrowing;
DROP TABLE copy;
DROP TABLE librarian;
DROP TABLE book;
DROP TABLE publisher;
DROP TABLE "user";
