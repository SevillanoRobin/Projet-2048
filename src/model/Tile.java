package model;
import java.util.Random;

/**
 * 
 * @author Guillaume & Adrien
 *
 */
public class Tile implements Parametres, Cloneable {

	private int pos;
	private int value;


	public Tile(int _x, int _value) {
		if (_x < 0 || _x >= SIZE) {
			throw new IllegalArgumentException("tile: position has a wrong value.");
		} else {
			this.pos = _x;
			this.value = _value;
		}
	}


	public Tile(int _pos) {
		this.pos = _pos;
		int value = new Random().nextInt(3);

		if (value == 2)
			this.value = 4;
		else
			this.value = 2;

	}


	public boolean compareValeur(Tile _tile) {
		return this.value == _tile.value;
	}

	/**
	 * Methode de surcharge pour contourner la visibilité de la methode original
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}


	// ---------------------- GETTER & SETTER ---------------------- //

	public int getX() {
		return this.pos;
	}

	public void setX(int _x) {
		this.pos = _x;
	}

	public int getValue() {
		return this.value;
	}

	public void setValue(int _value) {
		this.value = _value;
	}


	// ------------------------- TO STRING ------------------------- //

	@Override
	public String toString() {
		return "tile(" + pos + "," + value + ")";
	}

}