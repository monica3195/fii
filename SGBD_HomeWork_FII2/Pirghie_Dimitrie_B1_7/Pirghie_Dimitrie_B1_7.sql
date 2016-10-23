set serveroutput on;
/

@http://profs.info.uaic.ro/~vcosmin/pagini/resurse_psgbd/Script/Script.sql
/

-- CREATE TYPES
CREATE OR REPLACE TYPE EMP_SAL_UPD
AS
  OBJECT
  (
    empno         NUMBER(4,0),
    raise_procent REAL ); -- type pair (empno, raise_sal_procent)
  /
CREATE OR REPLACE TYPE EMP_SAL_UPD_NL
AS
  TABLE OF EMP_SAL_UPD;
  /
CREATE OR REPLACE TYPE EMP_SAL_REC
AS
  OBJECT
  (
    newsal NUMBER(7,2),
    atdate DATE );
  /
  
CREATE OR REPLACE TYPE EMP_SALS_REC_NL
AS
  TABLE OF EMP_SAL_REC;
  /
 

ALTER TABLE emp ADD (sal_updates EMP_SALS_REC_NL) 
NESTED TABLE sal_updates STORE AS EMP_SALS_REC_NL_TABLE;
/  

CREATE OR REPLACE PACKAGE EMP_PKG
IS
  --Exercitiul 1
  PROCEDURE RAISE_EMPS_SAL(
      emps_to_raise EMP_SAL_UPD_NL);
  --Exercitiul 2
  PROCEDURE PRINT_SAL_UPDS;
END EMP_PKG;
/

CREATE OR REPLACE PACKAGE BODY EMP_PKG
IS
  --forward FUNCTIONS AND PROCEDUREs declarations
  PROCEDURE UPDATE_SAL_PROCENT(
      empnumber     IN EMP.EMPNO%TYPE,
      raise_procent IN REAL);
      
  PROCEDURE UPDATE_EMP_SAL_HISTORY(
      empnumber IN EMP.EMPNO%TYPE,
      new_sal   IN EMP.SAL%TYPE
      );
      
  FUNCTION checkEmpExistance(
          p_empno emp.empno%TYPE)
  RETURN BOOLEAN;
        
  FUNCTION GET_SAL_BY_EMPNO(
            empnumber IN EMP.EMPNO%TYPE)
  RETURN EMP.SAL%TYPE;
  
          ----
        ---
        --Package specification implementation
  PROCEDURE RAISE_EMPS_SAL(
          emps_to_raise EMP_SAL_UPD_NL)
  IS
  BEGIN
      FOR i IN emps_to_raise.FIRST .. emps_to_raise.LAST LOOP
        BEGIN
              IF checkEmpExistance(emps_to_raise(i).empno) THEN
                --DBMS_OUTPUT.PUT_LINE('Exista');
                UPDATE_SAL_PROCENT(emps_to_raise(i).empno, emps_to_raise(i).raise_procent);
              END IF;
              --Catch EMP_NOT_FOUND Exception
              --EXCEPTION
              -- WHEN -20000 THEN
              --  DBMS_OUTPUT.PUT_LINE('Employee not found, number ', emps_to_raise(i).empno);
            END;
          END LOOP;
        END RAISE_EMPS_SAL;
  
  PROCEDURE PRINT_SAL_UPDS IS
  CURSOR emps_sal_upds_cursor IS
      SELECT *
      FROM emp
      WHERE sal_updates IS NOT NULL;
  BEGIN
      FOR cur_emp IN emps_sal_upds_cursor LOOP
        DBMS_OUTPUT.PUT_LINE(cur_emp.ename);
        FOR i IN cur_emp.sal_updates.FIRST .. cur_emp.sal_updates.LAST LOOP
          DBMS_OUTPUT.PUT_LINE('    ' || cur_emp.sal_updates(i).newsal ||  ' ' || cur_emp.sal_updates(i).atdate);
        END LOOP;
      END LOOP;
      
  END PRINT_SAL_UPDS;
      
   PROCEDURE UPDATE_EMP_SAL_HISTORY(
      empnumber IN EMP.EMPNO%TYPE,
      new_sal   IN EMP.SAL%TYPE
  ) IS
  current_history EMP_SALS_REC_NL;
  new_item EMP_SAL_REC;
  nested_list_size INTEGER;
  BEGIN
    -- sure emp exists
    
    new_item := EMP_SAL_REC(new_sal, sysdate);
    SELECT sal_updates INTO current_history FROM emp WHERE empno = empnumber;   
    
    --initialize
      IF current_history IS NULL THEN
        current_history := EMP_SALS_REC_NL();
      END IF;
      current_history.extend;
      current_history(current_history.count) := new_item;
      
    UPDATE emp SET sal_updates=current_history WHERE empno = empnumber;
  END;
      
    --functions implementations
    FUNCTION checkEmpExistance(
        p_empno emp.empno%TYPE) --private function
      RETURN BOOLEAN
    IS
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
      RAISE_APPLICATION_ERROR(-20000, 'EMP_NOT_FOUND');
    END;
    
    
  FUNCTION GET_SAL_BY_EMPNO(
      empnumber IN EMP.EMPNO%TYPE)
    RETURN EMP.SAL%TYPE
  IS
    emp_sal EMP.SAL%TYPE;
  BEGIN
    SELECT sal INTO emp_sal FROM emp WHERE empno = empnumber;
    RETURN emp_sal;
  END GET_SAL_BY_EMPNO;
  
  PROCEDURE UPDATE_SAL_PROCENT(
              empnumber     IN EMP.EMPNO%TYPE,
              raise_procent IN REAL)
          IS
            v_new_sal EMP.EMPNO%TYPE;
            v_emp_cur_sal EMP.EMPNO%TYPE;
          BEGIN
            v_emp_cur_sal := GET_SAL_BY_EMPNO(empnumber);
            v_new_sal     := (raise_procent/100)*v_emp_cur_sal;
            v_new_sal     := v_new_sal + v_emp_cur_sal;
            -- Update emp.sal with new salary
            UPDATE emp
            SET sal     = v_new_sal
            WHERE empno = empnumber;
            --Update NL_History of salaries 
            UPDATE_EMP_SAL_HISTORY(empnumber, v_new_sal);
          END UPDATE_SAL_PROCENT;  
END EMP_PKG;
/

BEGIN
  EMP_PKG.RAISE_EMPS_SAL(EMP_SAL_UPD_NL(EMP_SAL_UPD(7654,50)));
  EMP_PKG.RAISE_EMPS_SAL(EMP_SAL_UPD_NL(EMP_SAL_UPD(7369,30)));
  EMP_PKG.RAISE_EMPS_SAL(EMP_SAL_UPD_NL(EMP_SAL_UPD(7654,40)));
  EMP_PKG.RAISE_EMPS_SAL(EMP_SAL_UPD_NL(EMP_SAL_UPD(7902,100)));
  EMP_PKG.PRINT_SAL_UPDS();
END;