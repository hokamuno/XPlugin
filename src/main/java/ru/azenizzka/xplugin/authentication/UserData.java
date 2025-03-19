package ru.azenizzka.xplugin.authentication;

import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;

public class UserData {
  private String password;
  private String lastLoggedIp;

  public String getLastLoggedIp() {
    return lastLoggedIp;
  }

  public void setLastLoggedIp(String lastLoggedIp) {
    this.lastLoggedIp = lastLoggedIp;
  }

  public void setPassword(String password) {
    this.password = toHash(password);
  }

  public boolean checkPassword(String password) {
    return toHash(password).equals(this.password);
  }

  private String toHash(String string) {
    return Hashing.sha256().hashString(string, StandardCharsets.UTF_8).toString();
  }
}
