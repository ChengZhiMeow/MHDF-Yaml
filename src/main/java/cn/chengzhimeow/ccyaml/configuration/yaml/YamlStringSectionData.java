package cn.chengzhimeow.ccyaml.configuration.yaml;

import cn.chengzhimeow.ccyaml.configuration.StringSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.yaml.snakeyaml.nodes.ScalarNode;

@SuppressWarnings("unused")
public class YamlStringSectionData extends StringSection {
    private final @NotNull ScalarNode value;

    public YamlStringSectionData(@NotNull ScalarNode value) {
        this.value = value;
    }

    @Override
    public @Nullable String getValue() {
        return this.value.getValue();
    }

    public @NotNull ScalarNode node() {
        return this.value;
    }
}
