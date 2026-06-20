package net.furfurmc.gradle.deployer.entities;

import java.io.File;
import java.util.Arrays;

public abstract class AbstractEntity
{
    public String name;
    public String fileName;
    public File   destination;

    @Override
    public String toString()
    {
        var stringBuilder = new StringBuilder();
        var myClass       = this.getClass();

        stringBuilder.append(myClass.getName()).append(":{");

        var fields = Arrays.asList(myClass.getFields());
        for (var field : fields)
        {
            try
            {
                stringBuilder.append(field.getName()).append("=\"").append(field.get(this)).append("\"");
            }
            catch (IllegalArgumentException | IllegalAccessException exception)
            {
                throw new RuntimeException("Fail reflect mine config class: " + myClass.getName(), exception);
            }
            if (!field.equals(fields.getLast())) stringBuilder.append(";");
        }

        stringBuilder.append("}");

        return stringBuilder.toString();
    }
}
