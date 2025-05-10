
INSERT INTO "user" (user_id, name, avatar) VALUES
('user1', 'John Doe', 'avatar1.jpg'),
('user2', 'Jane Smith', 'avatar2.jpg'),
('user3', 'Jony Barny', 'avatar1.jpg'),
('user4', 'Sasha Grey', 'avatar2.jpg'),
('AI', 'AI', 'avatarAI.jpg');

INSERT INTO "group" (group_id, avatar, name) VALUES
('317e8505-e207-43cf-adfd-20e7cf700494', 'group_avatar1.jpg', 'Group One'),
('00b4b700-2993-41de-8640-c984594eebc8', 'group_avatar2.jpg', 'Group Two');

INSERT INTO message (message_id, message, user_id, group_id, message_type, ai_replied_id, created) VALUES
('ca0a6a41-f5dd-4fe0-a337-7db397df0023', 'Hello, World!', 'user2', '317e8505-e207-43cf-adfd-20e7cf700494', 'user' , null, 2024111313455),
('0186cae8-2460-4432-a41c-ce91bff78b1d', 'Hello', 'user2', '317e8505-e207-43cf-adfd-20e7cf700494', 'user', null, 2024111313415),
('c88931af-a484-4349-b2dd-53cd1a9b45f9', 'Hello, a am john!', 'user1', '317e8505-e207-43cf-adfd-20e7cf700494', 'user' , null, 2024111313405),
('2a0b283f-2588-4fbc-8ef5-358c725c15f3', 'How are you?', 'user1', '00b4b700-2993-41de-8640-c984594eebc8', 'user' , null, 2024111313450),
('2a0b283f-2588-4fbc-8ef5-358c72cc15f1', 'How are you?', 'user1', '00b4b700-2993-41de-8640-c984594eebc8', 'AI' ,null, 2024111313450),
('2a0b283f-2588-4fbc-8ef5-358c72ac15f2', 'Good', 'AI', '00b4b700-2993-41de-8640-c984594eebc8', 'AI' ,'2a0b283f-2588-4fbc-8ef5-358c72cc15f1', 2024111313452);

INSERT INTO group_user (group_id, user_id) VALUES
('317e8505-e207-43cf-adfd-20e7cf700494', 'user1'),
('317e8505-e207-43cf-adfd-20e7cf700494', 'user2'),
('317e8505-e207-43cf-adfd-20e7cf700494', 'user3'),
('317e8505-e207-43cf-adfd-20e7cf700494', 'user4');

INSERT INTO UZ (id, login, password, user_id) VALUES
('id1', 'admin', 'admin', 'user1'),
('id2', 'user', 'user123', 'user2');

INSERT INTO reaction (reaction_id, message_id, emoji) VALUES
('id1', '2a0b283f-2588-4fbc-8ef5-358c72ac15f2', '😂'),
('id2', '2a0b283f-2588-4fbc-8ef5-358c72ac15f2', '😂'),
('id3', '2a0b283f-2588-4fbc-8ef5-358c72ac15f2', '💩'),
('id4', '2a0b283f-2588-4fbc-8ef5-358c72ac15f2', '😎');

INSERT INTO reaction_user (reaction_id, user_id) VALUES
('id1', 'user1'),
('id2', 'user2'),
('id3', 'user3'),
('id4', 'user4');

