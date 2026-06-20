package net.furfurmc.gradle.deployer.extensions;

import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;

public abstract class DeployExtension
{
    public abstract Property<String> getIndexName();
    
    public abstract DirectoryProperty getCacheDirectory();

    public abstract DirectoryProperty getWorkDirectory();

    public abstract DirectoryProperty getDeployDirectory();
}
