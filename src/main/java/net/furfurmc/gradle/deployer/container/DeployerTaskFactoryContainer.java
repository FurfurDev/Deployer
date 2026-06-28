// DeployerTaskFactoryContainer.java : main
package net.furfurmc.gradle.deployer.container;

import javax.inject.Inject;
import org.gradle.api.model.ObjectFactory;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.factory.DeployerTaskFactoryBase;
import net.furfurmc.gradle.deployer.factory.InstallDeployerTaskFactory;
import net.furfurmc.gradle.deployer.factory.MakefileDeployerTaskFactory;

public abstract class DeployerTaskFactoryContainer extends FactoryContainer<EntityBase, DeployerTaskFactoryBase>
{
    @Inject
    public DeployerTaskFactoryContainer(ObjectFactory objects)
    {
        super(DeployerTaskFactoryBase.class, objects);

        this.register("InstallDeployerTaskFactory", InstallDeployerTaskFactory.class);
        this.register("MakefileDeployerTaskFactory", MakefileDeployerTaskFactory.class);
    }
}
