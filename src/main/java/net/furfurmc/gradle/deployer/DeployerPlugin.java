// DeployerPlugin.java : main
package net.furfurmc.gradle.deployer;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.jetbrains.annotations.NotNull;

public class DeployerPlugin implements Plugin<Object>
{
    private static final Logger logger = Logging.getLogger(DeployerProjectPlugin.class);
    
    static public final String TASKS_GROUP = "deployer";

    static public final String PREFIX_NAME      = "deploy";
    static public final String INDEX_NAME       = ".deploy";
    static public final String WORK_DIRECTORY   = "server";
    static public final String DEPLOY_DIRECTORY = "deploy";

    static public final String CONFIG_NAMESPACE = "deployer";
    static public final String CONFIG_NAME      = "name";
    static public final String CONFIG_TYPE      = "filename";

    @Override
    public void apply(@NotNull Object object)
    {
        logger.info("Apply plugin.");

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
