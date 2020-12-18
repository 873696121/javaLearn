package huhong;

import huhong.pojo.Category;
import huhong.pojo.Entity;
import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// 爬取维基百科的军事

public class WikipediaProcessor implements PageProcessor {

    private static final String url = "https://bk.tw.lvfukeji.com/baike-Category:%E5%86%9B%E4%BA%8B";

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        String title = html.xpath("//h1/text()").toString();
        System.out.println(title);
        if(title.contains(":")){

            // 分类
            String name = title.split(":")[1];
            Category category = new Category();
            category.setName(name);
            category.setUrl(url);
            List<Category> subCategories = new ArrayList<Category>();
            List<Entity> entities = new ArrayList<Entity>();
            List<Selectable> nodes = html.xpath("//div[@id=\"mw-subcategories\"]//div[@class=\"mw-category-group\"]//div[@class=\"CategoryTreeItem\"]").nodes();
            for (Selectable node : nodes) {
                String nodeName = node.xpath("a/text()").toString();
                String nodeUrl = node.xpath("a/@href").toString();
//                page.addTargetRequest(nodeUrl);
                Category subcategory = new Category();
                subcategory.setUrl(nodeUrl);
                subcategory.setName(nodeName);
                subCategories.add(subcategory);
            }

            List<Selectable> nodes1 = html.xpath("//div[@id=\"mw-pages\"]//div[@class=\"mw-category-group\"]//li").nodes();
            for (Selectable node : nodes1) {
                String nodeName = node.xpath("a/text()").toString();
                String nodeUrl = node.xpath("a/@href").toString();
                page.addTargetRequest(nodeUrl);
                Entity entity = new Entity();
                entity.setName(nodeName);
                entity.setUrl(nodeUrl);
                entities.add(entity);
            }
            category.setSubEntities(entities);
            category.setSubCategories(subCategories);
            try {
                FileUtils.writeStringToFile(new File("/Users/huhong/temp/1.txt"), category.toString() + "\n", "utf8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            // 实体
            Entity entity = new Entity();
            entity.setUrl(url);
            String content = "";
            List<Selectable> nodes = html.xpath("//p/text()").nodes();
            for (Selectable node : nodes) {
                content += node.toString();
            }

            entity.setContent(content);
            entity.setName(title);
            try {
                FileUtils.writeStringToFile(new File("/Users/huhong/temp/1.txt"), entity.toString() + "\n", "utf8", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Site site = Site.me()
            .addHeader("user-agent" ,"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36")
            .setRetrySleepTime(1000);

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        Spider spider = Spider.create(new WikipediaProcessor())
                .addUrl(url)  //设置爬取数据的页面
//                .addPipeline(new FilePipeline("/Users/huhong/爬虫/articles"))
                .thread(5);

        //执行爬虫
        spider.run();
    }
}
