package diskbiggest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * User: Oleksiy Pylypenko
 * At: 2/1/13  11:05 AM
 */
public class DiskBiggestTest {

    private File placeToMesure;

    @Before
    public void setUp() throws Exception {
        placeToMesure = File.createTempFile("diskbiggesttesting", null).getAbsoluteFile();
        placeToMesure.delete();
        placeToMesure.mkdir();
        writeFile("file1", placeToMesure, "abc1");
        writeFile("file2", placeToMesure, "abcdef");
        writeFile("file3", placeToMesure, "abcghi");
        writeFile("file4", placeToMesure, "abcxyz");
    }

    @Test
    public void testIterate() throws Exception {
        DiskBiggest diskBiggest = new DiskBiggest(30);
        diskBiggest.iterate(placeToMesure);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream str = new PrintStream(out);
        PrintStream oldOut = System.out;
        System.setOut(str);
        diskBiggest.output();
        str.flush();
        System.setOut(oldOut);
        String res = new String(out.toByteArray());
        assertEquals("22B " + placeToMesure.getPath() + "\n", res);
    }



    private void writeFile(String filename,
                           File dir,
                           String text) {
        try {
            FileWriter writer = new FileWriter(new File(dir, filename));
            writer.write(text);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void tearDown() throws Exception {
        for (File file : placeToMesure.listFiles()) {
            file.delete();
        }
        placeToMesure.delete();
    }
}
