package model;

public class Grids extends Movable{

	private Grid grids[];
	

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
		
		Grid tamp[] = reorganization(this.grids);
		for(Grid g : tamp) {
			if(!g.lose())
				return false;
		}
		//on remet la matrice dans le bon sens
		setGrids(reorganization(reorganization(reorganization(tamp))));
		
		for(Grid g : grids) {
			if(!g.lose())
				return false;
		}

		System.out.println(this);
		System.out.println("La partie est finie. Votre score est " + this.best());
		System.exit(1);
		return true;
	}


	/**
	 * La partie est gagné
	 */
	public void victory() {
		if(best() >= GOAL) {
			System.out.println(this);
	        System.out.println("Bravo ! Vous avez atteint " + GOAL);
	        System.exit(0);
		}
	}


	/**
	 * Gere les depacements des grilles
	 */
	public boolean move(int _d) {
		boolean verif[] = {false, false, false};

		for(int index  = 0; index < SIDE; index++) {
			if(_d == FRONT) {
				Grid tamp[] = reorganization(this.grids);
				verif[index] = tamp[index].move(DOWN);
				//on remet la matrice dans le bon sens
				setGrids(reorganization(reorganization(reorganization(tamp))));
			}
			else if(_d == BACK) {
				Grid tamp[] = reorganization(this.grids);
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
				victory();
				lose();
				System.out.println(this);
				return true;
			}
		return false;
	}


	private void setGrids(Grid[] _gs) {
		this.grids =_gs;
	}


	public String toString() {
		String s = new String();
		for(Grid g : grids)
			s += g.toString();
		return s;
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

		Grid[] result = {new Grid(result1), new Grid(result2), new Grid(result3)};
		return result;
	}
	
	
	/**
	 * Remplace les grilles
	 * @param tampon
	 */
	private void override(Grid[] _tampon) {
		for(int index  = 0; index < SIDE; index++) {
			grids[index].override(_tampon[index].getGrid());
		}
	}


	/**
	 * Crée une copie des grilles
	 * @return
	 */
	private Grid[] copy() {
		Grid[] g = {null, null, null};
		for(int index  = 0; index < SIDE; index++) {
			g[index] = new Grid(grids[index].copy());
		}
		
		return g;
	}

}
