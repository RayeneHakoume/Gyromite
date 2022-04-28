/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

/**
 *
 * @author Haowei WANG
 */
public class Ramassage extends EntiteStatique {

    public Ramassage(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutEtreEcrase() {
        return false;
    }

    public boolean peutServirDeSupport() {
        return false;
    }

    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }
}
