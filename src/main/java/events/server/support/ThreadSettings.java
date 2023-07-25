package events.server.support;

import events.server.support.objects.SecurityUser;

public class ThreadSettings {
    private SecurityUser securityUser;

    public ThreadSettings() {
    }

    public SecurityUser getSecurityUser() {
        return securityUser;
    }

    public void setSecurityUser(SecurityUser securityUser) {
        this.securityUser = securityUser;
    }
}
