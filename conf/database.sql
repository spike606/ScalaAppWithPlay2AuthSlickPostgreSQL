--
-- sudo -u postgres psql -p 55555 -d templatesite_db -f conf/database.sql
--
DROP TABLE IF EXISTS message;
DROP TABLE IF EXISTS account;
DROP TYPE IF EXISTS account_role;

CREATE TYPE account_role AS ENUM (
    'normal',
    'admin'
);

CREATE TABLE account (
    id serial PRIMARY KEY,
    name text NOT NULL,
    surname text NOT NULL,
    email text UNIQUE NOT NULL,
    telephone text NOT NULL,
    password text NOT NULL,
    role account_role NOT NULL,
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now()
);

CREATE TABLE message (
    id serial PRIMARY KEY,
    title text NOT NULL,
    content text NOT NULL,
    account_id int REFERENCES account(id),
    created_at timestamp with time zone not null default now(),
    updated_at timestamp with time zone not null default now()
);

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO templatesite_user;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO templatesite_user;

-- bcrypted password values are password in both users
INSERT INTO account (name, surname, email, telephone, role, password) values ('Admin User', 'Admin surname', 'admin@tetrao.eu', '+48 888-888-888', 'admin', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu');
INSERT INTO account (name, surname, email, telephone, role, password) values ('Bob ', 'Minion', 'bob@tetrao.eu', '+48 555-555-888', 'normal', '$2a$10$8K1p/a0dL1LXMIgoEDFrwOfMQbLgtnOoKsWc.6U6H0llP3puzeeEu');
INSERT INTO message (title, content, account_id) values ('Admin message', 'Test admin message', 1);
INSERT INTO message (title, content, account_id) values ('Test message 2', 'Test admin message content', 1);
INSERT INTO message (title, content, account_id) values ('Test message 3', 'Test admin message', 1);
INSERT INTO message (title, content, account_id) values ('Test message 4', 'Test admin message', 1);
INSERT INTO message (title, content, account_id) values ('User message 1', 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. ', 2);
INSERT INTO message (title, content, account_id) values ('User message 2', 'Even the all-powerful Pointing has no control about the blind texts it is an almost unorthographic life One day however a small line of blind text by the name of Lorem Ipsum decided to leave for the far World of Grammar. ', 2);
INSERT INTO message (title, content, account_id) values ('User message 3', 'Far far away, behind the word mountains, far from the countries Vokalia and Consonantia, there live the blind texts. Separated they live in Bookmarksgrove right at the coast of the Semantics, a large language ocean. A small river named Duden flows by their place and supplies it with the necessary regelialia. It is a paradisematic country, in which roasted parts of sentences fly into your mouth. ', 2);
INSERT INTO message (title, content, account_id) values ('User message 4', 'The Big Oxmox advised her not to do so, because there were thousands of bad Commas, wild Question Marks and devious Semikoli, but the Little Blind Text didn’t listen. She packed her seven versalia, put her initial into the belt and made herself on the way.', 2);
INSERT INTO message (title, content, account_id) values ('User message 5', 'When she reached the first hills of the Italic Mountains, she had a last view back on the skyline of her hometown Bookmarksgrove, the headline of Alphabet Village and the subline of her own road, the Line Lane.', 2);
INSERT INTO message (title, content, account_id) values ('User message 6', 'A wonderful serenity has taken possession of my entire soul, like these sweet mornings of spring which I enjoy with my whole heart. I am alone, and feel the charm of existence in this spot, which was created for the bliss of souls like mine. I am so happy, my dear friend, so absorbed in the exquisite sense of mere tranquil existence, that I neglect my talents. I should be incapable of drawing a single stroke at the present moment; and yet I feel that I never was a greater artist than now. ', 2);