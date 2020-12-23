import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Test {
    public static void main(String[] args) throws IOException {
        File file = new File("/Users/huhong/temp/NG/问题描述.md");
        for(int i = 1000; i <= 1043; i ++){
            String path = "/Users/huhong/workspace/c++Projects/algorithm/" + i + "/" + "问题描述.md";
            FileUtils.copyFile(file, new File(path));
        }
    }
}
