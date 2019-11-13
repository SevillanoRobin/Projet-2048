/*
 * Copyright (c) 15/11/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model;


public class Grid extends Movable {

	/**
     * Constructeur par défaut.
	 */
    Grid() {
		this.grid = new Tile[SIZE];
        this.newTile();
	}


	/**
     * Constructeur par paramètre.
     *
	 * Contruit une grille a partir d'un tableau de tuile
	 */
    Grid(Tile[] _g) {
		this.grid = _g;
	}


	/**
	 * Accesseur de l'attribut {@code grid} (tableau de {@link Tile tuile}).
     *
     * @return les tuiles.
	 */
    public Tile[] getGrid() {
		return this.grid;
	}

    /**
	 * Accesseur des éléments de l'attribut {@code grid} (tableau de {@link Tile tuile}).
	 *
	 * @param _ind Indice de la tuile dans le tableau.
	 *
	 * @return la tuile trouvée.
	 */
	public Tile getTile(int _ind) {
		return this.grid[_ind];
	}

	/**
     * Modificateur de l'attribut {@code grid} (tableau de {@link Tile tuile}).
     *
     * @param _grid Une grille contant la nouvelle valeur de l'attribut.
     */
    private void setGrid(Grid _grid) {
        this.grid = _grid.getGrid();
    }

    /**
	 * Modificateur de l'attribut {@code grid} (tableau de {@link Tile tuile}).
	 *
	 * @param _grid Nouvelle valeur de l'attribut.
	 */
	private void setGrid(Tile[] _grid) {
		this.grid = _grid;
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
     * renvoie {@code false} si le joueur a perdu.
     */
    boolean stillPlayeable() {
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
     * @param _tampon
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
     * @return cette copie.
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
     * Accesseur de l'attribut {@code grid}.
     *
     * @return la valeur de l'attribut.
	 */
	public Tile[] getGrid() {
		return this.grid;
	}


	public String toString() {
        StringBuilder s = new StringBuilder("|--------------|\n|");

		for (int index = 0; index < SIZE; index++) {
			if (grid[index] == null)
                s.append("    ");
			else if (grid[index].getValue() < 9)
                s.append(" ").append(grid[index].getValue()).append("  ");
			else if (grid[index].getValue() < 99)
                s.append(" ").append(grid[index].getValue()).append(" ");
			else if (grid[index].getValue() < 999)
                s.append(grid[index].getValue());

            s.append("|");

			if (index == 2 || index == 5)
                s.append("\n|--------------|\n|");
			if (index == 8)
                s.append("\n|--------------|\n");
		}

        return s.toString();
	}
}