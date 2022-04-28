/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

/**
 * Héros du jeu
 */
public class Heros extends EntiteDynamique {

    public Heros(Jeu _jeu, int id) {
        super(_jeu);
        ID = id;
    }

    public boolean peutEtreEcrase() {
        return true;
    }

    public boolean peutServirDeSupport() {
        return true;
    }

    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }

    private int ID;

    public int getID() {
        return ID;
    }
    private int nbVies=5;
    public boolean estEnVie = true;

    public int getNbVies() {
        return nbVies;
    }

    public void setVie(int n) {
        this.nbVies = n;
    }

}
