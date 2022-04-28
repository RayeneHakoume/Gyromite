package modele.deplacements;

import modele.plateau.Jeu;

import java.util.Observable;

import static java.lang.Thread.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Ordonnanceur extends Observable implements Runnable {

    private Jeu jeu;
    private List<RealisateurDeDeplacement> lstDeplacements = new CopyOnWriteArrayList<RealisateurDeDeplacement>();
    private long pause;

    public void add(RealisateurDeDeplacement deplacement) {
        lstDeplacements.add(deplacement);
    }

    public void remove(RealisateurDeDeplacement deplacement) {
        lstDeplacements.remove(deplacement);
    }
    public void clear(){
        for(RealisateurDeDeplacement r:lstDeplacements){
            lstDeplacements.remove(r);
        }
    }
    public Ordonnanceur(Jeu _jeu) {
        jeu = _jeu;
    }

    public void start(long _pause) {
        pause = _pause;
        new Thread(this).start();
    }

    @Override
    public void run() {
        boolean update = false;
        jeu.start();
        

        while (true) {
            
            jeu.resetCmptDepl();
            
            for (RealisateurDeDeplacement d : lstDeplacements) {
                if (d.realiserDeplacement()) {
                    update = true;
                }
            }
            Controle4Directions.getInstance().resetDirection();
            ColonneBlue.getInstance().resetDirection();
            ColonneRed.getInstance().resetDirection();

            if (update) {
                setChanged();
                notifyObservers();
               // jeu.start();
                
            }

            try {
                sleep(pause);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
          
        

    }
}
