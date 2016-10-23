DECLARE
  v_boss_name EMP.ENAME%TYPE;
  c_cursor  INTEGER;
  c_cursor2 INTEGER;
  v_stmt VARCHAR2(1024);
  v_stmt_insert VARCHAR2(1024);
  v_drop_stmt VARCHAR2(1024);
  v_numRowsAffected INTEGER := 0;
  v_empno EMP.EMPNO%TYPE;
  v_tel VARCHAR2(20) := '07410000';
  v_tel_complete VARCHAR2(20);
  v_counter INTEGER := 1;
  
  CURSOR emps_cur
  IS
    SELECT * 
    FROM EMP
    ORDER BY SAL DESC;
    
  v_emp_row EMP%ROWTYPE;
BEGIN
  --Get most valuable emp
  SELECT ename
  INTO v_boss_name
  FROM
      (SELECT *
      FROM emp
      WHERE comm  IS NOT NULL
      ORDER BY COMM DESC)
      WHERE ROWNUM = 1;
      
  --statement for creating new_table
  v_stmt := 'CREATE TABLE CONTRACT_ORANGE_' || TRIM(v_boss_name) || '(empno NUMBER(4,0)' || ', tel VARCHAR2(20))';
  v_drop_stmt := 'DROP TABLE CONTRACT_ORANGE_' || TRIM(v_boss_name);

  DBMS_OUTPUT.PUT_LINE('STMT : ' || v_stmt);

  c_cursor := DBMS_SQL.OPEN_CURSOR;
  DBMS_SQL.PARSE(c_cursor, v_stmt, DBMS_SQL.NATIVE);
  v_numRowsAffected := DBMS_SQL.EXECUTE(c_cursor);
  
  EXCEPTION 
    WHEN OTHERS THEN
      IF SQLCODE != -955 THEN
        RAISE;
      ELSE
        DBMS_OUTPUT.PUT_LINE('Table exists');
      END IF;
  DBMS_SQL.CLOSE_CURSOR(c_cursor);
  
  --loop emps
  OPEN emps_cur;
  LOOP 
    FETCH emps_cur INTO v_emp_row;
    EXIT WHEN emps_cur%NOTFOUND;
    
    IF v_counter < 10 THEN
      DBMS_OUTPUT.PUT_LINE(v_tel || '0' || v_counter);
      v_tel_complete := v_tel || '0' || v_counter;
    ELSE
      DBMS_OUTPUT.PUT_LINE(v_tel || v_counter);
      v_tel_complete := v_tel || v_counter;
    END IF;
    v_counter := v_counter + 1;
    
    c_cursor2 := DBMS_SQL.OPEN_CURSOR;
    v_stmt_insert := 'INSERT INTO CONTRACT_ORANGE_' || TRIM(v_boss_name) || '(empno, tel) VALUES (:new_empno, :new_tel)';
    DBMS_SQL.PARSE(c_cursor2, v_stmt_insert, DBMS_SQL.NATIVE);
    
    --bind variables
    DBMS_SQL.BIND_VARIABLE(c_cursor2, ':new_empno', v_emp_row.empno);
    DBMS_SQL.BIND_VARIABLE(c_cursor2, ':new_tel', v_tel_complete);
    
    v_numRowsAffected := DBMS_SQL.EXECUTE(c_cursor2);  
    DBMS_SQL.CLOSE_CURSOR(c_cursor2);
  END LOOP;
END;