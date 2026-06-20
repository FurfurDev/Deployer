package net.furfurmc.gradle.deployer.common;

import java.io.File;
import java.util.Arrays;
import org.apache.commons.io.FilenameUtils;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.file.FileNotFoundAction;
import com.electronwill.nightconfig.core.io.ConfigParser;
import com.electronwill.nightconfig.hocon.HoconFormat;
import com.electronwill.nightconfig.json.JsonFormat;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.yaml.YamlFormat;

public class NightConfigLoader
{
    public static final String[] SUPPORTED_EXTENSIONS = { "json", "hocon", "toml", "yaml" };

    ConfigParser<?> jsonParser;
    ConfigParser<?> hoconParser;
    ConfigParser<?> tomlParser;
    ConfigParser<?> yamlParser;

    public NightConfigLoader()
    {
        this.jsonParser  = JsonFormat.fancyInstance().createParser();
        this.hoconParser = HoconFormat.instance().createParser();
        this.tomlParser  = TomlFormat.instance().createParser();
        this.yamlParser  = YamlFormat.defaultInstance().createParser();
    }

    public boolean check(File configFile)
    {
        return Arrays.asList(NightConfigLoader.SUPPORTED_EXTENSIONS).contains(NightConfigLoader.makeFileExtension(configFile));
    }

    public UnmodifiableConfig load(File configFile)
    {
        switch (NightConfigLoader.makeFileExtension(configFile))
        {
            case "json":  return this.loadJson(configFile);
            case "hocon": return this.loadHocon(configFile);
            case "toml":  return this.loadToml(configFile);
            case "yaml":  return this.loadYaml(configFile);
            default: return null;
        }
    }

    private UnmodifiableConfig loadJson(File configFile)
    {
        return this.jsonParser.parse(configFile, FileNotFoundAction.THROW_ERROR);
    }

    private UnmodifiableConfig loadHocon(File configFile)
    {
        return this.hoconParser.parse(configFile, FileNotFoundAction.THROW_ERROR);
    }

    private UnmodifiableConfig loadToml(File configFile)
    {
        return this.tomlParser.parse(configFile, FileNotFoundAction.THROW_ERROR);
    }

    private UnmodifiableConfig loadYaml(File configFile)
    {
        return this.yamlParser.parse(configFile, FileNotFoundAction.THROW_ERROR);
    }

    private static String makeFileExtension(File configFile)
    {
        return FilenameUtils.getExtension(configFile.toString());
    }
}
