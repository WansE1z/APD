package philosophersProblem;

/**
 * @author cristian.chilipirea
 * 
 */
public class Philosopher implements Runnable {
	Object leftFork, rightFork;
	int id;

	public Philosopher(int id, Object leftFork, Object rightFork) {
		this.leftFork = leftFork;
		this.rightFork = rightFork;
		this.id = id;
	}

	private void sleep() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		// solutia am gandit-o in felul urmator
		// atata timp cat ei vor lua mereu furculita stanga si apoi dreapta
		// furculita stanga a cuiva e si furculita dreapta a altcuiva
		// asa ca, cei care au id impar, iau mai intai furculita dreapta dupa cea stanga
		// si cei cu id par, iau mai intai furculita stanga si apoi cea dreapta
		// ocolind problema respectiva
		if (id % 2 == 1){
			synchronized (rightFork) {
				sleep(); // delay added to make sure the dead-lock is visible
				synchronized (leftFork) {
					System.out.println("Philosopher " + id + " is eating");
				}
			}
		} else {
			synchronized (leftFork) {
				sleep(); // delay added to make sure the dead-lock is visible
				synchronized (rightFork) {
					System.out.println("Philosopher " + id + " is eating");
				}
			}
		}
	}
}
