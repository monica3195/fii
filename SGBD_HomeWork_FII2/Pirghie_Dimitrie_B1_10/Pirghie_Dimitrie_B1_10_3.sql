create or replace FUNCTION checkPKTable RETURN BOOLEAN IS
  noOfTables INTEGER;
  noOfPKContraints INTEGER;
BEGIN

  SELECT COUNT(*) INTO noOfTables 
  FROM user_tables;
  
  SELECT COUNT(*) INTO noOfPKContraints
  FROM user_constraints
  WHERE CONSTRAINT_TYPE = 'P';
  
  RETURN (noOfPKContraints = noOfTables);
  
END;