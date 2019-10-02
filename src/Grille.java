import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Grille implements Parametres {
    private int valeurMax;

    private HashSet<Case> grille;

    public Grille() {
        grille = new HashSet<>( TAILLE * 2 );
    }

    public int getValeurMax() {
        return this.valeurMax;
    }

    public HashSet<Case> getGrille() {
        return this.grille;
    }

    @Override
    public String toString() {
        int[][] tableau = new int[TAILLE][TAILLE];

        for ( Case c : this.grille ) {
            tableau[c.getY()][c.getX()] = c.getValue();  // Pris de la correction du TP 2048 de L2.
        }
        StringBuilder res = new StringBuilder();
        for ( int i = 0; i < tableau.length; i++ ) {
            res.append( Arrays.toString( tableau[i] ) ); // Pris de la correction du TP 2048 de L2.

            if ( i != tableau.length - 1 ) {
                res.append( "\n" );
            }
        }

        return res.toString();
    }

    private void victory() {
        System.out.println( "Congratulation! You won.\nScore: " + this.valeurMax );

        System.exit( 0 );
    }

    private void gameOver() {
        System.out.println( "Game over. The grid is full.\nScore: " + this.valeurMax );

        System.exit( 0 );
    }

    boolean nouvelleCase() {
        if ( this.grille.size() >= Math.pow( Parametres.TAILLE, 2 ) ) {
            return false;
        }

        Case cell;
        {
            Random random = new Random();

            int x;
            int y;
            int valeur = (int) random.nextDouble() * 2 + 2;

            do {
                x = (int) random.nextDouble() * Parametres.TAILLE;
                y = (int) random.nextDouble() * Parametres.TAILLE;
                cell = new Case( x, y, valeur );
            } while ( !this.grille.add( cell ) );

            if ( this.valeurMax < valeur ) {
                this.valeurMax = valeur;
            }
        }

        cell.setGrille( this );
        return true;
    }

    public boolean partieFinie() {
        int size = this.grille.size();

        if ( size < Math.pow( Parametres.TAILLE, 2 ) ) {
            return false;
        }

        int directions[] = {
                Parametres.HAUT,
                Parametres.GAUCHE,
                Parametres.DROITE,
                Parametres.BAS
        };

        for ( Case _case : this.grille ) {
            for ( int _direction : directions ) {
                Case _voisin = _case.getVoisinDirect( _direction );
                if ( _voisin != null && _case.valeurEgale( _voisin ) ) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * multiplie la valeur de la case passée en paramètre par 2 et met
     * éventuellement à jour la variable valeurMax
     */
    private void fusion( Case _case ) {
        int newValue = _case.getValue() * 2;
        _case.setValue( newValue );

        if ( this.valeurMax < newValue ) {
            this.valeurMax = newValue;
        }
    }

    /**
     * retourne sous forme d’un tableau les 4 cases
     * les plus proches de la direction choisie.
     */
    public Case[] getCasesExtremites( int _direction ) {
        Case[][] tableau = new Case[TAILLE][TAILLE];

        for ( Case c : this.grille ) {
            tableau[c.getY()][c.getX()] = c;  // Pris de la correction du TP 2048 de L2.
        }

        Case[] res = new Case[Parametres.TAILLE];
        Method usedMethod;
        try {
            switch ( _direction ) {
                case Parametres.HAUT:
                    usedMethod = Grille.class.getDeclaredMethod( "getHighestCase", int.class, Case[][].class );
                    break;
                case Parametres.BAS:
                    usedMethod = Grille.class.getDeclaredMethod( "getLowestCase", int.class, Case[][].class );
                    break;
                case Parametres.GAUCHE:
                    usedMethod = Grille.class.getDeclaredMethod( "getLeftestCase", int.class, Case[][].class );
                    break;
                case Parametres.DROITE:
                    usedMethod = Grille.class.getDeclaredMethod( "getRightestCase", int.class, Case[][].class );
                    break;
                default:
                    return null;
            }
        } catch ( NoSuchMethodException _e ) {
            _e.printStackTrace();
            return null;
        }

        for ( int i = 0; i < 4; i++ ) {
            try {
                res[i] = (Case) usedMethod.invoke( this, i, tableau );
            } catch ( IllegalAccessException | InvocationTargetException _e ) {
                _e.printStackTrace();
            }
        }

        return res;
    }

    private Case getHighestCase( int _i, Case[][] _tableau ) {
        Case _cell = null;
        for ( int j = 0; j < 4 && _cell == null; j++ ) {
            _cell = _tableau[j][_i];
        }
        return _cell;
    }

    private Case getLowestCase( int _i, Case[][] _tableau ) {
        Case _cell = null;
        for ( int j = 3; j >= 0 && _cell == null; j-- ) {
            _cell = _tableau[j][_i];
        }
        return _cell;
    }

    private Case getLeftestCase( int _i, Case[][] _tableau ) {
        Case _cell = null;
        for ( int j = 0; j < 4 && _cell == null; j++ ) {
            _cell = _tableau[_i][j];
        }
        return _cell;
    }

    private Case getRightestCase( int _i, Case[][] _tableau ) {
        Case _cell = null;
        for ( int j = 3; j >= 0 && _cell == null; j-- ) {
            _cell = _tableau[_i][j];
        }
        return _cell;
    }
}
