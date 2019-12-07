/*
 * Copyright (c) 07/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author utilisateur
 */
public class IaScoreMax implements IaStrategies {

    public String ia(Grids grids, int pronfondeurMax) {
        Etat e;
        boolean trouve = false;
        ArrayList<Action> listeactions, sol;
        ArrayList<Noeud> listefils = new ArrayList<Noeud>(), l;
        Noeud n, noeudtete;
        Probleme pb = new Probleme(grids);

        int max = 0;

        //initialisation de la solution à un etat "vide"
        sol = new ArrayList<Action>();

        // initialisation de la liste d'attente avec l'état initial
        n = new Noeud(new Etat(pb.getGrids(), null), null);
        l = new ArrayList<Noeud>();
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
                    for (int i = 0; i < listeactions.size(); i++) {
                        e = noeudtete.getetat().AppliqueAction(listeactions.get(i), noeudtete.getetat().getGrids()); // j'exécute AppliqueAction sur le noeud de tete de la liste, avec la ieme action, et j'obtiens l'état e 			
                        //je construis le noeud résultant : e en tant que nouvel état, la liste des actions associées à l'ancien noeud : noeudtete
                        if (e != null) {
                            n = new Noeud(e, noeudtete.getlisteaction());
                            n.ajoutaction(listeactions.get(i)); // j'ajoute la dernière action
                            listefils.add(n);
                        }
                    }
                    l.addAll(listefils); // pour une version en largeur
                }
                compteur++;
            }
            pronfondeur++;

        }

        if (sol.size() != 0) {
            return sol.get(0).getAction();
        } else {

            for (Noeud ne : listefils) { // on cherche à trouver le maximum de point que l'on peut avoir
                if (max < ne.getetat().getScoreMax()) {
                    max = ne.getetat().getScoreMax();
                }
            }

            ArrayList<Noeud> listeNoeudPossible = new ArrayList<>();
            System.out.println("Liste size :" + listefils.size());
            System.out.println("List l :"+l.size());
            System.out.println(l);
            for (Noeud ne : listefils) {
                if (max == ne.getetat().getScoreMax()) { // on ajoute toutes les possibilités optimales
                    listeNoeudPossible.add(ne);
                }
            }
            int nb = (int) (Math.random() * listeNoeudPossible.size()); // on choisi aléatoirement un noeud parmis les plus optimale
            grids.affichage();
            if (listeNoeudPossible.get(nb).getlisteaction().get(0).getAction().equals("Deplacement droite")) {
                grids.move(RIGHT);
            } else if (listeNoeudPossible.get(nb).getlisteaction().get(0).getAction().equals("Deplacement gauche")) {
                grids.move(LEFT);
            } else if (listeNoeudPossible.get(nb).getlisteaction().get(0).getAction().equals("Deplacement haut")) {
                grids.move(UP);
            } else if (listeNoeudPossible.get(nb).getlisteaction().get(0).getAction().equals("Deplacement bas")) {
                grids.move(DOWN);
            } else if (listeNoeudPossible.get(nb).getlisteaction().get(0).getAction().equals("Deplacement etages superieurs")) {
                grids.move(FRONT);
            } else {
                grids.move(BACK);
            }
            return listeNoeudPossible.get(nb).getlisteaction().get(0).getAction();
        }
    }
}
