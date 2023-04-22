package bean;

public class User {
    private String name;
    private String phone;
    private String account;
    private String password;
    private int start;

    public User(String name, String account, String password, String phone,int start) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.phone=phone;
        this.start = start;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return name + "\t"+ phone+"\t"+ account + "\t" + (start == 0 ? "离线" : "在线") + "\n";
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public User() {
    }
}
