package net.furfurmc.gradle.deployer.tests.deployers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.analyzers.ModrinthAnalyzer;
import net.furfurmc.gradle.deployer.deployers.ModrinthDeployer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class ModrinthDeployerTest
{
    @TempDir
    File projectDir;

    private File getCacheDirectory()
    {
        return FilesTestUtil.getCacheDirectory(projectDir);
    }

    private ModrinthAnalyzer analyzer = new ModrinthAnalyzer();
    private ModrinthDeployer deployer;

    @BeforeEach
    void setup() throws IOException
    {
        deployer = new ModrinthDeployer(getCacheDirectory());

        getCacheDirectory().mkdir();
    }
    
    @Test
    void deployTest()
    {
        var userdata = Config.inMemory();
        userdata.set(ModrinthAnalyzer.VERSION_ID_PATH,   "ECkM3yd0");
        userdata.set(ModrinthAnalyzer.VERSION_FILE_PATH, "mclogs-fabric-26.1-3.2.0.jar");

        var mineConfig = analyzer.analyze(projectDir, userdata);
        
        mineConfig.name        = "Test";
        mineConfig.fileName    = FilesTestUtil.TEST_FILE_NAME;
        mineConfig.destination = projectDir;

        deployer.deploy(mineConfig);

        assertTrue(FilesTestUtil.getTestFile(projectDir).exists());
    }
}
