package net.furfurmc.gradle.deployer.networks;

import com.electronwill.nightconfig.core.Config;

public class DeployerUserAgent
{
    static private final DeployerUserAgent instance = initInstance();

    private String userAgent;

    public DeployerUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    static private DeployerUserAgent initInstance()
    {
        var config = DeployerUserAgent.loadPluginJson();

        var pluginName      = config.get("plugin_name");
        var pluginVersion   = config.get("plugin_version");
        var pluginGroup     = config.get("plugin_group");
        var javaVersion     = config.get("java_version");
        var htmlunitVersion = config.get("htmlunit_version");

        var osName    = System.getProperty("os.name");
        var osVersion = System.getProperty("os.version");
        var osArch    = System.getProperty("os.arch");

        var stringBuilder = new StringBuilder();

        stringBuilder
            .append(pluginName).append("/").append(pluginVersion)
            .append(" (")
            .append(pluginGroup).append("; Java/").append(javaVersion).append("; ")
            .append(osName).append("/").append(osVersion).append("; ").append(osArch)
            .append(") ")
            .append("HtmlUnit").append("/").append(htmlunitVersion);

        return new DeployerUserAgent(stringBuilder.toString());
    }

    static private Config loadPluginJson()
    {
        Config config = Config.inMemory();

        config.add("plugin_name",      "Deployer");
        config.add("plugin_version",   "0.1.0");
        config.add("plugin_group",     "net.furfurmc.gradle.deployer");
        config.add("java_version",     "21");
        config.add("htmlunit_version", "5.1.0");

        return config;
    }

    public String getUserAgent()
    {
        return this.userAgent;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    static public DeployerUserAgent getInstance()
    {
        return DeployerUserAgent.instance;
    }
}
