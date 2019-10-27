package application;

import java.util.Scanner;
import model.Grids;
import model.Parametres;


public class Main_Terminal implements Parametres {

	public static void main(final String[] args) {

		Grids g = new Grids();
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		System.out.println(g);
		while (true) {
			System.out.println("Déplacer vers la Droite (d), Gauche (q), Haut (h), Bas (b), Devant (a) ou Derrier (e) ?");
			String s = sc.nextLine();
			if (!(s.equals("d") || s.equals("right") ||
					s.equals("q") || s.equals("left") ||
					s.equals("z") || s.equals("up") ||
					s.equals("s") || s.equals("down") ||
					s.equals("a") || s.equals("front") ||
					s.equals("e") || s.equals("back")	)) {
				System.out.println("Vous devez écrire (d) pour Droite, (q) pour Gauche, (z) pour Haut, (s) pour Bas, (a) pour Devant ou (e) pour Derrier");
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
				} else if(s.equals("a") || s.equals("front")){
					direction = FRONT;
				} else {
					direction = BACK;
				}

				g.move(direction);
			}
		}
	}
}