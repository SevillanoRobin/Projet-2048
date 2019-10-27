package model;
import java.util.ArrayList;
import java.util.Random;


public class Grid extends Movable {

	private Tile[] grid;


	/**
	 * Constructeur
	 */
	public Grid() {
		this.grid = new Tile[SIZE];
		newTile();
	}
	
	
	/**
	 * Contruit une grille a partir d'un tableau de tuile
	 * @return 
	 */
	public Grid(Tile[] _g) {
		this.grid = _g;
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
	 * Verifie si le joueur a gagné
	 */
	public boolean victory() {
		for (Tile t: grid)
			if (t != null && t.getValue() == GOAL) {
				return true;
			}

		return false;
	}


	/**
	 * renvoie vrai si le joueur a perdu
	 */
	public boolean lose() {
		boolean control[] = {
			false,
			false,
			false,
			false
		};
		
		Tile[] tampon = copy();

		control[0] = left(true, this);
		override(tampon);

		control[1] = right(true, this);
		override(tampon);

		control[2] = up(true, this);
		override(tampon);

		control[3] = down(true, this);
		override(tampon);

		for (boolean b: control) {
			if (b)
				return false;
		}

		return true;
	}


	/**
	 * Remplace la grille
	 * @param tampon
	 */
	protected void override(Tile[] _tampon) {
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
	protected Tile[] copy() {
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
	 * Meilleur score
	 */
	public int best() {
		int score = 0;
		
		for(Tile t : grid)
			if(t != null && t.getValue() > score)
				score = t.getValue();
		
		return score;
	}
	


	/**
	 * Gère les mouvement de la grille
	 * @param _d direction du mouvement
	 */
	public boolean move(int _d) {
		switch (_d) {

			case LEFT:
				return left(false, this);

			case RIGHT:
				return right(false, this);

			case UP:
				return up(false, this);

			case DOWN:
				return down(false, this);

			default:
				System.out.println("Erreur de déplacement");
				return true;
		}
	}
	
	
	/**
	 * renvoie l'objet
	 * @return 
	 */
	public Tile[] getGrid() {
		return this.grid;
	}


	public String toString() {
		String s = "|--------------|\n|";
		for (int index = 0; index < SIZE; index++) {
			if (grid[index] == null)
				s += "    ";
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