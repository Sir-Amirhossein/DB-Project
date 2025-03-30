-- مشخصات فرستنده را در کنار پیامی که می‌فرستد
CREATE OR REPLACE VIEW message_user AS
SELECT
    m.message_id,
    m.chat_id,
    m.message_text,
    m.time,
    u.user_id,
    u.first_name,
    u.last_name,
    u.username,
    u.email,
    u.phone_number
FROM
    message m
        JOIN
    user_account u ON m.sender_id = u.user_id;


-- نام کانتکت‌های یک کاربر را کنار نام آن
CREATE OR REPLACE VIEW contacts_user AS
SELECT
    ua.user_id AS user_id,
    ua.first_name AS user_first_name,
    ua.last_name AS user_last_name,
    ua.username AS user_username,
    ua.email AS user_email,
    ua.phone_number AS user_phone_number,
    ca.user_id AS contact_id,
    ca.first_name AS contact_first_name,
    ca.last_name AS contact_last_name,
    ca.username AS contact_username,
    ca.email AS contact_email,
    ca.phone_number AS contact_phone_number
FROM
    contact_list cl
        JOIN
    user_account ua ON cl.user_id = ua.user_id
        JOIN
    user_account ca ON cl.contact_id = ca.user_id;


-- نام هر کاربر با گروه‌هایی که در آن عضو است و پیام‌هایی که در هر گروه داده است
CREATE OR REPLACE VIEW group_message_user AS
SELECT
    gm.chat_id,
    gm.user_id,
    ua.first_name,
    ua.last_name,
    ua.username,
    ua.email,
    ua.phone_number,
    m.message_id,
    m.message_text,
    m.time
FROM
    group_members gm
        JOIN
    user_account ua ON gm.user_id = ua.user_id
        JOIN
    chat c ON gm.chat_id = c.chat_id
        JOIN
    message m ON gm.chat_id = m.chat_id
WHERE
    c.is_group_chat = TRUE;