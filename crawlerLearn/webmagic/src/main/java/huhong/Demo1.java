package huhong;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Demo1 implements PageProcessor {

    public void process(Page page) {
        page.putField("1", page.getHtml().css("div.post-content p").all());
    }


    private Site site = Site.me();
    public Site getSite() {
        return site;
    }

    // 主函数执行爬虫
    public static void main(String[] args) {
        Spider.create(new Demo1())
                .addUrl("https://blog.konghy.cn/2017/04/24/python-entry-program/")
                .run();
    }
}
