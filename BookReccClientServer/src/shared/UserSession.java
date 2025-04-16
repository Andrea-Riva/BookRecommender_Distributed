package shared;

public class UserSession {
    private static int userID;
    private static String user_Name;
    private static String user_Surname;
    private static String codice_Fiscale;
    private static String email;

    public static void setUserID(int id){
        userID = id;
    }

    public static int getUserID(){
        return userID;
    }

    public static String getUserName() {
        return user_Name;
    }

    public static void setUserName(String userName) {
        user_Name = userName;
    }

    public static String getUserSurname() {
        return user_Surname;
    }

    public static void setUserSurname(String userSurname) {
        user_Surname = userSurname;
    }

    public static String getCodice_Fiscale() {
        return codice_Fiscale;
    }

    public static void setCodice_Fiscale(String codiceFiscale) {
        codice_Fiscale = codiceFiscale;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String mail) {
        email = mail;
    }
}
