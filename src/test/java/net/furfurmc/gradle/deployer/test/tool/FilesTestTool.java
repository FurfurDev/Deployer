package net.furfurmc.gradle.deployer.test.tool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class FilesTestTool
{
    static public final String BUILD_FILE_NAME    = "build.gradle";
    static public final String SETTINGS_FILE_NAME = "settings.gradle";

    public static File getBuildFile(File destination)
    {
        return new File(destination, FilesTestTool.BUILD_FILE_NAME);
    }

    public static File getSettingsFile(File destination)
    {
        return new File(destination, FilesTestTool.SETTINGS_FILE_NAME);
    }

    public static File getIndexDirectory(File destination)
    {
        return new File(destination, DeployerPlugin.INDEX_NAME);
    }

    public static File getWorkDirectory(File destination)
    {
        return new File(destination, DeployerPlugin.WORK_DIRECTORY);
    }

    public static File getDeployDirectory(File destination)
    {
        return new File(destination, DeployerPlugin.DEPLOY_DIRECTORY);
    }

    public static void writeString(File file, String string) throws IOException
    {
        Files.writeString(file.toPath(), string);
    }

    public static String readString(File file) throws IOException
    {
        return Files.readString(file.toPath());
    }
}
