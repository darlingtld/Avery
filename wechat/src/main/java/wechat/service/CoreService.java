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
 * ���ķ�����
 */
@Service
public class CoreService {

    @Autowired
    private TulingApiService tulingApiService;

    private final String SERVER="http://lingdagames.sinaapp.com";

    /**
     * ����΢�ŷ���������
     *
     * @param request
     * @return xml
     */
    public String processRequest(HttpServletRequest request) {
        // xml��ʽ����Ϣ����
        // Ĭ�Ϸ��ص��ı���Ϣ����
        String respContent = "���������ı������ǿ�ʼ����ɣ�";
        try {
            // ����parseXml��������������Ϣ
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            // ���ͷ��ʺ�
            String fromUserName = requestMap.get("FromUserName");
            // ������΢�ź�
            String toUserName = requestMap.get("ToUserName");
            // ��Ϣ����
            String msgType = requestMap.get("MsgType");
            // ��Ϣ����ʱ��
            String createTime = requestMap.get("CreateTime");

            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                return processMessageEvent(requestMap);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                return processMessageText(requestMap, fromUserName, toUserName);
            } else {
                // TODO �������͵���Ϣ��������
                respContent = "�һ�С����̫��������˼��~~";
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
        // ����ͼ����Ϣ
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
        newsMessage.setFuncFlag(0);
        List<Article> articleList = new ArrayList<Article>();
        String content = requestMap.get("Content");
        if ("1".equals(content.trim())) {
            //������Ѷ
            Article article1 = new Article();
            article1.setTitle("������8888�š�������Ԫ��������");
            article1.setDescription("Ϊ��ף�й����ڣ�������������Һ�ӡ���ֽ��ڷ��������������桰����Ǯ�������С�����Ǯ���ı�Ŷ��ԡ�8888����ͷ��1��Ԫװ�ں���쿨Ƭ�У���Ƭ��д�š���»��ϲ���ţ������Ʊ�������������ϲ���ơ��������ǹ����꣬����Ҳ��æ���");
            article1.setPicUrl(SERVER+"/images/wechat/1.jpg");
            article1.setUrl("http://news.sina.com.cn/w/2015-02-20/005931534558.shtml");

            Article article2 = new Article();
            article2.setTitle("���Ǹ�����������Щ�������ף�");
            article2.setDescription("��ףũ�����겢����ȫ���˵ġ�ר�������������ܺ��Ļ�Ӱ�죬һЩ���ǹ��Ҽ�ǧ����Ҳ������ףũ�������ϰ�ף������ݸ��Դ�ͳ���γ���һЩ���صĹ����Ļ�����������Щ���ҹ�������Щ���ص����ס�");
            article2.setPicUrl(SERVER+"/images/wechat/2.jpg");
            article2.setUrl("http://weibo.com/p/1001603812252150048798");

            Article article3 = new Article();
            article3.setTitle("����N�� �����±���ʲô��");
            article3.setDescription("�������׷��ѣ��ߴ�ð˴��̾���һ������ᱻ���ʣ���Ը������⣬����������ʲô��������ʾ�����ʶ��ٺ��ж��������Ϊ�����ڹ������±��ʵ������飬�����ĳ��ܷ��߷��б��ʡ��������𣿡����������ܷ��ߺ��±��ʡ�ÿ����������Ǯ����");
            article3.setPicUrl(SERVER+"/images/wechat/3.jpg");
            article3.setUrl("http://news.sina.com.cn/s/2015-02-20/041531534632.shtml");

            articleList.add(article1);
            articleList.add(article2);
            articleList.add(article3);
            newsMessage.setArticleCount(articleList.size());
            newsMessage.setArticles(articleList);
            return MessageUtil.messageToXml(newsMessage);
        } else if ("2".equals(content.trim())) {
            //����Ӱ��
            Article article = new Article();
            article.setTitle("����Ӱ�� ������һ�������ȳ�");
            article.setDescription("�ṩ����Ӱ�ӵ��������ӣ����пͻ���App,�ǳ��õĶ���");
            article.setPicUrl(SERVER+"/images/wechat/4.jpg");
            article.setUrl("http://lingdagames.sinaapp.com/pages/index.html");
            articleList.add(article);
            // ����ͼ����Ϣ����
            newsMessage.setArticleCount(articleList.size());
            // ����ͼ����Ϣ������ͼ�ļ���
            newsMessage.setArticles(articleList);
            // ��ͼ����Ϣ����ת����xml�ַ���
            return MessageUtil.messageToXml(newsMessage);
        } else if ("3".equals(content.trim())) {
            //������Ϸ
            Article article1 = new Article();
            article1.setTitle("�����");
            article1.setDescription("");
            article1.setPicUrl(SERVER + "/images/wechat/5.jpg");
            article1.setUrl("http://lingdagames.sinaapp.com/game3/index.html");
            articleList.add(article1);

            Article article2 = new Article();
            article2.setTitle("��ﵯ����");
            article2.setDescription("");
            article2.setPicUrl(SERVER + "/images/wechat/5.jpg");
            article2.setUrl("http://lingdagames.sinaapp.com/game2/index.html");
            articleList.add(article2);

            Article article3 = new Article();
            article3.setTitle("���С��ľ");
            article3.setDescription("���������˾�֪����");
            article3.setPicUrl(SERVER + "/images/wechat/5.jpg");
            article3.setUrl("http://lingdagames.sinaapp.com/game1/index.html");
            articleList.add(article3);
            // ����ͼ����Ϣ����
            newsMessage.setArticleCount(articleList.size());
            // ����ͼ����Ϣ������ͼ�ļ���
            newsMessage.setArticles(articleList);
            // ��ͼ����Ϣ����ת����xml�ַ���
            return MessageUtil.messageToXml(newsMessage);
        } else if ("4".equals(content.trim())) {
            //����Ӱ��
            Article article = new Article();
            article.setTitle("�Ĵ����������Ƽ�");
            article.setDescription("�Ĵ����ǣ�Australia��λ����̫ƽ���ӡ����֮�䣬�ɰĴ����Ǵ�½����˹�����ǵ��ȵ���ͺ���������ɡ�������̫ƽ���ɺ��������˹����������������������ӡ�������Ե������������Ψһһ����ռһ����½�Ĺ��ҡ�");
            article.setPicUrl(SERVER+"/images/wechat/australia.jpg");
            article.setUrl("http://lingdagames.sinaapp.com/travel1/index.html");
            articleList.add(article);
            // ����ͼ����Ϣ����
            newsMessage.setArticleCount(articleList.size());
            // ����ͼ����Ϣ������ͼ�ļ���
            newsMessage.setArticles(articleList);
            // ��ͼ����Ϣ����ת����xml�ַ���
            return MessageUtil.messageToXml(newsMessage);
        } else {
            respContent = tulingApiService.getTulingResult(content);
            // �ظ��ı���Ϣ
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
        // �¼�����
        String eventType = requestMap.get("Event");
        // ����
        if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
            respContent = "��л����ע�����Ӫ�Ĺ��ںţ�������������Ѷ�������Ϸ���������벻������Ϣ��~~���У������ˣ����Ժ���������~~~\n" +
                    "�ظ�1��ȡ������Ѷ\n" +
                    "�ظ�2��ȡ����Ӱ��\n" +
                    "�ظ�3��ȡ���С��Ϸ";
        }
        // ȡ������
        else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
            // TODO ȡ�����ĺ��û����ղ������ںŷ��͵���Ϣ����˲���Ҫ�ظ���Ϣ
        }
        // �Զ���˵�����¼�
        else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
            // TODO �Զ���˵�Ȩû�п��ţ��ݲ����������Ϣ
        }
        return respContent;
    }
}