package diningphilosophers;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Philosopher extends Thread {
    private final static int delai = 1000;
    private final ChopStick myLeftStick;
    private final ChopStick myRightStick;
    private boolean running = true;

    public Philosopher(String name, ChopStick left, ChopStick right) {
        super(name);
        myLeftStick = left;
        myRightStick = right;
    }

    private void think() throws InterruptedException {
        System.out.println("M."+this.getName()+" pense... ");
        sleep(delai+new Random().nextInt(delai+1));
        System.out.println("M."+this.getName()+" arrête de penser");
    }

    private void eat() throws InterruptedException {
        System.out.println("M."+this.getName() + " mange...");
        sleep(delai+new Random().nextInt(delai+1));
        //System.out.println("M."+this.getName()+" arrête de manger");
    }

    @Override
    public void run() {
        while (running) {
            try {
                think();
                // Aléatoirement prendre la baguette de gauche puis de droite ou l'inverse
                        
                        if(myLeftStick.tryTake()){
                            System.out.println("M."+this.getName()+" a pris une baguette du coté gauche");
                            think(); // pour augmenter la probabilité d'interblocage
                            if(myRightStick.tryTake()){
                                System.out.println("M."+this.getName()+" a pris deux baguettes");
                                 // Si on arrive ici, on a pu "tryTake" les 2 baguettes
                                eat();
                               // On libère les baguettes : 
                                myLeftStick.release();
                                myRightStick.release();
                            }
                        }
                        else{
                            myLeftStick.release();
                             System.out.println("M."+this.getName()+" a relaché baguette du coté gauche");
                }
                      
                
               
                // try again
            } catch (InterruptedException ex) {
                Logger.getLogger("Table").log(Level.SEVERE, "{0} Interrupted", this.getName());
            }
        }
    }

    // Permet d'interrompre le philosophe "proprement" :
    // Il relachera ses baguettes avant de s'arrêter
    public void leaveTable() {
        running = false;
    }

}