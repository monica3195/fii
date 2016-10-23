--Pirghie Dimitrie B1 - Tema 5 - Exercitiu 2

-- Function which set sal value for a given empno
CREATE OR REPLACE PROCEDURE set_emp_sal
(
  p_emp_id    IN emp.empno%TYPE,
  p_sal_value IN NUMBER
) IS 
BEGIN
    UPDATE emp SET sal = p_sal_value
               WHERE empno = p_emp_id;
    COMMIT;
END set_emp_sal;

-- Function which raise sal with a value for a given empno
CREATE OR REPLACE PROCEDURE raise_emp_sal
(
  p_emp_id    IN emp.empno%TYPE,
  p_sal_raise IN NUMBER
) IS
BEGIN
  UPDATE emp
      SET sal = sal + p_sal_raise 
      WHERE empno = p_emp_id;
END raise_emp_sal;

-- Function which raise sal for all mgrs with a given value
CREATE OR REPLACE PROCEDURE raise_mgrs_sal_with
(
  p_mgr_sal_raise IN NUMBER
) IS
CURSOR cursor_emp IS
  SELECT * FROM emp;
BEGIN 
  FOR cur_emp IN cursor_emp LOOP
      IF cur_emp.job = 'MANAGER' THEN
        raise_emp_sal(cur_emp.empno, p_mgr_sal_raise);
      END IF;
  END LOOP;
END raise_mgrs_sal_with;

-- Raise mgrs sals with 500 and 3000
DECLARE
  e_mgr_sal EXCEPTION;
  PRAGMA EXCEPTION_INIT (e_mgr_sal, -20998);
  CURSOR c_emps IS
         SELECT * FROM emp WHERE job = 'MANAGER';
v_prev_sal NUMBER := 0;
BEGIN
  raise_mgrs_sal_with(500);
  
  FOR cur_emp IN c_emps LOOP
    BEGIN
      IF cur_emp.sal > 3000 THEN 
          RAISE e_mgr_sal;
      ELSE
          DBMS_OUTPUT.PUT_LINE(cur_emp.ename || ' ' || (cur_emp.sal) || ' ' || 500 );
    END IF;
    
    EXCEPTION 
        WHEN e_mgr_sal THEN
           v_prev_sal := 3500 - cur_emp.sal;
           set_emp_sal(cur_emp.empno, 3000);
           DBMS_OUTPUT.PUT_LINE(cur_emp.ename || ' ' || 3000 || ' ' || v_prev_sal);
    END;
  END LOOP;
END;