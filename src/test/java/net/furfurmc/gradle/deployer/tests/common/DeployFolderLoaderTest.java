package net.furfurmc.gradle.deployer.tests.common;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.common.DeployFolderLoader;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;

public class DeployFolderLoaderTest
{
    @TempDir
    File projectDir;
    
    private File getWorkDirectory()
    {
        return FilesTestUtil.getWorkDirectory(projectDir);
    }
    
    private File getIndexDirectory()
    {
        return FilesTestUtil.getIndexDirectory(getWorkDirectory());
    }

    private File getJsonConfigFile()
    {
        return FilesTestUtil.getJsonConfigFile(getIndexDirectory());
    }

    private File getHoconConfigFile()
    {
        return FilesTestUtil.getJsonConfigFile(getIndexDirectory());
    }

    private File getTomlConfigFile()
    {
        return FilesTestUtil.getJsonConfigFile(getIndexDirectory());
    }

    private File getYamlConfigFile()
    {
        return FilesTestUtil.getYamlConfigFile(getIndexDirectory());
    }

    private DeployFolderLoader deployFolderLoader = new DeployFolderLoader();

    @BeforeEach
    void setup() throws IOException
    {
        getWorkDirectory().mkdir();
        getIndexDirectory().mkdir();

        FilesTestUtil.writeString(getJsonConfigFile(), "{ \"data\"=\"string\" }");
        FilesTestUtil.writeString(getHoconConfigFile(), "{ data: string }");
        FilesTestUtil.writeString(getTomlConfigFile(), "data = \"string\"");
        FilesTestUtil.writeString(getYamlConfigFile(), "data: \"string\"");
    }

   @Test
   void loadDeployFolderTest()
   {
        var deployFolder = deployFolderLoader.loadDeployFolder(getWorkDirectory(), DeployerPlugin.DEFAULT_INDEX_NAME);
        assertNotNull(deployFolder);
   }

   @Test
   void loadDeployFoldersTest()
   {
        var deployFolders = deployFolderLoader.loadDeployFolders(getWorkDirectory(), DeployerPlugin.DEFAULT_INDEX_NAME);
        assertNotNull(deployFolders);
   }
}
