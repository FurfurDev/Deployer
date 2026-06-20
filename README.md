# Deployer

Deployer is a framework for fast and convenient deployment of Minecraft servers using simple config files. The plugin can download files from the Internet, install files from a local folder, download assets via the GitHub Rest API, and download version files via the CurseForge and Modrinth Rest APIs.

## Plugin

### Usage

To add a plugin to a project, use:
```groovy
plugins {
  id 'net.furfurmc.gradle.deployer' version '0.1.0'
}
```

To specify custom `workDirectory` and `deployDirectory` parameters, use:
```groovy
deploy {
    workDirectory   = layout.projectDirectory.dir('server')
    deployDirectory = layout.projectDirectory.dir('run')
}
```

### Parameters

 * `indexName` - sets the alias for the "index" folders. (Default `.deploy`)
 * `cacheDirectory` - sets the cache directory of the plugin. (Default `deplcache`)
 * `workDirectory` - sets the working directory of the plugin. (Default `deplwork`)
 * `deployDirectory` - sets the output directory of the plugin. (Default `deploy`)

### How it works

The plugin reads all directories in the `workDirectory`, extracts all folders with the name: `indexName`, configs with extensions are extracted from the index folders: "json", "hocon", "toml", "yaml". 
Uploaded configs are analyzed by Analyzers forming Deployment Entities. Deployment entities use Deployers to download/install files in the `deployDirectory`.

### Config examples
Json
```json
{
	"deployer": [
		{
			"name":     "Test"
			"filename": "deploy.file",
			"method":   "install",
			"userdata": {
				"origin": "origin.file",
			},
		}
	]
}
```

Hocon
```hocon
deployer: [
	{
		name:     Test
		filename: deploy.file
		method:   install
		userdata {
			origin: origin.file
		}
	}
]
```

Toml
```toml
[[deployer]]
name     = "Test"
filename = "deploy.file"
method   = "install"
userdata = { origin = "origin.file" }
```

Yaml
```yaml
deployer:
- name:     Test
  filename: deploy.file
  method:   install
  userdata: { origin: origin.file }
```

### Config schemes
All configs for deployment have the fields `name`, `filename`, `method`, `userdata` stored in the `deployer` array:
 * `name` - The name of the deployment entity used to generate the document cache.
 * `filename` - the final name of the download/installation file.
 * `method` - the analysis method determines what to do with the deployment entity and the contents of the `userdata` field.
 * `userdata` - unique data for a specific Analyzer.

Install - installing a local file from the directory above the config to the `deployDirectory`.
```toml
[[deployer]]
name     = "string"
filename = "string"
method   = "install"
userdata = { origin = "<file-name>" }
```

Makefile - creating a file from the specified content in the `deployDirectory`.
```toml
[[deployer]]
name     = "string"
filename = "string"
method   = "makefile"
userdata = { content = "<text>" }
```

Web - downloading an Internet file to the `deployDirectory`.
```toml
[[deployer]]
name     = "string"
filename = "string"
method   = "web"
userdata = { url = "<url>" }
```

GitHub - downloading a file via the GitHub Rest API to the `deployDirectory`.
```toml
[[deployer]]
name     = "string"
filename = "string"
method   = "github"
userdata = { owner = "<owner>", repo = "<repo>", tag = "<tag>", asset = "<asset>" }
```

Curseforge - downloading a file via the CurseForge Rest API to the `deployDirectory`.
```toml
[[deployer]]
name     = "string"
filename = "string"
method   = "сurseforge"
userdata = { modid = "<modid>", fileid = "<fileid>" }
```

Modrinth - downloading a file via the Modrinth Rest API to the `deployDirectory`.
```toml
[[deployer]]
name     = "string"
filename = "string"
method   = "modrinth"
userdata = { versionid = "<versionid>", versionfile = "<versionfile>" }
```
