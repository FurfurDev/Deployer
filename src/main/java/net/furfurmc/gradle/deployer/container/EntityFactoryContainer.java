// EntityFactoryContainer.java : main
package net.furfurmc.gradle.deployer.container;

import javax.inject.Inject;
import org.gradle.api.model.ObjectFactory;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.factory.EntityFactoryBase;
import net.furfurmc.gradle.deployer.factory.InstallEntityFactory;

public abstract class EntityFactoryContainer extends FactoryContainer<EntityBase, EntityFactoryBase>
{
    @Inject
    public EntityFactoryContainer(ObjectFactory objects)
    {
        super(EntityFactoryBase.class, objects);

        this.register("InstallEntityFactory", InstallEntityFactory.class);
    }
}
