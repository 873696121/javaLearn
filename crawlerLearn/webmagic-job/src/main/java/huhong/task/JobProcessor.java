package huhong.task;

import huhong.pojo.JobInfo;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

@Component
public class JobProcessor implements PageProcessor {

    private final String url1 = "https://www.zhipin.com/c101190400/?query=java&page=2&ka=page-2";
    private String cookie = "lastCity=101190400; t=rGu0xlk0ThnlGiah; wt=rGu0xlk0ThnlGiah; wt2=rGu0xlk0ThnlGiah; _bl_uid=2sk30iym4F06363yjmpn2R8ok10d; __g=sem; Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1606396205,1607420307,1607689771,1607689778; __l=l=%2Fwww.zhipin.com%2Fc101190400%2F%3Fquery%3Djava%26page%3D3&r=https%3A%2F%2Fwww.baidu.com%2Fbaidu.php%3Furl%3DKf0000K5cNxA6dzipyZahB8blxmrnDx_PNLaG3r8aQMdOM8JkqTdWKiavm5j5TEzm7ySRAhUN0d8plGi9iE5EJSxWxui3Po0g0PpTmxrSorfrKrUUXxl-pRHNIBVNNqHzCDYPHliWETCn24fGRYTxAmKAz4ZqtR-l1wP_T_1IIjSQmBs8TF1hG478MGRXuwyOkxEf1FNPXbeM-H3rhDVd3reE0Ig.DR_NR2Ar5Od663rj6t8AGSPticrZA1AlaqM766WHGek3hcYlXE_sgn8mE8kstVerQKMks4OgSWS534Oqo4yunOogEOtZV_zyUr1oWC_knmx5I9LtTrzEj4SrZuEse59sSX1jexo9vxQ5jWl3cMYAn5M8seSrZug9tOZj_L3IMs4t5MEseQnrOv3x5kseS1jeIMgVHC3ZHgng8WWlsk8sHfGmEIjfEl1F8xnhA6kNfCm3t5Zv3TMds45osTZK4TPHtU3bmTMdWHGs45ogu1RojPakvTXMkv20.U1Yk0ZDqmhq1TsKspynqn0KY5yFETLn0pyYqnWcd0ATqUvwlnfKdpHdBmy-bIfKspyfqnHb0mv-b5HR40AdY5HDsnH-xnH0kPdtznjRkg1DsnWPxn1Dzn7tknjfYg1nvnjD0pvbqn0KzIjY1njR0mhbqnHR3g1csP7tznHT0UynqnH0snNtkrjm4nHTdPHbsg1csPH7xnNtknjFxnH0zg100TgKGujYs0Z7Wpyfqn0KzuLw9u1Ys0A7B5HKxn0K-ThTqn0KsTjY1nWndrHRkrHT0UMus5H08nj0snj0snj00Ugws5H00uAwETjYs0ZFJ5H00uANv5gKW0AuY5H00TA6qn0KET1Ys0AFL5HDs0A4Y5H00TLCq0A71gv-bm1dsTzdBuAw30A-bm1dcfbR0IA7zuvNY5Hm1g1KxnHRs0ZwdT1YkPWTvPjb3nWfsrj04njmYPHTznsKzug7Y5HDvnjTvrjbLPW6vPjD0Tv-b5yDLnjubPyfvnj0snAn4PAf0mLPV5HRkrjm1PDnkwbcsPH7Dwjc0mynqnfKsUWYs0Z7VIjYs0Z7VT1Ys0Aw-I7qWTADqn0KlIjYs0AdWgvuzUvYqn7tsg1Kxn7tsg1DsPjuxn0Kbmy4dmhNxTAk9Uh-bT1Ysg1Kxn7tsg1f1nH04rHNxPjnknjb4PNts0ZK9I7qhUA7M5H00uAPGujYs0ANYpyfqQHD0mgPsmvnqn0KdTA-8mvnqn0KkUymqn0KhmLNY5H00pgPWUjYs0ZGsUZN15H00mywhUA7M5HD0UAuW5H00uAPWujY0IZF9uARqP1msnW0z0AFbpyfqPH6YfRujfWK7P1TvwjFanbD1rHnvrR7jwj6YPjn1wWD0UvnqnfKBIjYs0Aq9IZTqn0KEIjYk0AqzTZfqnBnsc1Dsc1cWrj0zrHnsnWDWnWfsnj0WnWfsnj08nj0snj0sc1DWnBnsczYWna3snj6LPjTWni3snH0snj00TNqv5H08rHFxna3sn7tsQW0sg108rHwxna3vrNtsQWn10AF1gLKzUvwGujYs0APzm1YdPWT4Ps%26word%3D%26ck%3D8858.4.38.432.183.322.179.110%26shh%3Dwww.baidu.com%26wd%3D%26bc%3D110101%26us%3D1.6975.2.0.0.0.0.0&g=%2Fwww.zhipin.com%2Fsem%2F10.html%3Fsid%3Dsem%26qudao%3Dbdpc_baidu-pc-BOSS-JD02-B19KA02084%26plan%3D%25E5%2593%2581%25E7%2589%258C%25E8%25AF%258D-cp%26unit%3D%25E5%2593%2581%25E7%2589%258C-%25E9%2580%259A%25E7%2594%25A8%26keyword%3Dboss%26bd_vid%3D7627654083839185479%26csource%3Dboctb&s=3&friend_source=0&s=3&friend_source=0; __c=1607616128; __a=95634102.1605532531.1607420306.1607616128.53.4.31.29; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1607696196; __zp_stoken__=7fdbbIzJgBXUhLVtfGC09eGUNBVtsbixhTjp2JR4tO3UtR2VKZUwnIUpLfWMeeDs6GhxuTiVHf0cfJBMXUkQxCjw1IxdzJ2oJNDM0agA8DBxYXUsDCRhBB3sxHlV%2BBy5MHE1kXX9Oe040WFF0RQ%3D%3D";
    @Override
    public void process(Page page) {
        String html = page.getHtml().toString();
        System.out.println(html.length());
        // 解析页面，获取招聘信息详情的url地址
        List<Selectable> list = page.getHtml().css("div.job-list ul li").nodes();

        // 判断获取的集合是否为空
        if(list.size() == 0){
            // 如果为空表示是详情页
            // 解析页面获取招聘详情信息,保存数据
            this.saveJobInfo(page);
        }
        else{
            // 如果不为空表示是列表页
            // 解析出详情页的url地址放到任务队列中
            for (Selectable selectable : list) {
                // 获取url地址
                String jobInfoUrl = selectable.links().toString();
                page.addTargetRequest(jobInfoUrl);
            }
            // 获取下一页的url
            String next = page.getHtml().css("div.page a.next").links().toString();
            // 把url放到任务队列中
            page.addTargetRequest(next);
        }

    }


