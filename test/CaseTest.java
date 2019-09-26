import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CaseTest {
    private Case aCase;

    @BeforeEach
    void setUp() {
        aCase = new Case( 0, 1, 32 );
    }

    @Test
    void toString_Test() {
        assertEquals( "Case(0,1,32)", "" + aCase );
    }
}