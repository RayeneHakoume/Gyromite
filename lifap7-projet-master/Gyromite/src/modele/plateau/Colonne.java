package modele.plateau;

import java.awt.Color;
import modele.deplacements.Direction;

public class Colonne extends EntiteDynamique {

    private Color ColonneColor;
    public Boolean inverse = null;
    public Boolean vertical = true;

    public Direction actualColonneDirection() {
        Direction retour = this.directionEntiteDynamique;
        if (inverse == null) {
            return null;
        }
        if ((inverse != null) && (inverse)) {
            if (vertical) {
                if (this.directionEntiteDynamique == Direction.haut) {
                    retour = Direction.bas;
                } else if (this.directionEntiteDynamique == Direction.bas) {
                    retour = Direction.haut;
                }
            }else{
                if (this.directionEntiteDynamique == Direction.gauche) {
                    retour = Direction.droite;
                } else if (this.directionEntiteDynamique == Direction.droite) {
                    retour = Direction.gauche;
                }
            }
        }
        return retour;
    }

    public Color getColonneColor() {
        return ColonneColor;
    }

    public void setColonneColor(Color c) {
        ColonneColor = c;
    }

    public Colonne(Jeu _jeu, Color c) {
        super(_jeu);
        ColonneColor = c;
    }

    public Colonne(Jeu _jeu, Color c, Direction d) {
        super(_jeu);
        ColonneColor = c;
        this.directionEntiteDynamique = d;
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
;

}
