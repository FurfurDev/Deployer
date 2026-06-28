// DeployerProjectPlugin.java : main
package net.furfurmc.gradle.deployer;

import java.util.Collection;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.TaskProvider;
import com.electronwill.nightconfig.core.Config;
import net.furfurmc.gradle.deployer.container.DeployerTaskContainer;
import net.furfurmc.gradle.deployer.container.DeployerTaskFactoryContainer;
import net.furfurmc.gradle.deployer.container.EntityContainer;
import net.furfurmc.gradle.deployer.container.EntityFactoryContainer;
import net.furfurmc.gradle.deployer.entity.EntityBase;
import net.furfurmc.gradle.deployer.extension.DefaultDeployerExtension;
import net.furfurmc.gradle.deployer.extension.DeployerExtension;
import net.furfurmc.gradle.deployer.factory.DeployerTaskFactoryBase;
import net.furfurmc.gradle.deployer.factory.EntityFactoryBase;
import net.furfurmc.gradle.deployer.index.IndexFolder;
import net.furfurmc.gradle.deployer.index.IndexFolderLoader;
import net.furfurmc.gradle.deployer.service.HtmlUnitService;
import net.furfurmc.gradle.deployer.task.DeployTask;
import net.furfurmc.gradle.deployer.util.DestinationUtil;

public class DeployerProjectPlugin implements Plugin<Project>
{
    private static final Logger logger = Logging.getLogger(DeployerProjectPlugin.class);

    private EntityFactoryContainer       entityFactories;
    private EntityContainer              entities;
    private DeployerTaskFactoryContainer taskFactories;
    private DeployerTaskContainer        tasks;
    private DeployerExtension            extension;

    @Override
    public void apply(Project project)
    {
        logger.info("Apply plugin.");

        var deployTask = this.registerDeployTask(project);

        this.entityFactories = project.getObjects().newInstance(EntityFactoryContainer.class, project.getObjects());
        this.entities        = project.getObjects().newInstance(EntityContainer.class, new Object[]{ project.getObjects(), this.entityFactories });
        this.taskFactories   = project.getObjects().newInstance(DeployerTaskFactoryContainer.class, project.getObjects());
        this.tasks           = project.getObjects().newInstance(DeployerTaskContainer.class, new Object[]{ project.getObjects(), this.taskFactories });
        this.extension       = project.getExtensions().create(DeployerExtension.class, DeployerExtension.NAME, DefaultDeployerExtension.class, new Object[]{ this.entities, this.tasks });

        this.extension.getWorkDirectory().convention(project.getLayout().getProjectDirectory().dir(DeployerPlugin.WORK_DIRECTORY));
        this.extension.getDeployDirectory().convention(project.getLayout().getProjectDirectory().dir(DeployerPlugin.DEPLOY_DIRECTORY));

        project.getGradle().getSharedServices().registerIfAbsent(HtmlUnitService.NAME, HtmlUnitService.class, service -> {
            service.getParameters().getUserAgent().set(extension.getUserAgent().get());
        });

        var workDirectory = this.extension.getWorkDirectory().getAsFile().get();
        if (workDirectory.exists())
        {
            var indexFolderLoader = new IndexFolderLoader();
            var indexFolders      = indexFolderLoader.loadIndexFolders(workDirectory, this.extension.getIndexName().get());
            this.factoryEntities(project, this.entityFactories, this.entities, this.extension, indexFolders);
        }

        var deployDirectory = this.extension.getDeployDirectory().getAsFile().get();
        if (!deployDirectory.exists()) deployDirectory.mkdirs();

        this.factoryDeployerTasks(project, taskFactories, tasks, entities);
        this.registerDeployerTasks(project, tasks, deployTask);
    }

    EntityFactoryContainer getEntityFactories()
    {
        return this.entityFactories;
    }

    EntityContainer getEntities()
    {
        return this.entities;
    }

    DeployerTaskFactoryContainer getTaskFactories()
    {
        return this.taskFactories;
    }

    DeployerTaskContainer getTasks()
    {
        return this.tasks;
    }

    DeployerExtension getExtension()
    {
        return this.extension;
    }

    private TaskProvider<DeployTask> registerDeployTask(Project project)
    {
        return project.getTasks().register(DeployTask.NAME, DeployTask.class, task -> {
            task.setGroup(DeployerPlugin.TASKS_GROUP);
            task.setDescription(DeployTask.DESCRIPTION);
        });
    }

    @SuppressWarnings("unchecked")
    private void factoryEntities(Project project, EntityFactoryContainer entityFactories, EntityContainer entities, DeployerExtension extension, Collection<IndexFolder> indexFolders)
    {
        if (indexFolders == null) return;

        indexFolders.forEach(indexFolder -> {
            var destination = DestinationUtil.moveDestination(extension.getWorkDirectory().getAsFile().get(), extension.getDeployDirectory().getAsFile().get(), indexFolder.getDestination());
            indexFolder.getConfigs().forEach(config -> {
                if (!config.contains(DeployerPlugin.CONFIG_NAMESPACE)) return;
                var namespace = (Collection<Config>) config.get(DeployerPlugin.CONFIG_NAMESPACE);
                namespace.forEach(userdata -> {
                    var entityFactory = this.findEntityFactory(entityFactories, (String) userdata.get(DeployerPlugin.CONFIG_TYPE));
                    entities.getEntities().add(entityFactory.entity(project.getObjects(), destination, userdata));
                });
            });
        });
    }

    private void factoryDeployerTasks(Project project, DeployerTaskFactoryContainer taskFactories, DeployerTaskContainer tasks, EntityContainer entities)
    {
        entities.getEntities().forEach(entity -> {
            var deployerTaskFactory = this.findDeployerTaskFactory(taskFactories, entity.getClass());
            tasks.getTasks().add(deployerTaskFactory.task(project, entity));
        });
    }

    private void registerDeployerTasks(Project project, DeployerTaskContainer tasks, TaskProvider<DeployTask> deployTask)
    {
        tasks.getTasks().forEach(task -> {
            project.getTasks().add(task);
            deployTask.get().dependsOn(task);
        });
    }

    private EntityFactoryBase findEntityFactory(EntityFactoryContainer entityFactories, String classSimpleName)
    {
        return entityFactories.stream().filter(factory -> factory.getEntityClass().get().getSimpleName().equals(classSimpleName)).findFirst().get();
    }

    private DeployerTaskFactoryBase findDeployerTaskFactory(DeployerTaskFactoryContainer taskFactories, Class<? extends EntityBase> entityClass)
    {
        return taskFactories.stream().filter(factory -> factory.getEntityClass().get().isAssignableFrom(entityClass)).findFirst().get();
    }
}
