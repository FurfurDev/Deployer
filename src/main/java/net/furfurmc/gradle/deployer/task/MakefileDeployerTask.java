package net.furfurmc.gradle.deployer.task;

import java.nio.file.Files;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;

@CacheableTask
public abstract class MakefileDeployerTask extends DeployerTaskBase
{
    @Input
    public abstract Property<String> getContent();

    @Override
    @TaskAction
    public void deploy()
    {
        try
        {
            Files.writeString(this.getDeployFile().get().getAsFile().toPath(), getContent().get());
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail deploy MakefileDeployerTask.", exception);
        }
    }
}
