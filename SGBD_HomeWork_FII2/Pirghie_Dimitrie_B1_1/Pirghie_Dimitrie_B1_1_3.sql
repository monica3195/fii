-- Pirghie Dimitrie - Ionut B1 - Februarie 2015 (SGBD)
-- Tema 1 - Exercitiul 3

<<outerScope>>
DECLARE
v_dimBirthDay VARCHAR2(20) := '23-01-1994';
BEGIN
  DECLARE
    v_dimBirthDay VARCHAR2(20);
  BEGIN
    v_dimBirthDay := TO_CHAR(SYSDATE);
    DBMS_OUTPUT.PUT_LINE('Inter block : ' || v_dimBirthDay);
    DBMS_OUTPUT.PUT_LINE('Inter block <<outerScope>>: ' || outerScope.v_dimBirthDay);
  END;
  DBMS_OUTPUT.PUT_LINE('Extern block : ' || v_dimBirthDay);
END;