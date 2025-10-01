package sptech.school.projetoPI.refactor.core.domain.aggregate;

import java.time.LocalDateTime;
import java.util.Set;

public class RoleDomain {
  private String name;
  private String description;
  private Set<UserDomain> userDomains;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void addUserDomain(UserDomain userDomain) {
    this.userDomains.add(userDomain);
  }

  public Set<UserDomain> getUserDomains() {
    return userDomains;
  }

  public void setUserDomains(Set<UserDomain> userDomains) {
    this.userDomains = userDomains;
  }
}
