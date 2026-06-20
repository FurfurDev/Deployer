package net.furfurmc.gradle.deployer;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.jetbrains.annotations.NotNull;

public class DeployerPlugin implements Plugin<Object>
{
    static public final String DEFAULT_INDEX_NAME = ".deploy";

    static public final String DEFAULT_CACHE_DIRECTORY  = "deplcache";
    static public final String DEFAULT_WORK_DIRECTORY   = "deplwork";
    static public final String DEFAULT_DEPLOY_DIRECTORY = "deploy";

    static public final String CONFIG_NAMESPACE = "deployer";

    static public final String CONFIG_METHOD   = "method";
    static public final String CONFIG_NAME     = "name";
    static public final String CONFIG_FILENAME = "filename";
    static public final String CONFIG_USERDATA = "userdata";

    @Override
    public void apply(@NotNull Object object)
    {
        if (object instanceof Project project)
        {
            project.getPluginManager().apply(DeployerProjectPlugin.class);
        }
        else
        {
            throw new IllegalArgumentException("DeployerPlugin can only be applied to a project");
        }
    }
}
