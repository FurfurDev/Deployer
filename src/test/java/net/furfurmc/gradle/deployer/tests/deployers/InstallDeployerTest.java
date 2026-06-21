package net.furfurmc.gradle.deployer.tests.deployers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.analyzers.InstallAnalyzer;
import net.furfurmc.gradle.deployer.deployers.InstallDeployer;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class InstallDeployerTest
{
    @TempDir
    File projectDir;

    private File getCacheDirectory()
    {
        return FilesTestUtil.getCacheDirectory(projectDir);
    }

    private InstallAnalyzer analyzer = new InstallAnalyzer();
    private InstallDeployer deployer;

    @BeforeEach
    void setup() throws IOException
    {
        deployer = new InstallDeployer(getCacheDirectory());

        getCacheDirectory().mkdir();
        FilesTestUtil.writeString(FilesTestUtil.getYamlConfigFile(projectDir), "value: \"string\"");
    }
    
    @Test
    void deployTest()
    {
        var userdata = Config.inMemory();
        userdata.set(InstallAnalyzer.ORIGIN_PATH, FilesTestUtil.YAML_CONFIG_FILE_NAME);

        var mineConfig = analyzer.analyze(projectDir, userdata);
        
        mineConfig.name        = "Test";
        mineConfig.fileName    = FilesTestUtil.TEST_FILE_NAME;
        mineConfig.destination = projectDir;

        deployer.deploy(mineConfig);

        assertTrue(FilesTestUtil.getTestFile(projectDir).exists());
    }
}
