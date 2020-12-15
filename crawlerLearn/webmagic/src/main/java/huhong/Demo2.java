package huhong;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class Demo2 {
    public static void main(String[] args) throws Exception{
        String content = FileUtils.readFileToString(new File("/Users/huhong/workspace/c++Projects/MyAlgorithm/0.cpp"), "utf8");
        for (int i = 1; i <= 44; i++) {
            FileUtils.writeStringToFile(new File("/Users/huhong/workspace/c++Projects/MyAlgorithm/" + i +".cpp"), content, "utf8");
        }
    }
}
