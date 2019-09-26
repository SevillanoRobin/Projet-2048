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
}
