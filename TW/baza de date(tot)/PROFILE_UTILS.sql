CREATE OR REPLACE PACKAGE PROFILE_UTILS
IS
  PROCEDURE EDIT_PASSWD(
      PASSWD_IN  IN VARCHAR2,
      PROFILE_ID IN INTEGER);
  PROCEDURE EDIT_USERNAME(
      USERNAME_IN IN VARCHAR2,
      PROFILE_ID  IN INTEGER);
  PROCEDURE EDIT_FIRST_NAME(
      FIRST_NAME_IN IN VARCHAR2,
      PROFILE_ID    IN INTEGER);
  PROCEDURE EDIT_LAST_NAME(
      LAST_NAME_IN IN VARCHAR2,
      PROFILE_ID   IN INTEGER);
  PROCEDURE EDIT_AGE(
      AGE_IN     IN INTEGER,
      PROFILE_ID IN INTEGER);
  PROCEDURE EDIT_EMAIL(
      EMAIL_IN   IN VARCHAR2,
      PROFILE_ID IN INTEGER);
  PROCEDURE EDIT_COUNTRY(
      COUNTRY_IN IN VARCHAR2,
      PROFILE_ID IN INTEGER);
  PROCEDURE EDIT_CITY(
      CITY_IN    IN VARCHAR2,
      PROFILE_ID IN INTEGER);
END PROFILE_UTILS;
/
CREATE OR REPLACE PACKAGE BODY PROFILE_UTILS
IS
  PROCEDURE EDIT_PASSWD(
      PASSWD_IN  IN VARCHAR2,
      PROFILE_ID IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF PASSWD;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES SET PASSWD=PASSWD_IN WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
  PROCEDURE EDIT_USERNAME(
      USERNAME_IN IN VARCHAR2,
      PROFILE_ID  IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF USERNAME;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES SET USERNAME=USERNAME_IN WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
  PROCEDURE EDIT_FIRST_NAME(
      FIRST_NAME_IN IN VARCHAR2,
      PROFILE_ID    IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF FIRST_NAME;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES SET FIRST_NAME=FIRST_NAME_IN WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
  PROCEDURE EDIT_LAST_NAME(
      LAST_NAME_IN IN VARCHAR2,
      PROFILE_ID   IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF LAST_NAME;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES SET LAST_NAME=LAST_NAME_IN WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
  PROCEDURE EDIT_AGE(
      AGE_IN     IN INTEGER,
      PROFILE_ID IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF AGE;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES SET AGE=AGE_IN WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
  PROCEDURE EDIT_EMAIL(
      EMAIL_IN   IN VARCHAR2,
      PROFILE_ID IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF EMAIL;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES SET EMAIL=EMAIL_IN WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
  PROCEDURE EDIT_COUNTRY(
      COUNTRY_IN IN VARCHAR2,
      PROFILE_ID IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF COUNTRY;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES SET COUNTRY=COUNTRY_IN WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
  PROCEDURE EDIT_CITY(
      CITY_IN    IN VARCHAR2,
      PROFILE_ID IN INTEGER)
  IS
    CURSOR MYCURSOR
    IS
      SELECT * FROM PROFILES WHERE ID_PROFILE=PROFILE_ID FOR UPDATE OF CITY;
      COUNTER INTEGER:=1;
  BEGIN
    FOR INDX IN MYCURSOR
    LOOP
      UPDATE PROFILES
      SET CITY=CITY_IN
      WHERE CURRENT OF MYCURSOR;
    END LOOP;
    EXCEPTION
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20001,'an error occured');
  END;
END PROFILE_UTILS;
/