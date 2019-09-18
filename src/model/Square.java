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
}
