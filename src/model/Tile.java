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

import java.io.Serializable;
import java.util.Random;

/**
 * @author Guillaume & Adrien
 */
public class Tile implements Cloneable, Serializable {

    private int pos;
    private int value;

    /**
     * @param _pos
     */
    Tile(int _pos) {
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
    boolean compareValeur(Tile _tile) {
        return this.value == _tile.value;
    }

    /**
     * Méthode de surcharge pour contourner la visibilité de la méthode originale
     *
     * @return
     *
     * @throws CloneNotSupportedException
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return "tile(" + pos + "," + value + ")";
    }

    // ---------------------- GETTER & SETTER ---------------------- //

    /**
     * @param _x
     */
    void setX(int _x) {
        this.pos = _x;
    }

    /**
     * @return
     */
    int getValue() {
        return this.value;
    }

    /**
     * @param _value
     */
    void setValue(int _value) {
        this.value = _value;
    }
}