/*
 * Copyright (c) 17/12/2019
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
import model.Parameters;

import java.util.ArrayList;

/**
 * IA aléatoire
 *
 * @author Robin
 */
public class IaRandom implements IaStrategies {

    /**
     * Méthode principale qui lance l'ia random
     *
     * @param grids
     * @param profondeurMax
     *
     * @return
     */
    public String ia(Grids grids, int profondeurMax) {
        Etat e;
        boolean trouve = false;
        ArrayList<Action> listeactions, sol;
        ArrayList<Noeud> listefils = new ArrayList<>(), l;
        Noeud n, noeudtete;
        Probleme pb = new Probleme(grids);

        //initialisation de la solution à un état "vide"
        sol = new ArrayList<>();

        // initialisation de la liste d'attente avec l'état initial
        n = new Noeud(new Etat(pb.getGrids(), null), null);
        l = new ArrayList<>();
        l.add(n);

        // je mémorise la liste d'actions du probleme
        listeactions = pb.getlisteactions();

        int profondeur = 0;
        while (!trouve && profondeur < profondeurMax) { // j'itère tant que je ne trouve pas l'état but
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
                        e = noeudtete.getetat().AppliqueAction(_listeaction,
                                noeudtete.getetat().getGrids()); // j'exécute AppliqueAction sur le noeud de tête de la liste, avec la ieme action, et j'obtiens l'état e
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
            profondeur++;

        }
        if (sol.size() != 0) { // si une solution a ete trouve, on la retourne
            return sol.get(0).getAction();
        } else if (listefils.size() == 0) { // si aucun mouvement n'est possible, on renvoie null
            return null;
        } else {

            int nb = (int) (Math.random() *
                            listefils.size()); // on choisit aléatoirement un noeud parmi les plus optimale
            switch (listefils.get(nb).getlisteaction().get(0).getAction()) {
                case "Déplacement droite":
                    grids.move(false, Parameters.RIGHT);
                    break;
                case "Déplacement gauche":
                    grids.move(false, Parameters.LEFT);
                    break;
                case "Déplacement haut":
                    grids.move(false, Parameters.UP);
                    break;
                case "Déplacement bas":
                    grids.move(false, Parameters.DOWN);
                    break;
                case "Déplacement étages supérieures":
                    grids.move(false, Parameters.FRONT);
                    break;
                default:
                    grids.move(false, Parameters.BACK);
                    break;
            }
            grids.affichage();
            return listefils.get(nb).getlisteaction().get(0).getAction(); // on renvoie l'action qui a ete effectue
        }
    }
}
