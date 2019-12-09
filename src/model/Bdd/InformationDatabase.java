/*
 * Copyright (c) 08/12/2019
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
package model.Bdd;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Robin
 */
public class InformationDatabase {

    private final ConnectionDatabase connexion;

    public InformationDatabase(ConnectionDatabase connexion) {
        this.connexion = connexion;
    }

    public ResultSet requestSQLSelect(String query) {
        ResultSet rst = null;
        try {
            Statement st = this.connexion.getCon().createStatement();
            rst = st.executeQuery(query);            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return rst;
    }
    
    public boolean requestSQLUpdate(String query) {
        boolean b;
        try {
            Statement st = this.connexion.getCon().createStatement();
            st.executeUpdate(query); 
            b=true;
        } catch (Exception ex) {
            ex.printStackTrace();
            b=false;
        }

        return b;
    }


    public ConnectionDatabase getConnexion() {
        return connexion;
    }

}

