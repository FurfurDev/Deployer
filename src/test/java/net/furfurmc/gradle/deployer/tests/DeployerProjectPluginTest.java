package net.furfurmc.gradle.deployer.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import net.furfurmc.gradle.deployer.DeployerProjectPlugin;
import net.furfurmc.gradle.deployer.analyzers.InstallAnalyzer;
import net.furfurmc.gradle.deployer.common.NightConfigUtil;
import net.furfurmc.gradle.deployer.tasks.DeployTask;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;
import net.furfurmc.gradle.deployer.tests.tools.NightConfigTestUtil;

public class DeployerProjectPluginTest
{
    @TempDir
    File projectDir;

    private File getWorkDirectory()
    {
        return FilesTestUtil.getWorkDirectory(projectDir);
    }

    private File getOriginFile()
    {
        return FilesTestUtil.getYamlConfigFile(getWorkDirectory());
    }

    private File getIndexDirectory()
    {
        return FilesTestUtil.getIndexDirectory(getWorkDirectory());
    }

    private File getIndexConfigFile()
    {
        return FilesTestUtil.getTomlConfigFile(getIndexDirectory());
    }

    private File getDeployDirectory()
    {
        return FilesTestUtil.getDeployDirectory(projectDir);
    }

    private File getDeployFile()
    {
        return FilesTestUtil.getTestFile(getDeployDirectory());
    }

    @BeforeEach
    void setup() throws IOException, NoSuchAlgorithmException
    {
        getWorkDirectory().mkdir();
        getWorkDirectory().mkdir();
        getIndexDirectory().mkdir();
        getDeployDirectory().mkdir();

        var config = NightConfigTestUtil.createExampleConfig();

        NightConfigUtil.setName(config, "Test");
        NightConfigUtil.setFileName(config, FilesTestUtil.TEST_FILE_NAME);
        NightConfigUtil.setMethod(config, InstallAnalyzer.ANALYZER_METHOD);
        
        var userdata = NightConfigUtil.getUserdata(config);
        userdata.set(InstallAnalyzer.ORIGIN_PATH, FilesTestUtil.YAML_CONFIG_FILE_NAME);

        NightConfigTestUtil.createTomlConfigFile(getIndexConfigFile(), config);

        FilesTestUtil.writeString(getOriginFile(), "data: \"string\"");
    }
    
    @Test
    void projectRegisterPluginTest()
    {
        var project = ProjectBuilder.builder().withProjectDir(projectDir).build();
        project.getPlugins().apply("net.furfurmc.gradle.deployer");
        assertNotNull(project.getPlugins().getPlugin(DeployerProjectPlugin.class));
    }

    @Test
    void deployTest() throws InterruptedException
    {
        var project = ProjectBuilder.builder().withProjectDir(projectDir).build();
        project.getPlugins().apply("net.furfurmc.gradle.deployer");
        var deployTask = (DeployTask) project.getTasks().findByName("deploy");
        deployTask.deploy();
        assertTrue(getDeployFile().exists());
    }
}
