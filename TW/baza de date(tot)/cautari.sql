---------------------------------------------------------------------------------pagina home de intrebari cu intrebari din categorii preferate, sau cu ultimele-----------------------------------------------------------
SELECT COUNT(*)
    INTO HAS_PREFERRED_CATEGORIES
    FROM PROFILES_CATEGORIES
    WHERE ID_PROFILE           =ID_PROFILE_IN;--verificam daca userul are trecute macar o categorie preferata  
    
   IF HAS_PREFERRED_CATEGORIES>0 THEN -----daca are

----selectam intrebari din categorii preferate
   SELECT   *
      FROM
        (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
        FROM QUESTIONS
        WHERE ID_CATEGORY IN
          (SELECT ID_CATEGORY FROM Profiles_categories WHERE ID_PROFILE=ID_PROFILE_IN
          )
        )
      WHERE RN BETWEEN ROWNUM_NEXT AND ROWNUM_NEXT+19;
      
    WHEN NO_DATA_FOUND THEN
      ARE_THERE_MORE_QUESTIONS:=0;
      
-------selectam categoria intrebarii
        SELECT NAME_CATEGORY
        INTO VAR_NAME_CATEGORY
        FROM CATEGORIES
        WHERE ID_CATEGORY=INDX.ID_CATEGORY;
----selectam username-ul de cine a fost postata intrebarea        
        SELECT USERNAME
        INTO VAR_USERNAME
        FROM PROFILES
        WHERE ID_PROFILE=INDX.ID_PROFILE;
---------selectam voturi-----------
        SELECT SUM(LIKES_DISLIKES) FROM QUESTIONS_VOTES WHERE ID_QUESTION=______;
        
    SELECT ID_QUESTION
    INTO ARE_THERE_MORE_QUESTIONS
    FROM
      (SELECT ID_QUESTION,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
      FROM QUESTIONS---verificam daca mai exista intrebari de afisat, ce apartin categoriilor preferate
      WHERE ID_CATEGORY IN
        (SELECT ID_CATEGORY FROM Profiles_categories WHERE ID_PROFILE=ID_PROFILE_IN
        )
      )
    WHERE RN =ROWNUM_NEXT+20;
  ARE_THERE_MORE_QUESTIONS:=1;
    WHEN NO_DATA_FOUND THEN
      ARE_THERE_MORE_QUESTIONS:=0;
        
-----------------------------------------------nu are trecute categorii preferate, luam din toate intrebari sortate dupa data postarii-----------------------   
    SELECT   *
      FROM
        (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
        FROM QUESTIONS
        )
      WHERE RN BETWEEN ROWNUM_NEXT AND ROWNUM_NEXT+19;

    WHEN NO_DATA_FOUND THEN
      ARE_THERE_MORE_QUESTIONS:=0;
      
-------selectam categoria intrebarii
        SELECT NAME_CATEGORY
        INTO VAR_NAME_CATEGORY
        FROM CATEGORIES
        WHERE ID_CATEGORY=INDX.ID_CATEGORY;
----selectam username-ul de cine a fost postata intrebarea        
        SELECT USERNAME
        INTO VAR_USERNAME
        FROM PROFILES
        WHERE ID_PROFILE=INDX.ID_PROFILE; 
---------selectam voturi-----------
        SELECT SUM(LIKES_DISLIKES) FROM QUESTIONS_VOTES WHERE ID_QUESTION=______;
    
    SELECT ID_QUESTION
    INTO ARE_THERE_MORE_QUESTIONS
      FROM
        (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
        FROM QUESTIONS
        )
    WHERE RN =ROWNUM_NEXT+20;
      
  ARE_THERE_MORE_QUESTIONS:=1;
    WHEN NO_DATA_FOUND THEN
      ARE_THERE_MORE_QUESTIONS:=0;
  
  
  
  
----------------------------------------------------------------------------------------------------------intrebari pentru categorie concreta------------------------------------------------------------------------------------------
SELECT   *
      FROM
       (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
        FROM QUESTIONS
        WHERE ID_CATEGORY=
          (SELECT ID_CATEGORY FROM CATEGORIES WHERE NAME_CATEGORY=VAR_NAME_CATEGORY
          )
        )
      WHERE RN BETWEEN ROWNUM_NEXT AND ROWNUM_NEXT+19;

    WHEN NO_DATA_FOUND THEN
      ARE_THERE_MORE_QUESTIONS:=0;

       SELECT USERNAME
        INTO VAR_USERNAME
        FROM PROFILES
        WHERE ID_PROFILE=INDX.ID_PROFILE;
