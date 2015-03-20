package wechat.message.resp;

/**
 * ÓïÒôÏûÏ¢
 * 
 */
public class VoiceMessage extends BaseMessage {
	// ÓïÒô
	private wechat.message.resp.Voice Voice;

	public wechat.message.resp.Voice getVoice() {
		return Voice;
	}

	public void setVoice(wechat.message.resp.Voice voice) {
		Voice = voice;
	}
}
