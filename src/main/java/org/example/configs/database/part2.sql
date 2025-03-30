CREATE INDEX idx_username ON user_account(username);
CREATE INDEX idx_chat_id_time ON message(chat_id, time);