-- Pirghie Dimitrie - Ionut B1 - Februarie 2015 (SGBD)
-- Tema 2 - Exercitiul 3

DROP TABLE FiboPrime;
CREATE TABLE FiboPrime (valoare NUMBER, prime NUMBER);

MERGE INTO FiboPrime
  USING Fibonacci
  ON (FiboPrime.valoare = Fibonacci.valoare)
  WHEN NOT MATCHED THEN
    INSERT VALUES (Fibonacci.valoare, 0);
    
MERGE INTO FiboPrime
  USING Prime
  ON (FiboPrime.valoare = Prime.valoare)
  WHEN MATCHED THEN
    UPDATE SET fiboprime.prime = 1;