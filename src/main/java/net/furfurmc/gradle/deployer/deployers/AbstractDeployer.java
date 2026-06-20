package net.furfurmc.gradle.deployer.deployers;

import java.io.File;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;

public abstract class AbstractDeployer
{
    private Class<?> entityClass;
    private File     cacheDirectory;

    public AbstractDeployer(Class<?> entityClass, File cacheDirectory)
    {
        this.entityClass    = entityClass;
        this.cacheDirectory = cacheDirectory;
    }

    public abstract void deploy(AbstractEntity entity);

    public Class<?> getEntityClass()
    {
        return this.entityClass;
    }

    public File getCacheDirectory()
    {
        return this.cacheDirectory;
    }

    public void setEntityClass(Class<?> entityClass)
    {
        this.entityClass = entityClass;
    }

    public void setCacheDirectory(File cacheDirectory)
    {
        this.cacheDirectory = cacheDirectory;
    }

    protected void checkEntityClass(AbstractEntity entity)
    {
        if (entity.getClass() != this.getEntityClass()) throw new RuntimeException("Fail check entity - isn't " + this.getEntityClass().getName() + ".");
    }
}
