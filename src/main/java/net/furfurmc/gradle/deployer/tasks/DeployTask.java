package net.furfurmc.gradle.deployer.tasks;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.CacheableTask;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.Optional;
import org.gradle.api.tasks.TaskAction;
import net.furfurmc.gradle.deployer.DeployerPlugin;
import net.furfurmc.gradle.deployer.DeployerProjectPlugin;
import net.furfurmc.gradle.deployer.common.DeployFolderLoader;
import net.furfurmc.gradle.deployer.tasks.utils.RegisterUtil;

@CacheableTask
public abstract class DeployTask extends DefaultTask
{
    @TaskAction
    public void deploy()
    {
        var deployPlugin     = super.getProject().getPlugins().getPlugin(DeployerProjectPlugin.class);
        var projectDirectory = super.getProject().getLayout().getProjectDirectory();
        var buildDirectory   = super.getProject().getLayout().getBuildDirectory().getOrElse(projectDirectory.dir("build"));
        var indexName        = this.getIndexName().getOrElse(DeployerPlugin.DEFAULT_INDEX_NAME);
        var cacheDirectory   = this.getCacheDirectory().getOrElse(buildDirectory.dir(DeployerPlugin.DEFAULT_CACHE_DIRECTORY)).getAsFile();
        var workDirectory    = this.getWorkDirectory().getOrElse(projectDirectory.dir(DeployerPlugin.DEFAULT_WORK_DIRECTORY)).getAsFile();
        var deployDirectory  = this.getDeployDirectory().getOrElse(projectDirectory.dir(DeployerPlugin.DEFAULT_DEPLOY_DIRECTORY)).getAsFile();

        // register basic deployers
        RegisterUtil.registerDeployers(deployPlugin, cacheDirectory);

        // load indexes and register mine configs
        var deployFolderLoader = new DeployFolderLoader();
        var deployFolders      = deployFolderLoader.loadDeployFolders(workDirectory, indexName);
        RegisterUtil.registerEntities(deployPlugin, workDirectory, deployDirectory, deployFolders);

        // make build directory
        if (!buildDirectory.getAsFile().exists()) buildDirectory.getAsFile().mkdirs();

        // make cache directory
        if (!cacheDirectory.exists()) cacheDirectory.mkdirs();

        // make work directory
        if (!workDirectory.exists()) workDirectory.mkdirs();

        // make deploy directory
        if (!deployDirectory.exists()) deployDirectory.mkdirs();

        // to deploy
        deployPlugin.deploy();
    }

    @Input
    @Optional
    public abstract Property<String> getIndexName();

    @Internal
    public abstract DirectoryProperty getCacheDirectory();

    @Internal
    public abstract DirectoryProperty getWorkDirectory();

    @Internal
    public abstract DirectoryProperty getDeployDirectory();
}
