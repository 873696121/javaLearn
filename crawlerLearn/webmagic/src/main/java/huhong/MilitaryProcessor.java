package huhong;

import org.apache.commons.io.FileUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MilitaryProcessor implements PageProcessor {

    private static final String url = "http://mil.news.sina.com.cn/roll/index.d.html?cid=57919&page=1";

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        List<String> all = html.css("ul.linkNews").links().all();
        if(all.size() == 0){
            // 获取文章
            StringBuilder builder = new StringBuilder();
            List<String> s = html.xpath("//div[@id=article]//p/text()").all();
            String title = html.xpath("//h1[@class=main-title]/text()").toString();
            for (String s1 : s) {
                builder.append(s1);
            }
            String article = builder.toString();
            try {
                FileUtils.writeStringToFile(new File("/Users/huhong/爬虫/articles/" + title + ".txt"), article, "utf8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            // 将连接放入队列中，继续获取下一页连接
            for (String s : all) {
                page.addTargetRequest(s);
            }
            String next = page.getHtml().css("span.pagebox_next").links().toString();
            page.addTargetRequest(next);
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
        Spider spider = Spider.create(new MilitaryProcessor())
                .addUrl(url)  //设置爬取数据的页面
//                .addPipeline(new FilePipeline("/Users/huhong/爬虫/articles"))
                .thread(5);

        //执行爬虫
        spider.run();
    }
}
