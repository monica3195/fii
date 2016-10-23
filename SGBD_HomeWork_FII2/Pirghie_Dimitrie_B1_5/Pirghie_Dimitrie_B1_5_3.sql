--Pirghie Dimitrie B1 - Tema 5 - Exercitiu 1

create or replace FUNCTION getSal
( 
  p_empno emp.empno%TYPE
) RETURN NUMBER IS
  sal_emp NUMBER;
  --e_emp_not_found EXCEPTION;
  --PRAGMA EXCEPTION_INIT (e_emp_not_found, -20997);
BEGIN
  SELECT sal INTO sal_emp
      FROM emp WHERE empno = p_empno AND ROWNUM = 1;
      
  RETURN sal_emp;
   
  EXCEPTION 
      WHEN NO_DATA_FOUND THEN
        RAISE_APPLICATION_ERROR (-20202,'Employee not found.');
 
END getSal;