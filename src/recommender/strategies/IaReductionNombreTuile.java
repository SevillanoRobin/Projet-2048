
/*
 * Copyright (c) 08/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package recommender.strategies;

import model.Grids;
import model.Ia.Action;
import model.Ia.Etat;
import model.Ia.Noeud;
import model.Ia.Probleme;

import java.util.ArrayList;

import static model.Parametres.*;

/**
 * IA base sur la recherche d'un score total maximum
 *
 * @author Robin
 */
public class IaReductionNombreTuile implements IaStrategies {

    /**
     * Methode principale qui lance l'ia score max
     *
     * @param grids
     * @param pronfondeurMax
     * @return
     */
    public String ia(Grids grids, int pronfondeurMax) {
        Etat e;
        boolean trouve = false;
        ArrayList<Action> listeactions, sol;
        ArrayList<Noeud> listefils = new ArrayList<>(), l;
        Noeud n, noeudtete;
        Probleme pb = new Probleme(grids);

        int max = 0;

        //initialisation de la solution à un etat "vide"
        sol = new ArrayList<>();

        // initialisation de la liste d'attente avec l'état initial
        n = new Noeud(new Etat(pb.getGrids(), null), null);
        l = new ArrayList<>();
        l.add(n);

        // je mémorise la liste d'actions du probleme
        listeactions = pb.getlisteactions();

        int pronfondeur = 0;
        while (!trouve && pronfondeur < pronfondeurMax) { // j'itère tant que je ne trouve pas l'état but
            // je récupère la tête de la liste d'attente et je la supprime

            int longueurListe = l.size();
            int compteur = 0;

            while (compteur < longueurListe) {
                noeudtete = l.remove(0);
                //noeudtete.affiche();
                if (noeudtete.getetat().estbut(pb)) { // si l'état but, on arrête
                    trouve = true;
                    sol = noeudtete.getlisteaction();
                } else { // sinon, j'applique chaque action
                    for (Action _listeaction : listeactions) {
                        e = noeudtete.getetat().AppliqueAction(_listeaction, new Grids(
                                noeudtete.getetat().getGrids().getGrids())); // j'exécute AppliqueAction sur le noeud de tete de la liste, avec la ieme action, et j'obtiens l'état e
                        //je construis le noeud résultant : e en tant que nouvel état, la liste des actions associées à l'ancien noeud : noeudtete
                        if (e != null) {
                            n = new Noeud(e, noeudtete.getlisteaction());
                            n.ajoutaction(_listeaction); // j'ajoute la dernière action
                            listefils.add(n);
                        }
                    }
                    l.addAll(listefils); // pour avoir une version en largeur
                }
                compteur++;
            }
            pronfondeur++;

        }

        if (sol.size() != 0) {
            return sol.get(0).getAction();
        } else if (listefils.size() == 0) {
            return null;
        } else {

            for (Noeud ne : listefils) { // on cherche à trouver le maximum de point que l'on peut avoir
                if (max < ne.getetat().getGrids().scoreTotalGrilleMajore()) {
                    max = ne.getetat().getGrids().scoreTotalGrilleMajore();
                }
            }
            System.out.println("C'est max : "+max);
            ArrayList<Noeud> listeNoeudPossible = new ArrayList<>();
            for (Noeud ne : listefils) {
                if (max == ne.getetat().getGrids().scoreTotalGrilleMajore()) { // on ajoute toutes les possibilités optimales
                    System.out.println(ne);
                    listeNoeudPossible.add(ne);
                }
            }
            int nb = (int) (Math.random() * listeNoeudPossible.size()); // on choisi aléatoirement un noeud parmis les plus optimale
            System.out.println(listeNoeudPossible.get(nb).getlisteaction().get(0));
            switch (listeNoeudPossible.get(nb).getlisteaction().get(0).getAction()) {
                case "Déplacement droite":
                    grids.move(false, RIGHT);
                    break;
                case "Déplacement gauche":
                    grids.move(false, LEFT);
                    break;
                case "Déplacement haut":
                    grids.move(false, UP);
                    break;
                case "Déplacement bas":
                    grids.move(false, DOWN);
                    break;
                case "Déplacement etages superieurs":
                    grids.move(false, FRONT);
                    break;
                default:
                    grids.move(false, BACK);
                    break;
            }
            grids.affichage();
            return listeNoeudPossible.get(nb).getlisteaction().get(0).getAction();
        }
    }
}
