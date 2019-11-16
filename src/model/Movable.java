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

import java.util.ArrayList;
import java.util.Random;

import static model.Parametres.SIZE;

public abstract class Movable {

    Tile[] grid;
	boolean fusion = true;


	/**
	 * Initialise la grille avant d'utiliser une methode
	 */
	private void setGrid(Grid _g) {
		this.grid = _g.getGrid();
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

			grid[_a].setPos(_a);

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
	 * Ajoute une nouvelle tuile à la grille
	 */
	void newTile() {
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
	 * Deplacement des tuiles de la grille vers la gauche
	 * @return boolean
	 */
	protected boolean left(boolean _control, Grid _g) {

		setGrid(_g);

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
	protected boolean right(boolean _control, Grid _g) {

		setGrid(_g);

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
	protected boolean up(boolean _control, Grid _g) {

		setGrid(_g);

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
	protected boolean down(boolean _control, Grid _g) {

		setGrid(_g);

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
				}
				return true;
			}
		}

		return false;
	}


}