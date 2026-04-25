package ly.loginserver;

import ly.net.ConnectSession;

/**
 * 登录服客户端占位对象，用于和公共网络/会话抽象保持一致。
 */
public class LoginClient extends ConnectSession {
    public LoginClient(long guid) {
        super(guid);
    }
}
