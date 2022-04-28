package modele.plateau;

public class Mur extends EntiteStatique {

    public Mur(Jeu _jeu) {
        super(_jeu);
    }

    public boolean peutEtreEcrase() {
        return false;
    }

    public boolean peutServirDeSupport() {
        return true;
    }

    public boolean peutPermettreDeMonterDescendre() {
        return false;
    }
}
