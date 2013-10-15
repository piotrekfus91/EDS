ALTER TABLE eds.comment DROP CONSTRAINT user_comment_user_id_fk;
ALTER TABLE eds.comment DROP CONSTRAINT document_comment_document_id_fk;
ALTER TABLE eds.directory DROP CONSTRAINT directory_directory_parent_directory_id_fk;
ALTER TABLE eds.document DROP CONSTRAINT mime_type_document_mime_type_id_fk;
ALTER TABLE eds.document DROP CONSTRAINT user_document_owner_id_fk;
ALTER TABLE eds.document_tag DROP CONSTRAINT document_document_tag_document_id_fk;
ALTER TABLE eds.document DROP CONSTRAINT directory_document_directory_id_fk;
ALTER TABLE eds.resource_group DROP CONSTRAINT user_resource_group_founder_id_fk;
ALTER TABLE eds.resource_group_directory DROP CONSTRAINT resource_group_resource_group_directory_resource_group_id_fk;
ALTER TABLE eds.resource_group_document DROP CONSTRAINT resource_group_resource_group_document_resource_group_id_fk;
ALTER TABLE eds.document_tag DROP CONSTRAINT tag_document_tag_tag_id_fk;
ALTER TABLE eds.resource_group_directory DROP CONSTRAINT directory_resource_group_directory_directory_id_fk;
ALTER TABLE eds.resource_group_document DROP CONSTRAINT document_resource_group_document_document_id_fk;
ALTER TABLE eds.directory DROP CONSTRAINT user_directory_owner_id_fk;
DROP TABLE eds.comment CASCADE;
DROP TABLE eds.resource_group CASCADE;
DROP TABLE eds.directory CASCADE;
DROP TABLE eds.tag CASCADE;
DROP TABLE eds.mime_type CASCADE;
DROP TABLE eds.document CASCADE;
DROP TABLE eds."user" CASCADE;
DROP TABLE eds.document_tag CASCADE;
DROP TABLE eds.resource_group_directory CASCADE;
DROP TABLE eds.resource_group_document CASCADE;
DROP SEQUENCE eds.resource_group_seq;
DROP SEQUENCE eds.directory_seq;
DROP SEQUENCE eds.tag_seq;
DROP SEQUENCE eds.comment_seq;
DROP SEQUENCE eds.document_seq;
DROP SEQUENCE eds.user_seq;
DROP SEQUENCE eds.mime_type_seq;
DROP SEQUENCE eds.user_seq;
DROP SEQUENCE eds.comment_seq;
DROP SEQUENCE eds.resource_group_seq;
DROP SEQUENCE eds.directory_seq;
DROP SEQUENCE eds.mime_type_seq;
DROP SEQUENCE eds.tag_seq;
DROP SEQUENCE eds.document_seq;
CREATE SEQUENCE eds.comment_seq;
CREATE SEQUENCE eds.resource_group_seq;
CREATE SEQUENCE eds.directory_seq;
CREATE SEQUENCE eds.tag_seq;
CREATE SEQUENCE eds.document_seq;
CREATE SEQUENCE eds.user_seq;
CREATE SEQUENCE eds.mime_type_seq;
CREATE SEQUENCE eds.user_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.comment_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.resource_group_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.directory_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.mime_type_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.tag_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.document_seq NO MINVALUE NO MAXVALUE;
CREATE TABLE eds.comment (
  id          integer DEFAULT nextval('eds.comment_seq') NOT NULL, 
  user_id     integer NOT NULL, 
  document_id integer NOT NULL, 
  content     text NOT NULL, 
  created     timestamp(6) NOT NULL, 
  modified    timestamp(6), 
  version     int4, 
  CONSTRAINT comment_pkey 
    PRIMARY KEY (id));
CREATE TABLE eds.resource_group (
  id          integer DEFAULT nextval('eds.resource_group_seq') NOT NULL, 
  founder_id  integer NOT NULL, 
  name        varchar(30) NOT NULL UNIQUE, 
  description text, 
  version     int4, 
  CONSTRAINT resource_group_pkey 
    PRIMARY KEY (id));
CREATE TABLE eds.directory (
  id                  integer DEFAULT nextval('eds.directory_seq') NOT NULL, 
  parent_directory_id integer, 
  owner_id            int4 NOT NULL, 
  name                varchar(30) NOT NULL, 
  version             int4, 
  CONSTRAINT directory_pkey 
    PRIMARY KEY (id));
CREATE TABLE eds.tag (
  id               integer DEFAULT nextval('eds.tag_seq') NOT NULL, 
  value            varchar(30) NOT NULL UNIQUE, 
  normalized_value varchar(30) NOT NULL, 
  version          int4,
  CONSTRAINT tag_pkey 
    PRIMARY KEY (id));
CREATE TABLE eds.mime_type (
  id                int4 DEFAULT nextval('eds.mime_type_seq') NOT NULL, 
  type              varchar(30) NOT NULL UNIQUE, 
  enabled           bool NOT NULL, 
  default_extension varchar(6), 
  description       text, 
  version           int4,
  PRIMARY KEY (id));
CREATE TABLE eds.document (
  id           integer DEFAULT nextval('eds.document_seq') NOT NULL, 
  mime_type_id int4 NOT NULL, 
  owner_id     integer NOT NULL, 
  directory_id integer, 
  name         varchar(255) NOT NULL, 
  content_md5  varchar(32) NOT NULL, 
  created      timestamp NOT NULL, 
  version      int4, 
  CONSTRAINT document_pkey 
    PRIMARY KEY (id));
