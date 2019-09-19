package model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test class for the the {@link Square class}.
 */
public class SquareUT {
    /**
     * Straw Square extension giving access to Square's private methods.
     */
    private static class SquareExt extends Square {

        SquareExt( int _value ) throws IllegalArgumentException {
            super( _value );
        }

        /**
         * [Altered copy of {@link Square's method}]
         */
        boolean canBeFused( SquareExt _square ) {
            if ( this == _square ) {
                System.err.println( "The to-be-fused squares are considered the same one." );
            }
            return !( this == _square ) && this.equals( _square );
        }
    }

    /** Instances used for the unit tests: normal class. */
    private Square    square;
    /** Instances used for the unit tests: able to use 'canBeFused()'. */
    private SquareExt squareExt;

    // === Initializers === //

    /**
     * Initialize the square attribute with a value of 2.
     */
    private void initializeSquareTo2() {
        this.square = new Square( 2 );
    }

    /**
     * Initialize the square attribute with a value of 4.
     */
    private void initializeSquareTo4() {
        this.square = new Square( 4 );
    }

    /**
     * Initialize the square extended attribute with a value of 2.
     */
    private void initializeSquareExtTo2() {
        this.squareExt = new SquareExt( 2 );
    }

    /**
     * Initialize the square extended attribute with a value of 4.
     */
    private void initializeSquareExtTo4() {
        this.squareExt = new SquareExt( 4 );
    }

    // === Tests on the constructor. === //

    /**
     * Case in which the constructor should be properly executed (e.g. no exception thrown).
     */
    @Test
    public void constructor_properExecution() {
        square = new Square( 2 );
    }

    /**
     * Case in which the constructor should be throw an exception due to the parameter constraint.
     * <p>
     * Here, an even number smaller than 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_lessThan2_evenNumber() {
        square = new Square( 0 );
    }

    /**
     * Case in which the constructor should be throw an exception due to the parameter constraint.
     * <p>
     * Here, an odd number smaller than 2.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_lessThan2_oddNumber() {
        square = new Square( 1 );
    }

    /**
     * Case in which the constructor should be throw an exception due to the parameter constraint.
     * <p>
     * Here, a negative number.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_lessThan2_negativeNumber() {
        square = new Square( -1 );
    }

    /**
     * Case in which the constructor should be throw an exception due to the parameter constraint.
     * <p>
     * Here, an even number greater than 4.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_MoreThan4_evenNumber() {
        square = new Square( 6 );
    }

    /**
     * Case in which the constructor should be throw an exception due to the parameter constraint.
     * <p>
     * Here, an odd number greater than 4.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_MoreThan4_oddNumber() {
        square = new Square( 5 );
    }

    /**
     * Case in which the constructor should be throw an exception due to the parameter constraint.
     * <p>
     * Here, a number equal to 3.
     */
    @Test(expected = IllegalArgumentException.class)
    public void constructor_EqualTo3() {
        square = new Square( 3 );
    }

    // === Tests on Square's equals() method. === //

    /**
     * Case in which the instance is compared to itself through the same variable.
     */
    @Test
    public void equals_sameInstance_calledOnItself() {
        this.initializeSquareTo2();
        assertEquals( "The instance is considered as different from itself.", this.square, this.square );

        this.initializeSquareTo4();
        assertEquals( "The instance is considered as different from itself.", this.square, this.square );
    }

    /**
     * Case in which the instance is compared to an unchanged copy of itself.
     */
    @Test
    public void equals_sameInstance_usingCopy() {
        Square _square;

        this.initializeSquareTo2();
        _square = this.square;
        assertEquals( "The instance is considered as different from itself.", this.square, _square );

        this.initializeSquareTo4();
        _square = this.square;
        assertEquals( "The instance is considered as different from itself.", this.square, _square );
    }

