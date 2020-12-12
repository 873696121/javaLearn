package huhong;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

import java.util.List;

public class MilitaryProcessor implements PageProcessor {

    private static final String url = "http://mil.news.sina.com.cn/roll/index.d.html?cid=57919&page=1";

    @Override
    public void process(Page page) {

    }

    private Site site = Site.me()
            .setRetrySleepTime(1 * 1000);

    @Override
    public Site getSite() {
        return site;
    }


    public static void main(String[] args) {
        Spider spider = Spider.create(new MilitaryProcessor())
                .addUrl(url)  //设置爬取数据的页面
                //.addPipeline(new FilePipeline("C:\\Users\\tree\\Desktop\\result"))
                .thread(5);

        //执行爬虫
        spider.run();
    }
}