CREATE TABLE eds."user" (
  id             integer DEFAULT nextval('eds.user_seq') NOT NULL, 
  login_name     varchar(32) NOT NULL UNIQUE, 
  password_value varchar(32) NOT NULL, 
  first_name     varchar(99) NOT NULL, 
  last_name      varchar(99) NOT NULL, 
  email          varchar(99) NOT NULL, 
  locked         bool DEFAULT 'false' NOT NULL, 
  created        timestamp NOT NULL, 
  version        int4 NOT NULL, 
  last_login     timestamp(6), 
  CONSTRAINT user_pkey 
    PRIMARY KEY (id));
CREATE TABLE eds.document_tag (
  document_id integer NOT NULL, 
  tag_id      integer NOT NULL, 
  CONSTRAINT document_tag_pkey 
    PRIMARY KEY (document_id, 
  tag_id));
CREATE TABLE eds.resource_group_directory (
  resource_group_id integer NOT NULL, 
  directory_id      integer NOT NULL, 
  CONSTRAINT resource_group_directory_pkey 
    PRIMARY KEY (resource_group_id, 
  directory_id));
CREATE TABLE eds.resource_group_document (
  resource_group_id integer NOT NULL, 
  document_id       integer NOT NULL, 
  CONSTRAINT resource_group_document_pkey 
    PRIMARY KEY (resource_group_id, 
  document_id));
ALTER TABLE eds.comment ADD CONSTRAINT user_comment_user_id_fk FOREIGN KEY (user_id) REFERENCES eds."user" (id);
ALTER TABLE eds.comment ADD CONSTRAINT document_comment_document_id_fk FOREIGN KEY (document_id) REFERENCES eds.document (id);
ALTER TABLE eds.directory ADD CONSTRAINT directory_directory_parent_directory_id_fk FOREIGN KEY (parent_directory_id) REFERENCES eds.directory (id);
ALTER TABLE eds.document ADD CONSTRAINT mime_type_document_mime_type_id_fk FOREIGN KEY (mime_type_id) REFERENCES eds.mime_type (id);
ALTER TABLE eds.document ADD CONSTRAINT user_document_owner_id_fk FOREIGN KEY (owner_id) REFERENCES eds."user" (id);
ALTER TABLE eds.document_tag ADD CONSTRAINT document_document_tag_document_id_fk FOREIGN KEY (document_id) REFERENCES eds.document (id);
ALTER TABLE eds.document ADD CONSTRAINT directory_document_directory_id_fk FOREIGN KEY (directory_id) REFERENCES eds.directory (id);
ALTER TABLE eds.resource_group ADD CONSTRAINT user_resource_group_founder_id_fk FOREIGN KEY (founder_id) REFERENCES eds."user" (id);
ALTER TABLE eds.resource_group_directory ADD CONSTRAINT resource_group_resource_group_directory_resource_group_id_fk FOREIGN KEY (resource_group_id) REFERENCES eds.resource_group (id);
ALTER TABLE eds.resource_group_document ADD CONSTRAINT resource_group_resource_group_document_resource_group_id_fk FOREIGN KEY (resource_group_id) REFERENCES eds.resource_group (id);
ALTER TABLE eds.document_tag ADD CONSTRAINT tag_document_tag_tag_id_fk FOREIGN KEY (tag_id) REFERENCES eds.tag (id);
ALTER TABLE eds.resource_group_directory ADD CONSTRAINT directory_resource_group_directory_directory_id_fk FOREIGN KEY (directory_id) REFERENCES eds.directory (id);
ALTER TABLE eds.resource_group_document ADD CONSTRAINT document_resource_group_document_document_id_fk FOREIGN KEY (document_id) REFERENCES eds.document (id);
ALTER TABLE eds.directory ADD CONSTRAINT user_directory_owner_id_fk FOREIGN KEY (owner_id) REFERENCES eds."user" (id);
CREATE INDEX comment_id 
  ON eds.comment (id);
CREATE INDEX comment_user_id 
  ON eds.comment (user_id);
CREATE INDEX comment_document_id 
  ON eds.comment (document_id);
CREATE INDEX resource_group_founder_id 
  ON eds.resource_group (founder_id);
CREATE INDEX resource_group_id 
  ON eds.resource_group (id);
CREATE INDEX directory_parent_directory_id 
  ON eds.directory (parent_directory_id);
CREATE INDEX directory_id 
  ON eds.directory (id);
CREATE INDEX tag_normalized_value 
  ON eds.tag (normalized_value);
CREATE UNIQUE INDEX tag_value_unique_key
  ON eds.tag (value);
CREATE INDEX tag_id 
  ON eds.tag (id);
CREATE INDEX mime_type_id 
  ON eds.mime_type (id);
CREATE INDEX document_owner_id 
  ON eds.document (owner_id);
CREATE INDEX document_id 
  ON eds.document (id);
CREATE INDEX document_directory_id 
  ON eds.document (directory_id);
CREATE INDEX document_mime_type_id 
  ON eds.document (mime_type_id);
CREATE INDEX user_id 
  ON eds."user" (id);
CREATE INDEX document_tag_tag_id 
  ON eds.document_tag (tag_id);
CREATE INDEX document_tag_document_id 
  ON eds.document_tag (document_id);
CREATE INDEX resource_group_directory_resource_group_id 
  ON eds.resource_group_directory (resource_group_id);
CREATE INDEX resource_group_directory_directory_id 
  ON eds.resource_group_directory (directory_id);
CREATE INDEX resource_group_document_resource_group_id 
  ON eds.resource_group_document (resource_group_id);
CREATE INDEX resource_group_document_document_id 
  ON eds.resource_group_document (document_id);
