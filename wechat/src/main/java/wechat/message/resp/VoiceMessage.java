package wechat.message.resp;

/**
 * ������Ϣ
 * 
 */
public class VoiceMessage extends BaseMessage {
	// ����
	private wechat.message.resp.Voice Voice;

	public wechat.message.resp.Voice getVoice() {
		return Voice;
	}

	public void setVoice(wechat.message.resp.Voice voice) {
		Voice = voice;
	}
}
