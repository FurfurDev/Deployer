package net.furfurmc.gradle.deployer.tests.instance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import net.furfurmc.gradle.deployer.instance.DeployerResources;

public class DeployerResourcesTest
{
    @Test
    void initInstanceTest()
    {
        var resources = DeployerResources.getInstance();
        assertNotNull(resources.getClassLoader());
    }
}
