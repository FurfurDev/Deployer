package net.furfurmc.gradle.deployer.analyzers;

import java.io.File;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;

public abstract class AbstractAnalyzer
{
    private String method;

    public AbstractAnalyzer(String method)
    {
        this.method = method;
    }

    public abstract AbstractEntity analyze(File destination, UnmodifiableConfig userdata);

    public String getMethod()
    {
        return this.method;
    }

    protected Object getAndCheck(UnmodifiableConfig userdata, String path)
    {
        if (!userdata.contains(path)) throw new RuntimeException("Not found \'" + path + "\'.");
        return userdata.get(path);
    }
}
