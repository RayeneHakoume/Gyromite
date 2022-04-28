package modele.deplacements;

import java.awt.Color;
import java.util.Calendar;
import modele.plateau.Bot;
import modele.plateau.EntiteDynamique;
import modele.plateau.Colonne;
import modele.plateau.Entite;
import modele.plateau.Heros;
import modele.plateau.Jeu;

/**
 * A la reception d'une commande, toutes les cases (EntitesDynamiques) des
 * colonnes se déplacent dans la direction définie (vérifier "collisions" avec
 * le héros)
 */
public class ControleColonne extends RealisateurDeDeplacement {

    private Color ColorChoisir;
    private static ControleColonne c1;
    public Jeu jeu;

    public static ControleColonne getInstance() {
        if (c1 == null) {
            c1 = new ControleColonne();
        }
        return c1;
    }

    public void setColonneColor(Color col) {
        ColorChoisir = col;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (((Colonne) e).getColonneColor() == ColorChoisir) {
                ((Colonne) e).inverse = false;
            }
        }
        setDirectionCourante();
    }

    private void setDirectionCourante() {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (((Colonne) e).getColonneColor() == ColorChoisir) {
                if (e.directionEntiteDynamique == null) {
                    e.directionEntiteDynamique = Direction.bas;
                } else {
                    if (((Colonne) e).inverse == true) {
                        System.out.println("Colonne inverse");
                    }
                }
            }
        }
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e.directionEntiteDynamique != null) {
                if (((Colonne) e).getColonneColor() == ColorChoisir) {
                    Direction acd = ((Colonne) e).actualColonneDirection();
                    if (acd != null) {
                        if (((Colonne) e).vertical) {
                            switch (acd) {
                                case haut:
                                    Entite eHaut = e.regarderDansLaDirection(Direction.haut);
                                    if (e.avancerDirectionChoisie(Direction.haut)) {
                                        ret = true;
                                    }else if (eHaut instanceof Heros || eHaut instanceof Bot) {
                                        Entite eHautHaut = ((EntiteDynamique) eHaut).regarderDansLaDirection(Direction.haut);
                                        if (eHautHaut != null && eHautHaut.peutServirDeSupport()) {
                                            ((EntiteDynamique) eHaut).ecraseEntiteDynamique();
                                        } else {
                                            ((EntiteDynamique) eHaut).avancerDirectionChoisie(Direction.haut);
                                        }
                                    }
                                    break;
                                case bas:
                                    Entite eBas = e.regarderDansLaDirection(Direction.bas);
                                    if ((eBas instanceof Heros) || (eBas instanceof Bot)) {
                                        ((EntiteDynamique) eBas).ecraseEntiteDynamique();
                                    }
                                    if (e.avancerDirectionChoisie(Direction.bas)) {
                                        ret = true;
                                        break;
                                    }
                            }
                        } else {
                            switch (acd) {
                                case gauche:
                                    if (e.avancerDirectionChoisie(Direction.gauche)) {
                                        ret = true;
                                    }
                                    break;
                                case droite:
                                    if (e.avancerDirectionChoisie(Direction.droite)) {
                                        ret = true;
                                        break;
                                    }
                            }
                        }
                    }
                }
            }
        }
        return ret;
    }


    /*
    keyReleased
     */
    public void resetDirection() {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (((Colonne) e).getColonneColor() == ColorChoisir) {
                if (((Colonne) e).inverse != null && (((Colonne) e).inverse == false)) {
                    ((Colonne) e).inverse = null;
                }
            }
        }
    }

    public void inverseColonne(Color cc) {
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (((Colonne) e).getColonneColor() == cc) {
                ((Colonne) e).inverse = true;
            }
        }
    }
}
