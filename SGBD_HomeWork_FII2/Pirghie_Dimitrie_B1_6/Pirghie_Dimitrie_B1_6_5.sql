DECLARE
BEGIN
  EMP_PKG.PRINTEMPTABLE('ecran');  
  --PROCEDURE addDepartment(p_dName IN dept.dname%TYPE, p_dLoc IN dept.loc%TYPE);  
  EMP_PKG.ADDDEPARTMENT('Cioflingari','Cucuteni');
  --PROCEDURE addEmp(p_ename IN EMP.ENAME%TYPE, p_sal IN EMP.SAL%TYPE, p_job IN EMP.JOB%TYPE, p_mgr IN EMP.EMPNO%TYPE);
  --EMP_PKG.ADDEMP('Gica',1900,'Cioflingar',112); --exception
  EMP_PKG.ADDEMP('Gica',1900,'Ciof',7654); 
  EMP_PKG.ADDEMP('Gica2',1930,'MANAGER',7782); 
  EMP_PKG.PRINTEMPTABLE('ecran');
  EMP_PKG.PRINTEMPTABLE('fisier');
  EMP_PKG.DISMISSALEMP('Gica');
  EMP_PKG.DISMISSALEMP('Gica2');
  EMP_PKG.PRINTEMPTABLE('ecran');
END;