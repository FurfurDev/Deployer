package net.furfurmc.gradle.deployer.tests.deployers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.analyzers.WebAnalyzer;
import net.furfurmc.gradle.deployer.deployers.WebDeployer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class UrlDeployerTest
{
    @TempDir
    File projectDir;

    private File getCacheDirectory()
    {
        return FilesTestUtil.getCacheDirectory(projectDir);
    }

    private WebAnalyzer analyzer = new WebAnalyzer();
    private WebDeployer deployer;

    @BeforeEach
    void setup() throws IOException
    {
        deployer = new WebDeployer(getCacheDirectory());

        getCacheDirectory().mkdir();
    }
    
    @Test
    void deployTest()
    {
        var userdata = Config.inMemory();
        userdata.set(WebAnalyzer.URL_PATH, "https://github.com/FurfurDev");

        var mineConfig = analyzer.analyze(projectDir, userdata);
        
        mineConfig.name        = "Test";
        mineConfig.fileName    = FilesTestUtil.TEST_FILE_NAME;
        mineConfig.destination = projectDir;

        deployer.deploy(mineConfig);

        assertTrue(FilesTestUtil.getTestFile(projectDir).exists());
    }
}
