**Identificarea Conceptelor(ESA-wikipedia, M2)**

**1.Descriere**

Modulul nostru se ocupa cu identificarea conceptelor pentru orice pagina a Wikipediei romanesti. Noi preluam textul din fiecare pagina si il parsam pentru a obtine structurat fiecare concept prezent in text. 

**2.Domenii**

Se vor descrie scenariile de utilizare ale conceptelor referitoare la paginile wikipediei romanesti cat si modul lor de utilizare ulterior.

**3.Actionari/Interese**

**M2**: punem la dispozitia altor module lista conceptelor din fiecare pagina.
**Utilizatorul**: va interactiona indirect cu rezultatele furnizate de modul, acestea fiind utilizate in reprezentarea grafica realizata de celelalte module.
**Dump**: preluam datele din dump si le procesam pentru a obtine lista conceptelor pentru fiecare pagina.

**4. Actori & Obiective**

**M2**: satisfactia clientului,prin oferirea rezultatelor 
**Utilizatorul**: sa aiba acces la conceptele unei anumite pagini

**5. Scenarii de utilizare**

**5.1  Identificare concepte pentru o pagina**

**5.1.1 Obiectiv/Context** 

Modulul nostru pune la dispozitie un mecanism prin care pot fi obtinute toate conceptele dintr-o pagina.

**5.1.2 Scenariu/Pasi**
 
 1. Utilizatorul specifica linkul catre o pagina
 2. Se apasa OK si se asteapta un rezultat
 3. Folosind modulul **M2** se va parsa textul
 4. Se vor cauta sectiuni din textul parsat in DUMP  si se vor identifica conceptele
 5. Se adauga intr-un vector conceptele
       		

**5.1.3 Extensii**

In cazul in care pagina nu exista sau calea spre pagina contine un format eronat sau  in cazul in care nu exista nici un concept se va afisa un mesaj corespunzator.

