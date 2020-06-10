package com.example.ubi_interfaces.classes;
import android.util.Log;

public class User {

    String name;
    String id; // Não tenho a certeza se os id's são todos ints...
    String email;
    int performanceId; // Por default vai ser;
    String picture; //Não sei see vai ser sempre String por causa de guardar as imagens na base de dados
    boolean currentUser; // Saber se o user está a ver o perfil dele ou nao
    String tagName = "USER Class";

    /* Os AuthType vão ser
    * firebase
    * facebook
    * google */
    String authType; // Mais fácil para saber que calls fazer

    public User() {

    }

    public User(String id, String email, String name, String authType) {
        this.id = id;
        this.authType = authType;
        this.email = email;
        this.name = name;
    }

    // Função que devolve o utilizador logado



    // Não precisa de um "set" pelo menos por agora
    public String getId() { return this.id; }

    // Também não precisa de um SET
    public String getAuthType() { return this.authType; }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public int getPerformanceId() {
        return this.performanceId;
    }
    public void setPerformanceId(int perfId) {
        this.performanceId = perfId;
    }

    public String getPicture() {
        return this.picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }

    public boolean isCurrentUser () {
        boolean current = false;
        return true;
    }
}
