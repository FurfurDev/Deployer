// InstallEntityFactory.java : main
package net.furfurmc.gradle.deployer.factory;

import java.io.File;
import javax.inject.Inject;
import org.gradle.api.model.ObjectFactory;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.entity.InstallEntity;

public abstract class InstallEntityFactory extends EntityFactoryBase
{
    static public final String CONFIG_ORIGIN = "origin";

    @Inject
    public InstallEntityFactory()
    {
        getEntityClass().convention(InstallEntity.class);
    }

    @Override
    public EntityBase entity(ObjectFactory objects, File disposition, UnmodifiableConfig userdata)
    {
        try
        {
            var entity = objects.newInstance(InstallEntity.class, userdata.get(DeployerPlugin.CONFIG_NAME));
            entity.getOriginFile().set(new File(disposition, userdata.get(InstallEntityFactory.CONFIG_ORIGIN)));
            return entity;
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail factory InstallEntity.", exception);
        }
    }
}
