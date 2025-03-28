package entity;

public class Registration {
    private int id;
    private String password;
    private String name;
    private String username;
    private int login;
    private String status;

    public Registration(){

    }

    public Registration(int id, String password, String name, String userName, int login, String status) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.username = userName;
        this.login = login;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public int getLogin() {
        return login;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLogin(int login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "BankEmployee{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
