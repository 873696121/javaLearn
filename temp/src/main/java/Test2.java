import java.io.File;

public class Test2 {
    public static void main(String[] args) {
        for(int i = 1000; i <= 1043; i ++){
            File file = new File("/Users/huhong/develop/c++Project/algorithm/" + i);
            file.mkdir();
        }
    }
}
