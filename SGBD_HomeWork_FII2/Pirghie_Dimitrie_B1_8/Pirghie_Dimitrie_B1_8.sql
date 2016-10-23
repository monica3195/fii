-- Autor : Dimitrie Pirghie, Grupa B1, Mai 2015
-- Tema SGBD - NR. 8
-- delete all below types before running this script
-- output stdout to console
set serveroutput on;
COMMIT;

-- restore default tables
@http://www.info.uaic.ro/~vcosmin/pagini/resurse_psgbd/Script/Script.sql;
COMMIT;

-- create overwrite object type department
CREATE OR REPLACE TYPE Department AS OBJECT (
  -- types from dept table
  deptno NUMBER(3, 0),
  dname VARCHAR2(16),
  
  -- Constructor function for this object
  CONSTRUCTOR FUNCTION Department(nameDepartment VARCHAR2, numberDepartment NUMBER)
  RETURN SELF AS RESULT
);
COMMIT;

-- Implementation of department object
CREATE OR REPLACE TYPE BODY Department 
AS CONSTRUCTOR FUNCTION Department(nameDepartment VARCHAR2, numberDepartment NUMBER)
RETURN SELF AS RESULT
AS
--implement constructor function
  BEGIN
    SELF.deptno := numberDepartment;
    SELF.dname  := nameDepartment;
  END;
END;
/ --commit to save changes

-- create or overwrite object type employee
CREATE OR REPLACE TYPE Employee 
AS OBJECT
(
  -- types from emp table
  empno NUMBER(4,0),
  ename VARCHAR2(10),
  sal   NUMBER(7,2),
  dept  REF Department,--NUMBER(3,0)
  ORDER MEMBER FUNCTION match (e Employee) RETURN INTEGER
) NOT FINAL; --derivation allowed
/ --commit to save changes

-- create or overwrite object type derivated from employee, manager
CREATE OR REPLACE TYPE Manager UNDER Employee
(
  nrEmp NUMBER(4,0), -- number of emps who have manages this employee
  CONSTRUCTOR FUNCTION Manager(noOfEmployes NUMBER)
  RETURN SELF AS RESULT,
  
  -- comparable function
  MEMBER FUNCTION match (given Manager) RETURN INTEGER
  
);
/ --commit to save changes

--Implementation of manager type
CREATE OR REPLACE TYPE BODY Manager
AS
-- implementation of constructor
CONSTRUCTOR FUNCTION Manager(noOfEmployes NUMBER)
RETURN SELF AS RESULT
AS
  BEGIN
    SELF.nrEmp := noOfEmployes;
    RETURN;
  END;
  
  MEMBER FUNCTION match(given Manager) RETURN INTEGER IS
  BEGIN 
    IF nrEmp < given.nrEmp THEN
      RETURN -1;
    ELSIF nrEmp > given.nrEmp THEn
      RETURN 1;
    ELSE
      RETURN 0;
    END IF;
  END;
END; -- end for body construction  
/ --commit to save changes

-- Create nested table for departments
DROP TABLE DepartsLIST;
CREATE TABLE DepartsLIST OF Department;

-- Create nested table for managers
CREATE OR REPLACE TYPE ManagersNL AS TABLE OF Manager;
/ --commit to save changes

CREATE OR REPLACE PROCEDURE LoadManagerNL 
(v_mansNL IN OUT ManagersNL)
IS
  --procedure variables
  v_dept_ref REF Department;
  -- dinamic cursor
  CURSOR c_selectDeptRefs (par_departmentNumber NUMBER) IS
  SELECT REF(Depart)
  FROM DepartsLIST Depart
  WHERE Depart.deptno = par_departmentNumber;
  
  objMan Manager;
  CURSOR EmpsMans IS
  SELECT DISTINCT Man.empno, Man.ename, Man.deptno, D.dname, Man.sal, COUNT(Man.empno) AS NoOFEmps
  FROM emp e, emp Man, dept D
  WHERE e.mgr = Man.empno AND Man.deptno = d.deptno
  GROUP BY Man.empno, Man.ename, Man.deptno, D.dname, Man.sal;

BEGIN
  v_mansNL := ManagersNL();
  
  FOR currentrec IN EmpsMans LOOP

    INSERT INTO DepartsLIST VALUES(Department(currentrec.deptno, currentrec.dname));

    OPEN c_selectDeptRefs(currentrec.deptno);
    FETCH c_selectDeptRefs INTO v_dept_ref;
    CLOSE c_selectDeptRefs;

    --create new manager object
    objMan := Manager(0);
    objMan.dept := v_dept_ref;
    objMan.empno := currentrec.empno;
    objMan.ename := currentrec.ename;
    objMan.sal := currentrec.sal;
    objMan.nrEmp := currentrec.NoOFEmps;    
  
    v_mansNL.EXTEND();
    v_mansNL(v_mansNL.COUNT) := objMan;
  END LOOP;
END;
/

CREATE OR REPLACE PROCEDURE sortManagers
  (p_mans_list IN OUT ManagersNL)
IS 
  --local variables
  indexI NUMBER;
  indexJ NUMBER;
  totalMans NUMBER;
  auxMan Manager;
BEGIN
  totalMans := p_mans_list.COUNT;
  
  -- bubble sort
  FOR indexI IN 1..(totalMans -1 ) LOOP
    FOR indexJ IN (indexI+1)..totalMans LOOP
      --swap
      IF p_mans_list(indexI) < (p_mans_list(indexJ)) THEN
        auxMan := p_mans_list(indexI);
        p_mans_list(indexI) := p_mans_list(indexJ);
        p_mans_list(indexJ) := auxMan;
      END IF;
    END LOOP;
  END LOOP;
END;

DECLARE 
  --local block vars
  curDept REF Department;
  curDeptNo NUMBER;
  curDName VARCHAR2(20);
  i NUMBER;
  totalMans NUMBER;
  curMan Manager;
  mansList ManagersNL;
  
BEGIN
    DBMS_OUTPUT.PUT_LINE('Loading managers');
    LoadManagerNL(mansList);
    DBMS_OUTPUT.PUT_LINE('Sorting managers');
    sortManagers(mansList);
    totalMans := mansList.COUNT;
    DBMS_OUTPUT.PUT_LINE('Echo mans');
    
    FOR i IN 1..totalMans LOOP
      DBMS_OUTPUT.PUT_LINE('MAN ENAME : ' || mansList(i).ename);
      DBMS_OUTPUT.PUT_LINE('MAN NREMP : ' || mansList(i).nrEMP);
      
      SELECT DNL.deptno, DNL.dname
      INTO curDeptNo, curDName
      FROM DepartsLIST DNL
      WHERE REF(DNL) = mansList(i).dept;
      
      DBMS_OUTPUT.PUT_LINE('MAN DNAME : ' || curDName);
    END LOOP;
END;

