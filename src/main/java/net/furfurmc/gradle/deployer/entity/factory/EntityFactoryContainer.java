// EntityFactoryContainer.java : main
package net.furfurmc.gradle.deployer.entity.factory;

import org.gradle.api.model.ObjectFactory;
import net.furfurmc.gradle.deployer.container.FactoryContainer;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import javax.inject.Inject;

public abstract class EntityFactoryContainer extends FactoryContainer<EntityBase, EntityFactoryBase>
{
    @Inject
    public EntityFactoryContainer(ObjectFactory objects)
    {
        super(EntityFactoryBase.class, objects);

        this.register("InstallEntityFactory", InstallEntityFactory.class);
    }
}
