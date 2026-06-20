package net.furfurmc.gradle.deployer.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import com.electronwill.nightconfig.core.file.FileConfig;

public class CacheDocument
{
    private File file;

    public CacheDocument(File parent, String child) throws IOException
    {
        this.file = new File(parent, child + "Cache.json");
        if (!this.file.exists()) Files.writeString(this.file.toPath(), "{}");
    }

    public boolean exists()
    {
        return this.file.exists();
    }

    public boolean contains(String path) throws IOException
    {
        try (var config = FileConfig.of(this.file))
        {
            config.load();
            return config.contains(path);
        }
    }

    public Object getField(String path) throws IOException
    {
        try (var config = FileConfig.of(this.file))
        {
            config.load();
            return config.get(path);
        }
    }

    public File getFile()
    {
        return this.file;
    }

    public void setField(String path, Object field) throws IOException
    {
        try (var config = FileConfig.of(this.file))
        {
            config.set(path, field);
            config.save();
        }
    }

    public void setFile(File file)
    {
        this.file = file;
    }
}
