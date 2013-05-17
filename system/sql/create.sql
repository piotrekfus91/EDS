DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS groups CASCADE;
DROP TABLE IF EXISTS users_groups CASCADE;

DROP SEQUENCE IF EXISTS users_seq;
DROP SEQUENCE IF EXISTS groups_seq;

CREATE SEQUENCE users_seq;
CREATE SEQUENCE groups_seq;

CREATE TABLE users (
       id INTEGER NOT NULL DEFAULT nextval('users_seq'),
       login VARCHAR(30) NOT NULL,
       first_name VARCHAR(30) NOT NULL,
       last_name VARCHAR(30) NOT NULL,
       registration_date DATE NOT NULL,
       last_login DATE
);
ALTER TABLE users ADD CONSTRAINT users_pk PRIMARY KEY (id);

DROP TABLE IF EXISTS groups;
CREATE TABLE groups (
       id INTEGER NOT NULL DEFAULT nextval('groups_seq'),
       name VARCHAR(20) NOT NULL,
       description TEXT,
       creation_date DATE NOT NULL
);
ALTER TABLE groups ADD CONSTRAINT groups_pk PRIMARY KEY (id);

DROP TABLE IF EXISTS users_groups;
CREATE TABLE users_groups (
       user_id INTEGER NOT NULL,
       group_id INTEGER NOT NULL
);
ALTER TABLE users_groups ADD CONSTRAINT users_groups_pk  PRIMARY KEY (user_id, group_id);
ALTER TABLE users_groups ADD CONSTRAINT users_groups_user_id_fk FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE users_groups ADD CONSTRAINT users_groups_group_id_fk FOREIGN KEY (group_id) REFERENCES groups(id);
