package net.furfurmc.gradle.deployer.entity;

import org.gradle.api.provider.Property;
import org.gradle.api.tasks.Input;

public interface MakefileEntity extends EntityBase
{
    @Input
    Property<String> getContent();
}
