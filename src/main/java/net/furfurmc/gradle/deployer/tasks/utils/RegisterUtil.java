package net.furfurmc.gradle.deployer.tasks.utils;

import java.io.File;
import java.util.Collection;
import net.furfurmc.gradle.deployer.DeployerProjectPlugin;
import net.furfurmc.gradle.deployer.common.DeployFolder;
import net.furfurmc.gradle.deployer.common.DestinationUtil;
import net.furfurmc.gradle.deployer.common.NightConfigLoader;
import net.furfurmc.gradle.deployer.common.NightConfigUtil;
import net.furfurmc.gradle.deployer.deployers.CurseforgeDeployer;
import net.furfurmc.gradle.deployer.deployers.GitHubDeployer;
import net.furfurmc.gradle.deployer.deployers.InstallDeployer;
import net.furfurmc.gradle.deployer.deployers.MakefileDeployer;
import net.furfurmc.gradle.deployer.deployers.ModrinthDeployer;
import net.furfurmc.gradle.deployer.deployers.WebDeployer;

public class RegisterUtil
{
    static public void registerDeployers(DeployerProjectPlugin minePlugin, File cacheDirectory)
    {
        minePlugin.registerDeployer(new InstallDeployer(cacheDirectory));
        minePlugin.registerDeployer(new MakefileDeployer(cacheDirectory));
        minePlugin.registerDeployer(new WebDeployer(cacheDirectory));
        minePlugin.registerDeployer(new CurseforgeDeployer(cacheDirectory));
        minePlugin.registerDeployer(new GitHubDeployer(cacheDirectory));
        minePlugin.registerDeployer(new ModrinthDeployer(cacheDirectory));
    }

    static public void registerEntities(DeployerProjectPlugin minePlugin, File workDirectory, File deployDirectory, Collection<DeployFolder> deployFolders)
    {
        var nightConfigLoader = new NightConfigLoader();
        for (var deployFolder : deployFolders)
        {
            var destination = DestinationUtil.moveDestination(workDirectory, deployDirectory, deployFolder.getDestination());
            for (File configFile : deployFolder.getConfigFiles())
            {
                try
                {
                    var nightConfig = nightConfigLoader.load(configFile);
                    var analyzer    = minePlugin.getAnalyzer(NightConfigUtil.getMethod(nightConfig));
                    var mineConfig  = analyzer.analyze(deployFolder.getDestination(), NightConfigUtil.getUserdata(nightConfig));

                    mineConfig.name        = NightConfigUtil.getName(nightConfig);
                    mineConfig.fileName    = NightConfigUtil.getFileName(nightConfig);
                    mineConfig.destination = destination;
                    
                    minePlugin.registerEntity(mineConfig);
                }
                catch (Exception exception)
                {
                    throw new RuntimeException("Fail register " + configFile.toString() + ".", exception);
                }
            }
        }
    }
}
