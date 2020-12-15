# ALGORITMI PARALELI SI DISTRIBUITI - Ionita Radu-Alexandru 332CA
## Tema #2 Traffic simulator

- In cadrul acestei teme, am avut de implementat modele de sincronizare
a unor situatii din trafic, in limbajul de programare JAVA.
- Cerintele sunt rezolvate in pachetul intersections, sub forma unor clase
care au numele cerintei respective, spre exemplu "task1". In cazul in care
am putut rezolva mai multe cerinte intr-o singura clasa, am numit-o "task345".
- Am incercat o implementare pe cat mai mult posibila de tip POO, folosind
setteri si getteri.

### Task1(simple_semaphore)

-  Am folosit comanda Thread.sleep(), pentru ca fiecare thread "car" sa astepte
timpul necesar la semafor. 
- Cu ajutorul lui car.getWaitingTime(), am procesat acest timp, dandu-l ca parametru lui Thread.sleep(),
astfel fiecare masina plecand cand trebuie de la semafor.

### Task2(simple_n_roundabout)

- Masiniile ajung in orice ordine si oricate, dar in sensul giratoriu intra maxim cars masini.
- Am folosit un semafor, initializat cu numarul de masini permise sa intre in sensul giratoriu, citit
din fisier in ReaderHandlerFactory. Semaforul este folosit pentru a permite doar unui numar de masini
sa intre, ca atunci cand o masina vrea sa intre in sensul giratoriu iar contorul de permits este 0, sa 
nu mai intre nicio masina. Dupa ce o masina iese, se da release la semafor, incrementand contorul si
permitand si altei masini sa intre.
- Folosesc si Thread.sleep() pentru ca in cerinta se zice ca dureaza roundaboutTime secunde sa intre
in sensul giratoriu. 

### Task345(simple_strict_1_car_roundabout/simple_strict_x_car_roundabout/simple_max_x_car_roundabout)

- Cerintele au fost asemanatoare, una constand generalizarea celeilalte, asa ca le-am putut impreuna.
- Vectorul de semafoare este folosit sa se tina cont pentru fiecare lane cate masini au voie, astfel
putand intra maxim maxNoOfCars masini
- In cazul task-ului 3, bariera are rolul de a permite thread-urilor sa intre de cate ori este necesar
- La exercitiul 4 am 4 bariere, 3 bariere dupa afisare, sa ma asigur ca se afiseaza cum trebuie,
si o bariera la inceput cu numarul de masini total, sa vina toate masiniile
- La task-ul 5, nu a fost necesara o bariera, deoarece nu mai conteaza daca mai ajung masini
la girator in timp ce altele sunt intrate sau ies din el
- Voi da si sleep pentru ca atat dureaza ca masina sa iasa din sesnsul giratoriu
- La final, urmand sa dau release de la fiecare semafor, pentru ca numarul de permisiuni sa se incrementeze

### Task6(priority_intersection)

- La acest exercitiu am folosit o coada si un semafor, astfel incat
 - Coada o folosesc pentru a stii in ce ordine au intrat masiniile cu prioritatea mica, adaugand 
 atunci cand prioritatea este 1 in coada masiniile respective, dupa care eliminandu-le din coada
 dupa ce afisez mesajul
 - Semaforul initializat cu numarul de masini cu prioritate mare, pentru ca atunci cand 
 numarul de masini cu prioritate mare nu este egal cu numarul de permisiuni de la semafor,
 inseamna ca este o masina cu prioritate mare in intersectie.
- Pentru a ocoli un busy-waiting, am folosit constructia wait() si notifyAll(), astfel atunci
cand ordinea masiniilor cu prioritate mica nu se respecta -> (carQueue.peek() != car.getId())
si conditia explicata anterior, sa ramana in wait(), asteptand ca thread-ul corespunzator
sa dea notifyAll si posibil urmatorul thread sa poata intra.

### Task7(crosswalk)

- Vectorul canPass este un vector boolean care atunci cand este true, inseamna ca masiniile au
culoarea verde la semafor, deci pot sa treaca, iar atunci cand este false, culoare este rosie, deci
raman la semafor
- Conditiile principale sunt folosite din clasa Pedestrians, care are doi getteri ce returneaza daca
timpul de asteptare este finalizat (pedestrians.isFinished) si daca pietonii trec (pedestrians.isPass)
- Cand pietonii trec (isPass), inseamna ca pietonii se afla pe trecere, deci masiniile trebuie sa cedeze
prioritate, si sa se opreasca (rosu).
- Cand pietonii nu trec (!isPass), se intampla opusul.

### Task8(simple_maintenance)

- Aici am folosit doua semafoare si doua cozi, cate o coada/ un semafor pentru fiecare directie
- Folosesc doua cozi pentru a tine cont de fiecare directie (directia 0 si directia 1), 
si asemanator ca la task-ul 6, atata timp cat nu se respecta conditia de iesire in ordine a masiniilor,
acestea asteapta (wait)
- Cele doua semafoare l-am gandit in felul urmator:
 - Primul semafor are noOfCarsPassing permisiuni, pentru ca in cerinta se zice ca masiniile
 pornesc de la lane 0
 - Al doilea semafor are 0 permisiuni ca sa nu se poata intra in acel moment
 la sensul giratoriu
- Prin intermediul barierei astept ca toate masiniile sa fie adaugate in coada pentru fiecare directie
- Atata timp cat numarul de permisiuni al semaforului este egal cu 0, adica nu vor trece masini din acel sens,
si ordinea masiniilor nu se respecta, va sta intr-un wait. Se da notifyAll la final pentru ca atunci termina de trecut o masina.
- In functie de directie, fiecare semafor va da acquire, sa decrementeze contorul. Cand acesta devine 0, o sa fie
reinitializat la noOfCarsPassing permisiuni.


### Task9(complex_maintenance)

- Neimplementat

### Task10(railroad)
- La aceasta cerinta folosesc doua bariere si o coada
- Coada are rolul de a extrage in ordine masiniile ce au ajuns la calea ferata
- Mai intai, adaug toate masiniile in coada, si pun o bariera sa ma asigur ca se executa toate cand trebuie
- Se specifica in enunt ca mesajul cu trecerea trenului trebuie executat de un thread, asa ca am ales
thread-ul 0
- Pun inca o bariera ca nu cumva sa se afiseze mesajul dupa celelalte mesaje de vor urma
- Cat timp ordinea nu se respecta , masiniile asteapta in coada
- Se afiseaza mesajul de faptul ca masiniile au pornit, masina respectiva este scoada din coada si dau
notifyAll ca cele care sunt in wait() sa isi verifice conditia




