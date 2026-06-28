// DeployerTaskFactoryContainer.java : main
package net.furfurmc.gradle.deployer.task.factory;

import org.gradle.api.model.ObjectFactory;
import net.furfurmc.gradle.deployer.container.FactoryContainer;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import javax.inject.Inject;

public abstract class DeployerTaskFactoryContainer extends FactoryContainer<EntityBase, DeployerTaskFactoryBase>
{
    @Inject
    public DeployerTaskFactoryContainer(ObjectFactory objects)
    {
        super(DeployerTaskFactoryBase.class, objects);

        this.register("InstallDeployerTaskFactory", InstallDeployerTaskFactory.class);
    }
}
