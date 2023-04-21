ALTER TABLE IF EXISTS accounts
    ADD COLUMN creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
    ADD COLUMN blocking_date DATE,
    ADD COLUMN closing_date DATE,
    DROP COLUMN is_blocked