/*
 * Copyright (c) 09/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model;

/**
 *
 * @author Sylvain
 */
public interface Parameters {
	int SIDE = 3;
	int SIZE = SIDE * SIDE;
	int GOAL = 2048;
	int UP = -SIDE;
	int RIGHT = 1;
	int DOWN = SIDE;
	int LEFT = -1;
	int FRONT = 2;
	int BACK = -2;
}