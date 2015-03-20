package wechat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wechat.message.resp.Article;
import wechat.message.resp.NewsMessage;
import wechat.message.resp.TextMessage;
import wechat.util.MessageUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 核心服务类
 */
@Service
public class CoreService {

    @Autowired
    private TulingApiService tulingApiService;

    private final String SERVER="http://lingdagames.sinaapp.com";

    /**
     * 处理微信发来的请求
     *
     * @param request
     * @return xml
     */
    public String processRequest(HttpServletRequest request) {
        // xml格式的消息数据
        // 默认返回的文本消息内容
        String respContent = "发送任意文本，我们开始聊天吧！";
        try {
            // 调用parseXml方法解析请求消息
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // 发送方帐号
            String fromUserName = requestMap.get("FromUserName");
            // 开发者微信号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            // 消息创建时间
            String createTime = requestMap.get("CreateTime");

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                return processMessageEvent(requestMap);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                return processMessageText(requestMap, fromUserName, toUserName);
            } else {
                // TODO 其他类型的信息慢慢搞了
                respContent = "我还小，不太明白撒意思呐~~";
                TextMessage textMessage = new TextMessage();
                textMessage.setToUserName(fromUserName);
                textMessage.setFromUserName(toUserName);
                textMessage.setCreateTime(new Date().getTime());
                textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
                textMessage.setContent(respContent);
                return MessageUtil.messageToXml(textMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return respContent;
    }

    private String processMessageText(Map<String, String> requestMap, String fromUserName, String toUserName) {
        String respContent = "";
        // 创建图文消息
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        String content = requestMap.get("Content");
        if ("1".equals(content.trim())) {
            //最新资讯
            Article article1 = new Article();
            article1.setTitle("限量版8888号“吉利美元”长这样");
            article1.setDescription("为庆祝中国春节，美国财政部造币和印钞局近期发行了羊年限量版“吉利钱”。所有“吉利钱”的编号都以“8888”开头，1美元装在横版大红卡片中，卡片上写着“福禄寿喜临门，金银财宝齐来”、“恭喜发财”……我们过个年，老外也够忙活的");
            article1.setPicUrl(SERVER+"/images/wechat/1.jpg");
            article1.setUrl("http://news.sina.com.cn/w/2015-02-20/005931534558.shtml");

            Article article2 = new Article();
            article2.setTitle("东亚各国过年有哪些独特民俗？");
            article2.setDescription("庆祝农历新年并不是全球华人的“专利”。由于深受汉文化影响，一些东亚国家几千年来也有着庆祝农历新年的习俗，并根据各自传统而形成了一些独特的过年文化。来看看这些国家过年有哪些独特的民俗。");
            article2.setPicUrl(SERVER+"/images/wechat/2.jpg");
            article2.setUrl("http://weibo.com/p/1001603812252150048798");

            Article article3 = new Article();
            article3.setTitle("过年N问 你最怕被问什么？");
            article3.setDescription("过年走亲访友，七大姑八大姨聚在一起，难免会被拷问，面对各种问题，你最怕听到什么？调查显示，工资多少和有对象了吗成为市民在过年最怕被问到的事情，而近四成受访者反感被问“买房买车了吗？”，超三成受访者害怕被问“每年给家里多少钱”。");
            article3.setPicUrl(SERVER+"/images/wechat/3.jpg");
            article3.setUrl("http://news.sina.com.cn/s/2015-02-20/041531534632.shtml");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            return MessageUtil.messageToXml(newsMessage);
        } else if ("2".equals(content.trim())) {
            //最新影视
            Article article = new Article();
            article.setTitle("灵猪影视 掀起新一轮下载热潮");
            article.setDescription("提供各类影视的下载链接，还有客户端App,非常好的东西");
            article.setPicUrl(SERVER+"/images/wechat/4.jpg");
            article.setUrl("http://lingdagames.sinaapp.com/pages/index.html");
            articleList.add(article);
            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            // 将图文消息对象转换成xml字符串
            return MessageUtil.messageToXml(newsMessage);
        } else if ("3".equals(content.trim())) {
            //最新游戏
            Article article1 = new Article();
            article1.setTitle("灵达抽大奖");
            article1.setDescription("");
            article1.setPicUrl(SERVER + "/images/wechat/5.jpg");
            article1.setUrl("http://lingdagames.sinaapp.com/game3/index.html");
            articleList.add(article1);

            Article article2 = new Article();
            article2.setTitle("灵达弹钢琴");
            article2.setDescription("");
            article2.setPicUrl(SERVER + "/images/wechat/5.jpg");
            article2.setUrl("http://lingdagames.sinaapp.com/game2/index.html");
            articleList.add(article2);

            Article article3 = new Article();
            article3.setTitle("灵达小积木");
            article3.setDescription("嘻嘻，完了就知道了");
            article3.setPicUrl(SERVER + "/images/wechat/5.jpg");
            article3.setUrl("http://lingdagames.sinaapp.com/game1/index.html");
            articleList.add(article3);
            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            // 将图文消息对象转换成xml字符串
            return MessageUtil.messageToXml(newsMessage);
        } else if ("4".equals(content.trim())) {
            //最新影视
            Article article = new Article();
            article.setTitle("澳大利亚旅游推荐");
            article.setDescription("澳大利亚（Australia）位于南太平洋和印度洋之间，由澳大利亚大陆和塔斯马尼亚岛等岛屿和海外领土组成。它东濒太平洋的珊瑚海和塔斯曼海，西、北、南三面临印度洋及其边缘海。是世界上唯一一个独占一个大陆的国家。");
            article.setPicUrl(SERVER+"/images/wechat/australia.jpg");
            article.setUrl("http://lingdagames.sinaapp.com/travel1/index.html");
            articleList.add(article);
            // 设置图文消息个数
            newsMessage.setArticleCount(articleList.size());
            // 设置图文消息包含的图文集合
            newsMessage.setArticles(articleList);
            // 将图文消息对象转换成xml字符串
            return MessageUtil.messageToXml(newsMessage);
        } else {
            respContent = tulingApiService.getTulingResult(content);
            // 回复文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            textMessage.setContent(respContent);
            return MessageUtil.messageToXml(textMessage);
        }
    }

    private String processMessageEvent(Map<String, String> requestMap) {
        String respContent = "";
        // 事件类型
        String eventType = requestMap.get("Event");
        // 订阅
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            respContent = "感谢您关注灵达运营的公众号，这里有最新资讯，最酷游戏，还有意想不到的信息！~~还有，无聊了，可以和我聊聊天~~~\n" +
                    "回复1获取最新资讯\n" +
                    "回复2获取最新影视\n" +
                    "回复3获取灵达小游戏";
        }
        // 取消订阅
        else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
            // TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
        }
        // 自定义菜单点击事件
        else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
            // TODO 自定义菜单权没有开放，暂不处理该类消息
        }
        return respContent;
    }
}