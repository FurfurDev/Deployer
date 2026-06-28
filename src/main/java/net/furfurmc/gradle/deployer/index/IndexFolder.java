// IndexFolder.java : main
package net.furfurmc.gradle.deployer.index;

import java.io.File;
import java.util.Collection;

public class IndexFolder
{
    private Collection<IndexConfig> configs;
    private File                    destination;

    public IndexFolder(Collection<IndexConfig> configs, File destination)
    {
        this.configs     = configs;
        this.destination = destination;
    }

    public Collection<IndexConfig> getConfigs()
    {
        return this.configs;
    }

    public File getDestination()
    {
        return this.destination;
    }

    public void setConfigs(Collection<IndexConfig> configs)
    {
        this.configs = configs;
    }

    public void setDestination(File destination)
    {
        this.destination = destination;
    }
}
