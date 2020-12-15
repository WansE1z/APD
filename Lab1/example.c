#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

// task 1
#define NUM_THREADS 2
 
void *f(void *arg)
{
    long id = *(long*) arg;
	/* task 3
	for (int i = 1; i <= 100; i++){
    	printf("Hello World din thread-ul %ld, mesajul numarul %d!\n", id, i);
	}
	*/

	printf("Hello World din thread-ul %ld\n", id);
    pthread_exit(NULL);
}

void *f1(void *arg)
{
    long id = *(long*) arg;
	/* task 3
	for (int i = 1; i <= 100; i++){
    	printf("Hello World din thread-ul %ld, mesajul numarul %d!\n", id, i);
	}
	*/

	printf("Bye din thread-ul %ld\n", id);
    pthread_exit(NULL);
}

 
int main(int argc, char *argv[])
{
	// task 2
	long cores = sysconf(_SC_NPROCESSORS_CONF);

    /* task 3
    pthread_t threads[cores];
    int r,r1;
    long id;
    void *status;
    long arguments[cores];
 
    for (id = 0; id < cores; id++) {
        arguments[id] = id;
        	r = pthread_create(&threads[id], NULL, f, (void *) &arguments[id]);
        if (r) {
            printf("Eroare la crearea thread-ului %ld\n", id);
            exit(-1);
        }
    }
 
    for (id = 0; id < cores; id++) {
        r = pthread_join(threads[id], &status);
 
        if (r) {
            printf("Eroare la asteptarea thread-ului %ld\n", id);
            exit(-1);
        }
    }
    */

    void *status;
    pthread_t thr1,thr2;
    int r,r1;
    long arguments[2] = {0,1};

    r = pthread_create(&thr1, NULL, f, (void *) &arguments[0]);
    if (r){
        printf("Eroare");
        exit(-1);
    }

    r = pthread_create(&thr2, NULL, f1, (void *) &arguments[1]);
    if (r){
        printf("Eroare");
        exit(-1);
    }

    pthread_join(thr1, &status);
    pthread_join(thr2, &status);

    pthread_exit(NULL);
    return 0;

}