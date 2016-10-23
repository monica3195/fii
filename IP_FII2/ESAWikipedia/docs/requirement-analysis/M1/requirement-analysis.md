# Fisa cerintelor

### 1. Descriere
Modulul se ocupa cu construirea vectorilor la nivel global (Vector space model) cu rolul de a reprezenta documente text (in cazul nostru pagini de Wikipedia, concepte) ca vectori de indentificatori folositi pentru extragerea informatiilor din documente (query, information filtering, information retrieval, indexing).

### 2. Domenii
Se vor descrie scenariile de utilizare ale motorului de cautare.

### 3. Actionari/Interese
__Utilizatorul__: Poate cauta concepte sau query-uri legate de un concept.
__Modulul__: Se ocupa cu cautarea si afisarea de rezultate relevante pe baza query-ului primit de la utilizator.

### 4. Actori & Obiective
__Utilizatorul__: Sa gasesca raspunsuri dorit la query-ul sau.
__Modulul__: Sa ofere rezultate cat mai relevante intr-o maniera atractiva la query-ul utilizatorului.

### 5. Scenarii de utilizare

#### 5.1. Utilizatorul cauta dupa un query

##### 5.1.1. Obiectiv/Context
Utilizatorul doreste sa caute informatii pe unui query.

##### 5.1.2. Scenariu/Pasi
1. Se deschide programul software si se introduce in casuta de cautare un query.
2. Se apasa OK si se asteapta un rezultat.
3. Folosind modulul __M4__ se vor identifica keyword-urile din query.
4. Folosind VSM se extrag rezultatele din baza de cunostinte.
5. Se afiseaza lista de rezultate relevante query-ului cautat.

##### 5.1.3 Extensii
- 5a. Dureaza prea mult cautarea, utilizatorul poate anula cautarea.
- 5b. Nu s-au gasit rezultate, se afiseaza un mesaj.
