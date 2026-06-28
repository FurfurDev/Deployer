// DeployerTaskContainer.java : main
package net.furfurmc.gradle.deployer.task;

import org.gradle.api.Action;
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import net.furfurmc.gradle.deployer.container.PolymorphicContainer;
import net.furfurmc.gradle.deployer.entity.factory.EntityFactoryContainer;
import javax.inject.Inject;

public abstract class DeployerTaskContainer extends PolymorphicContainer<DeployerTaskBase>
{
    private final EntityFactoryContainer factories;

    @Inject
    public DeployerTaskContainer(ObjectFactory objects, EntityFactoryContainer factories)
    {
        super(DeployerTaskBase.class, objects);
        this.factories = factories;
        super.registerBinding(InstallDeployerTask.class, InstallDeployerTask.class);
    }

    public ExtensiblePolymorphicDomainObjectContainer<DeployerTaskBase> getTasks()
    {
        return this.getDelegat();
    }

    public void tasks(Action<? super ExtensiblePolymorphicDomainObjectContainer<DeployerTaskBase>> action)
    {
        action.execute(this.getDelegat());
    }

    public EntityFactoryContainer getFactories()
    {
        return this.factories;
    }

    public void factories(Action<? super EntityFactoryContainer> action)
    {
        action.execute(factories);
    }
}
