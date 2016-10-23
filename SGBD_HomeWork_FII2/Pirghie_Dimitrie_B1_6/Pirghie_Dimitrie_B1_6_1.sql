--SGBD -  Pirghie Dimitrie Ionut - B1 

-- Package specification
CREATE OR REPLACE PACKAGE emp_pkg IS

  --Exercitul 1
  PROCEDURE addEmp(p_ename IN EMP.ENAME%TYPE, p_sal IN EMP.SAL%TYPE, p_job IN EMP.JOB%TYPE, p_mgr IN EMP.EMPNO%TYPE);
  --Execitiul 2
  --@Overloaded dismissal functions (called by empno NUMBER or ename CHAR)
  PROCEDURE dismissalEmp(p_empno IN emp.empno%TYPE);
  PROCEDURE dismissalEmp(p_ename IN emp.ename%TYPE);
  
  --Exercitul 3
  PROCEDURE addDepartment(p_dName IN dept.dname%TYPE, p_dLoc IN dept.loc%TYPE);  
  
  --Exercitiul 4
  PROCEDURE printEmpTable(p_where IN VARCHAR2);
  
END emp_pkg;
