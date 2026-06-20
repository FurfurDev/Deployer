package net.furfurmc.gradle.deployer.tests.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import net.furfurmc.gradle.deployer.common.DestinationUtil;

public class DestinationUtilTest
{
    private static final String ALFA_DIRECTORY_NAME = "alfa";
    private static final String BETA_DIRECTORY_NAME = "beta";

    @TempDir
    File projectDir;

    private File getAlfaDirectory()
    {
        return new File(projectDir, ALFA_DIRECTORY_NAME);
    }

    private File getAlfaAlfaDirectory()
    {
        return new File(getAlfaDirectory(), ALFA_DIRECTORY_NAME);
    }

    private File getAlfaBetaDirectory()
    {
        return new File(getAlfaDirectory(), BETA_DIRECTORY_NAME);
    }

    private File getBetaDirectory()
    {
        return new File(projectDir, BETA_DIRECTORY_NAME);
    }

    private File getBetaAlfaDirectory()
    {
        return new File(getBetaDirectory(), ALFA_DIRECTORY_NAME);
    }

    private File getBetaBetaDirectory()
    {
        return new File(getBetaDirectory(), BETA_DIRECTORY_NAME);
    }

    @Test
    void moveDestinationTest()
    {
        var test1Destination = DestinationUtil.moveDestination(getAlfaDirectory(), getBetaDirectory(), getAlfaAlfaDirectory());
        var test2Destination = DestinationUtil.moveDestination(getAlfaDirectory(), getBetaDirectory(), getAlfaBetaDirectory());
        assertEquals(test1Destination, getBetaAlfaDirectory());
        assertEquals(test2Destination, getBetaBetaDirectory());
    }
}
