// PolymorphicContainer.java : main
package net.furfurmc.gradle.deployer.container;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import org.gradle.api.Action;
import org.gradle.api.ExtensiblePolymorphicDomainObjectContainer;
import org.gradle.api.Named;
import org.gradle.api.NamedDomainObjectProvider;
import org.gradle.api.model.ObjectFactory;
import org.gradle.api.reflect.HasPublicType;
import org.gradle.api.reflect.TypeOf;

public abstract class PolymorphicContainer<T extends Named> implements HasPublicType
{
    private final ExtensiblePolymorphicDomainObjectContainer<T> delegat;
    private final Set<Class<? extends T>>                       registeredTypes;

    public PolymorphicContainer(Class<T> clazz, ObjectFactory objects)
    {
        this.delegat         = objects.polymorphicDomainObjectContainer(clazz);
        this.registeredTypes = new HashSet<>();
        this.registerBinding(clazz, clazz);
    }

    public ExtensiblePolymorphicDomainObjectContainer<T> getDelegat()
    {
        return this.delegat;
    }

    public void delegat(Action<? super ExtensiblePolymorphicDomainObjectContainer<T>> action)
    {
        action.execute(this.delegat);
    }

    public Stream<T> stream()
    {
        return this.delegat.stream();
    }

    public <LT extends T> void registerBinding(Class<LT> publicType, Class<? extends LT> implementationType)
    {
        if (registeredTypes.add(publicType)) this.delegat.registerBinding(publicType, implementationType);
    }

    public <LT extends T> void registerBinding(Class<LT> publicType, Class<? extends LT> implementationType, Action<? super T> configureAction)
    {
        this.registerBinding(publicType, implementationType);
        if (configureAction != null) this.delegat.withType(publicType).configureEach(configureAction);
    }

    public <LT extends T> NamedDomainObjectProvider<LT> register(String name, Class<LT> type)
    {
        ensureBindingRegistered(type);
        return this.delegat.register(name, type);
    }

    public <LT extends T> NamedDomainObjectProvider<LT> register(String name, Class<LT> type, Action<? super LT> action)
    {
        ensureBindingRegistered(type);
        return this.delegat.register(name, type, action);
    }

    private <LT extends T> void ensureBindingRegistered(Class<LT> type)
    {
        this.registerBinding(type, type);
    }

    @Override
    public TypeOf<?> getPublicType()
    {
        return TypeOf.typeOf(ExtensiblePolymorphicDomainObjectContainer.class);
    }
}
