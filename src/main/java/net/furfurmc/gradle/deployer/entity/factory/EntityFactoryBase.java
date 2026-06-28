// EntityFactoryBase.java : main
package net.furfurmc.gradle.deployer.entity.factory;

import java.io.File;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.factory.FactoryBase;

public abstract class EntityFactoryBase extends FactoryBase<EntityBase>
{
    @Input
    public abstract Property<Class<? extends EntityBase>> getEntityClass();

    @Override
    public Property<Class<? extends EntityBase>> getProductClass()
    {
        return this.getEntityClass();
    }

    public abstract EntityBase entity(ObjectFactory objects, File destination, UnmodifiableConfig userdata);
}
