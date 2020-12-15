#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <pthread.h>

int N,P;
int v[100];
int sum[12];
int sumFin = 0;
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

	for (int i = start; i < end; i++){
		sum[tid] += v[i];
	}

	// pthread_mutex_lock(&lock);
    // pthread_mutex_unlock(&lock);

	pthread_exit(NULL);
}

int main(int argc, char **argv)
{
	int i, r;
	double ma = 0;
	N = 100;
    P = sysconf(_SC_NPROCESSORS_CONF);
	pthread_t threads[P];
	int arguments[P];
	// pthread_barrier_init (&barrier, NULL, P);

    // if (pthread_mutex_init(&lock, NULL) != 0)
    // {
    //     printf("\n mutex init failed\n");
    //     return 1;
    // }
	for (int i = 0; i < N; i++){
		v[i] = i;
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

	for (int i = 0; i < 12; i++){
		sumFin += sum[i];
	}

	ma = (double) sumFin / N;
	printf("%lf\n", ma);
    // pthread_mutex_destroy(&lock);
	// pthread_barrier_destroy(&barrier);

	return 0;
}
