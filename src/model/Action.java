/*
 * Copyright (c) 27/11/2019
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
package model;

/**
 *
 * @author utilisateur
 */
public class Action{
    private String val ;

    // constructeurs
    public  Action(){
    }

    public  Action(String act){
	val=act;
    }

    // getteurs et setteurs
    public String getAction(){
	return(val);
    }
    public void setAction(String s){
	val=s;
    }
    public void affiche(){
	System.out.println(val);
    }
    public String toString(){
	return val;
    }
}

