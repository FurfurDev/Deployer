// EntityBase.java : main
package net.furfurmc.gradle.deployer.entity;

import org.gradle.api.Named;
import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Internal;
import org.gradle.api.tasks.OutputFile;

public interface EntityBase extends Named
{
    @OutputFile
    RegularFileProperty getDeployFile();

    @Internal
    Property<String> getGroup();
}
