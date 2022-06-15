package UNO.Test;

import UNO.Control.Control;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ControlTest {

    @Test
    void nextPlayer(){
        Control control = new Control();
        assertEquals(-1, control.nextPlayer());
    }

}