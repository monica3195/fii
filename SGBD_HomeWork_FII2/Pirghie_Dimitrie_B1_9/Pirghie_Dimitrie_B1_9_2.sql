DECLARE
    v_currentJob VARCHAR2(20);
    v_stmt_create VARCHAR2(1024);
    v_stmt_drop   VARCHAR2(1024);
    v_stmt_insert VARCHAR2(1024);
    c_cursor INTEGER;
    v_numRowsAffected INTEGER := 0;
BEGIN
  FOR c_job IN (SELECT DISTINCT job FROM EMP) LOOP
    v_currentJob := c_job.job;
    DBMS_OUTPUT.PUT_LINE(v_currentJob);
    v_stmt_create := 'CREATE TABLE EMP_' || TRIM(v_currentJob) || '(empno NUMBER(4,0)' || ', ename VARCHAR2(20), mgr NUMBER(4,0), hiredate DATE, sal NUMBER(7,2), comm NUMBER(7,2), deptno NUMBER(2,0))';
    DBMS_OUTPUT.PUT_LINE(v_stmt_create);
    
    c_cursor := DBMS_SQL.OPEN_CURSOR;
    DBMS_SQL.PARSE(c_cursor, v_stmt_create, DBMS_SQL.NATIVE);
    v_numRowsAffected := DBMS_SQL.EXECUTE(c_cursor);
    DBMS_SQL.CLOSE_CURSOR(c_cursor);
    
    FOR current_emp IN (SELECT * FROM EMP WHERE JOB = v_currentJob) LOOP
      v_stmt_insert := 'INSERT INTO EMP_' || TRIM(v_currentJob) || '(empno, ename, mgr, hiredate, sal, comm, deptno) 
          VALUES (:new_empno, :new_ename, :new_mgr, :new_hiredate, :new_sal, :new_comm, :new_deptno)';
      DBMS_OUTPUT.PUT_LINE(v_stmt_insert);
      
      c_cursor := DBMS_SQL.OPEN_CURSOR;
      --check syntax
      DBMS_SQL.PARSE(c_cursor, v_stmt_insert, DBMS_SQL.NATIVE);
      --binds
      DBMS_SQL.BIND_VARIABLE(c_cursor, ':new_empno', current_emp.empno);
      DBMS_SQL.BIND_VARIABLE(c_cursor, ':new_ename', current_emp.ename);
      DBMS_SQL.BIND_VARIABLE(c_cursor, ':new_mgr', current_emp.mgr);
      DBMS_SQL.BIND_VARIABLE(c_cursor, ':new_hiredate', current_emp.hiredate);
      DBMS_SQL.BIND_VARIABLE(c_cursor, ':new_sal', current_emp.sal);
      DBMS_SQL.BIND_VARIABLE(c_cursor, ':new_comm', current_emp.comm);
      DBMS_SQL.BIND_VARIABLE(c_cursor, ':new_deptno', current_emp.deptno);
      
      v_numRowsAffected := DBMS_SQL.EXECUTE(c_cursor);  
      DBMS_SQL.CLOSE_CURSOR(c_cursor);
      
    END LOOP;
  --  EXIT;
  END LOOP;
  -- DROP EMP_SALESMAN TABLE
  
  c_cursor := DBMS_SQL.OPEN_CURSOR;
  v_stmt_drop := 'DROP TABLE EMP_SALESMAN';
  
  DBMS_SQL.PARSE(c_cursor, v_stmt_drop, DBMS_SQL.NATIVE);
  v_numRowsAffected := DBMS_SQL.EXECUTE(c_cursor);
  DBMS_SQL.CLOSE_CURSOR(c_cursor);
END;

/*
DROP TABLE EMP_ANALYST;
DROP TABLE EMP_CLERK;
DROP TABLE EMP_MANAGER;
DROP TABLE EMP_PRESIDENT;
DROP TABLE EMP_SALESMAN;
*/