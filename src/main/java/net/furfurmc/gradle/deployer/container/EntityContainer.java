// EntityContainer.java : main
package net.furfurmc.gradle.deployer.container;

import javax.inject.Inject;
import org.gradle.api.Action;
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.entity.InstallEntity;
import net.furfurmc.gradle.deployer.entity.MakefileEntity;

public abstract class EntityContainer extends PolymorphicContainer<EntityBase>
{
    private final EntityFactoryContainer factories;

    @Inject
    public EntityContainer(ObjectFactory objects, EntityFactoryContainer factories)
    {
        super(EntityBase.class, objects);
        this.factories = factories;
        super.registerBinding(InstallEntity.class, InstallEntity.class);
        super.registerBinding(MakefileEntity.class, MakefileEntity.class);
    }

    public ExtensiblePolymorphicDomainObjectContainer<EntityBase> getEntities()
    {
        return this.getDelegat();
    }

    public void entities(Action<? super ExtensiblePolymorphicDomainObjectContainer<EntityBase>> action)
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
