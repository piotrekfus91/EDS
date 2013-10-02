CREATE OR REPLACE FUNCTION user_create_root_directory_after_insert_trigger_function()
  RETURNS trigger AS $$
BEGIN
  INSERT INTO directory("version", parent_directory_id, owner_id, "name") VALUES (0, NULL, NEW.id, '/');
  RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS user_create_root_directory_after_insert_trigger ON "user";
CREATE TRIGGER user_create_root_directory_after_insert_trigger
AFTER INSERT ON "user"
FOR EACH ROW
EXECUTE PROCEDURE user_create_root_directory_after_insert_trigger_function();