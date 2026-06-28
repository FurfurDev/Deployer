// DeployerTaskBase.java : main
package net.furfurmc.gradle.deployer.task;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.OutputFile;

@CacheableTask
public abstract class DeployerTaskBase extends DefaultTask
{
    @OutputFile
    public abstract RegularFileProperty getDeployFile();

    public abstract void deploy();
}
