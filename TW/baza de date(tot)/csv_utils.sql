CREATE OR REPLACE DIRECTORY CSV
AS
  'E:\Facultate\Workspace\ProiectTw\baza de date(tot)';
  /
CREATE OR REPLACE FUNCTION LOAD_CSV(
  p_table              IN VARCHAR2,
  P_FILENAME           IN VARCHAR2,
  p_ignore_headerlines IN INTEGER DEFAULT 0,
  p_dir                IN VARCHAR2 DEFAULT 'CSV' ,
  p_delimiter          IN VARCHAR2 DEFAULT ',',
  p_optional_enclosed  IN VARCHAR2 DEFAULT '"' )
RETURN NUMBER
IS
  /***************************************************************************
  -- P_FILENAME
  -- The name of the flat file(a text file)
  --
  -- P_DIRECTORY
  -- Name of the directory where the file is been placed.
  -- Note: The grant has to be given for the user to the directory
  -- before executing the function
  --
  -- P_IGNORE_HEADERLINES:
  -- '1' to ignore importing headers.
  --
  -- P_DELIMITER
  -- By default the delimiter is used as ','
  --
  -- P_OPTIONAL_ENCLOSED
  -- By default the optionally enclosed is used as '"'
  **************************************************************************/
  l_input utl_file.file_type;
  l_theCursor INTEGER DEFAULT dbms_sql.open_cursor;
  l_lastLine  VARCHAR2(4000);
  l_cnames    VARCHAR2(4000);
  l_bindvars  VARCHAR2(4000);
  l_status    INTEGER;
  l_cnt       NUMBER DEFAULT 0;
  l_rowCount  NUMBER DEFAULT 0;
  l_sep       CHAR(1) DEFAULT NULL;
  L_ERRMSG    VARCHAR2(4000);
  V_EOF       BOOLEAN := false;
BEGIN
  l_cnt := 1;
  FOR TAB_COLUMNS IN (SELECT column_name,data_type FROM user_tab_columns
                      WHERE table_name=p_table ORDER BY column_id)
  LOOP
      l_cnames   := l_cnames || tab_columns.column_name || ',';
      l_bindvars := l_bindvars ||
      CASE
      WHEN tab_columns.data_type IN ('DATE', 'TIMESTAMP(6)') THEN
        'to_date(:b' || l_cnt || ',''YYYY-MM-DD HH24:MI:SS''),'
      ELSE
        ':b'|| l_cnt || ','
      END;
      l_cnt := l_cnt + 1;
  END LOOP;
  l_cnames               := rtrim(l_cnames,',');
  L_BINDVARS             := RTRIM(L_BINDVARS,',');
  L_INPUT                := UTL_FILE.FOPEN( P_DIR, P_FILENAME, 'r' );
  IF p_ignore_headerlines > 0 THEN
    BEGIN
      FOR i IN 1 .. p_ignore_headerlines
      LOOP
        UTL_FILE.get_line(l_input, l_lastLine);
      END LOOP;
      EXCEPTION
      WHEN NO_DATA_FOUND THEN
      v_eof := TRUE;
    END;
  END IF;
  IF NOT v_eof THEN
      dbms_sql.parse( l_theCursor, 'insert into ' || p_table || '(' || l_cnames || ') values (' || l_bindvars || ')', dbms_sql.native );
      LOOP
          BEGIN
            utl_file.get_line( l_input, l_lastLine );
            EXCEPTION
            WHEN NO_DATA_FOUND THEN
            RETURN l_rowCount;
          END;
          IF LENGTH(l_lastLine) > 0 THEN
              FOR i IN 1 .. l_cnt-1
              LOOP
                dbms_sql.bind_variable( l_theCursor, ':b'||i, rtrim(rtrim(ltrim(ltrim( REGEXP_SUBSTR(l_lastline,'(^|,)("[^"]*"|[^",]*)',1,i),p_delimiter),p_optional_enclosed),p_delimiter),p_optional_enclosed));
              END LOOP;
              BEGIN
                l_status := dbms_sql.execute(l_theCursor);
                l_rowCount := l_rowCount + 1;
                EXCEPTION
                WHEN OTHERS THEN
                L_ERRMSG := SQLERRM;
                RAISE_APPLICATION_ERROR(-20001,'ERROR: '||SQLERRM||' ON ROW '||(l_rowCount+1));
                ROLLBACK;
                RETURN -1;
              END;
          END IF;
      END LOOP;
      dbms_sql.close_cursor(l_theCursor);
      utl_file.fclose( l_input );
      COMMIT;
  END IF;
  COMMIT;
  RETURN L_ROWCOUNT;
END LOAD_CSV;
/

CREATE OR REPLACE PROCEDURE dump_table_to_csv(
    p_tname    IN VARCHAR2,
    p_dir      IN VARCHAR2,
    p_filename IN VARCHAR2 )
IS
  l_output utl_file.file_type;
  l_theCursor   INTEGER DEFAULT dbms_sql.open_cursor;
  l_columnValue VARCHAR2(4000);
  l_status      INTEGER;
  l_query       VARCHAR2(1000) DEFAULT 'select * from ' || p_tname;
  l_colCnt      NUMBER := 0;
  l_separator   VARCHAR2(1);
  l_descTbl dbms_sql.desc_tab;
BEGIN
  l_output := utl_file.fopen( p_dir, p_filename, 'w' );
  EXECUTE immediate 'alter session set nls_date_format=''dd-mon-yyyy hh24:mi:ss''
';
  dbms_sql.parse( l_theCursor, l_query, dbms_sql.native );
  dbms_sql.describe_columns( l_theCursor, l_colCnt, l_descTbl );
  FOR i IN 1 .. l_colCnt
  LOOP
    utl_file.put( l_output, l_separator || '"' || l_descTbl(i).col_name || '"' );
    dbms_sql.define_column( l_theCursor, i, l_columnValue, 4000 );
    l_separator := ',';
  END LOOP;
  utl_file.new_line( l_output );
  l_status                                := dbms_sql.execute(l_theCursor);
  WHILE ( dbms_sql.fetch_rows(l_theCursor) > 0 )
  LOOP
    l_separator := '';
    FOR i IN 1 .. l_colCnt
    LOOP
      dbms_sql.column_value( l_theCursor, i, l_columnValue );
      utl_file.put( l_output, l_separator || l_columnValue );
      l_separator := ',';
    END LOOP;
    utl_file.new_line( l_output );
  END LOOP;
  dbms_sql.close_cursor(l_theCursor);
  utl_file.fclose( l_output );
  EXECUTE immediate 'alter session set nls_date_format=''dd-MON-yy'' ';
EXCEPTION
WHEN OTHERS THEN
  EXECUTE immediate 'alter session set nls_date_format=''dd-MON-yy'' ';
  raise;
END;
/

DECLARE
  ROWNB INTEGER;
BEGIN
  ROWNB:=LOAD_CSV('CATEGORIES','categories.csv',1);
--  ROWNB:=LOAD_CSV('ANSWERS' ,'fisier.csv');  
--  dump_table_to_csv('ANSWERS' ,'CSV','answers1.csv');
END;
/

--select * from answers;

