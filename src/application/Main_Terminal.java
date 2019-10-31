/*
 * Copyright (c) 02/11/2019
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

			System.out.println("Déplacer vers la Droite (d), Gauche (q), Haut (h), Bas (b), Devant (r) ou Derrier (f) ?");
			String s = sc.nextLine();
			if (!(s.equals("d") || s.equals("right") ||
					s.equals("q") || s.equals("left") ||
					s.equals("z") || s.equals("up") ||
					s.equals("s") || s.equals("down") ||
					s.equals("r") || s.equals("front") ||
					s.equals("f") || s.equals("back"))) {
				System.out.println("Vous devez écrire (d) pour Droite, (q) pour Gauche, (z) pour Haut, (s) pour Bas, (r) pour Devant ou (f) pour Derrier");
			} else {
				int direction;
				if (s.equals("d") || s.equals("right")) {
					direction = RIGHT;
				} else if (s.equals("q") || s.equals("left")) {
					direction = LEFT;
				} else if (s.equals("z") || s.equals("up")) {
					direction = UP;
				} else if(s.equals("s") || s.equals("down")){
					direction = DOWN;
				} else if(s.equals("r") || s.equals("front")){
					direction = FRONT;
				} else {
					direction = BACK;
				}

				g.move(direction);
			}
		}
		sc.close();
		System.exit(0);
	}
}