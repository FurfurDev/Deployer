package net.furfurmc.gradle.deployer.common;

import java.io.InputStream;
import java.net.URL;

public class ResourceProvider
{
    private ClassLoader classLoader = ResourceProvider.class.getClassLoader();

    public boolean isPresent(String path)
    {
        return classLoader.getResource(path) != null;
    }

    public URL get(String path)
    {
        return classLoader.getResource(path);
    }

    public InputStream getAsStream(String path)
    {
        return classLoader.getResourceAsStream(path);
    }
}
