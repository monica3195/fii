-- Pirghie Dimitrie - Ionut, Grupa B1
-- SGBD, Tema 3, Exercitiu 2

DECLARE 
  CURSOR most_val_emp_cursor (wanted_dept_id NUMBER) IS
      SELECT e.sal/(SYSDATE - e.hiredate) emp_value, e.ename emp_name, d.dname dept_name, d.loc emp_location
      FROM emp e, dept d
      WHERE e.deptno = d.deptno AND e.deptno = wanted_dept_id AND ROWNUM = 1
      ORDER BY emp_value; -- cursor extract most valuable employee details from a given department number
  
  emp_details most_val_emp_cursor%ROWTYPE; -- store most valuable emp cursor details
  c_department_number CONSTANT NUMBER(2,0) := 20;
BEGIN
  OPEN most_val_emp_cursor(c_department_number);
    FETCH most_val_emp_cursor INTO emp_details; -- only one featch, just one result in active set
    -- print employee details
    DBMS_OUTPUT.PUT_LINE('Employee name     : ' || emp_details.emp_name);
    DBMS_OUTPUT.PUT_LINE('Employee value    : ' || TRUNC(emp_details.emp_value, 5));
    DBMS_OUTPUT.PUT_LINE('Employee field    : ' || emp_details.dept_name);
    DBMS_OUTPUT.PUT_LINE('Employee location : ' || emp_details.emp_location);
  CLOSE most_val_emp_cursor;
END;