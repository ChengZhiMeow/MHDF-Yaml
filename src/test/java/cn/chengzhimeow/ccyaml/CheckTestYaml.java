package cn.chengzhimeow.ccyaml;

import cn.chengzhimeow.ccyaml.configuration.ConfigurationSection;
import cn.chengzhimeow.ccyaml.configuration.MemoryConfiguration;
import cn.chengzhimeow.ccyaml.configuration.SectionData;
import cn.chengzhimeow.ccyaml.configuration.yaml.YamlConfiguration;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class CheckTestYaml extends YamlTest {
    public void checkTestSection(@Nullable ConfigurationSection section, String name) {
        if (section == null) {
            Assertions.fail("找不到 " + name + " 的数据");
            return;
        }

        {
            Object emptyList = section.get("empty_list");
            this.checkNotNull(section, "empty_list", emptyList);
            this.checkValueType(section, "empty_list", emptyList, List.class);
            this.checkValue(section, "empty_list", emptyList, new ArrayList<>());
        }

        {
            ConfigurationSection empty_config = section.getConfigurationSection("empty_config");
            this.checkNotNull(section, "empty_config", empty_config);
            this.checkValueType(section, "empty_config", empty_config, ConfigurationSection.class);
            this.checkValue(section, "empty_config", empty_config, MemoryConfiguration.empty());
        }

        {
            Object _null = section.get("null");
            Assertions.assertNull(_null, "没有正确解析 " + section.getPath() + ".null 的数据, 当前数据: " + _null + ", 期望数据: " + null);
        }

        {
            Object string_literal = section.get("string_literal");
            this.checkNotNull(section, "string_literal", string_literal);
            this.checkValueType(section, "string_literal", string_literal, String.class);
            this.checkValue(section, "string_literal", string_literal, "123\n321\n");
        }

        {
            Object string_literal_remove_end_newline = section.get("string_literal_remove_end_newline");
            this.checkNotNull(section, "string_literal_remove_end_newline", string_literal_remove_end_newline);
            this.checkValueType(section, "string_literal_remove_end_newline", string_literal_remove_end_newline, String.class);
            this.checkValue(section, "string_literal_remove_end_newline", string_literal_remove_end_newline, "123\n321");
        }

        {
            Object string_folded = section.get("string_folded");
            this.checkNotNull(section, "string_folded", string_folded);
            this.checkValueType(section, "string_folded", string_folded, String.class);
            this.checkValue(section, "string_folded", string_folded, "123 321\n");
        }

        {
            Object string_folded_remove_end_newline = section.get("string_folded_remove_end_newline");
            this.checkNotNull(section, "string_folded_remove_end_newline", string_folded_remove_end_newline);
            this.checkValueType(section, "string_folded_remove_end_newline", string_folded_remove_end_newline, String.class);
            this.checkValue(section, "string_folded_remove_end_newline", string_folded_remove_end_newline, "123 321");
        }

        {
            Long _long = section.getLong("long");
            this.checkNotNull(section, "long", _long);
            this.checkValueType(section, "long", _long, Long.class);
            this.checkValue(section, "long", _long, 1000L);
        }

        {
            Integer _int = section.getInt("int");
            this.checkNotNull(section, "int", _int);
            this.checkValueType(section, "int", _int, Integer.class);
            this.checkValue(section, "int", _int, 1314);
        }

        {
            Short _short = section.getShort("short");
            this.checkNotNull(section, "short", _short);
            this.checkValueType(section, "short", _short, Short.class);
            this.checkValue(section, "short", _short, (short) 1);
        }

        {
            Double _double = section.getDouble("double");
            this.checkNotNull(section, "double", _double);
            this.checkValueType(section, "double", _double, Double.class);
            this.checkValue(section, "double", _double, 0d);
        }

        {
            Double int_to_double = section.getDouble("int_to_double");
            this.checkNotNull(section, "int_to_double", int_to_double);
            this.checkValueType(section, "int_to_double", int_to_double, Double.class);
            this.checkValue(section, "int_to_double", int_to_double, 0d);
        }

        {
            Float _float = section.getFloat("float");
            this.checkNotNull(section, "float", _float);
            this.checkValueType(section, "float", _float, Float.class);
            this.checkValue(section, "float", _float, 0f);
        }

        {
            Float int_to_float = section.getFloat("int_to_float");
            this.checkNotNull(section, "int_to_float", int_to_float);
            this.checkValueType(section, "int_to_float", int_to_float, Float.class);
            this.checkValue(section, "int_to_float", int_to_float, 0f);
        }

        {
            Boolean _boolean = section.getBoolean("boolean");
            this.checkNotNull(section, "boolean", _boolean);
            this.checkValueType(section, "boolean", _boolean, Boolean.class);
            this.checkValue(section, "boolean", _boolean, false);
        }

        {
            List<String> string_list = section.getStringList("string_list");
            this.checkNotNull(section, "string_list", string_list);
            this.checkValueType(section, "string_list", string_list, List.class);
            this.checkValue(section, "string_list", string_list, new ArrayList<>(List.of("qwq", "awa")));
        }

        {
            List<String> string_list_folded = section.getStringList("string_list_folded");
            this.checkNotNull(section, "string_list_folded", string_list_folded);
            this.checkValueType(section, "string_list_folded", string_list_folded, List.class);
            this.checkValue(section, "string_list_folded", string_list_folded, new ArrayList<>(List.of("qwq", "awa")));
        }

        {
            List<ConfigurationSection> config_list = section.getConfigurationSectionList("config_list");
            this.checkNotNull(section, "config_list", config_list);
            this.checkValueType(section, "config_list", config_list, List.class);

            MemoryConfiguration config_list_0 = MemoryConfiguration.empty();
            Map<String, Object> data = new LinkedHashMap<>();
            data.put("string", "test");
            config_list_0.getData().setData(data);

            this.checkValue(section, "config_list", config_list, new ArrayList<>(List.of(config_list_0)));
        }

        {
            List<ConfigurationSection> config_list_in_config_list = section.getConfigurationSectionList("config_list_in_config_list");
            this.checkNotNull(section, "config_list_in_config_list", config_list_in_config_list);
            this.checkValueType(section, "config_list_in_config_list", config_list_in_config_list, List.class);

            List<MemoryConfiguration> config_list_in_config_list_0_config_list = new ArrayList<>();
            {
                Map<Object, Object> config_list_in_config_list_0_config_list_0 = new LinkedHashMap<>();
                config_list_in_config_list_0_config_list_0.put("string", new SectionData("qwq"));

                MemoryConfiguration configuration = MemoryConfiguration.empty();
                configuration.getData().setData(SectionData.fromMap(config_list_in_config_list_0_config_list_0));
                config_list_in_config_list_0_config_list.add(configuration);
            }

            MemoryConfiguration config_list_in_config_list_0 = MemoryConfiguration.empty();
            {
                Map<String, Object> data = new LinkedHashMap<>();
                data.put("config_list", new SectionData(config_list_in_config_list_0_config_list));
                config_list_in_config_list_0.getData().setData(data);
            }

            this.checkValue(section, "config_list_in_config_list", config_list_in_config_list, List.of(config_list_in_config_list_0));
        }
    }

    public void checkTestConfiguration(YamlConfiguration yamlConfiguration) {
        {
            ConfigurationSection test = yamlConfiguration.getConfigurationSection("test");
            this.checkNotNull(null, "test", test);
            this.checkTestSection(test, "test");
        }

        {
            ConfigurationSection test_anchor = yamlConfiguration.getConfigurationSection("test_anchor");
            this.checkNotNull(null, "test_anchor", test_anchor);
            this.checkTestSection(test_anchor, "test_anchor");
        }

        {
            ConfigurationSection test_anchor_add = yamlConfiguration.getConfigurationSection("test_anchor_add");
            this.checkNotNull(null, "test_anchor_add", test_anchor_add);
            this.checkTestSection(test_anchor_add, "test_anchor_add");
            assert test_anchor_add != null;

            Object qwq = test_anchor_add.get("qwq");
            this.checkNotNull(test_anchor_add, "qwq", qwq);
            this.checkValueType(test_anchor_add, "qwq", qwq, String.class);
            this.checkValue(test_anchor_add, "qwq", qwq, "qwq");
        }

        {
            List<String> comment = yamlConfiguration.getCommentList("comment");
            this.checkNotNull(null, "comment", comment);
            this.checkValueType(null, "comment", comment, List.class);
            List<String> comment_check_list = new ArrayList<>();
            comment_check_list.add(null);
            comment_check_list.add("常规注释");
            this.checkValue(null, "comment", comment, comment_check_list);

            List<String> comment_inline = yamlConfiguration.getInlineCommentList("comment_inline");
            this.checkNotNull(null, "comment_inline", comment_inline);
            this.checkValueType(null, "comment_inline", comment_inline, List.class);
            this.checkValue(null, "comment_inline", comment_inline, new ArrayList<>(List.of("行内注释")));
        }

        {
            List<String> end_comment = yamlConfiguration.getEndCommentList();
            this.checkNotNull(null, "end_comment", end_comment);
            this.checkValueType(null, "end_comment", end_comment, List.class);
            List<String> comment_check_list = new ArrayList<>();
            comment_check_list.add(null);
            comment_check_list.add("尾部注释");
            comment_check_list.add("尾部注释");
            comment_check_list.add("尾部注释");
            this.checkValue(null, "end_comment", end_comment, comment_check_list);
        }
    }
}
