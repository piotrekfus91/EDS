CREATE TRIGGER user_create_root_directory_after_insert_trigger
  AFTER INSERT ON "user"
  REFERENCING NEW ROW AS new_row
  FOR EACH ROW
  INSERT INTO directory (id, parent_directory_id, owner_id, name, version)
    VALUES (NEXT VALUE FOR directory_seq, NULL, new_row.id, '/', 0);

-- chytry myk, aby pozbyc sie 0 z sekwencji,
-- z ktora nie radzi sobie dynatree
ALTER SEQUENCE directory_seq RESTART WITH 1;