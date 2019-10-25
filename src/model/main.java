package model;
import java.util.Scanner;

public class main implements Parametres {

	public static void main(final String[] args) {

		Grid g = new Grid();
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);

		System.out.println(g);
		while (true) {
			System.out.println("Déplacer vers la Droite (d), Gauche (q), Haut (h), ou Bas (b) ?");
			String s = sc.nextLine();
			if (!(s.equals("d") || s.equals("right") ||
					s.equals("q") || s.equals("left") ||
					s.equals("z") || s.equals("up") ||
					s.equals("s") || s.equals("down"))) {
				System.out.println("Vous devez écrire (d) pour Droite, (q) pour Gauche, (z) pour Haut ou (s) pour Bas");
			} else {
				int direction;
				if (s.equals("d") || s.equals("right")) {
					direction = RIGHT;
				} else if (s.equals("q") || s.equals("left")) {
					direction = LEFT;
				} else if (s.equals("z") || s.equals("up")) {
					direction = UP;
				} else {
					direction = DOWN;
				}

				g.move(direction);
			}
		}
	}
}