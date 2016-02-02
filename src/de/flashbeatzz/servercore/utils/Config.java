package de.flashbeatzz.servercore.utils;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Config {

    private File file;
    private YamlConfiguration yaml;

    public Config(String fileName, String folderName) {
        File folder = new File("plugins/" + folderName);
        this.file = new File(folder + "/" + fileName + ".yml");
        if(!folder.exists()) {
            folder.mkdir();
        }
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        yaml = YamlConfiguration.loadConfiguration(file);
    }

    public String getString(String path) {
        return yaml.getString(path);
    }

    public Boolean getBoolean(String path) {
        return yaml.getBoolean(path);
    }

    public Integer getInt(String path) {
        return yaml.getInt(path);
    }

    public List<String> getStringList(String path) {
        return yaml.getStringList(path);
    }

    public Double getDouble(String path) {
        return yaml.getDouble(path);
    }

    public Float getFloat(String path) {
        return (float) yaml.getInt(path);
    }

    public Object get(String path) {
        return yaml.get(path);
    }

    public void addDefault(String path, Object value) {
        yaml.addDefault(path, value);
    }

    public YamlConfiguration getYamlConfiguration() {
        return this.yaml;
    }

    public File getFile() {
        return this.file;
    }

    public void copyAndSave(Boolean b) {
        yaml.options().copyDefaults(b);
        try {
            yaml.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
