package org.example.configs.database;

import org.example.presenters.database.DatabaseConnector;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TablesStatements {
    public void createTables() {
        String user = "CREATE TABLE IF NOT EXISTS user_account (" +
                "user_id SERIAL PRIMARY KEY," +
                "first_name VARCHAR(50) NOT NULL," +
                "last_name VARCHAR(50) NOT NULL," +
                "username VARCHAR(50) UNIQUE NOT NULL," +
                "phone_number VARCHAR(15) NOT NULL," +
                "email VARCHAR(50) NOT NULL" +
                ")";

        String contactList = "CREATE TABLE IF NOT EXISTS contact_list (" +
                "id SERIAL PRIMARY KEY," +
                "user_id INT NOT NULL," +
                "contact_id INT NOT NULL," +
                "FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (contact_id) REFERENCES user_account(user_id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";

        String chat = "CREATE TABLE IF NOT EXISTS chat (" +
                "chat_id SERIAL PRIMARY KEY," +
                "is_group_chat BOOLEAN DEFAULT FALSE," +
                "timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ")";

        String message = "CREATE TABLE IF NOT EXISTS message (" +
                "message_id SERIAL PRIMARY KEY," +
                "chat_id INT NOT NULL," +
                "sender_id INT NOT NULL," +
                "message_text TEXT NOT NULL," +
                "time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (chat_id) REFERENCES chat(chat_id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (sender_id) REFERENCES user_account(user_id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";

        String groupMembers = "CREATE TABLE IF NOT EXISTS group_members (" +
                "id SERIAL PRIMARY KEY," +
                "chat_id INT NOT NULL," +
                "user_id INT NOT NULL," +
                "FOREIGN KEY (chat_id) REFERENCES chat(chat_id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (user_id) REFERENCES user_account(user_id) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";

        String adminOfGroups = "CREATE TABLE IF NOT EXISTS admin_group (" +
                "id SERIAL PRIMARY KEY," +
                "chat_id INT NOT NULL," +
                "admin_id INT NOT NULL," +
                "FOREIGN KEY (chat_id) REFERENCES chat(chat_id) ON DELETE CASCADE ON UPDATE CASCADE," +
                "FOREIGN KEY (admin_id) REFERENCES user_account(user_id) ON DELETE SET NULL ON UPDATE CASCADE" +
                ")";

        String chatLog = "CREATE TABLE IF NOT EXISTS chat_log (" +
                "id SERIAL PRIMARY KEY," +
                "table_name TEXT NOT NULL," +
                "operation_type TEXT NOT NULL," +
                "old_data JSONB," +
                "new_data JSONB," +
                "change_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "username varchar(50)," +
                "FOREIGN KEY (username) REFERENCES user_account(username) ON DELETE CASCADE ON UPDATE CASCADE" +
                ")";

        String dropUserIndex = "DROP INDEX IF EXISTS idx_username";
        String dropMessageIndex = "DROP INDEX IF EXISTS idx_chat_id_time";
        String indexOnUserAccount = "CREATE INDEX idx_username ON user_account(username)";
        String indexOnMessage = "CREATE INDEX idx_chat_id_time ON message(chat_id, time)";

//  **********               Part5         **********

//                theses were for create a user to connect to database. ------> part5.sql
//        String a = "CREATE USER Azad_Arousha WITH PASSWORD 'azad2000Arousha'";
//        String b = "GRANT CONNECT ON DATABASE postgres TO Azad_Arousha";
//        String c = "GRANT USAGE ON SCHEMA public TO Azad_Arousha";
//        String d = "GRANT SELECT ON ALL TABLES IN SCHEMA public TO Azad_Arousha";
//        String f = "ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO Azad_Arousha";


//  ****************** Part 6 ******************

//        String func = """
//                CREATE OR REPLACE FUNCTION log_user_changes()
//                RETURNS TRIGGER AS $$
//                BEGIN
//                    IF (TG_OP = 'INSERT') THEN
//                        INSERT INTO audit_log(table_name, operation_type, new_data, user_name)
//                        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(NEW), current_user);
//                RETURN NEW;
//                ELSIF (TG_OP = 'UPDATE') THEN
//                        INSERT INTO audit_log(table_name, operation_type, old_data, new_data, user_name)
//                        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(OLD), row_to_json(NEW), current_user);
//                RETURN NEW;
//                ELSIF (TG_OP = 'DELETE') THEN
//                        INSERT INTO audit_log(table_name, operation_type, old_data, user_name)
//                        VALUES (TG_TABLE_NAME, TG_OP, row_to_json(OLD), current_user);
//                RETURN OLD;
//                END IF;
//                END;
//                $$ LANGUAGE plpgsql""";
//
//        String trigger = """
//                CREATE TRIGGER user_changes_trigger
//                    AFTER INSERT OR UPDATE OR DELETE ON chat
//                    FOR EACH ROW
//                    EXECUTE FUNCTION log_user_changes()""";

        try {
            DatabaseConnector connector = DatabaseConnector.getConnector();
            Connection connection = connector.startConnection();
            Statement statement = connection.createStatement();
            statement.execute(user);
            statement.execute(contactList);
            statement.execute(chat);
            statement.execute(message);
            statement.execute(groupMembers);
            statement.execute(adminOfGroups);
            statement.execute(chatLog);
            statement.execute(dropUserIndex);
            statement.execute(dropMessageIndex);
            statement.execute(indexOnUserAccount);
            statement.execute(indexOnMessage);
            statement.close();
            connector.closeConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}