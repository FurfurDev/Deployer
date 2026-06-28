// DestinationUtil.java : main
package net.furfurmc.gradle.deployer.util;

import java.io.File;

public class DestinationUtil
{
    public static File moveDestination(File inputDest, File outputDest, File destination)
    {
        return DestinationUtil.getAbsolute(outputDest, DestinationUtil.getRelative(destination, inputDest));
    }

    public static String getRelative(File destination, File front)
    {
        return destination.getAbsolutePath().replace(front.toString(), "");
    }

    public static File getAbsolute(File destination, String relative)
    {
        return new File(destination.getAbsolutePath().concat(relative));
    }
}
