package net.furfurmc.gradle.deployer.functionaltests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class WebFunctionalTest
{
    @TempDir
    File projectDir;

    private File getSettingsFile()
    {
        return new File(projectDir, "settings.gradle");
    }

    private File getBuildFile()
    {
        return new File(projectDir, "build.gradle");
    }

    private File getWorkDirectory()
    {
        return new File(projectDir, "server");
    }

    private File getIndexDirectory()
    {
        return new File(getWorkDirectory(), DeployerPlugin.DEFAULT_INDEX_NAME);
    }

    private File getIndexConfigFile()
    {
        return new File(getIndexDirectory(), "Web.toml");
    }

    private File getDeployDirectory()
    {
        return new File(projectDir, "run");
    }

    private File getDeployFile()
    {
        return new File(getDeployDirectory(), "page.html");
    }

    @BeforeEach
    void setup() throws IOException
    {
        writeString(getSettingsFile(), "");

        writeString(getBuildFile(), """
            plugins {
                id "net.furfurmc.gradle.deployer"
            }
            
            deploy {
                workDirectory   = layout.projectDirectory.dir('server')
                deployDirectory = layout.projectDirectory.dir('run')
            }
        """
        );

        getWorkDirectory().mkdir();
        getIndexDirectory().mkdir();
    }

    @Test
    void installConfigFunctionalTest() throws IOException, InterruptedException
    {
        writeString(getIndexConfigFile(), """
            [[deployer]]
            name = "Web"
            filename = "page.html"
            method = "web"
            userdata = { url = "https://github.com/FurfurDev" }
        """
        );

        BuildResult result = GradleRunner.create()
            .withProjectDir(projectDir)
            .withPluginClasspath()
            .withArguments("deploy")
            .build();

        assertTrue(getDeployFile().exists());
        assertEquals(TaskOutcome.SUCCESS, result.task(":deploy").getOutcome());
    }

    private static void writeString(File file, String string) throws IOException
    {
        Files.writeString(file.toPath(), string);
    }
}
