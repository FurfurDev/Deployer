package net.furfurmc.gradle.deployer.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class DeployerPluginTest
{
    @Test
    void projectRegisterPluginTest()
    {
        var project = ProjectBuilder.builder().build();
        project.getPlugins().apply("net.furfurmc.gradle.deployer");
        assertNotNull(project.getPlugins().getPlugin(DeployerPlugin.class));
    }
}
