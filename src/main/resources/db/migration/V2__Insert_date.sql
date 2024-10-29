
INSERT INTO "user" (user_id, name, avatar) VALUES
('user1', 'John Doe', 'avatar1.jpg'),
('user2', 'Jane Smith', 'avatar2.jpg');

INSERT INTO "group" (group_id, avatar, name) VALUES
('group1', 'group_avatar1.jpg', 'Group One'),
('group2', 'group_avatar2.jpg', 'Group Two');

INSERT INTO message (message_id, message, user_id, group_id, message_type, ai_replied_id, created) VALUES
('msg1', 'Hello, World!', 'user1', 'group1', 'user' , 'ai_reply_1', CURRENT_TIMESTAMP),
('msg2', 'AI replied message', 'user2', 'group1', 'user', 'ai_reply_2', CURRENT_TIMESTAMP),
('msg3', 'Hello, World!', 'user3', 'group1', 'user' , 'ai_reply_3', CURRENT_TIMESTAMP),
('msg4', 'AI replied message', 'user4', 'group2', 'user', 'ai_reply_4', CURRENT_TIMESTAMP);


INSERT INTO group_user (group_id, user_id) VALUES
('group1', 'user1'),
('group1', 'user2');

INSERT INTO UZ (id, login, password, user_id) VALUES
('id1', 'login', '123', 'user1');
