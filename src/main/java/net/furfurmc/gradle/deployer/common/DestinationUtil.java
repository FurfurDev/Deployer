package net.furfurmc.gradle.deployer.common;

import java.io.File;

public class DestinationUtil
{
    public static String getRelative(File destination, File front)
    {
        var destinationString = destination.getAbsolutePath();
        return destinationString.replace(front.toString(), "");
    }

    public static File getAbsolute(File destination, String relative)
    {
        var destinationString = destination.getAbsolutePath();
        return new File(destinationString.concat(relative));
    }

    public static File moveDestination(File inputDest, File outputDest, File destination)
    {
        var relative = DestinationUtil.getRelative(destination, inputDest);
        return DestinationUtil.getAbsolute(outputDest, relative);
    }
}
