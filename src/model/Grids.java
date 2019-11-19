/*
 * Copyright (c) 19/11/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model;

import static model.Parametres.*;

public class Grids {

    private Grid[] grids;

	/**
	 * constructeur de la matrice 3D
	 */
	public Grids() {

		grids = new Grid[3];

		for(int index  = 0; index < 3; index++) {
			grids[index] = new Grid();
		}
	}

    /**
     * Accesseur de l'attribut {@code grids} (tableau de {@link Grid grille}).
     *
     * @return les grilles.
     */
    public Grid[] getGrids() {
        return this.grids;
    }

    /**
     * Accesseur des éléments de l'attribut {@code grids} (tableau de {@link Grid grille}).
     *
     * @param _ind Indice de la grille dans le tableau.
     *
     * @return la grille trouvée.
     */
    public Grid getGrid(int _ind) {
        return this.grids[_ind];
    }

    /**
     * Modificateur de l'attribut {@code grids} (tableau de {@link Grid grille}).
     *
     * @param _gs Nouvelle valeur de l'attribut.
     */
    private void setGrids(Grid[] _gs) {
        this.grids = _gs;
    }

	/**
	 * Meilleur score
	 */
	public int best() {
		int score = 0;

		for(Grid g : grids)
			if(g.best() > score)
				score = g.best();

		return score;
	}

	/**
	 * Verifie si les 3 grilles son bloquées
	 * @return boolean
	 */
	public boolean lose() {

        Grid[] tamp = reorganization(this.grids);
		for(Grid g : tamp) {
			if(g.stillPlayeable())
				return false;
		}
		//on remet la matrice dans le bon sens
		setGrids(reorganization(reorganization(reorganization(tamp))));

		for(Grid g : grids) {
			if(g.stillPlayeable())
				return false;
		}

		System.out.println(this);
		System.out.println("La partie est finie. Votre score est " + this.best());

		return true;
	}


	/**
     * Détermine si la partie est gagnée.
     *
     * @return {@code true} si la partie est déterminée comme gagnée.
	 */
	public boolean victory() {
		if(best() >= GOAL) {
			System.out.println(this);
	        System.out.println("Bravo ! Vous avez atteint " + GOAL);
	        return true;
		}
		return false;
	}


	/**
	 * Gere les depacements des grilles
	 */
	public void move(int _d) {
        boolean[] verif = { false, false, false };

		for(int index  = 0; index < SIDE; index++) {
			if(_d == FRONT) {
                Grid[] tamp = reorganization(this.grids);
				verif[index] = tamp[index].move(DOWN);
				//on remet la matrice dans le bon sens
				setGrids(reorganization(reorganization(reorganization(tamp))));
			}
			else if(_d == BACK) {
                Grid[] tamp = reorganization(this.grids);
				verif[index] = tamp[index].move(UP);
				//on remet la matrice dans le bon sens
				setGrids(reorganization(reorganization(reorganization(tamp))));
			}
			else {
				verif[index] = grids[index].move(_d);
			}
		}

		for(boolean b : verif)
			if(b) {
				System.out.println(this);
				return;
			}
	}


	public String toString() {
        StringBuilder s = new StringBuilder();
        for (Grid g : grids) {
            s.append(g.toString());
        }
        return s.toString();
	}


	/**
	 * Pivote la matrice de jeu
	 */
	private Grid[] reorganization(Grid[] _gs) {
		Tile[] result1 = new Tile[9];
		Tile[] result2 = new Tile[9];
		Tile[] result3 = new Tile[9];

		int index2 = 2, index3 = 0;
		for(int index1  = 0; index1 < SIZE; index1++) {
			if(_gs[index2].getGrid()[index3] != null)
				result1[index1] = _gs[index2].getGrid()[index3];
			if(_gs[index2].getGrid()[index3 + SIDE] != null)
				result2[index1] = _gs[index2].getGrid()[index3 + SIDE];
			if(_gs[index2].getGrid()[index3 + (SIDE*2)] != null)
				result3[index1] = _gs[index2].getGrid()[index3 + (SIDE*2)];

			index3++;
			if(index1 == 2 || index1 == 5) {
				index2--;
				index3 = 0;
			}
		}

        return new Grid[] { new Grid(result1), new Grid(result2), new Grid(result3) };
	}

}