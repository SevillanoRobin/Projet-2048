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
}
