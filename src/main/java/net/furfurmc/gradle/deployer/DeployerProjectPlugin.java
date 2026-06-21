package net.furfurmc.gradle.deployer;

import java.util.HashMap;
import java.util.Map;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import net.furfurmc.gradle.deployer.analyzers.AbstractAnalyzer;
import net.furfurmc.gradle.deployer.analyzers.CurseforgeAnalyzer;
import net.furfurmc.gradle.deployer.analyzers.GitHubAnalyzer;
import net.furfurmc.gradle.deployer.analyzers.InstallAnalyzer;
import net.furfurmc.gradle.deployer.analyzers.MakefileAnalyzer;
import net.furfurmc.gradle.deployer.analyzers.ModrinthAnalyzer;
import net.furfurmc.gradle.deployer.analyzers.WebAnalyzer;
import net.furfurmc.gradle.deployer.deployers.AbstractDeployer;
import net.furfurmc.gradle.deployer.entities.AbstractEntity;
import net.furfurmc.gradle.deployer.extensions.DeployExtension;
import net.furfurmc.gradle.deployer.tasks.DeployTask;

public class DeployerProjectPlugin implements Plugin<Project>
{
    private Map<String,   AbstractEntity>   entities;
    private Map<String,   AbstractAnalyzer> analyzers;
    private Map<Class<?>, AbstractDeployer> deployers;

    public DeployerProjectPlugin()
    {
        this.entities  = new HashMap<>();
        this.analyzers = new HashMap<>();
        this.deployers = new HashMap<>();
    }

    @Override
    public void apply(Project project)
    {
        this.registerAnalyzers();
        this.registerDeployTask(project);
    }

    public void deploy()
    {
        for (var entity : this.entities.values())
        {
            var entityClass = entity.getClass();

            if (!this.containsDeployer(entityClass)) throw new RuntimeException("Fail find deployer for " + entityClass.getName());
            
            var deployer = this.getDeployer(entityClass);
            deployer.deploy(entity);
            continue;
        }
    }

    public boolean containsEntity(String name)
    {
        return this.entities.containsKey(name);
    }

    public boolean containsAnalyzer(String method)
    {
        return this.analyzers.containsKey(method);
    }

    public boolean containsDeployer(Class<?> entityClass)
    {
        return this.deployers.containsKey(entityClass);
    }

    public AbstractEntity getEntity(String name)
    {
        return this.entities.get(name);
    }

    public AbstractAnalyzer getAnalyzer(String method)
    {
        return this.analyzers.get(method);
    }

    public AbstractDeployer getDeployer(Class<?> entityClass)
    {
        return this.deployers.get(entityClass);
    }

    public void registerEntity(AbstractEntity entity)
    {
        this.entities.put(entity.name, entity);
    }

    public void registerAnalyzer(AbstractAnalyzer analyzer)
    {
        this.analyzers.put(analyzer.getMethod(), analyzer);
    }

    public void registerDeployer(AbstractDeployer deployer)
    {
        this.deployers.put(deployer.getEntityClass(), deployer);
    }

    private void registerAnalyzers()
    {
        this.registerAnalyzer(new InstallAnalyzer());
        this.registerAnalyzer(new MakefileAnalyzer());
        this.registerAnalyzer(new WebAnalyzer());
        this.registerAnalyzer(new CurseforgeAnalyzer());
        this.registerAnalyzer(new GitHubAnalyzer());
        this.registerAnalyzer(new ModrinthAnalyzer());
    }

    private void registerDeployTask(Project project)
    {
        DeployExtension extension = project.getExtensions().create("deploy", DeployExtension.class);

        project.getTasks().register("deploy", DeployTask.class, task -> {
            task.getDeployerProjectPlugin().set(this);
            task.getProjectDirectory().set(project.getLayout().getProjectDirectory());

            task.getIndexName().set(extension.getIndexName());
            task.getCacheDirectory().set(extension.getCacheDirectory());
            task.getWorkDirectory().set(extension.getWorkDirectory());
            task.getDeployDirectory().set(extension.getDeployDirectory());
        });
    }
}
