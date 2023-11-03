import Logic.ReadWrite;
import Logic.Scroll;
import Logic.Signup;
import Logic.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;

import static Logic.ReadWrite.*;
import static org.junit.jupiter.api.Assertions.*;

public class ScrollTest {
    private User owner;
    private Scroll scroll;
    @Before
    public void setup() {
        setupUsers();
        setupScrolls("scrollDB.txt");
        owner = new User();
        scroll = new Scroll(owner, "name", "id", "date", 1, 1);
    }

    @Test
    public void testReadAndWriteBinaryScroll() {
        // Define a test binary scroll file name and content
        String testScrollFileName = "testScroll.bin";
        String testScrollContent = "test scroll content.";

        // Write the test binary scroll content to a file
        ReadWrite.writeScroll(testScrollFileName, testScrollContent);

        // Read the content from the binary scroll file
//        String readScrollContent = String.join("\n", ReadWrite.readScroll(testScrollFileName));

//        assertEquals(testScrollContent, readScrollContent);

        // Clean up: delete the test binary scroll file
        boolean fileDeleted = new File("src/main/resources/scroll/" + testScrollFileName).delete();
        assertTrue(fileDeleted, "Test file should no longer exist :(");
    }

    @Test
    public void testScrollExist() {
        assertNotNull(scroll);
    }

    @Test
    public void testScrollDownloadIncrement() {
        int downloadCountBefore = scroll.getDownloadCount();
        assertEquals(downloadCountBefore, 1);

        scroll.downloaded();
        assertEquals(scroll.getDownloadCount(), downloadCountBefore+1);
    }

    @Test
    public void testScrollGetter() {
        assertEquals(owner, scroll.getOwner());
        assertEquals("name", scroll.getScrollName());
        assertEquals("id", scroll.getScrollID());
        assertEquals(1, scroll.getUploadCount());
        assertEquals("date", scroll.getDate());
    }

    @Test
    public void testScrollSetter() {
        assertEquals("name", scroll.getScrollName());
        scroll.setScrollName("newName");
        assertEquals("newName", scroll.getScrollName());
        assertEquals("id", scroll.getScrollID());
        scroll.setScrollID("newID");
        assertEquals("newID", scroll.getScrollID());
    }

    @Test
    public void testFindScroll() {
        Scroll s;
        if (scrolls.size() > 0) {
            s = scrolls.get(0);
            assertNotNull(findScroll(s.getScrollName()));
        }
        scrolls.clear();
        assertNull(findScroll(null));
    }

    @Test
    public void testUniqueScrollNameAndID() {
        String id = "";
        //we prevent blank input as scroll id
        //so while this case won't happen
        //it's sure to return true since there no file name ''
        assertTrue(checkUniqueScrollID(id));
        String name = "-1";
        //invalid scroll name
        assertTrue(checkUniqueScrollName(name));
    }
}