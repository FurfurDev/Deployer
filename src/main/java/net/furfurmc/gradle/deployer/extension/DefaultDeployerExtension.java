// DefaultDeployerExtension.java : main
package net.furfurmc.gradle.deployer.extension;

import javax.inject.Inject;
import org.gradle.api.Action;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.container.DeployerTaskContainer;
import net.furfurmc.gradle.deployer.container.EntityContainer;
import net.furfurmc.gradle.deployer.instance.DeployerUserAgent;

public abstract class DefaultDeployerExtension implements DeployerExtension
{
    private final EntityContainer       entities;
    private final DeployerTaskContainer tasks;

    @Inject
    public DefaultDeployerExtension(EntityContainer entities, DeployerTaskContainer tasks)
    {
        this.entities = entities;
        this.tasks    = tasks;

        this.getUserAgent().convention(DeployerUserAgent.getInstance().toString());
        this.getIndexName().convention(DeployerPlugin.INDEX_NAME);
    }

    @Override
    public EntityContainer getEntities()
    {
        return this.entities;
    }

    @Override
    public void entities(Action<? super EntityContainer> action)
    {
        action.execute(entities);
    }

    @Override
    public DeployerTaskContainer getTasks()
    {
        return this.tasks;
    }

    @Override
    public void tasks(Action<? super DeployerTaskContainer> action)
    {
        action.execute(tasks);
    }
}
