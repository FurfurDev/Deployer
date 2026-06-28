package net.furfurmc.gradle.deployer.test.instance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import net.furfurmc.gradle.deployer.instance.DeployerConfig;

public class DeployerConfigTest
{
    @Test
    void initInstanceTest()
    {
        var config = DeployerConfig.getInstance();
        assertNotNull(config.getConfig());
    }
}
