package net.furfurmc.gradle.deployer.tests.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class FilesTestUtil
{
    public static final String JSON_CONFIG_FILE_NAME  = "config.json";
    public static final String HOCON_CONFIG_FILE_NAME = "config.hocon";
    public static final String TOML_CONFIG_FILE_NAME  = "config.toml";
    public static final String YAML_CONFIG_FILE_NAME  = "config.yaml";

    public static final String CACHE_FILE_NAME = "file.cache";
    public static final String TEST_FILE_NAME  = "file.test";

    public static File getCurrentDirectory()
    {
        return Paths.get(".").toAbsolutePath().toFile();
    }

    public static File getCacheDirectory(File destination)
    {
        return new File(destination, DeployerPlugin.DEFAULT_CACHE_DIRECTORY);
    }

    public static File getWorkDirectory(File destination)
    {
        return new File(destination, DeployerPlugin.DEFAULT_WORK_DIRECTORY);
    }

    public static File getIndexDirectory(File destination)
    {
        return new File(destination, DeployerPlugin.DEFAULT_INDEX_NAME);
    }

    public static File getDeployDirectory(File destination)
    {
        return new File(destination, DeployerPlugin.DEFAULT_DEPLOY_DIRECTORY);
    }

    public static File getJsonConfigFile(File destination)
    {
        return new File(destination, JSON_CONFIG_FILE_NAME);
    }

    public static File getHoconConfigFile(File destination)
    {
        return new File(destination, HOCON_CONFIG_FILE_NAME);
    }

    public static File getTomlConfigFile(File destination)
    {
        return new File(destination, TOML_CONFIG_FILE_NAME);
    }

    public static File getYamlConfigFile(File destination)
    {
        return new File(destination, YAML_CONFIG_FILE_NAME);
    }

    public static File getCacheFile(File destination)
    {
        return new File(destination, CACHE_FILE_NAME);
    }

    public static File getTestFile(File destination)
    {
        return new File(destination, TEST_FILE_NAME);
    }

    public static String readString(File file) throws IOException
    {
        return Files.readString(file.toPath());
    }

    public static void writeString(File file, String string) throws IOException
    {
        Files.writeString(file.toPath(), string);
    }
}
