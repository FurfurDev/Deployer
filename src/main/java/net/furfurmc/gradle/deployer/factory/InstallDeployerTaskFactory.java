// InstallDeployerTaskFactory.java : main
package net.furfurmc.gradle.deployer.factory;

import javax.inject.Inject;
import org.gradle.api.Project;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.entity.InstallEntity;
import net.furfurmc.gradle.deployer.task.DeployerTaskBase;
import net.furfurmc.gradle.deployer.task.InstallDeployerTask;

public abstract class InstallDeployerTaskFactory extends DeployerTaskFactoryBase
{
    @Inject
    public InstallDeployerTaskFactory()
    {
        getEntityClass().convention(InstallEntity.class);
    }

    @Override
    public DeployerTaskBase task(Project project, EntityBase entity)
    {
        if (entity instanceof InstallEntity installEntity)
        {
            try
            {
                var installTask = project.getObjects().newInstance(InstallDeployerTask.class, DeployerPlugin.PREFIX_NAME + installEntity.getName());
                installTask.setGroup(installEntity.getGroup().getOrElse(DeployerPlugin.TASKS_GROUP));
                installTask.getDeployFile().set(installEntity.getDeployFile());
                installTask.getOriginFile().set(installEntity.getOriginFile());
                return installTask;
            }
            catch (Exception exception)
            {
                throw new RuntimeException("Fail factory InstallDeployerTask.", exception);
            }
        }
        else
        {
            throw new RuntimeException("The entity does not match the InstallEntity type.");
        }
    }
}
