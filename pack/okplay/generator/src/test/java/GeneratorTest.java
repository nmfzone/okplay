import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.atteo.evo.inflector.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class GeneratorTest
{

    public static final Logger LOG = Logger.getLogger(GeneratorTest.class.getName());

    @Rule
    public TemporaryFolder tempFolder = new TemporaryFolder();

    @Test
    public void testWrite() throws IOException {
        // final File tempFile = tempFolder.newFile("tempFile.txt");
        //
        // FileUtils.writeStringToFile(tempFile, "hello world");
        //
        // final String content = FileUtils.readFileToString(tempFile);
        //
        // Assert.assertEquals("hello world", content);

        Generator g = new Generator();
        //Generator.main(new String[] {"aaaa"});

        //LOG.info("Mantap Bro");
        LOG.info(English.plural("class"));

        assertEquals("users", English.plural("user"));
        assertEquals("Yunika", "Yunika");
    }
}
