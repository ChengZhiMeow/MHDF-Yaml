package cn.chengzhimeow.ccyaml;

import cn.chengzhimeow.ccyaml.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;

import java.io.File;

public abstract class YamlTest {
    public final File parent;
    public final CCYaml yamlManager;
    public final ClassLoader loader;

    public YamlTest() {
        this.parent = new File("test");
        if (this.parent.exists()) this.parent.mkdirs();

        this.yamlManager = new CCYaml(this.getClass().getClassLoader(), this.parent, "1.0.0");
        this.loader = this.getClass().getClassLoader();
    }

    public void checkNotNull(ConfigurationSection section, String name, Object object) {
        Assertions.assertNotNull(object, "没有加载 " + (section != null ? section.getPath() + "." + name : name) + " 配置节点");
    }

    public void checkValueType(ConfigurationSection section, String name, Object object, Class<?> clazz) {
        Assertions.assertInstanceOf(clazz, object, "没有正确解析 " + (section != null ? section.getPath() + "." + name : name) + " 的类型");
    }

    public void checkValue(ConfigurationSection section, String name, @Nullable Object object, @NotNull Object value) {
        if (object == null) {
            Assertions.fail("找不到 " + (section != null ? section.getPath() + "." + name : name) + " 的数据");
            return;
        }
        // noinspection ConstantValue
        Assertions.assertTrue(
                object.equals(value) || object == value,
                "没有正确解析 " + (section != null ? section.getPath() + "." + name : name) + " 的数据\n" +
                        "当前数据: " + object + "\n" +
                        "期望数据: " + value
        );
    }
}
