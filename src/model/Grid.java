package model;
import java.util.ArrayList;
import java.util.Random;


public class Grid implements Parametres {

	private Tile[] grid;
	private boolean fusion = true;


	/**
	 * Constructeur
	 */
	public Grid() {
		this.grid = new Tile[SIZE];
		newTile();
	}


	/**
	 * Ajoute une nouvelle tuile à la grille
	 */
	private void newTile() {
		ArrayList < Integer > emptyTiles = new ArrayList < > ();

		for (int index = 0; index < SIZE - 1; index++)
			if (grid[index] == null)
				emptyTiles.add(index);

		if (emptyTiles.size() > 0) {
			int pos = new Random().nextInt(emptyTiles.size());
			grid[emptyTiles.get(pos)] = new Tile(pos);
		}

	}


	/**
	 * Verification de fin de tour
	 */
	private void control() {
		victory();
		lose();
		System.out.println(this);
	}


	/**
	 * Verifie si le joueur a gagné
	 */
	public boolean victory() {
		for (Tile t: grid)
			if (t != null && t.getValue() == GOAL) {
				System.out.println("Vous avez gagné, félicitation !");
				System.exit(0);
				return true;
			}

		return false;
	}


	/**
	 * Verifie si le joueur a perdu
	 */
	public boolean lose() {
		boolean control[] = {
			false,
			false,
			false,
			false
		};
		Tile[] tampon = copy();

		control[0] = left(true);
		override(tampon);

		control[1] = right(true);
		override(tampon);

		control[2] = up(true);
		override(tampon);

		control[3] = down(true);
		override(tampon);

		for (boolean b: control) {
			if (b)
				return false;
		}

		System.out.println("Vous avez perdu.");
		System.exit(1);
		return true;
	}


