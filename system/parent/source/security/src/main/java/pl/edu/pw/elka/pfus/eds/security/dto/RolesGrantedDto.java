package pl.edu.pw.elka.pfus.eds.security.dto;

public class RolesGrantedDto {
    private String roleName;
    private boolean has;

    public RolesGrantedDto(String roleName, boolean has) {
        this.roleName = roleName;
        this.has = has;
    }

    public String getRoleName() {
        return roleName;
    }

    public boolean isHas() {
        return has;
    }
}
