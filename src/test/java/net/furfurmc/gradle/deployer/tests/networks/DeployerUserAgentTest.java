package net.furfurmc.gradle.deployer.tests.networks;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import net.furfurmc.gradle.deployer.networks.DeployerUserAgent;

public class DeployerUserAgentTest
{
    @Test
    void initInstanceTest()
    {
        var deployerUserAgent = DeployerUserAgent.getInstance();
        System.out.println(deployerUserAgent.getUserAgent());
        assertNotNull(deployerUserAgent);
    }
}
