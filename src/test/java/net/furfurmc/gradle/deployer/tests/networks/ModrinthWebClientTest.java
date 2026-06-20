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

import net.furfurmc.gradle.deployer.networks.ModrinthWebClient;

public class ModrinthWebClientTest
{
    static private ModrinthWebClient modrinthWebClient = new ModrinthWebClient();

    @TempDir
    File projectDir;

    private File getTestJar()
    {
        return new File(projectDir, "test.jar");
    }

    @Test
    void getRestTest() throws URISyntaxException, IOException
    {
        final var url  = new URI("https://api.modrinth.com/v2/project/fabric-api");
        var rest       = modrinthWebClient.getRest(url);
        assertNotNull(rest);
    }

    @Test
    void getVersionTest() throws IOException, URISyntaxException
    {
        var version = modrinthWebClient.getVersion("3kB7XVBv");
        assertNotNull(version);
    }

    @Test
    void getVersionFileTest() throws IOException, URISyntaxException
    {
        var file = modrinthWebClient.getVersionFile("3kB7XVBv", "fabric-api-0.152.1+26.2.jar");
        assertNotNull(file);
    }

    @Test
    void getVersionFileHashTest() throws IOException, URISyntaxException
    {
        var hash = modrinthWebClient.getVersionFileHash("3kB7XVBv", "fabric-api-0.152.1+26.2.jar");
        assertNotNull(hash);
    }

    @Test
    void getVersionFileDownloadUrlTest() throws IOException, URISyntaxException
    {
        var versionFileDownloadUrl = modrinthWebClient.getVersionFileDownloadUrl("3kB7XVBv", "fabric-api-0.152.1+26.2.jar");
        assertNotNull(versionFileDownloadUrl);
    }
    
    @Test
    void downloadVersionFileTest() throws URISyntaxException, IOException
    {
        var response = modrinthWebClient.downloadVersionFile("3kB7XVBv", "fabric-api-0.152.1+26.2.jar");
        try (var stream = response.getContentAsStream())
        {
            Files.copy(stream, getTestJar().toPath());
            assertTrue(getTestJar().exists());
        }
    }
}
