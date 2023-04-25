ALTER TABLE loans
    ADD COLUMN length SMALLINT NOT NULL
    CHECK ( length BETWEEN 1  AND 360);
-- loan can be given for a period from 1 month to 30 years