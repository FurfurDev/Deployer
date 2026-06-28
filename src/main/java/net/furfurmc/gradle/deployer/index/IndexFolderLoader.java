// IndexFolderLoader.java : main
package net.furfurmc.gradle.deployer.index;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.FalseFileFilter;

public class IndexFolderLoader
{
    public IndexFolder loadIndexFolder(File destination, String indexName)
    {
        var indexDirectory = new File(destination, indexName);
        if (!indexDirectory.exists()) return null;

        var files   = FileUtils.listFiles(indexDirectory, IndexConfig.EXTENSIONS, false);
        var configs = new ArrayList<IndexConfig>();
        
        files.forEach(file -> {
            configs.add(new IndexConfig(file));
        });

        if (configs.isEmpty()) return null;
        return new IndexFolder(configs, destination);
    }

    public Collection<IndexFolder> loadIndexFolders(File destination, String indexName)
    {
        var directories  = FileUtils.listFilesAndDirs(destination, FalseFileFilter.INSTANCE, DirectoryFileFilter.DIRECTORY);
        var indexFolders = new ArrayList<IndexFolder>();

        directories.forEach(directory -> {
            var indexDirectory = new File(directory, indexName);
            if (indexDirectory.exists()) indexFolders.add(this.loadIndexFolder(directory, indexName));
        });
        
        if (indexFolders.isEmpty()) return null;
        return indexFolders;
    }
}
