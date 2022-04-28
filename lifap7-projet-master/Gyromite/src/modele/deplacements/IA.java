package modele.deplacements;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import modele.plateau.Ramassage;
import modele.plateau.Bot;
import modele.plateau.Corde;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Heros;
import modele.plateau.Jeu;

public class IA extends RealisateurDeDeplacement {

    private static IA ia;

    public static IA getInstance() {
        if (ia == null) {
            ia = new IA();
        }
        return ia;
    }

    private Direction getRandom(List<Direction> array) {
        int rnd = new Random().nextInt(array.size());
        return array.get(rnd);
    }

    private void setDirectionCourant(EntiteDynamique e) {
        List<Direction> arrayRandom = new ArrayList();
        if (e instanceof Bot) {
            Entite eBas = e.regarderDansLaDirection(Direction.bas);
            if ((eBas == null) || (eBas instanceof Corde) || (eBas instanceof Ramassage) || (eBas instanceof Heros)) {
                arrayRandom.add(Direction.bas);
            }
            Entite eHaut = e.regarderDansLaDirection(Direction.haut);
            if (eHaut == null) {
            } else if ((eHaut instanceof Corde) || (eHaut instanceof Heros)) {
                arrayRandom.add(Direction.haut);
            }
            Entite eGauche = e.regarderDansLaDirection(Direction.gauche);
            if ((eGauche == null) || (eGauche instanceof Corde) || (eGauche instanceof Ramassage) || (eGauche instanceof Heros)) {
                if (eBas != null && eBas.peutServirDeSupport()) {
                    arrayRandom.add(Direction.gauche);
                } else if (e.CurrentEntiteStatique() != null && e.CurrentEntiteStatique() instanceof Corde) {
                    arrayRandom.add(Direction.gauche);
                }
            }
            Entite eDroite = e.regarderDansLaDirection(Direction.droite);
            if ((eDroite == null) || (eDroite instanceof Corde) || (eDroite instanceof Ramassage) || (eDroite instanceof Heros)) {
                if (eBas != null && eBas.peutServirDeSupport()) {
                    arrayRandom.add(Direction.droite);
                } else if (e.CurrentEntiteStatique() != null && e.CurrentEntiteStatique() instanceof Corde) {
                    arrayRandom.add(Direction.droite);
                }
            }
            if (!arrayRandom.isEmpty()) {
                e.directionEntiteDynamique = getRandom(arrayRandom);
            } else {
                e.directionEntiteDynamique = Direction.bas;
            }
        }
    }

    public boolean realiserDeplacement() {
        boolean ret = false;
        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e != null && e instanceof Bot) {
                Direction botDirection = e.directionEntiteDynamique;
                if (botDirection == null) {
                    setDirectionCourant(e);
                } else {
                    switch (botDirection) {
                        case gauche:
                        case droite:
                            Entite eBas1 = e.regarderDansLaDirection(Direction.bas);
                            if (eBas1 != null && eBas1.peutServirDeSupport()) {
                                if (e.avancerDirectionChoisie(botDirection)) {
                                    ret = true;
                                } else {
                                    setDirectionCourant(e);
                                }
                            } else if (e.CurrentEntiteStatique() != null && e.CurrentEntiteStatique().peutPermettreDeMonterDescendre()) {
                                if (e.avancerDirectionChoisie(botDirection)) {
                                    ret = true;
                                } else {
                                    setDirectionCourant(e);
                                }
                            } else {
                                setDirectionCourant(e);
                            }
                            break;
                        case haut:
                            Entite eHaut = e.regarderDansLaDirection(Direction.haut);
                            if (eHaut != null && eHaut.peutPermettreDeMonterDescendre()) {
                                if (e.avancerDirectionChoisie(botDirection)) {
                                    ret = true;
                                } else {
                                    setDirectionCourant(e);
                                }
                            } else {
                                setDirectionCourant(e);
                            }
                            break;
                        case bas:
                            if (e.CurrentEntiteStatique() != null && e.CurrentEntiteStatique().peutPermettreDeMonterDescendre()) {
                                if (e.avancerDirectionChoisie(botDirection)) {
                                    ret = true;
                                } else {
                                    setDirectionCourant(e);
                                }
                            } else {
                                setDirectionCourant(e);
                            }
                            break;
                    }
                }
            }

        }
        return ret;
    }
}
