package model;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Unit test class for the the {@link Square class}.
 */
public class SquareUT {
    /** Instance used for the unit tests. */
    private Square square;

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
    @Ignore("The test is currently unavailable: the square values cannot be changed yet. ")
    @Test
    public void equals_alteredCopy() {
        Square _square;

        this.initializeSquareTo2();
        _square = this.square;
        assertEquals( "The instance is considered as different from itself.", this.square, _square );

        this.initializeSquareTo4();
        _square = this.square;
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
                         this.square,
                         _square );

        this.initializeSquareTo4();
        _square = new Square( 2 );
        assertNotEquals( "The instance is considered as the same as a square of different value.",
                         this.square,
                         _square );
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
}
