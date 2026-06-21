package net.furfurmc.gradle.deployer.networks;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.json.JsonFormat;
import net.furfurmc.gradle.deployer.common.ResourceProvider;

public class DeployerUserAgent
{
    public static final String PLUGIN_JSON_FILE = "plugin.json";

    public static final String PLUGIN_NAME_FIELD      = "plugin_name";
    public static final String PLUGIN_VERSION_FIELD   = "plugin_version";
    public static final String PLUGIN_GROUP_FIELD     = "plugin_group";
    public static final String JAVA_VERSIOON_FIELD    = "java_version";
    public static final String HTMLUNIT_VERSION_FIELD = "htmlunit_version";

    private static final DeployerUserAgent instance = initInstance();

    private String userAgent;

    public DeployerUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    private static DeployerUserAgent initInstance()
    {
        var config = DeployerUserAgent.loadPluginJson();

        var pluginName      = config.get(DeployerUserAgent.PLUGIN_NAME_FIELD);
        var pluginVersion   = config.get(DeployerUserAgent.PLUGIN_VERSION_FIELD);
        var pluginGroup     = config.get(DeployerUserAgent.PLUGIN_GROUP_FIELD);
        var javaVersion     = config.get(DeployerUserAgent.JAVA_VERSIOON_FIELD);
        var htmlunitVersion = config.get(DeployerUserAgent.HTMLUNIT_VERSION_FIELD);

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

    private static Config loadPluginJson()
    {
        var resourceProvider = new ResourceProvider();
        var jsonParser       = JsonFormat.fancyInstance().createParser();

        if (!resourceProvider.isPresent(DeployerUserAgent.PLUGIN_JSON_FILE)) throw new RuntimeException("Not found " + DeployerUserAgent.PLUGIN_JSON_FILE + ".");

        var config = jsonParser.parse(resourceProvider.get(DeployerUserAgent.PLUGIN_JSON_FILE));

        if (!config.contains(DeployerUserAgent.PLUGIN_NAME_FIELD))      throw new RuntimeException("Not found " + DeployerUserAgent.PLUGIN_NAME_FIELD +      " field.");
        if (!config.contains(DeployerUserAgent.PLUGIN_VERSION_FIELD))   throw new RuntimeException("Not found " + DeployerUserAgent.PLUGIN_VERSION_FIELD +   " field.");
        if (!config.contains(DeployerUserAgent.PLUGIN_GROUP_FIELD))     throw new RuntimeException("Not found " + DeployerUserAgent.PLUGIN_GROUP_FIELD +     " field.");
        if (!config.contains(DeployerUserAgent.JAVA_VERSIOON_FIELD))    throw new RuntimeException("Not found " + DeployerUserAgent.JAVA_VERSIOON_FIELD +    " field.");
        if (!config.contains(DeployerUserAgent.HTMLUNIT_VERSION_FIELD)) throw new RuntimeException("Not found " + DeployerUserAgent.HTMLUNIT_VERSION_FIELD + " field.");

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

    public static DeployerUserAgent getInstance()
    {
        return DeployerUserAgent.instance;
    }
}
