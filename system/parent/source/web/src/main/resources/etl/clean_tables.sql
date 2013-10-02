DELETE FROM security_user_resource_group_role;
DELETE FROM security_resource_group;
DELETE FROM security_role_permission;
DELETE FROM security_role;
DELETE FROM security_permission;
DELETE FROM security_user;

DELETE FROM document_tag;
DELETE FROM resource_group_document;
DELETE FROM resource_group_directory;
DELETE FROM comment;
DELETE FROM document;
DELETE FROM mime_type;
DELETE FROM tag;
DELETE FROM directory;
DELETE FROM resource_group;
DELETE FROM "user";

ALTER SEQUENCE comment_seq RESTART WITH 1;
ALTER SEQUENCE user_seq RESTART WITH 1;
ALTER SEQUENCE document_seq RESTART WITH 1;
ALTER SEQUENCE directory_seq RESTART WITH 1;
ALTER SEQUENCE resource_group_seq RESTART WITH 1;
ALTER SEQUENCE mime_type_seq RESTART WITH 1;
ALTER SEQUENCE tag_seq RESTART WITH 1;
