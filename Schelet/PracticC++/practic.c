#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

int N,P;
// pthread_barrier_t barrier;
// pthread_mutex_t lock;

int min(int a, int b){
    if (a <= b) return a;
    return b;
}

void *f(void *arg)
{
    int tid = *(int *)arg;
    int start = tid * (double)N / P;
    int end = min((tid + 1) * (double)N / P, N);

    // pthread_barrier_wait (&barrier);
	// pthread_mutex_lock(&lock);
    // pthread_mutex_unlock(&lock);

	pthread_exit(NULL);
}

int main(int argc, char **argv)
{
	int i, r;
    P = sysconf(_SC_NPROCESSORS_CONF);
	pthread_t threads[P];
	int arguments[P];
	// pthread_barrier_init (&barrier, NULL, P);

    // if (pthread_mutex_init(&lock, NULL) != 0)
    // {
    //     printf("\n mutex init failed\n");
    //     return 1;
    // }

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

    // pthread_mutex_destroy(&lock);
	// pthread_barrier_destroy(&barrier);

	return 0;
}
