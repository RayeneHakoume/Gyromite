package modele.deplacements;

import modele.plateau.Bot;
import modele.plateau.Entite;
import modele.plateau.EntiteDynamique;
import modele.plateau.Corde;
import modele.plateau.Heros;

public class Gravite extends RealisateurDeDeplacement {

    @Override
    public boolean realiserDeplacement() {
        boolean ret = false;

        for (EntiteDynamique e : lstEntitesDynamiques) {
            if (e != null) {
                Entite eBas = e.regarderDansLaDirection(Direction.bas);
                Entite eCurrentEntiteStatique = e.CurrentEntiteStatique();
                if (!(eCurrentEntiteStatique instanceof Corde)) {
                    if (e instanceof Heros && eBas instanceof Bot) {
                        e.ecraseEntiteDynamique();
                        return false;
                    }
                    if (eBas == null || (eBas != null && !eBas.peutServirDeSupport())) {
                        if (e.avancerDirectionChoisie(Direction.bas)) {
                            ret = true;
                        }
                    }
                }
            }
        }

        return ret;
    }
}
