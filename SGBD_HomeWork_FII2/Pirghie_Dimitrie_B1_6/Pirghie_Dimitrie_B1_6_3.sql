CREATE OR REPLACE PACKAGE BODY emp_pkg IS
  -- forword declarions
  PROCEDURE addDepartment(p_dName IN dept.dname%TYPE, p_dLoc IN dept.loc%TYPE);
  FUNCTION getNewDeptNo RETURN dept.deptno%TYPE;
  
  --Implementation
  --Add new department
  PROCEDURE addDepartment(p_dName IN dept.dname%TYPE, p_dLoc IN dept.loc%TYPE) IS
  v_next_deptno dept.deptno%TYPE;
  BEGIN
    v_next_deptno := getNewDeptNo();
    INSERT INTO dept (deptno, dname, loc) VALUES (getNewDeptNo, p_dName, p_dLoc);
  END addDepartment;
  
  --Function generate next deptno
  FUNCTION getNewDeptNo RETURN dept.deptno%TYPE
  IS
  v_last_deptno dept.deptno%TYPE;
  BEGIN
   SELECT deptno
      INTO v_last_deptno
      FROM 
        (SELECT deptno
          FROM dept      
          ORDER BY deptno DESC) deptnodesc
      WHERE ROWNUM = 1;
      
      RETURN (v_last_deptno+10);
  END getNewDeptNo;
END emp_pkg;