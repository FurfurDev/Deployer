package net.furfurmc.gradle.deployer.tests.deployers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.electronwill.nightconfig.core.Config;

import net.furfurmc.gradle.deployer.analyzers.MakefileAnalyzer;
import net.furfurmc.gradle.deployer.deployers.MakefileDeployer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class MakefileDeployerTest
{
    @TempDir
    File projectDir;

    private File getCacheDirectory()
    {
        return FilesTestUtil.getCacheDirectory(projectDir);
    }

    private MakefileAnalyzer analyzer = new MakefileAnalyzer();
    private MakefileDeployer deployer;

    @BeforeEach
    void setup() throws IOException
    {
        deployer = new MakefileDeployer(getCacheDirectory());

        getCacheDirectory().mkdir();
    }
    
    @Test
    void deployTest()
    {
        var userdata = Config.inMemory();
        userdata.set(MakefileAnalyzer.CONTENT_PATH, "Content-Text");

        var mineConfig = analyzer.analyze(projectDir, userdata);
        
        mineConfig.name        = "Test";
        mineConfig.fileName    = FilesTestUtil.TEST_FILE_NAME;
        mineConfig.destination = projectDir;

        deployer.deploy(mineConfig);

        assertTrue(FilesTestUtil.getTestFile(projectDir).exists());
    }
}
