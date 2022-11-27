--liquibase formatted sql

--changeset scherbakov:1
CREATE INDEX index_students_name ON students (name);


--changeset scherbakov:2
CREATE INDEX index_faculties_name_color ON faculties (name, color);
