/*
 * Copyright (c) 20/11/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model;

import java.util.Random;

/**
 * 
 * @author Guillaume & Adrien
 *
 */
public class Tile implements Cloneable {

	private int pos;
	private int value;

	Tile(int _pos) {
		this.pos = _pos;
		int value = new Random().nextInt(3);

		if (value == 2)
			this.value = 4;
		else
			this.value = 2;

	}

	/**
	 * Methode de surcharge pour contourner la visibilité de la methode original
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	// ---------------------- GETTER & SETTER ---------------------- //

	public int getPos() {
		return this.pos;
	}

	public void setPos(int _pos) {
		this.pos = _pos;
	}

	public int getValue() {
		return this.value;
	}

	void doubleValue() {
		this.value *= 2;
	}
}