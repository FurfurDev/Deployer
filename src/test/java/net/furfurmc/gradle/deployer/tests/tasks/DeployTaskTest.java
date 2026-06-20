package net.furfurmc.gradle.deployer.tests.tasks;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.Test;

public class DeployTaskTest
{
    @Test
    void pluginRegisterTaskTest()
    {
        var project = ProjectBuilder.builder().build();
        project.getPlugins().apply("net.furfurmc.gradle.deployer");
        assertNotNull(project.getTasks().findByName("deploy"));
    }
}
