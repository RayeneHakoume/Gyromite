package modele.deplacements;

import modele.plateau.Bot;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Heros;
import modele.plateau.Jeu;

/**
 * Controle4Directions permet d'appliquer une direction (connexion avec le
 * clavier) à un ensemble d'entités dynamiques
 */
public class Controle4Directions extends RealisateurDeDeplacement {

    private Direction directionCourante;
    private int idCourant;
    // Design pattern singleton
    private static Controle4Directions c4d;
    public Jeu jeu;

    public static Controle4Directions getInstance() {
        if (c4d == null) {
            c4d = new Controle4Directions();
        }
        return c4d;
    }

    public void setDirectionCourante(Direction _directionCourante,int id) {
        directionCourante = _directionCourante;
        idCourant=id;
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (((Heros)e).getID()==idCourant) {
                if (directionCourante != null) {
                    if (e.regarderDansLaDirection(directionCourante) instanceof Bot) {
                        e.ecraseEntiteDynamique();                       
                        return false;
                    }
                    switch (directionCourante) {
                        case gauche:
                        case droite:
                            Entite eBas1 = e.regarderDansLaDirection(Direction.bas);
                            if (eBas1 != null && eBas1.peutServirDeSupport()) {
                                if (e.avancerDirectionChoisie(directionCourante)) {
                                    ret = true;
                                }
                            } else if (e.CurrentEntiteStatique() != null && e.CurrentEntiteStatique().peutPermettreDeMonterDescendre()) {
                                if (e.avancerDirectionChoisie(directionCourante)) {
                                    ret = true;
                                }
                            }
                            break;

                        case haut:
                            // on ne peut pas sauter sans prendre appui
                            // (attention, test d'appui réalisé à partir de la position courante, si la gravité à été appliquée, il ne s'agit pas de la position affichée, amélioration possible)

                            Entite eBas = e.regarderDansLaDirection(Direction.bas);
                            Entite eHaut = e.regarderDansLaDirection(Direction.haut);
                            if (eBas != null && eHaut != null && eBas.peutServirDeSupport() && eHaut.peutPermettreDeMonterDescendre()) {
                                if (e.avancerDirectionChoisie(Direction.haut)) {
                                    ret = true;
                                }
                            } else if (eHaut != null && eHaut.peutPermettreDeMonterDescendre() && e.CurrentEntiteStatique() != null && e.CurrentEntiteStatique().peutPermettreDeMonterDescendre()) {
                                if (e.avancerDirectionChoisie(directionCourante)) {
                                    ret = true;
                                }
                            }
                            break;
                        case bas:
                            if (e.CurrentEntiteStatique() != null && e.CurrentEntiteStatique().peutPermettreDeMonterDescendre()) {
                                if (e.avancerDirectionChoisie(directionCourante)) {
                                    ret = true;
                                }
                            }
                            break;
                    }
                }
            }
        }

        return ret;

    }

    public void resetDirection() {
        directionCourante = null;
    }
}
