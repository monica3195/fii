# Fisa cerintelor

###  1. Descriere

Modulul se ocupa de procesarea si lematizarea input-ului. In urma acestor 
procese, vom obtine siruri de cuvinte/grupuri de cuvinte definitorii conceptului
ce trebuie analizat (ex: verbe/substantive/etc.).

### 2. Domenii

Se vor descrie scenariile de utilizare ale modulului.

### 3. Actionari/Interese

Administratorul: Folosirea intregului program (si implicit a modulului nostru)
				in vederea crearii leagaturilor intre concepte.
Modulul nostru: Livreaza siruri de cuvinte/grupuri de cuvinte pentru celelalte module.
Celelalte module: Preiau cuvintele pentru a-si rezolva sarcinile.


### 4. Actori & Obiective

Administratorul: Pastrarea unei baze de date actualizata.
Modulul nostru: Transformarea documentului intr-un sir de cuvinte/grupuri de cuvinte 
				lematizate.
Celelalte module: Sa preia cuvintele de la noi si sa le foloseasca pentru
				  a identifica conceptele.
	
###  5. Scenarii de utilizare

#### 5.1 Procesarea textului

##### 5.1.1 Obiectiv/Context
Dorim sa scapam de 'stop-words'. (ex: articole/prepozitii/conjunctii/etc.)

##### 5.1.2 Scenariu/Pasi
1. Preluam paginile din dump-ul Wikipedia RO.
2. Procesam fiecare pagina in parte.
3. Eliminam din text, pana cand ramanem doar cu cuvinte/grupuri de cuvinte 
		ce definesc conceptul.
4. Obtinem cate un sir de cuvinte pentru fiecare pagina de Wikipedia.
##### 5.1.3 Extensii
- 1a. Paginile din dump pot avea un format eronat din diferite cauze. (bad-uri pe hard, a picat reteaua in momentul transferarii)
- 2a. Paginile mari pot fi procesate intr-un mod ineficient si se va recurge la procesarea pe capitole.	
- 3a. Dificultati in a reliza acest lucru intr-un mod eficient.
- 3b. Alegerea unui cuvant in loc de un grup de cuvinte (ex: alegem doar Paris cand intalnim Paris Hilton).
- 3c. Pastrarea unor cuvinte de legatura in grupurile de cuvinte (ex: Stefan cel Mare)
- 4a. Intalnirea aceluiasi cuvant sub diferite forme (ex: pastrarea articolelor: copac, copacul, copacel.)
		
#### 5.2 Lematizarea

##### 5.2.1 Obiectiv/Context
Prin acest proces, dorim sa scapam de extensia 4a de la 5.1.3

##### 5.2.2 Scenariu/Pasi
1. Preluam sirurile de cuvinte obtinute la primul scenariu.
2. Folosindu-ne de un serviciu web obtinem noi siruri, lematizate.
3. Obtinem un sir de cuvinte/grupuri de cuvinte definitorii conceptului.

##### 5.2.3 Extensii
- 1a. In cazul in care procesul de procesare a textului nu merge cum ar trebui 
		sau nu merge deloc, procesul de lematizare nu poate fi initiat.
- 2a. Dificultati in a gasi un serviciu web care sa ne satisfaca cerintele
		(mai ales ca se doreste suport pentru limba romana).
- 2b. Serviciul web poate fi incet, astfel se vor acumula date ce trebuiesc lematizate de la punctul 5.1.
