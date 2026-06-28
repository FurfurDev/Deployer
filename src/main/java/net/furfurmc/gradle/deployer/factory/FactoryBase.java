// FactoryBase.java : main
package net.furfurmc.gradle.deployer.factory;

import org.gradle.api.Named;
import org.gradle.api.provider.Property;

public abstract class FactoryBase<T> implements Named
{
    public abstract Property<Class<? extends T>> getProductClass();
}
