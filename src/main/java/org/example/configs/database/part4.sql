-- عدم تکرار شماره تلفن کاربران
ALTER TABLE user_account
ADD CONSTRAINT unique_phone_number UNIQUE (phone_number);




-- عدم مالکیت دو کاربر برای یک گروه
CREATE OR REPLACE FUNCTION check_single_owner()
RETURNS TRIGGER AS $$
BEGIN
    IF (SELECT COUNT(*) FROM admin_group WHERE chat_id = NEW.chat_id) >= 1 THEN
        RAISE EXCEPTION 'A group can have only one owner';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ایجاد تریگر برای استفاده از تابع تریگر
CREATE TRIGGER trigger_check_single_owner
    BEFORE INSERT ON admin_group
    FOR EACH ROW
    EXECUTE FUNCTION check_single_owner();