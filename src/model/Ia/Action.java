/*
 * Copyright (c) 09/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

package model.Ia;

/**
 * Action
 *
 * @author Robin
 */
public class Action {
    private String val;

    // getteurs et setteurs

    /**
     * Getter
     *
     * @return
     */
    public String getAction() {
        return (val);
    }

    /**
     * Setter
     *
     * @param s
     */
    void setAction(String s) {
        val = s;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return val;
    }
}

