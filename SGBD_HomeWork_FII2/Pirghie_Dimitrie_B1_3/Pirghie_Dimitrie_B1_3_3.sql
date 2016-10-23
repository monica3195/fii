-- Pirghie Dimitrie - Ionut, Grupa B1
-- SGBD, Tema 3, Exercitiu 3

DECLARE
  CURSOR emp_manager_cursor IS
      SELECT empno emp_id, mgr mgr_id, hiredate emp_hire_date, sal emp_sal
      FROM emp;
      
  CURSOR manager_emp_cursor IS
      SELECT empno emp_id, hiredate mgr_hire_date, sal mgr_sal
      FROM emp;
      
  emp_mgr_ptr emp_manager_cursor%ROWTYPE; --ptr to emp...
  mgr_emp_ptr manager_emp_cursor%ROWTYPE; -- ptr to mgr
  
BEGIN
  FOR mgr_emp_ptr IN manager_emp_cursor LOOP
    FOR emp_mgr_ptr IN emp_manager_cursor LOOP
        IF (emp_mgr_ptr.mgr_id = mgr_emp_ptr.emp_id) THEN
            IF (emp_mgr_ptr.emp_sal + mgr_emp_ptr.mgr_sal) > 400 THEN
                IF (emp_mgr_ptr.emp_hire_date > mgr_emp_ptr.mgr_hire_date) THEN
                    -- update emp and mgr sal
                    UPDATE emp
                      SET emp.sal = emp.sal + 1
                      WHERE (emp.empno = emp_mgr_ptr.mgr_id) OR (emp.empno = mgr_emp_ptr.emp_id);
                      DBMS_OUTPUT.PUT_LINE(emp_mgr_ptr.mgr_id || '<->' || mgr_emp_ptr.emp_id);
                END IF;
            END IF;
        END IF;
    END LOOP;
  END LOOP;

END;