package net.furfurmc.gradle.deployer.tests.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import net.furfurmc.gradle.deployer.common.HashBuilder;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class HashBuilderTest
{
    private static final String EXAMPLE_STRING = "Example-String";
    private static final String EXAMPLE_FILE   = "example.txt";
    private static final String EXAMPLE_URL    = "https://example.com/page";
    
    @TempDir
    File projectDir;
    
    private File getExampleFile()
    {
        return new File(projectDir, EXAMPLE_FILE);
    }

    private HashBuilder hashBuilder;

    public HashBuilderTest() throws NoSuchAlgorithmException, URISyntaxException
    {
        hashBuilder = new HashBuilder();
    }

    @BeforeEach
    void setup() throws IOException, NoSuchAlgorithmException
    {
        FilesTestUtil.writeString(getExampleFile(), EXAMPLE_STRING);
    }

    @Test
    void makeStringHashTest()
    {
        var hash = hashBuilder.makeStringHash(EXAMPLE_STRING);
        assertNotNull(hash);
    }

    @Test
    void makeFileHashTest() throws FileNotFoundException, IOException
    {
        var hash = hashBuilder.makeFileHash(getExampleFile());
        assertNotNull(hash);
    }

    @Test
    void makeUrlHashTest() throws URISyntaxException
    {
        var hash = hashBuilder.makeUrlHash(new URI(EXAMPLE_URL));
        assertNotNull(hash);
    }
}
