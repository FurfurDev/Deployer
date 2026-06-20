package net.furfurmc.gradle.deployer.tests.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.ConfigFormat;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.io.ConfigWriter;
import com.electronwill.nightconfig.core.io.WritingMode;
import com.electronwill.nightconfig.hocon.HoconFormat;
import com.electronwill.nightconfig.json.JsonFormat;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.yaml.YamlFormat;
import net.furfurmc.gradle.deployer.DeployerPlugin;

public class NightConfigTestUtil
{
    public static void createJsonConfigFile(File file, UnmodifiableConfig uConfig) throws IOException
    {
        ConfigFormat<?> jsonFormat = JsonFormat.fancyInstance();
        ConfigWriter    jsonWriter = jsonFormat.createWriter();
        jsonWriter.write(uConfig, file, WritingMode.REPLACE_ATOMIC);
    }

    public static void createHoconConfigFile(File file, UnmodifiableConfig uConfig) throws IOException
    {
        ConfigFormat<?> hoconFormat = HoconFormat.instance();
        ConfigWriter    hoconWriter = hoconFormat.createWriter();
        hoconWriter.write(uConfig, file, WritingMode.REPLACE_ATOMIC);
    }

    public static void createTomlConfigFile(File file, UnmodifiableConfig uConfig) throws IOException
    {
        ConfigFormat<?> tomlFormat = TomlFormat.instance();
        ConfigWriter    tomlWriter = tomlFormat.createWriter();
        tomlWriter.write(uConfig, file, WritingMode.REPLACE_ATOMIC);
    }

    public static void createYamlConfigFile(File file, UnmodifiableConfig uConfig) throws IOException
    {
        ConfigFormat<?> yamlFormat = YamlFormat.defaultInstance();
        ConfigWriter    yamlWriter = yamlFormat.createWriter();
        yamlWriter.write(uConfig, file, WritingMode.REPLACE_ATOMIC);
    }

    public static Config createExampleConfig()
    {
        var settings = Config.inMemory();
        settings.set(DeployerPlugin.CONFIG_METHOD,    "string");
        settings.set(DeployerPlugin.CONFIG_NAME,      "string");
        settings.set(DeployerPlugin.CONFIG_FILENAME,  "string");

        var userdata = Config.inMemory();
        settings.set(DeployerPlugin.CONFIG_USERDATA, userdata);

        var namespace = new ArrayList<Config>();
        namespace.add(settings);

        var config = Config.inMemory();
        config.set(DeployerPlugin.CONFIG_NAMESPACE, namespace);
        return config;
    }
}
