package net.furfurmc.gradle.deployer.factory;

import javax.inject.Inject;
import org.gradle.api.Project;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.entity.MakefileEntity;
import net.furfurmc.gradle.deployer.task.DeployerTaskBase;
import net.furfurmc.gradle.deployer.task.MakefileDeployerTask;

public abstract class MakefileDeployerTaskFactory extends DeployerTaskFactoryBase
{
    @Inject
    public MakefileDeployerTaskFactory()
    {
        getEntityClass().convention(MakefileEntity.class);
    }

    @Override
    public DeployerTaskBase task(Project project, EntityBase entity)
    {
        if (entity instanceof MakefileEntity makefileEntity)
        {
            try
            {
                var task = project.getObjects().newInstance(MakefileDeployerTask.class, DeployerPlugin.PREFIX_NAME + makefileEntity.getName());
                task.setGroup(makefileEntity.getGroup().getOrElse(DeployerPlugin.TASKS_GROUP));
                task.getDeployFile().set(makefileEntity.getDeployFile());
                task.getContent().set(makefileEntity.getContent());
                return task;
            }
            catch (Exception exception)
            {
                throw new RuntimeException("Fail factory MakefileDeployerTask.", exception);
            }
        }
        else
        {
            throw new RuntimeException("The entity does not match the MakefileEntity type.");
        }
    }
}
