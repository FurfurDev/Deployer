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
import net.furfurmc.gradle.deployer.networks.GitHubWebClient;

public class GitHubWebClientTest
{
    static private GitHubWebClient gitHubWebClient = new GitHubWebClient();

    @TempDir
    File projectDir;

    private File getTestJar()
    {
        return new File(projectDir, "test.jar");
    }

    @Test
    void getRestTest() throws URISyntaxException, IOException
    {
        final var url  = new URI("https://api.github.com/repos/HtmlUnit/htmlunit/releases/tags/5.1.0");
        var rest       = gitHubWebClient.getRest(url);
        assertNotNull(rest);
    }

    @Test
    void getReleaseByTagTest() throws IOException, URISyntaxException
    {
        var release = gitHubWebClient.getReleaseByTag("HtmlUnit", "htmlunit", "5.1.0");
        assertNotNull(release);
    }

    @Test
    void getReleaseAssetByTagTest() throws IOException, URISyntaxException
    {
        var asset = gitHubWebClient.getReleaseAssetByTag("HtmlUnit", "htmlunit", "5.1.0", "htmlunit-5.1.0.jar");
        assertNotNull(asset);
    }

    @Test
    void getAssetHashTest() throws IOException, URISyntaxException
    {
        var hash = gitHubWebClient.getAssetHash("HtmlUnit", "htmlunit", "5.1.0", "htmlunit-5.1.0.jar");
        assertNotNull(hash);
    }

    @Test
    void getAssetDownloadUrlTest() throws IOException, URISyntaxException
    {
        var assetDownloadUrl = gitHubWebClient.getAssetDownloadUrl("HtmlUnit", "htmlunit", "5.1.0", "htmlunit-5.1.0.jar");
        assertNotNull(assetDownloadUrl);
    }
    
    @Test
    void downloadModFileTest() throws URISyntaxException, IOException
    {
        var response = gitHubWebClient.downloadAsset("HtmlUnit", "htmlunit", "5.1.0", "htmlunit-5.1.0.jar");
        try (var stream = response.getContentAsStream())
        {
            Files.copy(stream, getTestJar().toPath());
            assertTrue(getTestJar().exists());
        }
    }
}
