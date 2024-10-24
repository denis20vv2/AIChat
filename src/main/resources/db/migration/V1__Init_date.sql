
CREATE TABLE "user" (
    user_id VARCHAR(255) NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    avatar VARCHAR(255) NOT NULL
);


CREATE TABLE "group" (
    group_id VARCHAR(255) NOT NULL PRIMARY KEY,
    avatar VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    last_message_id VARCHAR(255)
);


CREATE TABLE message (
    message_id VARCHAR(255) NOT NULL PRIMARY KEY,
    message TEXT NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    group_id VARCHAR(255) NOT NULL,
    message_type VARCHAR(255) NOT NULL,
    ai_replied_id VARCHAR(255),
    created TIMESTAMP NOT NULL

);

ALTER TABLE "group"
ADD CONSTRAINT fk_last_message FOREIGN KEY (last_message_id) REFERENCES message(message_id) ON DELETE CASCADE;


CREATE TABLE group_user (
    group_id VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL,
    PRIMARY KEY (group_id, user_id),
    CONSTRAINT fk_group FOREIGN KEY (group_id) REFERENCES "group" (group_id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE
);

ALTER TABLE group_user
ADD CONSTRAINT fk_user_message FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE,
ADD CONSTRAINT fk_group_message FOREIGN KEY (group_id) REFERENCES "group" (group_id) ON DELETE CASCADE