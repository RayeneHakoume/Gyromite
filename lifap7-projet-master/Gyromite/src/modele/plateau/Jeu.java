/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.plateau;

import java.awt.Color;
import modele.deplacements.Controle4Directions;
import modele.deplacements.Direction;
import modele.deplacements.Gravite;
import modele.deplacements.Ordonnanceur;
import modele.deplacements.ColonneBlue;
import modele.deplacements.ColonneRed;
import modele.deplacements.IA;

import java.awt.Point;
import java.util.HashMap;
import VueControleur.TimerHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import modele.deplacements.RealisateurDeDeplacement;
import VueControleur.FenetreFin;

/**
 * Actuellement, cette classe gère les postions (ajouter conditions de victoire,
 * chargement du plateau, etc.)
 */
public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 20;
    public static final int SIZE_Y = 10;
    public int nbHero = 0;
    // compteur de déplacements horizontal et vertical (1 max par défaut, à chaque pas de temps)
    private HashMap<Entite, Integer> cmptDeplH = new HashMap<Entite, Integer>();
    private HashMap<Entite, Integer> cmptDeplV = new HashMap<Entite, Integer>();

    private HashMap<Entite, Point> map = new HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private MultiValueMap<EntiteDynamique, List> MapEntitePeutEcraser = new LinkedMultiValueMap<>();// Les details du Heros et Ennemies
    private Entite[][] grilleEntites = new Entite[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées
    private Entite[][] grilleEntitesStatique = new Entite[SIZE_X][SIZE_Y];//Empêchez la corde/bombe d'être endommagée.
    //Utilisé pour restaurer la corde passée par entiteDynamique.
    private Ordonnanceur ordonnanceur = new Ordonnanceur(this);
    private IA ia = new IA().getInstance();//deplacementIA des Bots
    private boolean perdu, gagne;

    private int point, nbBonus, nbBombe, nbBot, nbVies, nbVies1, nbVies2, VieID, nbHeroInit;

    private boolean estEnVie;
    private int timer;
    private int timeInit, timeUpdate;

    public int getNbHeroInit() {
        return nbHeroInit;
    }

    public int getNbBombe() {
        return nbBombe;
    }

    public int getNbBot() {
        return nbBot;
    }

    public int getNbBonus() {
        return nbBonus;
    }

    public int getNbVies1() {
        return nbVies1;
    }

    public int getNbVies2() {
        return nbVies2;
    }

    public int getVieID() {
        return VieID;
    }

    public int getNbPoint() {
        return point;
    }

    public boolean estEnVie() {
        return estEnVie;
    }

    public boolean aTermine() {
        return perdu;
    }

    public boolean aGagner() {
        return gagne;
    }

    public void setTerminer(boolean termine) {
        termine = perdu;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int t) {
        timer = t;
    }

    public int getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(int t) {
        timeUpdate = t;
    }

    public int getTimerInit() {
        return timeInit;
    }

    public void setTimerInit(int t) {
        timeInit = t;
    }

    public Jeu() {
        perdu = false;
        gagne = false;
        nbBonus = 0;
        nbBombe = 0;
        nbBot = 0;
        point = 0;
        ordonnanceur.clear();
    }

    public Jeu(int tUp, int t, int nBV, int niveau, int nbheroinit) {
        timeInit = timer = t;
        timeUpdate = tUp;
        perdu = false;
        gagne = false;
        nbBonus = 0;
        nbBombe = 0;
        nbBot = 0;
        nbVies = nBV;
        nbVies1 = nBV;
        if (nbheroinit == 1) {
            nbVies2 = 0;
        } else {
            nbVies2 = nBV;
        }
        point = 0;
        nbHeroInit = nbheroinit;
        ordonnanceur.clear();
        initialisationDesEntites(niveau);
        initialisationTimer();

        //EntiteDynamique Heros, Bot, Colonne;
    }

    public void resetCmptDepl() {
        cmptDeplH.clear();
        cmptDeplV.clear();
    }

    public void start(long _pause) {
        ordonnanceur.start(_pause);
    }

    public Entite[][] getGrille() {
        return grilleEntites;
    }

    public Entite[][] getGrilleStatique() {
        return grilleEntitesStatique;
    }

    private void initialisationTimer() {
        TimerHandler timerHandler = new TimerHandler(this, timeInit);
        timerHandler.start();
    }

    private void initialisationDesEntites(int niveau) {
        // murs extérieurs horizontaux
        for (int x = 0; x < 20; x++) {
            addEntite(new Mur(this), x, 0);
            addEntite(new Mur(this), x, 9);
        }

        // murs extérieurs verticaux
        for (int y = 1; y < 9; y++) {
            addEntite(new Mur(this), 0, y);
            addEntite(new Mur(this), 19, y);
        }

        switch (niveau) {
            case 1:
                /*
                new Hero
                 */
                newHero(2, 1, 1);
                if (nbHeroInit == 2) {
                    newHero(1, 1, 2);
                }
                /*
                new Bot
                 */
                ia = new IA().getInstance();
                ia.addEntiteDynamique(newBot(10, 1));
                ia.addEntiteDynamique(newBot(15, 1));
                ordonnanceur.add(ia);

                addEntite(new Mur(this), 13, 1);
                addEntite(new Mur(this), 16, 1);

                addEntite(new Mur(this), 1, 3);
                addEntite(new Mur(this), 2, 3);
                addEntite(new Mur(this), 2, 5);
                addEntite(new Mur(this), 3, 5);
                addEntite(new Mur(this), 6, 5);
                addEntite(new Mur(this), 7, 5);
                addEntite(new Mur(this), 8, 5);
                addEntite(new Mur(this), 10, 5);
                addEntite(new Mur(this), 11, 5);
                addEntite(new Mur(this), 12, 5);
                addEntite(new Mur(this), 10, 2);

                for (int n = 1; n < 9; n++) {
                    addEntite(new Corde(this), 15, n);
                }
                addEntite(new Corde(this), 16, 2);
                addEntite(new Corde(this), 16, 3);
                addEntite(new Corde(this), 17, 1);
                addEntite(new Corde(this), 17, 2);
                addEntite(new Corde(this), 9, 4);
                addEntite(new Corde(this), 9, 5);
                addEntite(new Corde(this), 9, 6);
                addEntite(new Corde(this), 9, 7);
                addEntite(new Corde(this), 9, 8);
                addEntite(new Corde(this), 1, 4);
                addEntite(new Corde(this), 1, 5);
                addEntite(new Corde(this), 1, 6);
                addEntite(new Corde(this), 1, 7);
                addEntite(new Corde(this), 3, 7);

                addEntite(new Bombe(this), 18, 1);
                addEntite(new Bombe(this), 14, 1);
                addEntite(new Bombe(this), 2, 8);
                addEntite(new Bombe(this), 11, 8);
                addEntite(new Bonus(this), 1, 8);
                addEntite(new Bonus(this), 12, 8);
                /*
                new colonne
                 */
                newColonneBlue(3, 1, Direction.bas, true);
                newColonneBlue(3, 2, Direction.bas, true);

                newColonneRed(5, 5, Direction.bas, true);
                newColonneRed(5, 6, Direction.bas, true);

                newColonneRed(14, 2, Direction.gauche, false);
                newColonneRed(15, 2, Direction.gauche, false);

                break;

            case 2:
                newHero(2, 1, 1);
                if (nbHeroInit == 2) {
                    newHero(1, 1, 2);
                }
                /*
                new Bot
                 */
                ia = new IA().getInstance();
                ia.addEntiteDynamique(newBot(11, 4));
                ordonnanceur.add(ia);
                addEntite(new Mur(this), 1, 3);
                addEntite(new Mur(this), 2, 3);
                for (int n = 1; n < 8; n++) {
                    addEntite(new Mur(this), 4, n);
                }
                for (int n = 5; n < 16; n++) {
                    addEntite(new Mur(this), n, 7);
                }
                for (int n = 4; n < 6; n++) {
                    addEntite(new Mur(this), 6, n);
                }
                for (int n = 6; n < 16; n++) {
                    addEntite(new Mur(this), n, 2);
                }
                addEntite(new Corde(this), 18, 6);
                addEntite(new Corde(this), 18, 7);
                addEntite(new Corde(this), 18, 8);
                for (int n = 1; n < 7; n++) {
                    addEntite(new Corde(this), 5, n);
                }
                addEntite(new Corde(this), 8, 3);
                addEntite(new Corde(this), 9, 3);
                addEntite(new Corde(this), 9, 4);
                addEntite(new Corde(this), 10, 3);
                addEntite(new Corde(this), 10, 4);
                addEntite(new Corde(this), 10, 5);

                addEntite(new Bombe(this), 7, 1);
                addEntite(new Bombe(this), 18, 1);
                addEntite(new Bombe(this), 16, 5);
                addEntite(new Bonus(this), 7, 3);

                newColonneRed(2, 5, Direction.gauche, false);
                newColonneRed(3, 5, Direction.gauche, false);
                newColonneRed(2, 7, Direction.droite, false);
                newColonneRed(3, 7, Direction.droite, false);
                newColonneBlue(17, 7, Direction.gauche, false);
                newColonneBlue(18, 7, Direction.gauche, false);
                newColonneRed(6, 1, Direction.bas, true);
                newColonneRed(6, 2, Direction.bas, true);
                newColonneBlue(17, 2, Direction.droite, false);
                newColonneBlue(18, 2, Direction.droite, false);
                break;
            case 3:
                /*
                new Hero
                 */
                newHero(1, 1, 1);
                if (nbHeroInit == 2) {
                    newHero(18, 1, 2);
                }
                addEntite(new Mur(this), 1, 3);
                addEntite(new Mur(this), 2, 3);
                addEntite(new Mur(this), 17, 3);
                addEntite(new Mur(this), 18, 3);
                addEntite(new Mur(this), 3, 5);
                addEntite(new Mur(this), 16, 5);
                addEntite(new Mur(this), 8, 3);
                addEntite(new Mur(this), 9, 3);
                addEntite(new Mur(this), 15, 5);
                addEntite(new Mur(this), 10, 3);
                addEntite(new Corde(this), 10, 1);
                addEntite(new Corde(this), 10, 2);
                addEntite(new Corde(this), 8, 1);
                addEntite(new Corde(this), 8, 2);

                newColonneBlue(3, 1, Direction.bas, true);
                newColonneBlue(3, 2, Direction.bas, true);
                newColonneRed(16, 1, Direction.bas, true);
                newColonneRed(16, 2, Direction.bas, true);

                newColonneRed(4, 7, Direction.haut, true);
                newColonneRed(4, 8, Direction.haut, true);
                newColonneRed(5, 1, Direction.bas, true);
                newColonneRed(5, 2, Direction.bas, true);
                newColonneBlue(6, 7, Direction.haut, true);
                newColonneBlue(6, 8, Direction.haut, true);
                newColonneBlue(7, 1, Direction.bas, true);
                newColonneBlue(7, 2, Direction.bas, true);
                newColonneRed(11, 1, Direction.bas, true);
                newColonneRed(11, 2, Direction.bas, true);
                newColonneRed(12, 7, Direction.haut, true);
                newColonneRed(12, 8, Direction.haut, true);
                newColonneBlue(13, 1, Direction.bas, true);
                newColonneBlue(13, 2, Direction.bas, true);
                newColonneBlue(14, 7, Direction.haut, true);
                newColonneBlue(14, 8, Direction.haut, true);
                addEntite(new Bombe(this), 9, 1);
                addEntite(new Bombe(this), 2, 2);
                addEntite(new Bombe(this), 17, 2);
                addEntite(new Bombe(this), 1, 8);
                addEntite(new Bombe(this), 18, 8);

                break;
            default :
                System.out.println("Niveau error : Niveau="+niveau);
        }
    }

    private void addEntite(Entite e, int x, int y) {
        grilleEntites[x][y] = e;
        if (e instanceof EntiteStatique) {
            grilleEntitesStatique[x][y] = e;
            if (e instanceof Bombe) {
                nbBombe++;
            }
            if (e instanceof Bonus) {
                nbBonus++;
            }
        }
        map.put(e, new Point(x, y));
    }

    /* *
     *  Les fonctions des initialisation du Entite
     */
    public void newHero(int a, int b, int id) {
        nbHero++;
        List list = new ArrayList();
        Heros hector = new Heros(this, id);
        addEntite(hector, a, b);
        list.add(new Point(a, b));
        Gravite g = new Gravite();
        g.addEntiteDynamique(hector);
        ordonnanceur.add(g);
        list.add(g);
        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ordonnanceur.add(Controle4Directions.getInstance());
        list.add(Controle4Directions.getInstance());
        MapEntitePeutEcraser.set(hector, list);
    }

    private void newHero(int a, int b, int id, int vie) {
        nbHero++;
        List list = new ArrayList();
        Heros hector = new Heros(this, id);
        hector.setVie(vie);
        addEntite(hector, a, b);
        list.add(new Point(a, b));
        Gravite g = new Gravite();
        g.addEntiteDynamique(hector);
        ordonnanceur.add(g);
        list.add(g);
        Controle4Directions.getInstance().addEntiteDynamique(hector);
        ordonnanceur.add(Controle4Directions.getInstance());
        list.add(Controle4Directions.getInstance());
        MapEntitePeutEcraser.set(hector, list);
    }

    private Bot newBot(int a, int b) {
        nbBot++;
        List list = new ArrayList();
        Bot robot = new Bot(this);
        addEntite(robot, a, b);
        list.add(new Point(a, b));
        Gravite gr = new Gravite();
        gr.addEntiteDynamique(robot);
        ordonnanceur.add(gr);
        list.add(gr);
        MapEntitePeutEcraser.set(robot, list);
        return robot;
    }

    private void newColonneBlue(int a, int b, Direction c, Boolean bool) {
        Colonne bluecolonne = new Colonne(this, Color.blue, c);
        bluecolonne.vertical = bool;
        addEntite(bluecolonne, a, b);
        ColonneBlue CtrlCol = ColonneBlue.getInstance();
        CtrlCol.addEntiteDynamique(bluecolonne);
        ordonnanceur.add(CtrlCol);
    }

    private void newColonneRed(int a, int b, Direction c, Boolean bool) {
        Colonne redcolonne = new Colonne(this, Color.red, c);
        redcolonne.vertical = bool;
        addEntite(redcolonne, a, b);
        ColonneRed CtrlCol = ColonneRed.getInstance();
        CtrlCol.addEntiteDynamique(redcolonne);
        ordonnanceur.add(CtrlCol);
    }

    /**
     * Permet par exemple a une entité de percevoir sont environnement proche et
     * de définir sa stratégie de déplacement
     *
     */
    public Entite regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }

    /* *
     *
     */
    public Entite getCurrentEntiteStatique(Entite e) {
        Entite retour = null;
        if (e != null) {
            Point positionEntite = map.get(e);
            if (contenuDansGrille(positionEntite)) {
                retour = grilleEntitesStatique[positionEntite.x][positionEntite.y];
            }
        }
        return retour;
    }

    /**
     * Si le déplacement de l'entité est autorisé (pas de mur ou autre entité),
     * il est réalisé Sinon, rien n'est fait.
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        boolean retour = false;

        Point pCourant = map.get(e);

        Point pCible = calculerPointCible(pCourant, d);

        if ((contenuDansGrille(pCible) && objetALaPosition(pCible) == null)
                || (contenuDansGrille(pCible) && objetALaPosition(pCible) instanceof Corde)
                || (contenuDansGrille(pCible) && objetALaPosition(pCible) instanceof Ramassage)
                || ((contenuDansGrille(pCible) && objetALaPosition(pCible) instanceof Heros) && (e instanceof Bot))) { // a adapter (collisions murs, etc.)
            // compter le déplacement : 1 deplacement horizontal et vertical max par pas de temps par entité
            switch (d) {
                case bas:
                case haut:
                    if (cmptDeplV.get(e) == null) {
                        cmptDeplV.put(e, 1);
                        retour = true;
                    }
                    break;
                case gauche:
                case droite:
                    if (cmptDeplH.get(e) == null) {
                        cmptDeplH.put(e, 1);
                        retour = true;
                    }
                    break;
            }
        }

        if (retour) {
            deplacerEntite(pCourant, pCible, e);
        }

        return retour;
    }

    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;

        switch (d) {
            case haut:
                pCible = new Point(pCourant.x, pCourant.y - 1);
                break;
            case bas:
                pCible = new Point(pCourant.x, pCourant.y + 1);
                break;
            case gauche:
                pCible = new Point(pCourant.x - 1, pCourant.y);
                break;
            case droite:
                pCible = new Point(pCourant.x + 1, pCourant.y);
                break;

        }

        return pCible;
    }

    private void deplacerEntite(Point pCourant, Point pCible, Entite e) {
        grilleEntites[pCourant.x][pCourant.y] = null;
        if (grilleEntitesStatique[pCourant.x][pCourant.y] instanceof Corde) {
            grilleEntites[pCourant.x][pCourant.y] = grilleEntitesStatique[pCourant.x][pCourant.y];
        }
        if (grilleEntitesStatique[pCourant.x][pCourant.y] instanceof Ramassage && (e instanceof Colonne)) {
            grilleEntites[pCourant.x][pCourant.y] = grilleEntitesStatique[pCourant.x][pCourant.y];
        }
        if ((grilleEntitesStatique[pCible.x][pCible.y] instanceof Ramassage) && (e instanceof Heros)) {
            if ((grilleEntitesStatique[pCible.x][pCible.y] instanceof Bombe)) {
                nbBombe--;
                point++;
            } else if ((grilleEntitesStatique[pCible.x][pCible.y] instanceof Bonus)) {
                nbBonus--;
                point++;
                if (nbBonus == 0) {
                    point += 10;
                }
            }
            map.remove(grilleEntitesStatique[pCible.x][pCible.y]);
            grilleEntitesStatique[pCible.x][pCible.y] = null;
        }
        if ((grilleEntitesStatique[pCourant.x][pCourant.y] instanceof Ramassage) && (e instanceof Bot)) {
            grilleEntites[pCourant.x][pCourant.y] = grilleEntitesStatique[pCourant.x][pCourant.y];
        }
        if ((grilleEntites[pCible.x][pCible.y] instanceof Heros) && (e instanceof Colonne)) {
            ecraseEntiteDynamique((EntiteDynamique) grilleEntites[pCible.x][pCible.y]);
        }
        if ((grilleEntites[pCible.x][pCible.y] instanceof Bot) && (e instanceof Colonne)) {
            ecraseEntiteDynamique((EntiteDynamique) grilleEntites[pCible.x][pCible.y]);
        }
        if ((grilleEntites[pCible.x][pCible.y] instanceof Heros) && (e instanceof Bot)) {
            ecraseEntiteDynamique((EntiteDynamique) grilleEntites[pCible.x][pCible.y]);
        }
        grilleEntites[pCible.x][pCible.y] = e;
        map.put(e, pCible);
    }

    public boolean ecraseEntiteDynamique(EntiteDynamique e) {
        boolean heroEcraser = false;
        List list = MapEntitePeutEcraser.getValues(e);
        int a = ((Point) list.get(0)).x;
        int b = ((Point) list.get(0)).y;
        int id = -1;
        int vie = 0;
        if (e instanceof Heros) {
            vie = ((Heros) e).getNbVies() - 1;
            if (((Heros) e).getID() == 1) {
                nbVies1 = vie;
            } else if (((Heros) e).getID() == 2) {
                nbVies2 = vie;
            }
            if (vie == 0) {
                ((Heros) e).estEnVie = false;
            }
            id = ((Heros) e).getID();
            VieID = id;
        }
        int xCourant = map.get(e).x;
        int yCourant = map.get(e).y;
        if (grilleEntitesStatique[xCourant][yCourant] instanceof Corde) {
            grilleEntites[xCourant][yCourant] = grilleEntitesStatique[xCourant][yCourant];
        } else {
            grilleEntites[xCourant][yCourant] = null;
        }
        map.remove(e);
        if (e instanceof Heros) {
            for (int i = 1; i < list.size(); i++) {
                ordonnanceur.remove((RealisateurDeDeplacement) list.get(i));
                ((RealisateurDeDeplacement) list.get(i)).supEntiteDynamique(e);

            }
        }
        if (e instanceof Bot) {
            nbBot--;
            point++;
            for (int i = 1; i < list.size(); i++) {
                ordonnanceur.remove((RealisateurDeDeplacement) list.get(i));
                ((RealisateurDeDeplacement) list.get(i)).supEntiteDynamique(e);
            }
            ordonnanceur.remove(ia);
            ia.supEntiteDynamique(e);
            if (nbBot != 0) {
                ordonnanceur.add(ia);
            } else {
                MapEntitePeutEcraser.remove(e);
            }
            return true;
        }
        if ((e instanceof Heros) && (((Heros) e).estEnVie == true)) {
            newHero(a, b, id, vie);
            nbHero--;
            MapEntitePeutEcraser.remove(e);
            return true;
        }
        return heroEcraser;
    }

    /**
     * Indique si p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }

    private Entite objetALaPosition(Point p) {
        Entite retour = null;

        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }

        return retour;
    }

    public Ordonnanceur getOrdonnanceur() {
        return ordonnanceur;
    }

    public void refresh() {
        setChanged();
        notifyObservers();
    }

    public void start() {
        new Thread(this).start();

    }

    @Override
    public void run() {

        while (!perdu && !gagne) {

            if (nbBombe == 0) {

                gagne = true;
            } else if (timer == 0 || (nbVies1 == 0 && nbVies2 == 0)) {
                perdu = true;

            }

            try {
                Thread.sleep(600 - timeUpdate);

            } catch (InterruptedException e) {
                e.printStackTrace();

            }
        }

        if (perdu) {
            {
                FenetreFin fenetre = new FenetreFin(false,
                        timeUpdate,
                        nbBombe,
                        timeInit,
                        point);
            }
        } else if (gagne) {
            FenetreFin fenetre = new FenetreFin(true,
                    timeUpdate,
                    nbBombe,
                    timeInit,
                    point);

        }

        refresh();

    }

}
