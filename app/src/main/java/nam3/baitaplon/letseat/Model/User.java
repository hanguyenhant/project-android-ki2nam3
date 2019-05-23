package nam3.baitaplon.letseat.Model;

public class User {
    private String Name;
    private String Password;

    public User(){

    }

    public User(String Name, String Password){
        this.Name = Name;
        this.Password = Password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }
}