    /**
     * Case in which the instance is compared to an altered copy of itself.
     */
    @Test
    public void equals_alteredCopy() {
        Square _square;

        this.initializeSquareTo2();
        _square = this.square;
        _square.fuseTwoSquares( new Square( 2 ) );
        this.square.fuseTwoSquares( new Square( 2 ) );
        assertEquals( "The instance is considered as different from itself.", this.square, _square );

        this.initializeSquareTo4();
        _square = this.square;
        _square.fuseTwoSquares( new Square( 4 ) );
        this.square.fuseTwoSquares( new Square( 4 ) );
        assertEquals( "The instance is considered as different from itself.", this.square, _square );
    }

    /**
     * Case in which the instance is compared to a square instance of same value.
     */
    @Test
    public void equals_differentSquares_sameValue() {
        Square _square;

        this.initializeSquareTo2();
        _square = new Square( 2 );
        assertEquals( "The instance is considered as different from a square of same value.", this.square, _square );

        this.initializeSquareTo4();
        _square = new Square( 4 );
        assertEquals( "The instance is considered as different from a square of same value.", this.square, _square );
    }

    /**
     * Case in which the instance is compared to a square instance of different value.
     */
    @Test
    public void equals_differentSquares_differentValue() {
        Square _square;

        this.initializeSquareTo2();
        _square = new Square( 4 );
        assertNotEquals( "The instance is considered as the same as a square of different value.",
                         this.square, _square );

        this.initializeSquareTo4();
        _square = new Square( 2 );
        assertNotEquals( "The instance is considered as the same as a square of different value.",
                         this.square, _square );
    }

    /**
     * Case in which the instance is compared to an object instance unrelated to the {@link Square class} (here, {@link String}).
     */
    @Test
    public void equals_notASquare() {
        String __pseudoSquare;

        this.initializeSquareTo2();
        __pseudoSquare = "new Square( 2 )";
        assertNotEquals( "The instance is considered as the same as a non-square", this.square, __pseudoSquare );

        this.initializeSquareTo4();
        __pseudoSquare = "new Square( 4 )";
        assertNotEquals( "The instance is considered as the same as a non-square", this.square, __pseudoSquare );
    }

    // === Tests on the canBeFused() method. === //

    /**
     * Case in which it is an unique unaltered square accessed through an unique variable.
     */
    @Test
    public void canBeFused_sameInstance_sameVar() {
        this.initializeSquareExtTo2();
        assertFalse( "The instance is wrongfully considered to be fuse-able with itself.",
                     this.squareExt.canBeFused( this.squareExt ) );
    }

    /**
     * Case in which it is an unique unaltered square accessed through two distinct variable.
     */
    @Test
    public void canBeFused_sameInstance_differentVar() {
        this.initializeSquareExtTo2();
        SquareExt _square = this.squareExt;
        assertFalse( "The instance is wrongfully considered to be fuse-able with a copy of itself.",
                     this.squareExt.canBeFused( _square ) );
    }

    /**
     * Case in which it is an unique unaltered square and an altered copy of itself.
     */
    @Test
    public void canBeFused_sameInstance_alteredCopy() {
        this.initializeSquareExtTo2();
        SquareExt _square = this.squareExt;

        this.squareExt.fuseTwoSquares( new SquareExt( 2 ) );
        _square.fuseTwoSquares( new SquareExt( 2 ) );

        assertFalse( "The instance is wrongfully considered to be fuse-able with a copy of itself.",
                     _square.canBeFused( this.squareExt ) );
    }

    /**
     * Case in which it is two distinct squares with the same values.
     */
    @Test
    public void canBeFused_differentInstance_sameValue() {
        SquareExt _square;

        this.initializeSquareExtTo2();
        _square = new SquareExt( 2 );
        assertTrue( "The instance is wrongfully considered to not be fuse-able with a square of same value.",
                    this.squareExt.canBeFused( _square ) );

        this.initializeSquareExtTo4();
        _square = new SquareExt( 4 );
        assertTrue( "The instance is wrongfully considered to not be fuse-able with a square of same value.",
                    this.squareExt.canBeFused( _square ) );
    }