    // 解析页面获取招聘详情信息,保存数据
    private void saveJobInfo(Page page) {
        // 创建招聘详情对象
        JobInfo jobInfo = new JobInfo();
        // 解析页面
        Html html = page.getHtml();
        // 获取数据，封装到对象中





    }

    private Site site = Site.me()
//            .setCharset("utf8")
            .addHeader("user-agent", "Baiduspider")
//            .addHeader("upgrade-insecure-requests", "1")
//            .addHeader("sec-fetch-user", "?1")
//            .addHeader("sec-fetch-site", "same-origin")
//            .addHeader("sec-fetch-mode", "navigate")
//            .addHeader("sec-fetch-dest", "document")
//            .addHeader("cookie", cookie)
//            .addHeader("accept-language", "zh-CN,zh;q=0.9")
//            .addHeader("accept-encoding", "gzip, deflate, br")
//            .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
            .setTimeOut(10 * 1000)
            .setSleepTime(1000)
            .setRetrySleepTime(3 * 1000)
            .setRetryTimes(3);


    @Override
    public Site getSite() {
        return site;
    }

    @Scheduled(initialDelay = 1000, fixedDelay = 100 * 1000)
    public void run(){
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
        httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("60.246.207.194", 80)));



        Spider.create(new JobProcessor())
                .addUrl(url1)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(10)
                .setDownloader(httpClientDownloader)
                .run();
    }
}
