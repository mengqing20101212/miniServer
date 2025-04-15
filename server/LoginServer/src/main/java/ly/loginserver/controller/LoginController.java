package ly.loginserver.controller;

import java.util.HashMap;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RestController;

/*
 * Author: liuYang
 * Date: 2025/4/14
 * File: LoginController
 */
@RestController(value = "login")
public class LoginController {

  @Autowired private LoginService loginService;

  @GetMapping(value = "serverList")
  private LoginResult<ServerListResult> getServerList(String account) {
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
    }
    return new LoginResult<ServerListResult>(ErrorCode.OK, result);
  }

  @GetMapping(value = "register")
  private LoginResult register(String account, String channel) {
    if (!StringUtils.hasText(account)) {
      return new LoginResult(ErrorCode.PARAM_ERROR);
    }
    if (!StringUtils.hasText(channel)) {
      return new LoginResult(ErrorCode.PARAM_ERROR);
    }
    if (RedisUtils.exists(RedisKeys.LOGIN_ACCOUNT_ID_KEY.getKey(account))) {
      return new LoginResult(ErrorCode.ACCOUNT_HAS_EXISTS);
    }
    LoginEntry newAccount = loginService.createNewAccount(account, channel);
    Map<String, Object> accountInfo = new HashMap<>();
    if (newAccount != null) {
      accountInfo.put("account", account);
      accountInfo.put("channel", channel);
      accountInfo.put("token", newAccount.getToken());
      accountInfo.put("accountId", newAccount.getId());
      return new LoginResult(ErrorCode.OK, accountInfo);
    } else {
      return new LoginResult(ErrorCode.SYSTEM_ERROR);
    }
  }
}
