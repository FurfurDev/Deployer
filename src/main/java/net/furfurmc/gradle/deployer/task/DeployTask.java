// DeployTask.java : main
package net.furfurmc.gradle.deployer.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.TaskAction;

@CacheableTask
public abstract class DeployTask extends DefaultTask
{
    public static final String NAME        = "deploy";
    public static final String DESCRIPTION = "Deploying all the tasks of the deployer.";

    @TaskAction
    public void deploy()
    {
        // Empty.
    }
}
