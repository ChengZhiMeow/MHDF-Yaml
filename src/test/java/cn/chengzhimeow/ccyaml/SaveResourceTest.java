package cn.chengzhimeow.ccyaml;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SaveResourceTest extends YamlTest {
    @Test
    public void saveFileTest() {
        String resourcePath = "test.yml";
        String outPath = "save_resource.yml";

        byte[] originData;
        try (InputStream in = super.loader.getResourceAsStream(resourcePath)) {
            Assertions.assertNotNull(in, "找不到测试文件");
            originData = in.readAllBytes();
        } catch (IOException e) {
            Assertions.fail("无法正常读取测试文件", e);
            return;
        }

        this.yamlManager.getFileManager().saveResource(resourcePath, outPath, true);
        File file = new File(this.parent, outPath);
        Assertions.assertTrue(file.exists(), "找不到输出文件");

        try (InputStream in = new FileInputStream(file)) {
            Assertions.assertArrayEquals(in.readAllBytes(), originData, "保存文件和输出文件并不一致");
        } catch (IOException e) {
            Assertions.fail("无法正常读取输出文件", e);
        }
    }
}
