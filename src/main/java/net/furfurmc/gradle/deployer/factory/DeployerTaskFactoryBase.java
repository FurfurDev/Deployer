// DeployerTaskFactoryBase.java : main
package net.furfurmc.gradle.deployer.factory;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.task.DeployerTaskBase;

public abstract class DeployerTaskFactoryBase extends FactoryBase<EntityBase>
{
    @Input
    public abstract Property<Class<? extends EntityBase>> getEntityClass();

    @Override
    public Property<Class<? extends EntityBase>> getProductClass()
    {
        return this.getEntityClass();
    }
    
    public abstract DeployerTaskBase task(Project project, EntityBase entity);
}
