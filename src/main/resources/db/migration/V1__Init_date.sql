--CREATE SEQUENCE user_seq
--START WITH 1
--INCREMENT BY 1;
--
--CREATE SEQUENCE group_seq
--START WITH 1
--INCREMENT BY 1;
--
--CREATE SEQUENCE message_seq
--START WITH 1
--INCREMENT BY 1;

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
    user_id VARCHAR(255),
    group_id VARCHAR(255) NOT NULL,
    message_type VARCHAR(255) NOT NULL,
    ai_replied_id VARCHAR(255),
    created BIGINT NOT NULL,
    CONSTRAINT fk_message FOREIGN KEY (ai_replied_id) REFERENCES message (message_id) ON DELETE CASCADE,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (user_id) ON DELETE CASCADE
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

CREATE TABLE UZ (
    id VARCHAR(255) NOT NULL,
    login VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    user_id VARCHAR(255) NOT NULL
);

--select * from "user"
--select * from "group"
--select * from message
--select * from flyway_schema_history
--
--drop table "user" CASCADE;
--drop table "group" CASCADE;
--drop table message CASCADE;
--drop table group_user;
--delete from flyway_schema_history;
--drop table UZ
--
--drop sequence message_seq
--drop sequence group_seq
--drop sequence user_seq
