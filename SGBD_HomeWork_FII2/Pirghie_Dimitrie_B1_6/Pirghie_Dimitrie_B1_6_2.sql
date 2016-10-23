create or replace PACKAGE BODY emp_pkg IS
    --forword declarations
    
    --Exercitul 2
    FUNCTION checkEmpExistance(p_ename emp.ename%TYPE) RETURN BOOLEAN;
    FUNCTION checkEmpExistance(p_empno emp.empno%TYPE) RETURN BOOLEAN;
    FUNCTION getEmpDept(p_ename emp.ename%TYPE) RETURN emp.deptno%TYPE;
    FUNCTION getEmpDept(p_empno emp.empno%TYPE) RETURN emp.deptno%TYPE;
    FUNCTION getDeptEmpCount(p_deptno dept.deptno%TYPE) RETURN INTEGER;
    PROCEDURE removeEmptyDept(p_deptno IN dept.deptno%TYPE);
    
    --Exercitul 3
    --PROCEDURE addDepartment(p_dName IN dept.dname%TYPE, p_dLoc IN dept.loc%TYPE);
    FUNCTION getNewDeptNo RETURN dept.deptno%TYPE;
    FUNCTION getNewEmpNo RETURN EMP.EMPNO%TYPE;
    
    --generate new emp no
    FUNCTION getNewEmpNo
    RETURN EMP.EMPNO%TYPE IS
    v_new_empno EMP.EMPNO%TYPE;
    BEGIN
      SELECT empno 
        INTO v_new_empno
        FROM (SELECT empno
              FROM emp
              ORDER BY empno DESC) 
        WHERE ROWNUM = 1;
        
    RETURN v_new_empno+1;
    
    END getNewEmpNo;
    
    FUNCTION countEmpsByJob (p_job emp.job%TYPE) 
    RETURN INTEGER IS
    v_ret_emp_count INTEGER;
    BEGIN 
      SELECT COUNT(*)
        INTO v_ret_emp_count
        FROM emp
        WHERE job = p_job;
        
      RETURN v_ret_emp_count;
    END countEmpsByJob;
    
    FUNCTION getSuitableDeptByJob(p_job emp.job%TYPE) 
    RETURN emp.deptno%TYPE IS
    v_suitable_deptno emp.deptno%TYPE;
    BEGIN
      IF countEmpsByJob(p_job) = 0 THEN
        -- find less populated dept
       SELECT deptno
       INTO v_suitable_deptno
        FROM    (SELECT * 
                FROM  (SELECT deptno, COUNT(ename) how
                      FROM (
                          SELECT d.deptno, e.ename
                          FROM dept d, emp e
                          WHERE d.deptno = e.deptno(+)
                          )
                        GROUP BY deptno
                      )
            ORDER BY how ASC)
        WHERE ROWNUM = 1;
      ELSE
        SELECT deptno
        INTO v_suitable_deptno
                FROM (SELECT COUNT(*) how, e.deptno --get depts count emp, and deptno, but have emp with jos
                        FROM  (SELECT * -- department filtered by job
                                FROM emp
                                WHERE job = p_job
                              ) deptsJob, emp e
                      WHERE e.deptno = deptsJob.deptno
                      GROUP BY e.deptno) res
                WHERE ROWNUM = 1
                ORDER BY how ASC;
      END IF;
      
      RETURN v_suitable_deptno;
    
    END getSuitableDeptByJob;
    
    PROCEDURE addEmp(p_ename IN EMP.ENAME%TYPE, p_sal IN EMP.SAL%TYPE, p_job IN EMP.JOB%TYPE, p_mgr IN EMP.EMPNO%TYPE) IS
    v_new_emp_no EMP.EMPNO%TYPE;
    v_new_emp_dep EMP.DEPTNO%TYPE;
    BEGIN
      IF checkEmpExistance(p_mgr) THEN
        -- mgr exists
        v_new_emp_no := getNewEmpNo;
        v_new_emp_dep := getSuitableDeptByJob(p_job);
        INSERT INTO emp VALUES(v_new_emp_no, p_ename, p_job, p_mgr, SYSDATE, p_sal, NULL ,v_new_emp_dep);
      ELSE
        -- raise exception
        RAISE_APPLICATION_ERROR (-20203,'Manager number not found');
        DBMS_OUTPUT.PUT_LINE('Exception mgr');
      END IF;
    END addEmp;
    
    --Exercitiu 2
  --@Brief Check if employee exist in emp table
  --@Returns boolean (True - exist, False - !exist)
  --@Overloaded checkEmpExistance function (called by empno NUMBER or ename CHAR)
  FUNCTION checkEmpExistance(p_ename emp.ename%TYPE) --private function
  RETURN BOOLEAN IS
  --local function variables
  v_ename_local emp.ename%TYPE := 'undefined';
  BEGIN
    --Query database for ename
    SELECT ename 
      INTO v_ename_local
      FROM emp
      WHERE UPPER(TRIM(ename)) = UPPER(TRIM(p_ename));
      
      RETURN TRUE;
      
    EXCEPTION -- check exceptions
      WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
  END;
  FUNCTION checkEmpExistance(p_empno emp.empno%TYPE) --private function
  RETURN BOOLEAN IS 
  --local function variables
  v_empno_local emp.empno%TYPE;
  BEGIN
     --Query database for ename
    SELECT empno 
      INTO v_empno_local
      FROM emp
      WHERE empno = p_empno;
      
      RETURN TRUE;
      
    EXCEPTION -- check exceptions
      WHEN NO_DATA_FOUND THEN
        RETURN FALSE;
  END;
  
  --@Brief get employee department number
  --@Return number 
  --@Overloaded getEmpDept (called by empno NUMBER or ename CHAR)
  FUNCTION getEmpDept(p_ename emp.ename%TYPE) -- private function
  RETURN emp.deptno%TYPE IS
  v_emp_deptno emp.deptno%TYPE;
  BEGIN 
      SELECT deptno INTO v_emp_deptno
        FROM emp
        WHERE ( UPPER(TRIM(ename)) = UPPER((TRIM(p_ename))) ) AND ROWNUM = 1;
        
      RETURN v_emp_deptno;     
  END getEmpDept;
  FUNCTION getEmpDept(p_empno emp.empno%TYPE) -- private function
  RETURN emp.deptno%TYPE IS
  v_emp_deptno emp.deptno%TYPE;
  BEGIN 
      SELECT deptno INTO v_emp_deptno
        FROM emp
        WHERE empno = p_empno;
        
      RETURN v_emp_deptno;     
  END getEmpDept;
  
  --@Brief remove employee from emp table if exists and if deptament remains empty remove department
  --@Overloaded dismissal function (called by empno)
  PROCEDURE dismissalEmp(p_empno IN emp.empno%TYPE) IS
  v_emp_deptno emp.deptno%TYPE;
  BEGIN
    IF checkEmpExistance(p_empno) THEN
       -- employee with p_empno exists get empdept number
      v_emp_deptno := getEmpDept(p_empno); 
      -- remove employee
      DELETE FROM emp WHERE empno = p_empno;
      COMMIT; --
      -- if employee was last in his department -> remove dept
      removeEmptyDept(v_emp_deptno);
    ELSE
      DBMS_OUTPUT.PUT_LINE('Dissmisal emp not found');
    END IF;
  END dismissalEmp;
  PROCEDURE dismissalEmp(p_ename emp.ename%TYPE) IS
  v_emp_deptno emp.deptno%TYPE;
  BEGIN
    IF checkEmpExistance(p_ename) THEN
      -- employee with p_ename exists get empdept number
      v_emp_deptno := getEmpDept(p_ename); 
      -- remove employee
      DELETE FROM emp WHERE UPPER(TRIM(ename)) = UPPER(TRIM(p_ename));
      COMMIT; --
      -- if employee was last in his department -> remove dept
      removeEmptyDept(v_emp_deptno);
    ELSE
      DBMS_OUTPUT.PUT_LINE('Dissmisal emp not found');
    END IF;
  END dismissalEmp;
  
  --@Brief remove deparment from dept table if it has no employees
  PROCEDURE removeEmptyDept(p_deptno IN dept.deptno%TYPE) IS
  BEGIN
    IF getDeptEmpCount(p_deptno) = 0 THEN
      DELETE FROM dept WHERE deptno = p_deptno;
    END IF;
  END removeEmptyDept;
  
  --@Brief get number of employee in a given deptno
  --@Return INTEGER 
  FUNCTION getDeptEmpCount(p_deptno dept.deptno%TYPE)
  RETURN INTEGER IS
  v_ret_emp_count INTEGER := 0;
  BEGIN
      SELECT COUNT(empno) INTO v_ret_emp_count
        FROM emp
        WHERE deptno = p_deptno;
        
      RETURN v_ret_emp_count;
  END getDeptEmpCount;
  
  --Exercitul 3
   --Implementation
  --Add new department
  PROCEDURE addDepartment(p_dName IN dept.dname%TYPE, p_dLoc IN dept.loc%TYPE) IS
  v_next_deptno dept.deptno%TYPE;
  BEGIN
    v_next_deptno := emp_pkg.getNewDeptNo;
    INSERT INTO dept (deptno, dname, loc) VALUES (v_next_deptno, p_dName, p_dLoc);
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
  
  PROCEDURE printEmpTable(p_where IN VARCHAR2) IS
  CURSOR c_emp IS
    SELECT * FROM emp;
    
  vFile UTL_FILE.FILE_TYPE; --fileHandler
  vExists BOOLEAN;
  vLength NUMBER;
  vBlockSize NUMBER;
  vfilename VARCHAR2(256) := 'emptable.txt';
  rec c_emp%ROWTYPE;
  
  BEGIN
    IF (TRIM(UPPER(p_where))) = TRIM(UPPER('ecran')) THEN
      BEGIN
        DBMS_OUTPUT.PUT_LINE('ecran');
        FOR rec IN c_emp LOOP
          BEGIN
            DBMS_OUTPUT.PUT_LINE(rec.empno || ' ' || rec.ename || ' ' || rec.job || ' ' || rec.mgr || ' ' || rec.hiredate || ' ' || rec.sal || '' || rec.comm || '' || rec.deptno);
          END;
        END LOOP;
      END;
    END IF;      
    
    IF (TRIM(UPPER(p_where))) = TRIM(UPPER('fisier')) THEN
      DBMS_OUTPUT.PUT_LINE('fisier');
      
        vFile := UTL_FILE.FOPEN('EMPDIR', 'vfilename', 'W');
        FOR rec IN c_emp LOOP
          BEGIN
            FETCH c_emp INTO rec;
            EXIT WHEN c_emp%NOTFOUND;
            
            UTL_FILE.PUT_LINE(vFile ,rec.empno || ' ' || rec.ename || ' ' || rec.job || ' ' || rec.mgr || ' ' || rec.hiredate || ' ' || rec.sal || '' || rec.comm || '' || rec.deptno);
          END;
        END LOOP;        
        
        UTL_FILE.FCLOSE(vFile);
      
    END IF;
    
    EXCEPTION
      WHEN utl_file.invalid_path THEN
        raise_application_error(-20000, 'ERROR: Invalid PATH FOR file.');
    
  END printEmpTable;
-- end package
END emp_pkg;