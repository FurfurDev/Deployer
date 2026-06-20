package net.furfurmc.gradle.deployer.common;

import java.io.File;
import java.util.Collection;

public class DeployFolder
{
    private Collection<File> configFiles;
    private File             destination;
    private File             index;

    public DeployFolder(File destination)
    {
        this.configFiles = null;
        this.destination = destination;
    }

    public Collection<File> getConfigFiles()
    {
        return this.configFiles;
    }

    public File getDestination()
    {
        return this.destination;
    }

    public File getIndex()
    {
        return this.index;
    }

    public void setConfigFiles(Collection<File> configFiles)
    {
        this.configFiles = configFiles;
    }

    public void setDestination(File destination)
    {
        this.destination = destination;
    }

    public void setIndex(File index)
    {
        this.index = index;
    }
}
