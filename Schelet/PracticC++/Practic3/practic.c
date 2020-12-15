#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

int P;
pthread_barrier_t barrier;
pthread_mutex_t lock;

typedef struct _pereche {
	int key;
	int value;
} pereche;

pereche rezultat[10];

int min(int a, int b){
    if (a <= b) return a;
    return b;
}

void *f(void *arg)
{
    int tid = *(int *)arg;
	int pairs = rand() % 10 + 1;
	for (int i = 0; i < pairs; i++){
		int key = rand() % 10 + 1;
		int value = rand() % 100000 + 1;

		printf("Sunt thread-ul %d si am generat pereche (%d, %d).\n", tid, key, value);

		pthread_mutex_lock(&lock);
		if (rezultat[key - 1].value < value){
			rezultat[key - 1].value = value;
		}
		pthread_mutex_unlock(&lock);
	}
    pthread_barrier_wait (&barrier);

	if (tid == 0) {
		for (int i = 0; i < 10 ; i++) {
		printf("(%d, %d) ", rezultat[i].key, rezultat[i].value);
		}
		printf("\n");
  	}

	pthread_exit(NULL);
}

int main(int argc, char **argv)
{
	int i, r;
    P = sysconf(_SC_NPROCESSORS_CONF);
	pthread_t threads[P];
	int arguments[P];

	for (int i = 1; i <= 10; i++){
		rezultat[i - 1].key = i;
		rezultat[i - 1].value = 0;
	}

	pthread_barrier_init (&barrier, NULL, P);

    if (pthread_mutex_init(&lock, NULL) != 0)
    {
        printf("\n mutex init failed\n");
        return 1;
    }

	for (i = 0; i < P; i++) {
		arguments[i] = i;
		r = pthread_create(&threads[i], NULL, f, &arguments[i]);

		if (r) {
			printf("Eroare la crearea thread-ului %d\n", i);
			exit(-1);
		}
	}

	for (i = 0; i < P; i++) {
		r = pthread_join(threads[i], NULL);

		if (r) {
			printf("Eroare la asteptarea thread-ului %d\n", i);
			exit(-1);
		}
	}

    pthread_mutex_destroy(&lock);
	pthread_barrier_destroy(&barrier);

	return 0;
}
