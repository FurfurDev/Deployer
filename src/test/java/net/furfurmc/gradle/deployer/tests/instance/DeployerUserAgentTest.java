package net.furfurmc.gradle.deployer.tests.instance;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import net.furfurmc.gradle.deployer.instance.DeployerUserAgent;

public class DeployerUserAgentTest
{
    @Test
    void initInstanceTest()
    {
        var userAgent = DeployerUserAgent.getInstance();
        assertNotNull(userAgent.toString());
    }
}
