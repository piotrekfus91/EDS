CREATE TABLE eds.people(
	id bigint NOT NULL DEFAULT nextval('users_seq'),
	login varchar(30) NOT NULL,
	password varchar(32) NOT NULL,
	first_name varchar(30) NOT NULL,
	last_name varchar(50) NOT NULL,
	email varchar(50) NOT NULL,
	locked char NOT NULL DEFAULT 'N',
	creation_time timestamptz NOT NULL,
	last_login_time timestamptz,
	CONSTRAINT users_pk PRIMARY KEY (id),
	CONSTRAINT users_locked_check CHECK (locked IN ('Y', 'N'))
)
WITH (OIDS=TRUE);

CREATE TABLE eds.mime_types(
	id integer NOT NULL DEFAULT nextval('mime_types_seq'),
	type varchar(20) NOT NULL,
	enabled char NOT NULL,
	default_extension varchar(6),
	description text,
	CONSTRAINT mime_type_pk PRIMARY KEY (id),
	CONSTRAINT mime_type_enabled_check CHECK (enabled IN ('Y', 'N'))
)
WITH (OIDS=TRUE);

CREATE TABLE eds.directories(
	id bigint NOT NULL DEFAULT nextval('directories_id'),
	parent_directory_id bigint,
	owner_id bigint NOT NULL,
	name varchar(30) NOT NULL,
	CONSTRAINT directories_pk PRIMARY KEY (id),
	CONSTRAINT directories_owner_id FOREIGN KEY (owner_id)
	REFERENCES eds.people (id) MATCH FULL
	ON DELETE SET NULL ON UPDATE SET NULL NOT DEFERRABLE,
	CONSTRAINT directories_parent_directory_id FOREIGN KEY (parent_directory_id)
	REFERENCES eds.directories (id) MATCH FULL
	ON DELETE CASCADE ON UPDATE CASCADE NOT DEFERRABLE
)
WITH (OIDS=TRUE);

CREATE TABLE eds.documents(
	id bigint NOT NULL DEFAULT nextval('documents_seq'),
	mime_type_id integer,
	directory_id bigint,
	owner_id bigint NOT NULL,
	name varchar(255) NOT NULL,
	content_md5 varchar(32) NOT NULL,
	CONSTRAINT documents_pk PRIMARY KEY (id),
	CONSTRAINT documents_mime_type_id_fk FOREIGN KEY (mime_type_id)
	REFERENCES eds.mime_types (id) MATCH FULL
	ON DELETE SET NULL ON UPDATE SET NULL NOT DEFERRABLE,
	CONSTRAINT documents_directory_id FOREIGN KEY (directory_id)
	REFERENCES eds.directories (id) MATCH FULL
	ON DELETE SET NULL ON UPDATE SET NULL NOT DEFERRABLE,
	CONSTRAINT documents_owner_id FOREIGN KEY (owner_id)
	REFERENCES eds.people (id) MATCH FULL
	ON DELETE SET NULL ON UPDATE SET NULL NOT DEFERRABLE
)
WITH (OIDS=TRUE);

CREATE TABLE eds.comments(
	id bigint NOT NULL DEFAULT nextval('comments_seq'),
	user_id bigint NOT NULL,
	document_id bigint NOT NULL,
	modifable char NOT NULL DEFAULT 'Y',
	content text NOT NULL,
	creation_time timestamptz NOT NULL,
	updating_time timestamptz,
	CONSTRAINT comments_id PRIMARY KEY (id),
	CONSTRAINT comments_user_id_fk FOREIGN KEY (user_id)
	REFERENCES eds.people (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT comments_document_id_fk FOREIGN KEY (document_id)
	REFERENCES eds.documents (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT comments_modifable_check CHECK (modifable IN ('Y', 'N'))
)
WITH (OIDS=TRUE);

CREATE TABLE eds.tags(
	id bigint NOT NULL DEFAULT nextval('tags_seq'),
	value varchar(30) NOT NULL,
	normalized_value varchar(30) NOT NULL,
	CONSTRAINT tags_pk PRIMARY KEY (id)
)
WITH (OIDS=TRUE);

CREATE TABLE eds.documents_tags(
	document_id bigint NOT NULL,
	tag_id bigint NOT NULL,
	CONSTRAINT document_tags_pk PRIMARY KEY (document_id,tag_id),
	CONSTRAINT document_tags_document_id_fk FOREIGN KEY (document_id)
	REFERENCES eds.documents (id) MATCH FULL
	ON DELETE SET NULL ON UPDATE SET NULL NOT DEFERRABLE,
	CONSTRAINT document_tags_tag_id_fk FOREIGN KEY (tag_id)
	REFERENCES eds.tags (id) MATCH FULL
	ON DELETE SET NULL ON UPDATE SET NULL NOT DEFERRABLE
)
WITH (OIDS=TRUE);

CREATE TABLE eds.resource_groups(
	id bigint NOT NULL DEFAULT nextval('resource_groups_seq'),
	parent_resource_group_id bigint,
	owner_id bigint,
	name varchar(30) NOT NULL,
	CONSTRAINT resource_groups_pk PRIMARY KEY (id),
	CONSTRAINT resource_groups_parent_resource_group_id FOREIGN KEY (parent_resource_group_id)
	REFERENCES eds.resource_groups (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT resource_groups_owner_id_fk FOREIGN KEY (owner_id)
	REFERENCES eds.people (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE
)
WITH (OIDS=TRUE);

CREATE TABLE eds.resource_groups_directories(
	resource_group_id bigint NOT NULL,
	directory_id bigint NOT NULL,
	CONSTRAINT resource_groups_directories_pk PRIMARY KEY (resource_group_id,directory_id),
	CONSTRAINT resource_groups_directories_resource_group_id_fk FOREIGN KEY (resource_group_id)
	REFERENCES eds.resource_groups (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT resource_groups_directories_directory_id_fk FOREIGN KEY (directory_id)
	REFERENCES eds.directories (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE
)
WITH (OIDS=TRUE);

CREATE TABLE eds.resource_groups_documents(
	resource_group_id bigint NOT NULL,
	document_id bigint NOT NULL,
	CONSTRAINT resource_groups_documents_pk PRIMARY KEY (resource_group_id,document_id),
	CONSTRAINT resource_groups_documents_resource_group_id_fk FOREIGN KEY (resource_group_id)
	REFERENCES eds.resource_groups (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE,
	CONSTRAINT resource_groups_documents_document_id_fk FOREIGN KEY (document_id)
	REFERENCES eds.documents (id) MATCH FULL
	ON DELETE NO ACTION ON UPDATE NO ACTION NOT DEFERRABLE
)
WITH (OIDS=TRUE);
