// IndexConfig.java : main
package net.furfurmc.gradle.deployer.index;

import java.io.File;
import com.electronwill.nightconfig.core.file.FileConfig;

public class IndexConfig
{
    public static final String[] EXTENSIONS = { "json", "hocon", "toml", "yaml" };

    private FileConfig fileConfig;

    public IndexConfig(File file)
    {
        this.fileConfig = FileConfig.of(file);
        this.fileConfig.load();
    }

    public boolean contains(String path)
    {
        return this.fileConfig.contains(path);
    }

    public Object get(String path)
    {
        return this.fileConfig.get(path);
    }

    public Object getOrElse(String path, Object defaultValue)
    {
        return this.fileConfig.getOrElse(path, defaultValue);
    }

    public Object getAndCheck(String path)
    {
        if (!this.contains(path)) throw new RuntimeException("Not found " + path + " path.");
        return this.get(path);
    }

    public FileConfig getFileConfig()
    {
        return this.fileConfig;
    }

    public void setFileConfig(File file)
    {
        this.fileConfig = FileConfig.of(file);
    }
}
