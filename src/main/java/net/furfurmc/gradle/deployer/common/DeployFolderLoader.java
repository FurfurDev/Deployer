package net.furfurmc.gradle.deployer.common;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;

public class DeployFolderLoader
{
    public DeployFolder loadDeployFolder(File folder, String indexName)
    {
        var deployFolder = new DeployFolder(folder);
        deployFolder.setIndex(this.getDirectory(folder, indexName));
        deployFolder.setConfigFiles(FileUtils.listFiles(deployFolder.getIndex(), NightConfigLoader.SUPPORTED_EXTENSIONS, false));
        return deployFolder;
    }

    public Collection<DeployFolder> loadDeployFolders(File workDirectory, String indexName)
    {
        var directories   = FileUtils.listFilesAndDirs(workDirectory, FalseFileFilter.INSTANCE, DirectoryFileFilter.DIRECTORY);
        var deployFolders = new ArrayList<DeployFolder>();
        directories.forEach(directory -> { if (directory.getName().equals(indexName)) deployFolders.add(this.loadDeployFolder(directory.getParentFile(), indexName)); });
        return deployFolders;
    }

    public File getDirectory(File folder, String name)
    {
        return FileUtils.getFile(folder, name);
    }
}
