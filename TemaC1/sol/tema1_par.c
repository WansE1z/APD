#include "tema1_par.h"

void *juliaMandelbrot(void *arg)
{
	int tid = *(int *)arg;

	// citirea si alocare necesara juliei
    if (tid == 0){
        read_input_file(in_filename_julia, &par);
        width = (par.x_max - par.x_min) / par.resolution;
        height = (par.y_max - par.y_min) / par.resolution;
        result = allocate_memory(width, height);
    }
    pthread_barrier_wait(&barrier);

    int start = tid * (double)width/P;
    int end = min ((tid + 1) * (double)width/P, width);

	// implementare paralelizata Julia
    for (int w = start; w < end; w++) {
        for (int h = 0; h < height; h++) {
            int step = 0;
            complex z = { .a = w * par.resolution + par.x_min,
                            .b = h * par.resolution + par.y_min };

            while (sqrt(pow(z.a, 2.0) + pow(z.b, 2.0)) < 2.0 && step < par.iterations) {
                complex z_aux = { .a = z.a, .b = z.b };

                z.a = pow(z_aux.a, 2) - pow(z_aux.b, 2) + par.c_julia.a;
                z.b = 2 * z_aux.a * z_aux.b + par.c_julia.b;

                step++;
            }
            
            result[h][w] = step % 256;
        }
    }

	// asteptam executia de catre toate thread-urile a celor doua for-uri
    pthread_barrier_wait(&barrier);

	// formula de start si end din laborator pentru impartirea liniara
	// in loc de N, folosesc height / 2 ca in algoritm
    start = tid * (double)height / 2/P;
    end = min ((tid + 1) * (double)height/ 2 /P, width);

    for (int i = start; i < end; i++) {
            int *aux = result[i];
            result[i] = result[height - i - 1];
            result[height - i - 1] = aux;
        }
    pthread_barrier_wait(&barrier);

	// folosim un singur thread, nu e nevoie de mai multe
	// previn suprascrierea care nu este necesara
    if(tid == 0) {
		write_output_file(out_filename_julia, result, width, height);
        free_memory(result, height);
		read_input_file(in_filename_mandelbrot, &par);
		width = (par.x_max - par.x_min) / par.resolution;
		height = (par.y_max - par.y_min) / par.resolution;
		result = allocate_memory(width, height);
	}
	pthread_barrier_wait(&barrier);

	// formula necesara pentru a segmenta parcurgerea liniara
    start = tid * (double)width/P;
    end = min((tid + 1) * (double)width/P, width);

	// implementare Mandelbrot paralelizata
    for (int w = start; w < end; w++) {
        for (int h = 0; h < height; h++) {
            complex c = { .a = w * par.resolution + par.x_min,
                            .b = h * par.resolution + par.y_min };
            complex z = { .a = 0, .b = 0 };
            int step = 0;

            while (sqrt(pow(z.a, 2.0) + pow(z.b, 2.0)) < 2.0 && step < par.iterations) {
                complex z_aux = { .a = z.a, .b = z.b };

                z.a = pow(z_aux.a, 2.0) - pow(z_aux.b, 2.0) + c.a;
                z.b = 2.0 * z_aux.a * z_aux.b + c.b;

                step++;
            }
            result[h][w] = step % 256;
        }
    }

    // asteptam executia de catre toate thread-urile a celor doua for-uri
    pthread_barrier_wait(&barrier);

	// formula de start si end din laborator pentru impartirea liniara
	// in loc de N, folosesc height / 2 ca in algoritm
    start = tid * (double)height / 2 / P;
    end = min ((tid + 1) * (double)height/ 2 / P, width);

    for (int i = start; i < end; i++) {
		int *aux = result[i];
		result[i] = result[height - i - 1];
		result[height - i - 1] = aux;
	}

	pthread_exit(NULL);
}

int main(int argc, char *argv[])
{
	// se citesc argumentele programului
	get_args(argc, argv);

	// initializam vector-ul care va fi tine numarul thread-ului
	// si un vector de thread-uri
	int tid[P];
	pthread_t thr[P];

    // initializam bariere
	pthread_barrier_init (&barrier, NULL, P);

    // facem thread-urile
	for (int i = 0; i < P; i++) {
		tid[i] = i;
		pthread_create(&thr[i], NULL, juliaMandelbrot, &tid[i]);
	}

	// se asteapta thread-urile
	for (int i = 0; i < P; i++) {
		pthread_join(thr[i], NULL);
	}

    write_output_file(out_filename_mandelbrot, result, width, height);
    free_memory(result, height);

    pthread_barrier_destroy(&barrier);
	return 0;
}
