package net.furfurmc.gradle.deployer.tests.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import com.electronwill.nightconfig.core.Config;

import net.furfurmc.gradle.deployer.common.NightConfigLoader;
import net.furfurmc.gradle.deployer.tests.tools.FilesTestUtil;
import net.furfurmc.gradle.deployer.tests.tools.NightConfigTestUtil;

public class NightConfigLoaderTest
{
    @TempDir
    File projectDir;

    private File getJsonConfigFile()
    {
        return FilesTestUtil.getJsonConfigFile(projectDir);
    }

    private File getHoconConfigFile()
    {
        return FilesTestUtil.getHoconConfigFile(projectDir);
    }

    private File getTomlConfigFile()
    {
        return FilesTestUtil.getTomlConfigFile(projectDir);
    }

    private File getYamlConfigFile()
    {
        return FilesTestUtil.getYamlConfigFile(projectDir);
    }

    Config exampleConfig;

    public NightConfigLoaderTest()
    {
        exampleConfig = NightConfigTestUtil.createExampleConfig();
    }

    @BeforeEach
    void setup() throws IOException
    {
        NightConfigTestUtil.createJsonConfigFile(getJsonConfigFile(),   exampleConfig);
        NightConfigTestUtil.createHoconConfigFile(getHoconConfigFile(), exampleConfig);
        NightConfigTestUtil.createTomlConfigFile(getTomlConfigFile(),   exampleConfig);
        NightConfigTestUtil.createYamlConfigFile(getYamlConfigFile(),   exampleConfig);
    }

    @Test
    void loadTest() throws IOException
    {
        var nightConfigLoader = new NightConfigLoader();
        assertEquals(nightConfigLoader.load(getJsonConfigFile()),  exampleConfig);
        assertEquals(nightConfigLoader.load(getHoconConfigFile()), exampleConfig);
        assertEquals(nightConfigLoader.load(getTomlConfigFile()),  exampleConfig);
        assertEquals(nightConfigLoader.load(getYamlConfigFile()),  exampleConfig);
    }
}
