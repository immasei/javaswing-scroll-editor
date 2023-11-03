package GUI.Application;

import Logic.Scroll;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static GUI.Application.ViewScrollPane.updateScrollTable;
import static Logic.ReadWrite.*;

public class ArchivePane {
    public static void archiveScrolls() {
        File scrollFolder = new File("src/main/resources/scroll");
        File archiveFolder = new File("src/main/resources/Archive");

        // Create the archive folder if it doesn't exist
        if (!archiveFolder.exists()) {
            archiveFolder.mkdirs();
        }

        // Archive the files
        for (File file : scrollFolder.listFiles()) {
            if (file.isFile()) {
                Path sourcePath = Paths.get(file.getAbsolutePath());
                Path targetPath = Paths.get(archiveFolder.getAbsolutePath() + File.separator + file.getName());

                try {
                    //update table view of scroll information
                    Scroll s = findScroll(file.getName());
                    if (scrolls.contains(s)) {
                        scrolls.remove(s);
                    }
                    //save in history scroll dir
                    Scroll oldScroll = findOldScroll(file.getName());
                    if (oldScroll != null) {
                        archives.remove(oldScroll);
                    }
                    if (s!=null)
                        archives.add(s);

                    Files.move(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
                    //update txt
                    updateScrolls("scrollDB.txt");
                    updateScrolls("archive.txt");
                    updateScrollTable();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
