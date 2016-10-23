Statistici per pagina (ESA-Wikipedia, M3)
==================
Realizatori: Steiner Victoria, Vultur Ovidiu Alexandru, Blanaru Ramona-Elena, Ungurean Ioan
Grupa: B1;  Anul II

###1.	Descriere
>Acest modul se ocupa cu generarea statisticilor la cerere pentru orice pagina al wikipediei romanesti. Aceste statistici contin:
- numarul de cuvinte per pagina
- frecventa cuvintelor
- numarul de legaturi raportat la numarul de cuvinte
	
###2.	Domenii
>Se vor descrie scenariile de utilizare ale statisticilor referitoare la paginile wikipediei romanesti cat si modul lor de utilizare

###3.	Actionari/Interese
>M3: Punem la dispozitia altor module statistici referitoare la paginile wikipediei romanesti. 
Utilizatorul:  poate accesa statisticile generate pentru a le utiliza in folosul lui (identificare concept, etc).


###4. Actori & Obiective
>M3: satisfactia clientului
Utilizatorul: sa aiba acces la statistica oricarei pagini a wikipediei romanesti

###5. Scenarii de utilizare
>5.1 Se creeaza o statistica
>>5.1.1 Obiectiv/Context 
Modulul nostru pune la dispozitie un mecanism prin care poate fi obtinut orice statistica implementata la cerere.
5.1.2 Scenariu/Pasi
>>>1. Se specifica pagina caruia ii se doresc statisticile
>>>2. Se scaneaza si se genereaza statisticile intr-o structura de date
>>
>5.1.3 Extensii
In cazul in care pagina nu exista sau calea spre pagina contine un format eronat, atunci utilizatorul primeste un mesaj corespunzator.
>
>5.2. Utilizatorul doreste sa obtina o statistica
>>5.2.1. Obiectiv/Context 
Utilizatorul doreste o statistica a unei pagini wikipedia romaneasca.
5.2.2 Scenariu/Pasi
>>>1. Utilizatorul specifica pagina
2. Utilizatorul asteapta pana se creeaza statisticile pentru acea pagina.
3. Utilizatorului ii ramane posibilitatea de a alege o metoda prin care sa preia statistica. Fie doar ceea ce il intereseaza, fie toate la un loc.
>>
5.2.3 Extensii 
Nu este gasit nici un rezultat al cautarii utilizatorului : acesta primeste un mesaj corespunzator.