    /**
     * Case in which it is two distinct squares with different values.
     */
    @Test
    public void canBeFused_differentInstance_differentValue() {
        SquareExt _square;

        this.initializeSquareExtTo2();
        _square = new SquareExt( 4 );
        assertFalse( "The instance is wrongfully considered to be fuse-able with a square of different value.",
                     this.squareExt.canBeFused( _square ) );

        this.initializeSquareExtTo4();
        _square = new SquareExt( 2 );
        assertFalse( "The instance is wrongfully considered to be fuse-able with a square of different value.",
                     this.squareExt.canBeFused( _square ) );
    }

    // === Tests on the fuseTwoSquares() method. === //

    /**
     * Case in which we attempt to fuse an unique instance with itself through the same variable and, then, an unaltered copy.
     * <p>
     * Compacted since the <code>canBeFused()</code> method tests should cover those cases.
     */
    @Test
    public void fuseTwoSquares_sameInstance() {
        Square _square;

        // Prepare the square for the coming loop.
        this.initializeSquareTo2();

        for ( int i = 2; i <= 4; i += 2 ) {
            // Check if the square doesn't fuse with itself through the same variable.
            this.square.fuseTwoSquares( this.square );
            assertEquals( "", i, this.square.getValue() );

            // Check if the square doesn't fuse with itself through an unaltered copy.
            _square = this.square;
            this.square.fuseTwoSquares( _square );
            assertEquals( "The fused square's value has been abnormally modified.", i, this.square.getValue() );
            assertEquals( "The other square's value has been abnormally modified.", i, _square.getValue() );

            // Change the square's value for the next loop.
            this.initializeSquareTo4();
        }
    }

    /**
     * Case in which we attempt to fuse an unique instance with a different instance of same value.
     */
    @Test
    public void fuseTwoSquares_differentInstance_sameValue() {
        Square _square;

        // Prepare the square for the coming loop.
        this.initializeSquareTo2();

        for ( int i = 2; i <= 4; i += 2 ) {
            _square = new Square( i );

            this.square.fuseTwoSquares( _square );
            assertEquals( "The fused square's value hasn't been properly modified", i * 2, this.square.getValue() );
            assertEquals( "The other square's value hasn't been properly nullified.", 0, _square.getValue() );
        }
    }

    /**
     * Case in which we attempt to fuse an unique instance with a different instance of different value.
     */
    @Test
    public void fuseTwoSquares_differentInstance_differentValue() {
        Square _square;

        // Prepare the squares for the coming loop.
        this.initializeSquareTo2();
        _square = new Square( 4 );

        for ( int i = 2; i <= 4; i += 2 ) {

            this.square.fuseTwoSquares( _square );
            assertEquals( "The fused square's value has been abnormally modified.", i, this.square.getValue() );
            assertEquals( "The other square's value has been abnormally modified.", i * 2, _square.getValue() );

            // Prepare the squares for the next loop.
            this.initializeSquareTo4();
            _square.fuseTwoSquares( new Square( 4 ) );
        }
    }

    /**
     * Case in which we attempt to fuse an unique instance with an altered copy.
     */
    @Test
    public void fuseTwoSquares_alteredCopy() {
        // Create the copy.
        this.initializeSquareTo2();
        Square _square = this.square;

        // Alter the squares' values.
        this.square.fuseTwoSquares( new Square( 2 ) );
        _square.fuseTwoSquares( new Square( 2 ) );

        // Check if the squares properly fused.
        assertEquals( "The fused square's value hasn't been properly modified", 4, this.square.getValue() );
        assertEquals( "The other square's value hasn't been properly modified.", 4, _square.getValue() );

        // Check if the square fuse with the altered copy.
        _square = this.square;
        this.square.fuseTwoSquares( _square );
        assertEquals( "The fused square's value has been abnormally modified.", 4, this.square.getValue() );
        assertEquals( "The other square's value has been abnormally modified.", 4, _square.getValue() );
    }
}
