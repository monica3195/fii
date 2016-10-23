-- Pirghie Dimitrie - Ionut B1 - Februarie 2015 (SGBD)
-- Tema 1 - Exercitiul 2

DECLARE
  c_dimBirthDay CONSTANT VARCHAR2(20) := '23-01-1994';
  v_dateCurrentSysDate DATE := sysdate;
BEGIN
  DBMS_OUTPUT.PUT_LINE('Months between ' || MONTHS_BETWEEN(v_dateCurrentSysDate, TO_DATE(c_dimBirthDay,'DD-MM-YYYY'))); 
  DBMS_OUTPUT.PUT_LINE('Days between ' || (v_dateCurrentSysDate - TO_DATE(c_dimBirthDay,'DD-MM-YYYY')));
  DBMS_OUTPUT.PUT_LINE('BirthDay: Day of week : ' || TO_CHAR(TO_DATE(c_dimBirthDay,'DD-MM-YYYY'), 'DAY'));
END;