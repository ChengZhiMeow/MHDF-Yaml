package cn.chengzhimeow.ccyaml.configuration.yaml;

import cn.chengzhimeow.ccyaml.configuration.ConfigurationSection;
import cn.chengzhimeow.ccyaml.configuration.MemoryConfiguration;
import cn.chengzhimeow.ccyaml.configuration.SectionData;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.comments.CommentType;
import org.yaml.snakeyaml.nodes.*;
import org.yaml.snakeyaml.representer.Represent;
import org.yaml.snakeyaml.representer.Representer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("unused")
public class YamlRepresenter extends Representer {
    @Getter
    private final @NotNull List<Integer> foldLineList = new ArrayList<>();

    public YamlRepresenter(DumperOptions options) {
        super(options);
        this.representers.put(YamlStringSectionData.class, new StringSectionDataRepresenter(this));
        this.representers.put(ConfigurationSection.class, new ConfigurationSectionRepresenter(this));
        this.representers.put(MemoryConfiguration.class, new ConfigurationSectionRepresenter(this));
        this.representers.put(YamlConfiguration.class, new ConfigurationSectionRepresenter(this));
    }

    @Override
    public Node representScalar(Tag tag, String value, DumperOptions.ScalarStyle style) {
        return super.representScalar(tag, value, style);
    }

    private record StringSectionDataRepresenter(
            YamlRepresenter representer
    ) implements Represent {
        @Override
        public Node representData(Object data) {
            YamlStringSectionData styledString = (YamlStringSectionData) data;
            String value = styledString.getValue();

            if (value != null && styledString.value().getScalarStyle() == DumperOptions.ScalarStyle.FOLDED) {
                this.representer.getFoldLineList().add(styledString.value().getStartMark().getLine());
                return this.representer.representScalar(Tag.STR, value.replace(" ", "\n"), DumperOptions.ScalarStyle.LITERAL);
            } else return this.representer.representScalar(Tag.STR, value, styledString.value().getScalarStyle());
        }
    }

    private record ConfigurationSectionRepresenter(
            YamlRepresenter representer
    ) implements Represent {
        private @NotNull MappingNode mapToMappingNode(@NotNull Map<String, SectionData> map) {
            List<NodeTuple> tupleList = new ArrayList<>();

            for (Map.Entry<String, SectionData> entry : map.entrySet()) {
                Node keyNode = this.representer.represent(entry.getKey());
                Node valueNode;

                SectionData sectionData = entry.getValue();
                Object data = sectionData.getData();
                if (data instanceof Map<?, ?> v) // noinspection unchecked
                    valueNode = this.mapToMappingNode((Map<String, SectionData>) v);
                else valueNode = this.representer.represent(data);

                // 应用注释
                List<String> commentList = sectionData.getCommentList();
                if (commentList.isEmpty()) keyNode.setBlockComments(null);
                else keyNode.setBlockComments(YamlConfiguration.getCommentLines(commentList, CommentType.BLOCK));

                List<String> inlineCommentList = sectionData.getInlineCommentList();
                if (valueNode instanceof MappingNode || valueNode instanceof SequenceNode)
                    if (inlineCommentList.isEmpty()) keyNode.setInLineComments(null);
                    else
                        keyNode.setInLineComments(YamlConfiguration.getCommentLines(inlineCommentList, CommentType.IN_LINE));
                else if (inlineCommentList.isEmpty()) valueNode.setInLineComments(null);
                else
                    valueNode.setInLineComments(YamlConfiguration.getCommentLines(inlineCommentList, CommentType.IN_LINE));

                tupleList.add(new NodeTuple(keyNode, valueNode));
            }

            return new MappingNode(Tag.MAP, tupleList, DumperOptions.FlowStyle.BLOCK);
        }

        @Override
        public Node representData(Object data) {
            ConfigurationSection configurationSection = (ConfigurationSection) data;
            SectionData sectionData = configurationSection.getData();
            // noinspection unchecked
            MappingNode node = mapToMappingNode((Map<String, SectionData>) Objects.requireNonNull(sectionData.getData()));
            node.setBlockComments(YamlConfiguration.getCommentLines(sectionData.getCommentList(), CommentType.BLOCK));
            node.setInLineComments(YamlConfiguration.getCommentLines(sectionData.getInlineCommentList(), CommentType.IN_LINE));
            node.setEndComments(YamlConfiguration.getCommentLines(sectionData.getEndCommentList(), CommentType.BLOCK));

            return node;
        }
    }
}