---------selectam voturi-----------
        SELECT SUM(LIKES_DISLIKES) FROM QUESTIONS_VOTES WHERE ID_QUESTION=______;        

    SELECT ID_QUESTION
    INTO ARE_THERE_MORE_QUESTIONS
    FROM
        (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
      FROM QUESTIONS
      WHERE ID_CATEGORY=
        (SELECT ID_CATEGORY FROM CATEGORIES WHERE NAME_CATEGORY=VAR_NAME_CATEGORY
        )
      )
    WHERE RN=ROWNUM_NEXT+20;
    ARE_THERE_MORE_QUESTIONS:=1;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    ARE_THERE_MORE_QUESTIONS:=0;


 
------------------------------------------------------------------------------------------------------------intrebare dupa tag concret-------------------------------------------------------------------------------
  
-----FUNCTIA VA FI APELATA PE PAGINA DE INTREBARI CAUTATE DUPA UN SINGUR TAG
------DACA NU EXISA INTREBARI CE SATISFAC CRITERII, SE VA RETURNA NULL
------PARAMETRUL ROWNUM_NEXT ESTE NUMARUL DE ORDINE A URMATOAREI INTREBARI, A.I. SE VOR SELECTA CEL MULT 20 DE INTREBARI, MAXIMUL NECESAR AFISARII PE O SINGURA PAGINA
------PARAMETRUL ARE_THERE_MORE_QUESTIONS VA SERVI LA VERIFICAREA DACA MAI EXISTA INTREBARI, A.I. DACA ACESTEA NU MAI SUNT, NU SE VA MAI AFISA BUTONUL 'MORE QUESTIONS'
  
      SELECT   *
      FROM
      (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
        FROM QUESTIONS
        WHERE ID_QUESTION IN
          (SELECT ID_QUESTION
          FROM QUESTIONS_TAGS
          WHERE ID_TAG=
            (SELECT ID_TAG FROM TAGS WHERE TAG_NAME=TAG
            )
          )
        )
      WHERE RN BETWEEN ROWNUM_NEXT AND ROWNUM_NEXT+19
      
    WHEN NO_DATA_FOUND THEN
      ARE_THERE_MORE_QUESTIONS:=0;      
      
-------selectam categoria intrebarii
        SELECT NAME_CATEGORY
        INTO VAR_NAME_CATEGORY
        FROM CATEGORIES
        WHERE ID_CATEGORY=INDX.ID_CATEGORY;
----selectam username-ul de cine a fost postata intrebarea        
        SELECT USERNAME
        INTO VAR_USERNAME
        FROM PROFILES
        WHERE ID_PROFILE=INDX.ID_PROFILE;
---------selectam voturi-----------
        SELECT SUM(LIKES_DISLIKES) FROM QUESTIONS_VOTES WHERE ID_QUESTION=______;      

    SELECT ID_QUESTION
    INTO ARE_THERE_MORE_QUESTIONS
    FROM
        (SELECT ID_QUESTION,QUESTION_CONTENT,TIME_POSTED,ID_CATEGORY,ID_PROFILE,NMB_VIEWS,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
      FROM QUESTIONS
      WHERE ID_QUESTION IN
        (SELECT ID_QUESTION
        FROM QUESTIONS_TAGS
        WHERE ID_TAG=
          (SELECT ID_TAG FROM TAGS WHERE TAG_NAME=TAG
          )
        )
      )
    WHERE RN=ROWNUM_NEXT+20;
    ARE_THERE_MORE_QUESTIONS:=1;
    RETURN P_O_Q;
  EXCEPTION
  WHEN NO_DATA_FOUND THEN
    ARE_THERE_MORE_QUESTIONS:=0;
    
---------------------------------------------------------------------raspunsuri la o anumita intrebare------------------------------------------------------
    SELECT   *
      FROM
        (SELECT  ID_ANSWER,ID_PROFILE,ID_QUESTION,ANSW_CONT,TIME_POSTED,ROW_NUMBER() over (order by TIME_POSTED DESC) rn
        FROM ANSWERS WHERE ID_QUESTIONS=______
        )
      WHERE RN BETWEEN ROWNUM_NEXT AND ROWNUM_NEXT+19;

