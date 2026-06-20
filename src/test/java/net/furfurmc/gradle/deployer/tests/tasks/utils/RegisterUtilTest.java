package net.furfurmc.gradle.deployer.tests.tasks.utils;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.DeployerProjectPlugin;
import net.furfurmc.gradle.deployer.analyzers.InstallAnalyzer;
import net.furfurmc.gradle.deployer.common.DeployFolderLoader;
import net.furfurmc.gradle.deployer.common.NightConfigUtil;
import net.furfurmc.gradle.deployer.entities.CurseforgeEntity;
import net.furfurmc.gradle.deployer.entities.GitHubEntity;
import net.furfurmc.gradle.deployer.entities.InstallEntity;
import net.furfurmc.gradle.deployer.entities.MakefileEntity;
import net.furfurmc.gradle.deployer.entities.ModrinthEntity;
import net.furfurmc.gradle.deployer.entities.WebEntity;
import net.furfurmc.gradle.deployer.tasks.utils.RegisterUtil;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;
import net.furfurmc.gradle.deployer.tests.tools.NightConfigTestUtil;

public class RegisterUtilTest
{
    @TempDir
    File projectDir;

    private File getCacheDirectory()
    {
        return FilesTestUtil.getCacheDirectory(projectDir);
    }

    private File getWorkDirectory()
    {
        return FilesTestUtil.getWorkDirectory(projectDir);
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

    private DeployFolderLoader segmentLoader = new DeployFolderLoader();

    @BeforeEach
    void setup() throws IOException, NoSuchAlgorithmException
    {
        getWorkDirectory().mkdir();
        getIndexDirectory().mkdir();

        var config = NightConfigTestUtil.createExampleConfig();

        NightConfigUtil.setName(config, "Test");
        NightConfigUtil.setFileName(config, FilesTestUtil.TEST_FILE_NAME);
        NightConfigUtil.setMethod(config, InstallAnalyzer.ANALYZER_METHOD);
        
        var userdata = NightConfigUtil.getUserdata(config);
        userdata.set(InstallAnalyzer.ORIGIN_PATH, FilesTestUtil.YAML_CONFIG_FILE_NAME);
        userdata.set("twoo", FilesTestUtil.YAML_CONFIG_FILE_NAME);
        userdata.set("threa", FilesTestUtil.YAML_CONFIG_FILE_NAME);

        NightConfigTestUtil.createTomlConfigFile(getIndexConfigFile(), config);
    }

    @Test
    void registerDeployersTest()
    {
        var project = ProjectBuilder.builder().withProjectDir(projectDir).build();
        project.getPlugins().apply("net.furfurmc.gradle.deployer");
        
        var deployPlugin = project.getPlugins().getPlugin(DeployerProjectPlugin.class);
        RegisterUtil.registerDeployers(deployPlugin, getCacheDirectory());

        assertNotNull(deployPlugin.containsDeployer(InstallEntity.class));
        assertNotNull(deployPlugin.containsDeployer(MakefileEntity.class));
        assertNotNull(deployPlugin.containsDeployer(WebEntity.class));
        assertNotNull(deployPlugin.containsDeployer(CurseforgeEntity.class));
        assertNotNull(deployPlugin.containsDeployer(GitHubEntity.class));
        assertNotNull(deployPlugin.containsDeployer(ModrinthEntity.class));
    }

    @Test
    void registerEntitiesTest()
    {
        var project = ProjectBuilder.builder().withProjectDir(projectDir).build();
        project.getPlugins().apply("net.furfurmc.gradle.deployer");

        var segments = segmentLoader.loadDeployFolders(getWorkDirectory(), DeployerPlugin.DEFAULT_INDEX_NAME);
        
        var deployPlugin = project.getPlugins().getPlugin(DeployerProjectPlugin.class);
        RegisterUtil.registerEntities(deployPlugin, getWorkDirectory(), getDeployDirectory(), segments);

        assertTrue(deployPlugin.containsEntity("Test"));
        System.out.println(deployPlugin.getEntity("Test"));
    }
}
