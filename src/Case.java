import java.util.Objects;

public class Case implements Parametres {
    private int x;
    private int y;
    private int value;

    private Grille grille;

    public Case( int _x, int _y, int _value ) {
        if ( _x < 0 || _x >= Parametres.TAILLE ) {
            throw new IllegalArgumentException( "Case: x has a wrong value." );
        }
        if ( _y < 0 || _y >= Parametres.TAILLE ) {
            throw new IllegalArgumentException( "Case: y has a wrong value." );
        }

        this.x = _x;
        this.y = _y;
        this.value = _value;
    }

    public Case( int[] _coors, int _value ) {
        this( _coors[0], _coors[1], _value );
    }

    public int getX() {
        return this.x;
    }

    public void setX( int _x ) {
        this.x = _x;
    }

    public int getY() {
        return this.y;
    }

    public void setY( int _y ) {
        this.y = _y;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue( int _value ) {
        this.value = _value;
    }

    public void setGrille( Grille _grille ) {
        this.grille = _grille;
    }

    @Override
    public String toString() {
        return "Case(" + x + "," + y + "," + value + ")";
    }

    @Override
    public int hashCode() {
        return Objects.hash( getX(), getY(), grille );
    }

    @Override
    public boolean equals( Object _o ) {
        if ( this == _o ) return true;
        if ( !( _o instanceof Case ) ) return false;
        Case aCase = (Case) _o;
        return getX() == aCase.getX() &&
               getY() == aCase.getY() &&
               grille.equals( aCase.grille );
    }

    public boolean valeurEgale( Case _case ) {
        return this.value == _case.value;
    }

    public Case getVoisinDirect( int _direction ) {
        Case     cell    = null;
        Case[][] tableau = new Case[Parametres.TAILLE][Parametres.TAILLE];

        for ( Case c : this.grille.getGrille() ) {
            if ( !this.equals( c ) )
                tableau[c.getX()][c.getY()] = c;  // Pris de la correction du TP 2048 de L2.
        }

        switch ( _direction ) {
            case Parametres.HAUT:
                for ( int j = this.y; j >= 0 && cell == null; j-- ) {
                    cell = tableau[this.x][j];
                }
                break;
            case Parametres.BAS:
                for ( int j = this.y; j < Parametres.TAILLE && cell == null; j++ ) {
                    cell = tableau[this.x][j];
                }
                break;
            case Parametres.GAUCHE:
                for ( int i = this.x; i >= 0 && cell == null; i-- ) {
                    cell = tableau[i][this.y];
                }
                break;
            case Parametres.DROITE:
                for ( int i = this.x; i < Parametres.TAILLE && cell == null; i++ ) {
                    cell = tableau[i][this.y];
                }
                break;
        }

        return cell;
    }
}
