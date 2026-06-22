package net.furfurmc.gradle.deployer.instance;

import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.json.JsonFormat;

public class DeployerConfig
{
    public static final String PLUGIN_JSON_FILE = "deployer.json";

    private static final DeployerConfig instance = DeployerConfig.initInstance();

    private UnmodifiableConfig config;

    protected DeployerConfig(UnmodifiableConfig config)
    {
        this.config = config;
    }

    private static DeployerConfig initInstance()
    {
        var jsonFormat = JsonFormat.fancyInstance();
        var jsonParser = jsonFormat.createParser();
        var resources  = DeployerResources.getInstance();
        return new DeployerConfig(jsonParser.parse(resources.getAndCheck(PLUGIN_JSON_FILE)));
    }

    public static DeployerConfig getInstance()
    {
        return DeployerConfig.instance;
    }

    public boolean contains(String path)
    {
        return config.contains(path);
    }

    public Object get(String path)
    {
        return config.get(path);
    }

    public Object getOrElse(String path, Object defaultValue)
    {
        if (!this.contains(path)) return defaultValue;
        return this.get(path);
    }

    public Object getAndCheck(String path)
    {
        if (!this.contains(path)) throw new RuntimeException("Not found " + path + " field.");
        return this.get(path);
    }

    public UnmodifiableConfig getConfig()
    {
        return this.config;
    }

    public void setConfig(UnmodifiableConfig config)
    {
        this.config = config;
    }
}