	/**
	 * Remplace la grille
	 * @param tampon
	 */
	private void override(Tile[] _tampon) {
		for (int index = 0; index < SIZE; index++) {
			try {
				if (_tampon[index] != null)
					grid[index] = (Tile) _tampon[index].clone();
				else
					grid[index] = null;
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}


	/**
	 * Crée une copie de la grille
	 * @return
	 */
	private Tile[] copy() {
		Tile[] tampon = new Tile[SIZE];

		for (int index = 0; index < SIZE; index++) {
			try {
				if (grid[index] != null)
					tampon[index] = (Tile) grid[index].clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return tampon;
	}


	/**
	 * Gère les mouvement de la grille
	 * @param _d direction du mouvement
	 */
	protected boolean move(int _d) {
		switch (_d) {

			case LEFT:
				return left(false);

			case RIGHT:
				return right(false);

			case UP:
				return up(false);

			case DOWN:
				return down(false);

			default:
				System.out.println("Erreur de déplacement");
				return true;

		}
	}


	/**
	 * Effectue un mouvement entre deux tuile
	 * @param _a première coordoné
	 * @param _b seconde coordoné
	 * @return boolean
	 */
	private boolean moveTile(int _a, int _b) {

		// Les case sont vide
		if (grid[_a] == null && grid[_b] == null) {
			return false;
		}
		// Mouvement de _b vers _a
		else if (grid[_a] == null && grid[_b] != null) {
			grid[_a] = grid[_b];
			grid[_b] = null;

			return true;
		}

		// Fusion de _a et _b
		else if (grid[_b] != null && grid[_a].getValue() == grid[_b].getValue() && !fusion) {
			grid[_a].setValue(grid[_a].getValue() * 2);
			grid[_b] = null;

			fusion = true;
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Deplacement des tuiles de la grille vers la gauche
	 * @return boolean
	 */
	private boolean left(boolean _control) {
		// tableau récapitulatif des mouvements
		boolean moves[] = new boolean[9];

		// première ligne
		moves[0] = moveTile(0, 1);
		moves[1] = moveTile(1, 2);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[2] = moveTile(0, 1);
		fusion = false; // On donne a nouveau la possibilité de faire une fusion pour prochaine ligne

		// seconde ligne
		moves[3] = moveTile(3, 4);
		moves[4] = moveTile(4, 5);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[5] = moveTile(3, 4);
		fusion = false;

		// troisième ligne
		moves[6] = moveTile(6, 7);
		moves[7] = moveTile(7, 8);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[8] = moveTile(6, 7);
		fusion = false;

		// Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
		for (boolean b: moves) {
			if (b) {
				if (!_control) {
					newTile();
					control();
				}
				return true;
			}
		}


		return false;
	}


	/**
	 * Deplacement des tuiles de la grille vers la droite
	 * @return boolean
	 */
	private boolean right(boolean _control) {
		// tableau récapitulatif des mouvements
		boolean moves[] = new boolean[9];

		// première ligne
		moves[0] = moveTile(2, 1);
		moves[1] = moveTile(1, 0);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[2] = moveTile(2, 1);
		fusion = false;

		// seconde ligne
		moves[3] = moveTile(5, 4);
		moves[4] = moveTile(4, 3);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[5] = moveTile(5, 4);
		fusion = false;

		// troisième ligne
		moves[6] = moveTile(8, 7);
		moves[7] = moveTile(7, 6);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[8] = moveTile(8, 7);
		fusion = false;

		// Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
		for (boolean b: moves) {
			if (b) {
				if (!_control) {
					newTile();
					control();
				}
				return true;
			}
		}

		return false;
	}


	/**
	 * Deplacement des tuiles de la grille vers le haut
	 * @return boolean
	 */
	private boolean up(boolean _control) {
		// tableau récapitulatif des mouvements
		boolean moves[] = new boolean[9];

		// première ligne
		moves[0] = moveTile(0, 3);
		moves[1] = moveTile(3, 6);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[2] = moveTile(0, 3);
		fusion = false;

		// seconde ligne
		moves[3] = moveTile(1, 4);
		moves[4] = moveTile(4, 7);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[5] = moveTile(1, 4);
		fusion = false;

		// troisième ligne
		moves[6] = moveTile(2, 5);
		moves[7] = moveTile(5, 8);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[8] = moveTile(2, 5);
		fusion = false;

		// Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
		for (boolean b: moves) {
			if (b) {
				if (!_control) {
					newTile();
					control();
				}
				return true;
			}
		}

		return false;
	}


	/**
	 * Deplacement des tuiles de la grille vers le bas
	 * @return boolean
	 */
	private boolean down(boolean _control) {
		// tableau récapitulatif des mouvements
		boolean moves[] = new boolean[9];

		// première ligne
		moves[0] = moveTile(6, 3);
		moves[1] = moveTile(3, 0);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[2] = moveTile(6, 3);
		fusion = false;

		// seconde ligne
		moves[3] = moveTile(7, 4);
		moves[4] = moveTile(4, 1);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[5] = moveTile(7, 4);
		fusion = false;

		// troisième ligne
		moves[6] = moveTile(8, 5);
		moves[7] = moveTile(5, 2);
		// si il y a déja eu une fusion sur la ligne on n'en permet pas une nouvelle
		moves[8] = moveTile(8, 5);
		fusion = false;

		// Si un mouvement a été fait on le retourne et on crée une nouvelle tuile
		for (boolean b: moves) {
			if (b) {
				if (!_control) {
					newTile();
					control();
				}
				return true;
			}
		}

		return false;
	}


	public String toString() {
		String s = "|--------------|\n|";
		for (int index = 0; index < SIZE; index++) {
			if (grid[index] == null)
				s += "	";
			else if (grid[index].getValue() < 9)
				s += " " + grid[index].getValue() + "  ";
			else if (grid[index].getValue() < 99)
				s += " " + grid[index].getValue() + " ";
			else if (grid[index].getValue() < 999)
				s += grid[index].getValue();

			s += "|";

			if (index == 2 || index == 5)
				s += "\n|--------------|\n|";
			if (index == 8)
				s += "\n|--------------|\n";
		}

		return s;
	}
}