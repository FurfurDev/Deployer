package net.furfurmc.gradle.deployer.tests.analyzers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.analyzers.GitHubAnalyzer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class GitHubAnalyzerTest
{
    private static final String OWNER_TEXT = "Owner-Text";
    private static final String REPO_TEXT  = "Repo-Text";
    private static final String TAG_TEXT   = "Tag-Text";
    private static final String ASSET_TEXT = "Asset-Text";

    private GitHubAnalyzer analyzer = new GitHubAnalyzer();

    @Test
    void analyzeTest()
    {
        var userdata = Config.inMemory();
        userdata.set(GitHubAnalyzer.OWNER_PATH, OWNER_TEXT);
        userdata.set(GitHubAnalyzer.REPO_PATH,  REPO_TEXT);
        userdata.set(GitHubAnalyzer.TAG_PATH,   TAG_TEXT);
        userdata.set(GitHubAnalyzer.ASSET_PATH, ASSET_TEXT);
        var entity = analyzer.analyze(FilesTestUtil.getCurrentDirectory(), userdata);
        assertNotNull(entity);
    }
}
