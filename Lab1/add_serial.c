#include <pthread.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>

#define min(x, y) (((x) < (y)) ? (x) : (y))

/*
    schelet pentru exercitiul 5
*/

#define MAX_THREAD sysconf(_SC_NPROCESSORS_CONF)

int* arr;
int array_size;

void *f (void *arg) {
    long id = *(long*) arg;
    int start = id * (double)array_size / MAX_THREAD;
    int end = min( (id + 1) * (double)array_size / MAX_THREAD, array_size);
    for (int i = start; i < end; i++){
        arr[i] += 100;
    }
    pthread_exit(NULL);
}

int main(int argc, char *argv[]) {

    int r;
    long id;
    void *status;
    long arguments[MAX_THREAD];
    pthread_t threads[MAX_THREAD];

    if (argc < 2) {
        perror("Specificati dimensiunea array-ului\n");
        exit(-1);
    }

    array_size = atoi(argv[1]);

    arr = malloc(array_size * sizeof(int));
    for (int i = 0; i < array_size; i++) {
        arr[i] = i;
    }

    for (int i = 0; i < array_size; i++) {
        printf("%d", arr[i]);
        if (i != array_size - 1) {
            printf(" ");
        } else {
            printf("\n");
        }
    }

    for (id = 0; id < MAX_THREAD; id++){
        arguments[id] = id;
        r = pthread_create(&threads[id], NULL, f, &arguments[id]);
        if (r){
            printf("Eroare la crearea thread-ului %ld\n", id);
            exit(-1);
        }
    }

    for (id = 0; id < MAX_THREAD; id++){
        arguments[id] = id;
        r = pthread_join(threads[id], &status);
        if (r){
            printf("Eroare la asteptarea thread-ului %ld\n", id);
            exit(-1);
        }
    }

    for (int i = 0; i < array_size; i++) {
        printf("%d", arr[i]);
        if (i != array_size - 1) {
            printf(" ");
        } else {
            printf("\n");
        }
    }
  	pthread_exit(NULL);

    /*
        pentru mai multe numere s-a observat o scadere in timpul de procesare in varianta paralela
        in schimb, varianta cealalta a obtinut rezultate mai bune pentru mai putine numere
    */
}
