-- Pirghie Dimitrie - Ionut, Grupa B1
-- SGBD, Tema 3, Exercitiu 1

DECLARE
  v_employee_name emp.ename%TYPE;
  CURSOR c_employees_cursor IS
         SELECT ename 
         FROM emp
         WHERE sal > 1500;
BEGIN
  OPEN c_employees_cursor;
      LOOP
          FETCH c_employees_cursor INTO v_employee_name;
          EXIT WHEN c_employees_cursor%NOTFOUND;
          DBMS_OUTPUT.PUT_LINE('Employee name ' || v_employee_name);
      END LOOP;
  CLOSE c_employees_cursor;
END;