package ly.loginserver.controller;

import ly.loginserver.result.ErrorCode;
import ly.loginserver.result.LoginResult;
import ly.loginserver.result.ServerListResult;
import ly.loginserver.service.LoginService;
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
    return new LoginResult<ServerListResult>(ErrorCode.OK, result);
  }
}
