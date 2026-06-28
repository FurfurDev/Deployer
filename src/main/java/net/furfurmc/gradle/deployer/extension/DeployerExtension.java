// DeployerExtension.java : main
package net.furfurmc.gradle.deployer.extension;

import org.gradle.api.Action;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import net.furfurmc.gradle.deployer.entity.EntityContainer;
import net.furfurmc.gradle.deployer.task.DeployerTaskContainer;

public interface DeployerExtension
{
    static final String NAME = "deployer";
    
    Property<String> getUserAgent();
    
    Property<String> getIndexName();

    DirectoryProperty getWorkDirectory();

    DirectoryProperty getDeployDirectory();

    EntityContainer getEntities();

    void entities(Action<? super EntityContainer> action);

    DeployerTaskContainer getTasks();

    void tasks(Action<? super DeployerTaskContainer> action);
}
