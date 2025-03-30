    -- تابع شمارش پیام های بین دو کاربر
CREATE OR REPLACE FUNCTION count_messages_between_users(user1_id INT, user2_id INT)
RETURNS INT AS $$
    DECLARE total_messages INT;
    BEGIN
        SELECT COUNT(*)
        INTO total_messages
        FROM message m
                 JOIN chat c ON m.chat_id = c.chat_id
                 JOIN group_members gm1 ON c.chat_id = gm1.chat_id AND gm1.user_id = user1_id
                 JOIN group_members gm2 ON c.chat_id = gm2.chat_id AND gm2.user_id = user2_id
        WHERE c.is_group_chat = FALSE;

        RETURN total_messages;
    END;
$$ LANGUAGE plpgsql;


   -- تابع برای دریافت کاربران فعال در ۲۴ ساعت اخیر
CREATE OR REPLACE FUNCTION get_recent_active_users()
RETURNS TABLE(user_id INT, username VARCHAR) AS $$
BEGIN
RETURN QUERY
SELECT DISTINCT u.user_id, u.username
FROM user_account u
         JOIN message m ON u.user_id = m.sender_id
WHERE m.time >= NOW() - INTERVAL '24 HOURS';
END;
$$ LANGUAGE plpgsql;


   -- تابع برای گرفتن هیستوری پیام بین دو کاربر
CREATE OR REPLACE FUNCTION get_conversation_history(user1_id INT, user2_id INT, limit INT)
RETURNS TABLE(chat_id INT, sender_id INT, message_text TEXT, time TIMESTAMP) AS $$
    BEGIN
    RETURN QUERY
    SELECT m.chat_id, m.sender_id, m.message_text, m.time
    FROM message m
             JOIN chat c ON m.chat_id = c.chat_id
             JOIN group_members gm1 ON c.chat_id = gm1.chat_id AND gm1.user_id = user1_id
             JOIN group_members gm2 ON c.chat_id = gm2.chat_id AND gm2.user_id = user2_id
    WHERE c.is_group_chat = FALSE
    ORDER BY m.time DESC
        LIMIT limit;
    END;
$$ LANGUAGE plpgsql;


   -- تابع برای جستجوی پیامها بر اساس کلمه کلیدی
CREATE OR REPLACE FUNCTION search_messages(keyword TEXT)
RETURNS TABLE(chat_id INT, sender_id INT, message_text TEXT, time TIMESTAMP) AS $$
    BEGIN
    RETURN QUERY
    SELECT m.chat_id, m.sender_id, m.message_text, m.time
    FROM message m
    WHERE m.message_text ILIKE '%' || keyword || '%'
    ORDER BY m.time DESC;
    END;
$$ LANGUAGE plpgsql;

