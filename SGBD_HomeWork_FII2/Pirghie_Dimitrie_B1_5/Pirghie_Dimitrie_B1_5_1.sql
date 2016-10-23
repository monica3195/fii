--Pirghie Dimitrie B1 - Tema 5 - Exercitiu 1

DROP TABLE SRC;
CREATE TABLE SRC(
  empno NUMBER(4,0) NOT NULL,
  ename VARCHAR2(10 BYTE),
  job VARCHAR2(9 BYTE),
  mgr NUMBER(4, 0),
  copied NUMBER(1,0)
);

INSERT INTO SRC VALUES(9001,'NIKON','ANALYST',7902,null);
INSERT INTO SRC VALUES(9002,'FORD','ANALYST',7902,null);
INSERT INTO SRC VALUES(9003,'CANON','ANALYST',7902,null);
INSERT INTO SRC VALUES(9004,'CANON','ANALYST',7902,null);

DROP INDEX unique_index;
CREATE UNIQUE INDEX unique_index ON emp(ename);

DECLARE
--
CURSOR cursor_emps IS
  SELECT * FROM SRC;
BEGIN
  -- Try to insert from SRC to EMP
  FOR cur_emp IN cursor_emps LOOP
    -- For each row 
    BEGIN
      -- Try to insert trows DUP_VAL_ON_INDEX
      INSERT INTO emp (EMPNO, ENAME, JOB, MGR) VALUES(cur_emp.empno, cur_emp.ename, cur_emp.job, cur_emp.mgr);
      -- Update 1
      UPDATE SRC SET COPIED = 1 WHERE EMPNO = cur_emp.empno;
      
      EXCEPTION
        WHEN DUP_VAL_ON_INDEX THEN
          DBMS_OUTPUT.PUT_LINE('Duplicate on UNIQUE_INDEX');
          -- Update 0
          UPDATE SRC SET COPIED = 0  WHERE EMPNO = cur_emp.empno;
    END;
  END LOOP;
  
  -- Print successful copied
  DBMS_OUTPUT.PUT_LINE('Angajati copiati cu succes :');
  FOR cur_emp IN  cursor_emps LOOP
    IF cur_emp.copied = 1 THEN
      DBMS_OUTPUT.PUT_LINE(cur_emp.ename);
    END IF;
  END LOOP;
  
  DBMS_OUTPUT.PUT_LINE('Angajati care nu au putut fi copiati :');
  FOR cur_emp IN  cursor_emps LOOP
    IF cur_emp.copied = 0 THEN
      DBMS_OUTPUT.PUT_LINE(cur_emp.ename);
    END IF;
  END LOOP;
END;