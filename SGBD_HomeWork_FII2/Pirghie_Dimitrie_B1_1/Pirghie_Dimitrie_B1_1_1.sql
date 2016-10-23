-- Pirghie Dimitrie - Ionut B1 - Februarie 2015 (SGBD)
-- Tema 1 - Exercitiul 1

DECLARE
v_len_ename_max INTEGER := 0;
v_ename_len_max emp.ename%TYPE;

BEGIN

SELECT MAX(LENGTH(TRIM(ename)))
       INTO v_len_ename_max
       FROM emp;
       
SELECT ename 
       INTO v_ename_len_max
       FROM emp 
       WHERE LENGTH(TRIM(ename)) = v_len_ename_max 
                                 AND ROWNUM = 1; -- avoid "exact fetch returns more than requested number of rows"
                                
DBMS_OUTPUT.PUT_LINE(INITCAP(v_ename_len_max));
DBMS_OUTPUT.PUT_LINE(v_len_ename_max);
END;