ALTER TABLE users
    ADD COLUMN email varchar NOT NULL UNIQUE,
    ADD COLUMN authorities varchar[] NOT NULL DEFAULT '{USER}',
    ADD COLUMN created DATE NOT NULL DEFAULT CURRENT_DATE,
    ADD COLUMN is_locked BOOL NOT NULL DEFAULT false;

