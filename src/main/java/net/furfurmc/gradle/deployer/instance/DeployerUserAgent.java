// DeployerUserAgent.java : main
package net.furfurmc.gradle.deployer.instance;

public class DeployerUserAgent
{
    public static final String PROJECT_NAME_FIELD     = "project_name";
    public static final String PROJECT_VERSION_FIELD  = "project_version";
    public static final String PROJECT_GROUP_FIELD    = "project_group";
    public static final String JAVA_VERSIOON_FIELD    = "java_version";
    public static final String HTMLUNIT_VERSION_FIELD = "htmlunit_version";

    private static final DeployerUserAgent instance = DeployerUserAgent.initInstance();

    private String userAgent;

    protected DeployerUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    private static DeployerUserAgent initInstance()
    {
        var config = DeployerConfig.getInstance();

        var pluginName      = config.getAndCheck(DeployerUserAgent.PROJECT_NAME_FIELD);
        var pluginVersion   = config.getAndCheck(DeployerUserAgent.PROJECT_VERSION_FIELD);
        var pluginGroup     = config.getAndCheck(DeployerUserAgent.PROJECT_GROUP_FIELD);
        var javaVersion     = config.getAndCheck(DeployerUserAgent.JAVA_VERSIOON_FIELD);
        var htmlunitVersion = config.getAndCheck(DeployerUserAgent.HTMLUNIT_VERSION_FIELD);

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

    public static DeployerUserAgent getInstance()
    {
        return DeployerUserAgent.instance;
    }

    public void setUserAgent(String userAgent)
    {
        this.userAgent = userAgent;
    }

    @Override
    public String toString()
    {
        return this.userAgent;
    }
}
