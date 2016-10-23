-- Pirghie Dimitrie - Ionut B1 - Februarie 2015 (SGBD)
-- Tema 2 - Exercitiul 2

DROP TABLE Prime;
CREATE TABLE Prime (id INTEGER, valoare INTEGER);

DECLARE
  v_prime_id      INTEGER := 1;
  v_isPrime_flag  BOOLEAN := true;
BEGIN
  FOR i IN 2 .. 2000 LOOP 
    v_isPrime_flag := true;
    
    FOR j IN 2 .. i/2 LOOP
      IF MOD(i, j ) = 0 THEN
        v_isPrime_flag := false;
      END IF; -- end if statement
      EXIT WHEN v_isPrime_flag = false; -- break loop 
    END LOOP; -- end second for loop
    
    -- INSERT i in Prime table if is prime
    IF v_isPrime_flag = true THEN
      INSERT INTO Prime VALUES (v_prime_id, i);
      v_prime_id := v_prime_id + 1; -- increment id
    END IF;
  END LOOP; -- end first for loop
  --Delete primes between 1500, 1800
  DELETE FROM Prime 
         WHERE valoare BETWEEN 1500 AND 1800;
  
  DBMS_OUTPUT.PUT_LINE(SQL%ROWCOUNT);
END;