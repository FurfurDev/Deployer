package net.furfurmc.gradle.deployer.tests.networks;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import net.furfurmc.gradle.deployer.networks.CurseforgeWebClient;

public class CurseforgeWebClientTest
{
    static private CurseforgeWebClient curseforgeWebClient = new CurseforgeWebClient();

    @TempDir
    File projectDir;

    private File getTestJar()
    {
        return new File(projectDir, "test.jar");
    }

    @Test
    void getRestTest() throws URISyntaxException, IOException
    {
        final var url  = new URI("https://api.curseforge.com/v1/mods/238222");
        var rest       = curseforgeWebClient.getRest(url);
        assertNotNull(rest);
    }

    @Test
    void getModFileTest() throws URISyntaxException, IOException
    {
        var rest = curseforgeWebClient.getModFile("238222", "7391682");
        assertNotNull(rest);
    }

    @Test
    void getModFileHashTest() throws URISyntaxException, IOException
    {
        var hash = curseforgeWebClient.getModFileHash("238222", "7391682");
        assertNotNull(hash);
    }

    @Test
    void getModFileDownloadUrlTest() throws URISyntaxException, IOException
    {
        var rest = curseforgeWebClient.getModFileDownloadUrl("238222", "7391682");
        assertNotNull(rest);
    }
    
    @Test
    void downloadModFileTest() throws URISyntaxException, IOException
    {
        var response = curseforgeWebClient.downloadModFile("238222", "7391682");
        try (var stream = response.getContentAsStream())
        {
            Files.copy(stream, getTestJar().toPath());
            assertTrue(getTestJar().exists());
        }
    }
}
