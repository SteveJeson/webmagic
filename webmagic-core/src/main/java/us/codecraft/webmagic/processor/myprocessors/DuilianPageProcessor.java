package us.codecraft.webmagic.processor.myprocessors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jason on 2017/1/23.
 */
public class DuilianPageProcessor implements PageProcessor{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public void process(Page page) {
        List<String> urls = page.getHtml().$("a").links().regex("http://www.duiduilian.com/.*").all();
        page.addTargetRequests(urls);
        if (page.getHtml().css("div.content_zw") != null) {
            page.putField("对联文本",page.getHtml().css("div.content_zw"));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main (String[] args) {
        Spider.create(new DuilianPageProcessor()).addUrl("http://www.duiduilian.com/")
                .addPipeline(new FilePipeline("D:\\webmagic\\"))
                .thread(5).run();
    }
}
