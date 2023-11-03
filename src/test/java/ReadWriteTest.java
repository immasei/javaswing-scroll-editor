import org.junit.Before;
import org.junit.Test;

import static Logic.ReadWrite.*;
import static org.junit.jupiter.api.Assertions.*;

public class ReadWriteTest {

    @Before
    public void setup() {
        setupUsers();
        setupScrolls("scrollDB.txt");
        setupScrolls("archive.txt");
    }
    @Test
    public void testScrollSetup() {
        assertFalse(scrolls==null);
    }

    @Test
    public void testUpdateScroll() {
        updateScrolls("scrollDB.txt");
        updateScrolls("archive.txt");
        checkReupScroll("a");
        assertNull(readScroll("o", "curr"));
        assertNull(findReupScroll("a", "abs"));
    }
}
