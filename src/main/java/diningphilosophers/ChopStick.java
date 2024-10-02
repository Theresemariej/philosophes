package diningphilosophers;

public class ChopStick {
    private static int stickCount = 0;
    private boolean iAmFree = true;
    private final int myNumber;
    private int delay;

    public ChopStick() {
        myNumber = ++stickCount;
        delay = 1;
    }

    synchronized public boolean tryTake() throws InterruptedException {
        if (!iAmFree) {
            wait(delay);
            if (!iAmFree) // Toujours pas libre, on abandonne
            {
                return false; // Echec, on ne peut pas prendre la baguette
            }
        }
        iAmFree = false; //dans tout les cas, la baguette est prise: 
        //ou bien elle est déja prise, ou bien alors on la prends nous.
        // Pas utile de faire notifyAll ici, personne n'attend qu'elle soit occupée
        return true; // Succès, on a pu prendre la baguette
    }

    synchronized public void release() {
        // assert !iAmFree;
        System.out.println("baguette " + myNumber + " relâchée");
        iAmFree = true;
        notifyAll(); // On prévient ceux qui attendent que la baguette soit libre
    }

   @Override
    public String toString() {
        return "baguette #" + myNumber;
    }
    
}
