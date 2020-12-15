Ionita Radu-Alexandru 332CA

Solutia este una clasica, de tip multiple producers multiple consumers, unde producatorii sunt parintii
si copiii sunt consumatorii. Singura diferenta o consta faptul ca trebuie sa se consume in ordine. Pentru
aceasta problema, in clasa Buffer, la functia get am modificat-o, in sensul ca vreau sa folosesc get pe un
anumit id. 

if(i == value) {
            System.out.println("The child " + i + " ate the cake.");
            return (int)queue.poll();
        } else {
            return -1;
        }

Am acesta conditie ca atunci cand valoarea nu corespunde cu id-ul de este bun, pentru a afisa
corect ordinea, returnez -1, astfel in consumator daca id-ul de il primesc din buffer nu corespunde
cu cel bun, dau break, sa se consume ce s-a produs in ordinea necesara.

In concluzie, afisarea am facut-o din buffer, pentru ca aparea o problema de sincronizare daca
afisam din Producer si Consumer, astfel ocolind aceasta problema.

Solutia merge conform asteptarilor.