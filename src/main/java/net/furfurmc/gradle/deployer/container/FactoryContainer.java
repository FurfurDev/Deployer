// FactoryContainer.java : main
package net.furfurmc.gradle.deployer.container;

import org.gradle.api.Action;
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer;
import org.gradle.api.model.ObjectFactory;
import net.furfurmc.gradle.deployer.factory.FactoryBase;

public abstract class FactoryContainer<T, FT extends FactoryBase<T>> extends PolymorphicContainer<FT>
{
    public FactoryContainer(Class<FT> clazz, ObjectFactory objects)
    {
        super(clazz, objects);
        super.registerBinding(clazz, clazz);

        enforceMandatoryProductClass();
        enforceUniqueProductClass();
    }

    private void enforceMandatoryProductClass()
    {
        this.getFactories().whenObjectAdded(factory -> {
            if (!factory.getProductClass().isPresent())
            {
                throw new IllegalStateException(
                    "Property 'productClass' is required for factory '" + 
                    factory.getName() + "' but was not set."
                );
            }
            factory.getProductClass().finalizeValueOnRead();
            factory.getProductClass().disallowChanges();
        });
    }
    
    private void enforceUniqueProductClass()
    {
        this.getFactories().whenObjectAdded(factory -> {
            factory.getProductClass().finalizeValue();

            Class<? extends T> newClass = factory.getProductClass().getOrNull();
            if (newClass == null) return;

            boolean duplicate = this.getFactories().stream()
                .filter(f -> f != factory)
                .anyMatch(f -> {
                    f.getProductClass().finalizeValue();
                    return newClass.equals(f.getProductClass().getOrNull());
                });

            if (duplicate) throw new IllegalStateException("Duplicate factory for product class: " + newClass.getName() + ".");
        });
    }

    public ExtensiblePolymorphicDomainObjectContainer<FT> getFactories()
    {
        return super.getDelegat();
    }

    public void factories(Action<? super ExtensiblePolymorphicDomainObjectContainer<FT>> action)
    {
        action.execute(this.getDelegat());
    }
}
