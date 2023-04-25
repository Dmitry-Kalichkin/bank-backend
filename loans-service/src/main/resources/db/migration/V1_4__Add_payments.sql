CREATE TABLE IF NOT EXISTS payments (
    id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    loan_id BIGINT REFERENCES loans(id),
    amount NUMERIC(12, 2) NOT NULL,
    is_overdue BOOL,
    date DATE NOT NULL,
    CHECK ( amount > 0 )
);

CREATE OR REPLACE FUNCTION add_payments()
    RETURNS trigger
AS $add_payments$
DECLARE
    payment_value NUMERIC(12, 2);
    percent NUMERIC(4,2);
    payment_date DATE := NOW();
BEGIN
    SELECT (interest_rate + 100) / 100
    INTO percent
    FROM rates WHERE rates.id = NEW.rate_id;

    UPDATE loans
    SET amount = amount * percent, amount_remain = amount_remain * percent
    WHERE id = NEW.id;

    payment_value := NEW.amount * percent / NEW.length;

    FOR i IN 1..NEW.length LOOP
        INSERT INTO payments
        VALUES (DEFAULT, NEW.id, payment_value, NULL, payment_date);
        payment_date := payment_date + INTERVAL  '1 day';
    END LOOP;

    RETURN NEW;
END;
$add_payments$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER add_payments
    AFTER INSERT ON loans
    FOR EACH ROW
    EXECUTE PROCEDURE add_payments();
