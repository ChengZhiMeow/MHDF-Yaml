package cn.chengzhiya.mhdfyaml.manager;

import cn.chengzhiya.mhdfyaml.MHDFYaml;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public abstract class FolderYamlManager {
    private final MHDFYaml instance;
    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Map<File, YamlConfiguration> fileHashMap;

    public FolderYamlManager(MHDFYaml instance) {
        this.instance = instance;
    }

    /**
     * 获取源文件夹路径
     *
     * @return 源文件夹路径
     */
    abstract public String getOriginFolderPath();

    /**
     * 获取文件夹路径
     *
     * @return 文件夹路径
     */
    abstract public String getFolderPath();

    /**
     * 获取文件夹文件实例
     *
     * @return 文件夹文件实例
     */
    public File getFolder() {
        return new File(this.getInstance().getPlugin().getDataFolder(), this.getFolderPath());
    }

    /**
     * 保存默认文件
     */
    public void saveDefaultFile() {
        this.getInstance().getFileManager().saveFolderResource(this.getOriginFolderPath(), this.getFolderPath(), false);
    }

    /**
     * 保存文件
     */
    @SneakyThrows
    public void save() {
        for (Map.Entry<File, YamlConfiguration> entry : this.getFileHashMap().entrySet()) {
            entry.getValue().save(entry.getKey());
        }
    }

    /**
     * 重载配置
     */
    public void reload() {
        Map<File, YamlConfiguration> fileHashMap = new ConcurrentHashMap<>();
        for (File file : this.getInstance().getFileManager().listFiles(this.getFolder())) {
            fileHashMap.put(file, YamlConfiguration.loadConfiguration(file));
        }
        this.setFileHashMap(fileHashMap);
    }

    /**
     * 获取文件实例列表
     *
     * @return 文件实例列表
     */
    public Set<File> getFileList() {
        return this.getFileHashMap().keySet();
    }

    /**
     * 获取配置实例实例列表
     *
     * @return 配置实例实例列表
     */
    public List<YamlConfiguration> getDataList() {
        return new ArrayList<>(this.getFileHashMap().values());
    }

    /**
     * 获取配置实例
     *
     * @param file 文件实例
     * @return 配置实例
     */
    public YamlConfiguration getData(File file) {
        if (file == null) {
            return null;
        }
        return this.getFileHashMap().get(file);
    }

    /**
     * 获取配置实例
     *
     * @param path 文件路径
     * @return 配置实例
     */
    public YamlConfiguration getData(String path) {
        if (path == null) {
            return null;
        }
        return this.getData(new File(this.getFolder(), path));
    }
}
