/*
 * Copyright (c) 08/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model;

import java.io.Serializable;
import java.util.Random;

/**
 * @author Guillaume & Adrien
 */
public class Tile implements Parametres, Cloneable, Serializable {

    private int pos;
    private int value;

    /**
     * @param _x
     * @param _value
     */
    public Tile(int _x, int _value) {
        if (_x < 0 || _x >= SIZE) {
            throw new IllegalArgumentException("tile: position has a wrong value.");
        } else {
            this.pos = _x;
            this.value = _value;
        }
    }

    /**
     * @param _pos
     */
    public Tile(int _pos) {
        this.pos = _pos;
        int value = new Random().nextInt(3);

        if (value == 2)
            this.value = 4;
        else
            this.value = 2;

    }

    /**
     * @param _tile
     *
     * @return
     */
    public boolean compareValeur(Tile _tile) {
        return this.value == _tile.value;
    }

    /**
     * Methode de surcharge pour contourner la visibilité de la methode original
     *
     * @return
     *
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }


    // ---------------------- GETTER & SETTER ---------------------- //

    /**
     * @return
     */

    public int getX() {
        return this.pos;
    }

    /**
     * @param _x
     */
    public void setX(int _x) {
        this.pos = _x;
    }

    /**
     * @return
     */
    public int getValue() {
        return this.value;
    }

    /**
     * @param _value
     */
    public void setValue(int _value) {
        this.value = _value;
    }


    // ------------------------- TO STRING ------------------------- //

    /**
     * @return
     */
    @Override
    public String toString() {
        return "tile(" + pos + "," + value + ")";
    }

}