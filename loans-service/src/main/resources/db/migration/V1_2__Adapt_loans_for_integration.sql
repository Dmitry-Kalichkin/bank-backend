ALTER TABLE loans
    ADD COLUMN account_id BIGINT NOT NULL CHECK ( account_id > 0 );