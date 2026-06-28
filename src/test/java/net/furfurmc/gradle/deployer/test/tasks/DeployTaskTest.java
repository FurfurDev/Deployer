package net.furfurmc.gradle.deployer.test.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class DeployTaskTest
{
    @Test
    void pluginRegisterTaskTest()
    {
        var project = ProjectBuilder.builder().build();
        project.getPlugins().apply(DeployerPlugin.class);
        assertNotNull(project.getTasks().findByName("deploy"));
    }
}
