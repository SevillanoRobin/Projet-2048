/*
 * Copyright (c) 07/12/2019
 *
 * Auteurs :
 *      - Behm Guillaume
 *      - Claudel Adrien
 *      - Richez Guillaume
 *      - Sevillano Robin
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Ia;

/**
 *
 * @author utilisateur
 */
public class Action{
    private String val ;


    /**
     * Constructeur
     */
    public  Action(){
    }

    /**
     * Constructeur
     * @param act
     */
    public  Action(String act){
	val=act;
    }

    // getteurs et setteurs

    /**
     * Getter
     * @return
     */
    public String getAction(){
	return(val);
    }

    /**
     * Setter
     * @param s
     */
    public void setAction(String s){
	val=s;
    }


    @Override
    public String toString(){
	return val;
    }
}

