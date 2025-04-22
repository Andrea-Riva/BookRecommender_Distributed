package shared;

public class LoggedUser {
    String nome, cognome;
    String codFiscale;
    String email;
    int userID;
    String password;

    public LoggedUser(String nome, String cognome, String codFiscale, String email, int userID, String password) {
        this.nome = nome;
        this.cognome = cognome;
        this.codFiscale = codFiscale;
        this.email = email;
        this.userID = userID;
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCodFiscale() {
        return codFiscale;
    }

    public void setCodFiscale(String codFiscale) {
        this.codFiscale = codFiscale;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
}