CREATE SEQUENCE eds.user_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.comment_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.resource_group_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.directory_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.mime_type_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.tag_seq NO MINVALUE NO MAXVALUE;
CREATE SEQUENCE eds.document_seq NO MINVALUE NO MAXVALUE;
CREATE TABLE eds.comment (
  id          int4 DEFAULT nextval('eds.comment_seq') NOT NULL, 
  user_id     int4 NOT NULL, 
  document_id int4 NOT NULL, 
  content     text NOT NULL, 
  created     timestamp NOT NULL, 
  modified    timestamp, 
  version     int4, 
  PRIMARY KEY (id));
CREATE TABLE eds.resource_group (
  id          int4 DEFAULT nextval('eds.resource_group_seq') NOT NULL, 
  founder_id  int4 NOT NULL, 
  name        varchar(30) NOT NULL, 
  description text, 
  version     int4, 
  PRIMARY KEY (id));
CREATE TABLE eds.directory (
  id                  int4 DEFAULT nextval('eds.directory_seq') NOT NULL, 
  parent_directory_id int4, 
  name                varchar(30) NOT NULL, 
  version             int4, 
  PRIMARY KEY (id));
CREATE TABLE eds.tag (
  id               int4 DEFAULT nextval('eds.tag_seq') NOT NULL, 
  value            varchar(30) NOT NULL UNIQUE, 
  normalized_value varchar(30) NOT NULL, 
  version          int4, 
  PRIMARY KEY (id));
CREATE TABLE mime_type (
  id                int4 DEFAULT nextval('eds.mime_type_seq') NOT NULL, 
  type              varchar(20) NOT NULL UNIQUE, 
  enabled           bool NOT NULL, 
  default_extension varchar(6), 
  description       text, 
  PRIMARY KEY (id));
CREATE TABLE eds.document (
  id           int4 DEFAULT nextval('eds.document_seq') NOT NULL, 
  mime_type_id int4 NOT NULL, 
  owner_id     int4 NOT NULL, 
  directory_id int4, 
  name         varchar(255) NOT NULL, 
  content_md5  varchar(32) NOT NULL, 
  version      int4, 
  PRIMARY KEY (id));
CREATE TABLE eds."user" (
  id             int4 DEFAULT nextval('eds.user_seq') NOT NULL, 
  login_name     varchar(32) NOT NULL UNIQUE, 
  password_value varchar(32) NOT NULL, 
  first_name     varchar(99) NOT NULL, 
  last_name      varchar(99) NOT NULL, 
  email          varchar(99) NOT NULL UNIQUE, 
  locked         bool DEFAULT 'FALSE' NOT NULL, 
  created        bool NOT NULL, 
  version        int4 NOT NULL, 
  last_login     timestamp, 
  PRIMARY KEY (id));
CREATE TABLE eds.document_tag (
  document_id int4 NOT NULL, 
  tag_id      int4 NOT NULL, 
  PRIMARY KEY (document_id, 
  tag_id));
CREATE TABLE eds.resource_group_directory (
  resource_group_id int4 NOT NULL, 
  directory_id      int4 NOT NULL, 
  PRIMARY KEY (resource_group_id, 
  directory_id));
CREATE TABLE eds.resource_group_document (
  resource_group_id int4 NOT NULL, 
  document_id       int4 NOT NULL, 
  PRIMARY KEY (resource_group_id, 
  document_id));
ALTER TABLE eds.comment ADD CONSTRAINT user_comment_user_id_fk FOREIGN KEY (user_id) REFERENCES eds."user" (id);
ALTER TABLE eds.comment ADD CONSTRAINT document_comment_document_id_fk FOREIGN KEY (document_id) REFERENCES eds.document (id);
ALTER TABLE eds.directory ADD CONSTRAINT directory_directory_parent_directory_id_fk FOREIGN KEY (parent_directory_id) REFERENCES eds.directory (id);
ALTER TABLE eds.document ADD CONSTRAINT mime_type_document_mime_type_id_fk FOREIGN KEY (mime_type_id) REFERENCES mime_type (id);
ALTER TABLE eds.document ADD CONSTRAINT user_document_owner_id_fk FOREIGN KEY (owner_id) REFERENCES eds."user" (id);
ALTER TABLE eds.document_tag ADD CONSTRAINT document_document_tag_document_id_fk FOREIGN KEY (document_id) REFERENCES eds.document (id);
ALTER TABLE eds.document ADD CONSTRAINT directory_document_directory_id_fk FOREIGN KEY (directory_id) REFERENCES eds.directory (id);
ALTER TABLE eds.resource_group ADD CONSTRAINT user_resource_group_founder_id_fk FOREIGN KEY (founder_id) REFERENCES eds."user" (id);
ALTER TABLE eds.resource_group_directory ADD CONSTRAINT resource_group_resource_group_directory_resource_group_id_fk FOREIGN KEY (resource_group_id) REFERENCES eds.resource_group (id);
ALTER TABLE eds.resource_group_document ADD CONSTRAINT resource_group_resource_group_document_resource_group_id_fk FOREIGN KEY (resource_group_id) REFERENCES eds.resource_group (id);
ALTER TABLE eds.document_tag ADD CONSTRAINT tag_document_tag_tag_id_fk FOREIGN KEY (tag_id) REFERENCES eds.tag (id);
ALTER TABLE eds.resource_group_directory ADD CONSTRAINT directory_resource_group_directory_directory_id_fk FOREIGN KEY (directory_id) REFERENCES eds.directory (id);
ALTER TABLE eds.resource_group_document ADD CONSTRAINT document_resource_group_document_document_id_fk FOREIGN KEY (document_id) REFERENCES eds.document (id);
CREATE INDEX comment_id 
  ON eds.comment (id);
CREATE INDEX comment_user_id 
  ON eds.comment (user_id);
CREATE INDEX comment_document_id 
  ON eds.comment (document_id);
CREATE INDEX resource_group_id 
  ON eds.resource_group (id);
CREATE INDEX resource_group_founder_id 
  ON eds.resource_group (founder_id);
CREATE INDEX directory_id 
  ON eds.directory (id);
CREATE INDEX directory_parent_directory_id 
  ON eds.directory (parent_directory_id);
CREATE INDEX tag_id 
  ON eds.tag (id);
CREATE INDEX tag_normalized_value 
  ON eds.tag (normalized_value);
CREATE INDEX mime_type_id 
  ON mime_type (id);
CREATE INDEX document_id 
  ON eds.document (id);
CREATE INDEX document_mime_type_id 
  ON eds.document (mime_type_id);
CREATE INDEX document_owner_id 
  ON eds.document (owner_id);
CREATE INDEX document_directory_id 
  ON eds.document (directory_id);
CREATE INDEX user_id 
  ON eds."user" (id);
CREATE INDEX document_tag_document_id 
  ON eds.document_tag (document_id);
CREATE INDEX document_tag_tag_id 
  ON eds.document_tag (tag_id);
CREATE INDEX resource_group_directory_resource_group_id 
  ON eds.resource_group_directory (resource_group_id);
CREATE INDEX resource_group_directory_directory_id 
  ON eds.resource_group_directory (directory_id);
CREATE INDEX resource_group_document_resource_group_id 
  ON eds.resource_group_document (resource_group_id);
CREATE INDEX resource_group_document_document_id 
  ON eds.resource_group_document (document_id);
