-- Exercitiul 1 3pt
-- Functie generare int interval

CREATE TABLE cartografie
(
    x INTEGER,
    y INTEGER,
    name VARCHAR2(10)
);

create or replace FUNCTION getRandomInteger
(
  p_inf_limit INTEGER,
  p_sup_limit INTEGER
)
RETURN INTEGER IS
v_random_x INTEGER;
BEGIN
  v_random_x := ROUND(DBMS_RANDOM.VALUE(p_inf_limit, p_sup_limit));
  RETURN v_random_x;
END;

-- Procedure generare padure
create or replace PROCEDURE create_forest
    --Parameters
        (p_number_trees IN INTEGER,
          p_number_arin OUT INTEGER,
          p_number_artar OUT INTEGER,
          p_number_fag OUT INTEGER,
          p_number_mesteacan OUT INTEGER,
          p_number_stejar OUT INTEGER) IS
          
          v_random_x CARTOGRAFIE.X%TYPE;
          v_random_y CARTOGRAFIE.Y%TYPE;
          v_random_tree_type INTEGER;
          v_tree_name CARTOGRAFIE.NAME%TYPE;
BEGIN
    --Initialize return values
    p_number_arin       := 0;
    p_number_artar      := 0;
    p_number_fag       := 0;
    p_number_mesteacan  := 0;
    p_number_stejar     := 0;

    FOR i IN 1..p_number_trees LOOP
        -- Choose tree type
        v_random_tree_type := getRandomInteger(0,4);
        
        IF v_random_tree_type = 0 THEN 
          p_number_arin := p_number_arin + 1;
          v_tree_name := 'Arin';
        END IF;
        
        IF v_random_tree_type = 1 THEN
          p_number_artar := p_number_artar + 1;
          v_tree_name := 'Artar';
        END IF;
        
        IF v_random_tree_type = 2 THEN
          p_number_fag := p_number_fag + 1;
          v_tree_name := 'Fag';
        END IF;
        
        IF v_random_tree_type = 3 THEN
          p_number_mesteacan := p_number_mesteacan + 1;
          v_tree_name := 'Mesteacan';
        END IF;
        
        IF v_random_tree_type = 4 THEN
          p_number_stejar := p_number_stejar + 1;
          v_tree_name := 'Stejar';
        END IF;
        
        -- get coordinates
        v_random_x := getRandomInteger(0,1000);
        v_random_y := getRandomInteger(0,1000);
        
        --insert tree
        --DBMS_OUTPUT.PUT_LINE('Insert ' || v_tree_name || ' at ' || v_random_x || ' , ' || v_random_y);
        INSERT INTO cartografie VALUES (v_random_x, v_random_y, v_tree_name);
        
    END LOOP;
    --DBMS_OUTPUT.PUT_LINE('Number of arin ' || p_number_arin);
END create_forest;


-- Test ex 1
DECLARE
  v_number_tree INTEGER := 1017325;
  v_number_arin INTEGER;
  v_number_artar INTEGER;
  v_number_fag INTEGER;
  V_number_mesteacan INTEGER;
  v_number_stejar INTEGER;
BEGIN
  DBMS_OUTPUT.PUT_LINE('Populate forest with : '|| v_number_tree || ' trees');
  create_forest(v_number_tree, v_number_arin, v_number_artar, v_number_fag, v_number_mesteacan, v_number_stejar);
  DBMS_OUTPUT.PUT_LINE('Arin number ' || v_number_arin);
  DBMS_OUTPUT.PUT_LINE('Artar number ' || v_number_artar);
  DBMS_OUTPUT.PUT_LINE('Fag number ' || v_number_fag);
  DBMS_OUTPUT.PUT_LINE('Mesteacan number ' || v_number_mesteacan);
  DBMS_OUTPUT.PUT_LINE('Stejar number ' || v_number_stejar);
END;


-- Ex 2
CREATE OR REPLACE FUNCTION noOfCutTrees
(
    x_Coordinate CARTOGRAFIE.X%TYPE,
    y_Coordinate CARTOGRAFIE.Y%TYPE
) RETURN NUMBER IS
noOfNeededTrees INTEGER;
BEGIN
  noOfNeededTrees := 0;
  
  -- FIND Number of needed trees
  SELECT COUNT(x)
  INTO noOfNeededTrees
  FROM cartografie carto
  WHERE (carto.x >= x_Coordinate - 25 AND carto.x <= x_Coordinate + 25) AND 
        (carto.y >= y_Coordinate - 25 AND carto.y <= y_Coordinate + 25) AND 
        ((carto.x - x_Coordinate)*(carto.x - x_Coordinate)) + ((carto.y - y_Coordinate)*(carto.y - y_Coordinate)) <= (25*25);
  
  RETURN noOfNeededTrees;
END noOfCutTrees;

-- Test ex 2
DECLARE
BEGIN
  DBMS_OUTPUT.PUT_LINE('Cut trees on 55, 70 ' || noOfCutTrees(50,70));
END;

-- Ex 3
CREATE TABLE locatii_candidat
(
  x INTEGER,
  y INTEGER
);

CREATE OR REPLACE PROCEDURE populate_locatii IS
  v_random_x locatii_candidat.x%TYPE;
  v_random_y locatii_candidat.y%TYPE;
BEGIN
    FOR i IN 1..100 LOOP
      v_random_x := getRandomInteger(0,1000);
      v_random_y := getRandomInteger(0,1000);
      INSERT INTO locatii_candidat VALUES (v_random_x, v_random_y);
    END LOOP;
END;

-- Test Ex 3
DECLARE
BEGIN 
    populate_locatii();
END;

-- Index ex 4
CREATE INDEX optimus_index ON cartografie(x,y);
DROP INDEX optimus_index;

SELECT MIN(noOfCutTrees(x,y)) from locatii_candidat;


-- All, Delete
SELECT * FROM cartografie;
DELETE FROM cartografie WHERE 1=1;
