package ly.gmserver.dto;

public class LoginResponse {
    private String token;
    private AdminVO admin;

    public LoginResponse() {}

    public LoginResponse(String token, AdminVO admin) {
        this.token = token;
        this.admin = admin;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public AdminVO getAdmin() { return admin; }
    public void setAdmin(AdminVO admin) { this.admin = admin; }
}
