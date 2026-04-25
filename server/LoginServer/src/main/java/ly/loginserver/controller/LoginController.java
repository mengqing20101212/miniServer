package ly.loginserver.controller;

import ly.db.entry.LoginEntry;
import ly.loginserver.result.ErrorCode;
import ly.loginserver.result.LoginResult;
import ly.loginserver.result.ServerListResult;
import ly.loginserver.service.LoginService;
import ly.redis.RedisKeys;
import ly.redis.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录服对外提供的 HTTP 接口。
 * <p>
 * 客户端先通过这里注册账号或拉取可用服务器列表，再使用返回的账号 id、token
 * 和网关/游戏服信息进入后续的长连接登录流程。
 */
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    /**
     * 获取账号可登录的服务器列表和登录凭证。
     * <p>
     * 已存在账号会从数据库读取账号 id，并刷新 Redis 中的登录 token；
     * 如果数据库记录暂时不可用，则尝试读取 Redis 中缓存的账号 id，保证注册后
     * 立即拉取列表的流程仍能继续。
     *
     * @param account 登录账号名
     * @return 网关、游戏服列表、角色列表以及账号登录凭证
     */
    @GetMapping("serverList")
    public LoginResult<ServerListResult> getServerList(String account) {
        if (!StringUtils.hasText(account)) {
            return new LoginResult<>(ErrorCode.PARAM_ERROR);
        }
        ServerListResult result = new ServerListResult();
        result.setPlayers(loginService.getPlayers(account));
        result.setGate(loginService.selectGate());
        result.setGameServerList(loginService.selectGameServerList());
        LoginEntry entry = loginService.getLoginEntry(account);
        if (entry != null) {
            result.setAccountId(entry.getId());
            result.setToken(loginService.getToken(account));
            loginService.saveToken(account, result.getToken());
            entry.asyncUpdate();
        } else {
            Integer cachedAccountId = RedisUtils.get(RedisKeys.LOGIN_ACCOUNT_ID_KEY.getKey(account));
            if (cachedAccountId != null && cachedAccountId > 0) {
                result.setAccountId(cachedAccountId);
                result.setToken(loginService.getToken(account));
            }
        }
        return new LoginResult<>(ErrorCode.OK, result);
    }

    /**
     * 创建新账号并返回首登所需信息。
     * <p>
     * Redis 中存在账号 id 视为账号已经注册；创建成功后会返回 token 和推荐网关，
     * 客户端可直接进入后续的服务器登录流程。
     *
     * @param account 账号名
     * @param channel 注册渠道
     * @return 新账号信息，包含账号 id、token、渠道和网关
     */
    @GetMapping("register")
    public LoginResult<Map<String, Object>> register(String account, String channel) {
        if (!StringUtils.hasText(account)) {
            return new LoginResult<>(ErrorCode.PARAM_ERROR);
        }
        if (!StringUtils.hasText(channel)) {
            return new LoginResult<>(ErrorCode.PARAM_ERROR);
        }
        if (RedisUtils.exists(RedisKeys.LOGIN_ACCOUNT_ID_KEY.getKey(account))) {
            return new LoginResult<>(ErrorCode.ACCOUNT_HAS_EXISTS);
        }
        LoginEntry newAccount = loginService.createNewAccount(account, channel);
        Map<String, Object> accountInfo = new HashMap<>();
        if (newAccount != null) {
            accountInfo.put("account", account);
            accountInfo.put("channel", channel);
            accountInfo.put("token", newAccount.getToken());
            accountInfo.put("accountId", newAccount.getId());
            accountInfo.put("gate", loginService.selectGate());
            return new LoginResult<>(ErrorCode.OK, accountInfo);
        } else {
            return new LoginResult<>(ErrorCode.SYSTEM_ERROR);
        }
    }
}
