package net.furfurmc.gradle.deployer.test.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import net.furfurmc.gradle.deployer.util.DestinationUtil;

public class DestinationUtilTest
{
    static public final String ALFA_NAME = "Alfa";
    static public final String BETA_NAME = "Beta";

    @TempDir
    File projectDir;

    private File getAlfaDirectory()
    {
        return new File(projectDir, DestinationUtilTest.ALFA_NAME);
    }

    private File getAlfaAlfaDirectory()
    {
        return new File(getAlfaDirectory(), DestinationUtilTest.ALFA_NAME);
    }

    private File getAlfaBetaDirectory()
    {
        return new File(getAlfaDirectory(), DestinationUtilTest.BETA_NAME);
    }

    private File getBetaDirectory()
    {
        return new File(projectDir, DestinationUtilTest.BETA_NAME);
    }

    private File getBetaAlfaDirectory()
    {
        return new File(getBetaDirectory(), DestinationUtilTest.ALFA_NAME);
    }

    private File getBetaBetaDirectory()
    {
        return new File(getBetaDirectory(), DestinationUtilTest.BETA_NAME);
    }

    @Test
    void moveDestinationTest()
    {
        assertEquals(DestinationUtil.moveDestination(getAlfaDirectory(), getBetaDirectory(), getAlfaAlfaDirectory()), getBetaAlfaDirectory());
        assertEquals(DestinationUtil.moveDestination(getAlfaDirectory(), getBetaDirectory(), getAlfaBetaDirectory()), getBetaBetaDirectory());
    }
}
