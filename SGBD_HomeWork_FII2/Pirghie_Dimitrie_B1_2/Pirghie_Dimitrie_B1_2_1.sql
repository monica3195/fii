-- Pirghie Dimitrie - Ionut B1 - Februarie 2015 (SGBD)
-- Tema 2 - Exercitiul 1

DROP TABLE Fibonacci;
CREATE TABLE Fibonacci (id INTEGER, valoare INTEGER);

DECLARE
  v_fib_current1 INTEGER := 1;
  v_fib_current2 INTEGER := 1;
  v_fib_next     INTEGER;
  v_fib_id       INTEGER := 1;
BEGIN
  -- INSERT first two FIB's sequence numbers
  INSERT INTO Fibonacci VALUES (v_fib_id, v_fib_current1);
  v_fib_id := v_fib_id + 1;
  INSERT INTO Fibonacci VALUES (v_fib_id, v_fib_current1);
  v_fib_id := v_fib_id + 1;
  -- INSERT remaining elements less than 1000
  WHILE (v_fib_current1 + v_fib_current2) < 1000 LOOP
    v_fib_next := (v_fib_current1 + v_fib_current2);
    INSERT INTO Fibonacci VALUES (v_fib_id, v_fib_next);
    v_fib_id       := v_fib_id + 1;
    v_fib_current1 := v_fib_current2;
    v_fib_current2 := v_fib_next;
  END LOOP;
END;