package net.furfurmc.gradle.deployer.factory;

import java.io.File;
import javax.inject.Inject;
import org.gradle.api.model.ObjectFactory;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.entity.MakefileEntity;

public abstract class MakefileEntityFactory extends EntityFactoryBase
{
    static public final String CONFIG_CONTENT = "content";

    @Inject
    public MakefileEntityFactory()
    {
        getEntityClass().convention(MakefileEntity.class);
    }

    @Override
    public EntityBase entity(ObjectFactory objects, File disposition, UnmodifiableConfig userdata)
    {
        try
        {
            var entity = objects.newInstance(MakefileEntity.class, userdata.get(DeployerPlugin.CONFIG_NAME));
            entity.getContent().set((String) userdata.get(MakefileEntityFactory.CONFIG_CONTENT));
            return entity;
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail factory MakefileEntity.", exception);
        }
    }
}
