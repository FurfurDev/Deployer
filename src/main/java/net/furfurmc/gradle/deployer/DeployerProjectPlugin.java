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
import net.furfurmc.gradle.deployer.networks.CurseforgeWebClient;
import net.furfurmc.gradle.deployer.networks.DeployerWebClient;
import net.furfurmc.gradle.deployer.networks.GitHubWebClient;
import net.furfurmc.gradle.deployer.networks.ModrinthWebClient;
import net.furfurmc.gradle.deployer.tasks.DeployTask;

public class DeployerProjectPlugin implements Plugin<Project>
{
    private Map<String,   AbstractEntity>   entities;
    private Map<String,   AbstractAnalyzer> analyzers;
    private Map<Class<?>, DeployerWebClient>    webClients;
    private Map<Class<?>, AbstractDeployer> deployers;

    public DeployerProjectPlugin()
    {
        this.entities     = new HashMap<>();
        this.analyzers    = new HashMap<>();
        this.webClients   = new HashMap<>();
        this.deployers    = new HashMap<>();
    }

    @Override
    public void apply(Project project)
    {
        this.registerWebClients();
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

    public boolean containsWebClient(Class<?> webClientClass)
    {
        return this.webClients.containsKey(webClientClass);
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

    public DeployerWebClient getWebClient(Class<?> webClientClass)
    {
        return this.webClients.get(webClientClass);
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

    public void registerWebClient(DeployerWebClient webClient)
    {
        this.webClients.put(webClient.getClass(), webClient);
    }

    public void registerDeployer(AbstractDeployer deployer)
    {
        this.deployers.put(deployer.getEntityClass(), deployer);
    }

    private void registerWebClients()
    {
        this.registerWebClient(new DeployerWebClient());
        this.registerWebClient(new CurseforgeWebClient());
        this.registerWebClient(new GitHubWebClient());
        this.registerWebClient(new ModrinthWebClient());
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
            task.getIndexName().set(extension.getIndexName());
            task.getCacheDirectory().set(extension.getCacheDirectory());
            task.getWorkDirectory().set(extension.getWorkDirectory());
            task.getDeployDirectory().set(extension.getDeployDirectory());
        });
    }
}
