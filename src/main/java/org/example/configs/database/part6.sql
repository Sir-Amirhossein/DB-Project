CREATE TABLE IF NOT EXISTS audit_log (
    id SERIAL PRIMARY KEY,
    table_name TEXT NOT NULL,
    operation_type TEXT NOT NULL,
    old_data JSONB,
    new_data JSONB,
    change_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    username varchar(50),
    FOREIGN KEY (username) REFERENCES user_account(username) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE OR REPLACE FUNCTION log_user_changes()
RETURNS TRIGGER AS $$
BEGIN
    IF (TG_OP = 'INSERT') THEN
        INSERT INTO audit_log(table_name, operation_type, new_data, user_name)
        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(NEW), current_user);
RETURN NEW;
ELSIF (TG_OP = 'UPDATE') THEN
        INSERT INTO audit_log(table_name, operation_type, old_data, new_data, user_name)
        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(OLD), row_to_json(NEW), current_user);
RETURN NEW;
ELSIF (TG_OP = 'DELETE') THEN
        INSERT INTO audit_log(table_name, operation_type, old_data, user_name)
        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(OLD), current_user);
RETURN OLD;
END IF;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER user_changes_trigger
    AFTER INSERT OR UPDATE OR DELETE ON chat
    FOR EACH ROW
    EXECUTE FUNCTION log_user_changes();