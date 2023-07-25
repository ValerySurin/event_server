package events.server.support.objects;

import java.util.HashSet;
import java.util.Set;

public class SecurityUser {
    private String userName;
    private Set<String> roles = new HashSet<>();
    private Set<String> backendRoles = new HashSet<>();

    public SecurityUser() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getBackendRoles() {
        return backendRoles;
    }

    public void setBackendRoles(Set<String> backendRoles) {
        this.backendRoles = backendRoles;
    }
}
