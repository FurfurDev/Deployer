package net.furfurmc.gradle.deployer.tests.deployers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.analyzers.GitHubAnalyzer;
import net.furfurmc.gradle.deployer.deployers.GitHubDeployer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class GitHubDeployerTest
{
    @TempDir
    File projectDir;

    private File getCacheDirectory()
    {
        return FilesTestUtil.getCacheDirectory(projectDir);
    }

    private GitHubAnalyzer analyzer = new GitHubAnalyzer();
    private GitHubDeployer deployer;

    @BeforeEach
    void setup() throws IOException
    {
        deployer = new GitHubDeployer(getCacheDirectory());

        getCacheDirectory().mkdir();
    }
    
    @Test
    void deployTest()
    {
        var userdata = Config.inMemory();
        userdata.set(GitHubAnalyzer.OWNER_PATH, "MilkBowl");
        userdata.set(GitHubAnalyzer.REPO_PATH,  "Vault");
        userdata.set(GitHubAnalyzer.TAG_PATH,   "1.7.3");
        userdata.set(GitHubAnalyzer.ASSET_PATH, "Vault.jar");

        var mineConfig = analyzer.analyze(projectDir, userdata);
        
        mineConfig.name        = "Test";
        mineConfig.fileName    = FilesTestUtil.TEST_FILE_NAME;
        mineConfig.destination = projectDir;

        deployer.deploy(mineConfig);

        assertTrue(FilesTestUtil.getTestFile(projectDir).exists());
    }
}
