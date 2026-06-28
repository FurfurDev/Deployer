// DeployerResources.java : main
package net.furfurmc.gradle.deployer.instance;

import java.io.InputStream;
import java.net.URL;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class DeployerResources
{
    private static final DeployerResources instance = DeployerResources.initInstance();

    private ClassLoader classLoader;

    protected DeployerResources(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }

    private static DeployerResources initInstance()
    {
        return new DeployerResources(DeployerPlugin.class.getClassLoader());
    }

    public static DeployerResources getInstance()
    {
        return DeployerResources.instance;
    }

    public boolean contains(String path)
    {
        return classLoader.getResource(path) != null;
    }

    public URL get(String path)
    {
        return classLoader.getResource(path);
    }

    public URL getAndCheck(String path)
    {
        if (!this.contains(path)) throw new RuntimeException("Not found " + path + " resource.");
        return this.get(path);
    }

    public InputStream getAsStream(String path)
    {
        return classLoader.getResourceAsStream(path);
    }

    public ClassLoader getClassLoader()
    {
        return this.classLoader;
    }

    public void setClassLoader(ClassLoader classLoader)
    {
        this.classLoader = classLoader;
    }
}
