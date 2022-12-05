ALTER TABLE students
    ADD CHECK ( age >= 16 ),
    ALTER COLUMN unique_name UNIQUE (name),
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE faculties
    ADD CONSTRAINT unique_name_and_color unique (name, color);
