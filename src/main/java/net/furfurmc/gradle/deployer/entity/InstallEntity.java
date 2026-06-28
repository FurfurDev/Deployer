// InstallEntity.java : main
package net.furfurmc.gradle.deployer.entity;

import org.gradle.api.file.RegularFileProperty;
import org.gradle.api.tasks.InputFile;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

public interface InstallEntity extends EntityBase
{
    @InputFile
    @PathSensitive(PathSensitivity.RELATIVE)
    RegularFileProperty getOriginFile();
}
