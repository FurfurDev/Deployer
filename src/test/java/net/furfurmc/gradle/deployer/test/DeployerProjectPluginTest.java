package net.furfurmc.gradle.deployer.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.File;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import net.furfurmc.gradle.deployer.DeployerProjectPlugin;

public class DeployerProjectPluginTest
{
    @TempDir
    File projectDir;
    
    @Test
    void projectRegisterPluginTest()
    {
        var project = ProjectBuilder.builder().withProjectDir(projectDir).build();
        project.getPlugins().apply(DeployerProjectPlugin.class);
        assertNotNull(project.getPlugins().getPlugin(DeployerProjectPlugin.class));
    }
}
