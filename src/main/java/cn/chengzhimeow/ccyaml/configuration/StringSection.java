package cn.chengzhimeow.ccyaml.configuration;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@SuppressWarnings("unused")
public abstract class StringSection {
    abstract public @Nullable String getValue();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!(o instanceof StringSection target)) return false;
        return Objects.equals(target.getValue(), this.getValue());
    }

    @Override
    public @NotNull String toString() {
        String value = this.getValue();
        if (value == null) return "null";
        else return value;
    }
}
