// InstallDeployerTask.java : main
package net.furfurmc.gradle.deployer.task;

import java.io.FileOutputStream;
import java.nio.file.Files;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;
import org.gradle.api.tasks.TaskAction;

@CacheableTask
public abstract class InstallDeployerTask extends DeployerTaskBase
{
    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    public abstract RegularFileProperty getOriginFile();

    @Override
    @TaskAction
    public void deploy()
    {
        try (var outputStream = new FileOutputStream(this.getDeployFile().get().getAsFile()))
        {
            Files.copy(this.getOriginFile().get().getAsFile().toPath(), outputStream);
        }
        catch (Exception exception)
        {
            throw new RuntimeException("Fail deploy InstallDeployerTask.", exception);
        }
    }
}
