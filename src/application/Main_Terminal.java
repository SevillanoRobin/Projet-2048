/*
 * Copyright (c) 20/11/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package application;

import model.Grids;
import model.Parametres;

import java.util.Scanner;

public class Main_Terminal implements Parametres {

    public static void main(final String[] args) {

        Grids g = new Grids();

        Scanner sc = new Scanner(System.in);

        System.out.println(g);
        while (!g.victory() && !g.lose()) {

            System.out.println("Déplacer vers la Droite (d), Gauche (q), Haut (z), Bas (s), Devant (r) ou Derrière (f) ?");
            System.out.println("Sauvegarder (e), charger (l)");
            String s = sc.nextLine();
            if (!(s.equals("d") || s.equals("right") ||
                  s.equals("q") || s.equals("left") ||
                  s.equals("z") || s.equals("up") ||
                  s.equals("s") || s.equals("down") ||
                  s.equals("r") || s.equals("front") ||
                  s.equals("f") || s.equals("back") ||
                  s.equals("e") || s.equals("save") ||
                  s.equals("l") || s.equals("load"))) {
                System.out.println("Vous devez écrire (d) pour Droite, (q) pour Gauche, (z) pour Haut, (s) pour Bas, (r) pour Devant ou (f) pour Derrière");
                System.out.println("Sauvegarder (e), charger (l)");
            } else {
                int direction = BACK;
                switch (s) {
                    case "d":
                    case "right":
                        direction = RIGHT;
                        break;
                    case "q":
                    case "left":
                        direction = LEFT;
                        break;
                    case "z":
                    case "up":
                        direction = UP;
                        break;
                    case "s":
                    case "down":
                        direction = DOWN;
                        break;
                    case "r":
                    case "front":
                        direction = FRONT;
                        break;
                    case "e":
                    case "save":
                        g.save();
                        break;
                    case "l":
                    case "load":
                        g.load();
                        break;
                    default:
                        break;
                }

                g.move(direction);
            }
        }
        sc.close();
        System.exit(0);
    }
}