/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package qlgv.web.session;

import java.io.Serializable;
import queenb.net.entitybean.Login;

/**
 *
 * @author truong
 */
public class LoginSession implements Serializable{
    private Login login;
    private boolean logged;

    /**
     * @return the login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * @return the logged
     */
    public boolean isLogged() {
        return logged;
    }

    /**
     * @param logged the logged to set
     */
    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}
