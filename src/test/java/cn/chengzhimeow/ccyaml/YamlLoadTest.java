package cn.chengzhimeow.ccyaml;

import cn.chengzhimeow.ccyaml.configuration.yaml.YamlConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class YamlLoadTest extends CheckTestYaml {
    @Test
    public void loadTest() {
        this.yamlManager.getFileManager().saveResource("test.yml", "load.yml", true);
        File file = new File(this.parent, "load.yml");
        Assertions.assertTrue(file.exists(), "找不到输出文件");

        try {
            YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
            super.checkTestConfiguration(yamlConfiguration);
        } catch (IOException e) {
            Assertions.fail("无法正常加载配置文件", e);
        }
    }
}
