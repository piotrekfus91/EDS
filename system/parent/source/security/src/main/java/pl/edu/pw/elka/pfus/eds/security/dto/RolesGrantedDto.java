package pl.edu.pw.elka.pfus.eds.security.dto;

import com.google.common.base.Objects;

public class RolesGrantedDto {
    private String roleName;
    private boolean has;

    public RolesGrantedDto() {
    }

    public RolesGrantedDto(String roleName, boolean has) {
        this.roleName = roleName;
        this.has = has;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isHas() {
        return has;
    }

    public void setHas(boolean has) {
        this.has = has;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper("Role").add("roleName", roleName).add("has", has).toString();
    }
}
