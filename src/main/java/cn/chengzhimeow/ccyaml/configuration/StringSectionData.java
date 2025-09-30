package cn.chengzhimeow.ccyaml.configuration;

import org.jetbrains.annotations.Nullable;

public class StringSectionData extends StringSection {
    private final @Nullable String value;

    public StringSectionData(@Nullable String value) {
        this.value = value;
    }

    @Override
    public @Nullable String getValue() {
        return this.value;
    }
}
