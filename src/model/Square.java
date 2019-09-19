package model;

/**
 * Square class.
 * <p>
 * Represent the squares of the 2048 game, valued between 2 and 2048+.
 */
public class Square {
    /** The current value of the square. */
    private int value;

    /**
     * Constructor.
     * <p>
     * Initialize the square and its value based on the parameter.
     * It should be called upon by a factory class or generating method in the same package.
     *
     * @param _value Initial value of the square (must be 2 or 4).
     *
     * @throws IllegalArgumentException if the parameter doesn't respect the value constraint (being 2 or 4).
     */
    Square( int _value ) throws IllegalArgumentException {
        if ( _value < 2 ) {
            throw new IllegalArgumentException( "La valeur initiale d'un carré a été initialisée à moins de 2." );
        } else if ( _value > 4 ) {
            throw new IllegalArgumentException( "La valeur initiale d'un carré a été initialisée à plus de 4." );
        } else if ( _value % 2 != 0 ) {
            throw new IllegalArgumentException( "La valeur initiale d'un carré a été initialisée à 3 (invalide)." );
        }

        this.value = _value;
    }

    /**
     * The square's value (<code>this.value</code>) public read accessor.
     *
     * @return the value of the attribute.
     */
    public int getValue() {
        return value;
    }

    /**
     * Override the {@link Object#equals(Object)} method for the Square instances.
     * <p>
     * If the parameter is a Square (or Square-inheriting) instance, then the values of the two squares are compared.
     *
     * @param _obj The object to be compared with the current square object.
     *
     * @return <code>true</code> if the two objects are considered as the same, or as being squares of same value.
     */
    @Override
    public boolean equals( Object _obj ) {
        // Check if the two variables point toward the same instance.
        if ( super.equals( _obj ) ) {
            return true;
        }

        // Check that the parameter is a Square instance.
        if ( !( _obj instanceof Square ) ) {
            return false;
        }

        // Compare the value between the two squares' values.
        Square _square = (Square) _obj;
        return this.getValue() == _square.getValue();
    }

    /**
     * Identify if two squares (the current and the parameter) can be fused.
     * <p>
     * The two squares must not be the same instance (including copies), and must have the same value.
     * <p>
     * TODO: Change this method to consider position if deemed necessary, possible and optimal.
     *
     * @param _square The other square in the potential fusion.
     *
     * @return <code>true</code> if the squares are deemed merge-able.
     */
    private boolean canBeFused( Square _square ) {
        if ( super.equals( _square ) ) {
            System.err.println( "The to-be-fused squares are considered the same one." );
        }
        return !super.equals( _square ) && this.equals( _square );
    }

    /**
     * Fuse two squares together as one.
     * <p>
     * The square passed in argument will have its value nullified if the fusion proceeds properly.
     * <p>
     * TODO: Find a way to delete the nullified squares, if possible.
     *
     * @param _square the square to be fused with the current one.
     */
    public void fuseTwoSquares( Square _square ) {
        if ( !this.canBeFused( _square ) ) {
            return;
        }

        this.value += _square.value;
        _square.value = 0;
    }

    /**
     * Attempt to fuse two squares (arguments).
     * <p>
     * The two squares must not be the same instance (including copies), and must have the same value.
     *
     * @param _square1 The first square in the potential fusion.
     * @param _square2 The second square in the potential fusion.
     *
     * @see Square#fuseTwoSquares(Square)
     */
    public static void fuseTwoSquares( Square _square1, Square _square2 ) {
        _square1.fuseTwoSquares( _square2 );
    }
}
