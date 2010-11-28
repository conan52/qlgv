/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package qlgv.web.store;

import java.sql.SQLException;
import java.util.List;
import queenb.net.entitybean.Login;
import qlgv.web.util.Util;

/**
 *
 * @author truong
 */
public class LoginStore {

    private Login login;

    public Login checkLogin(String userName, String password) throws SQLException, InstantiationException, IllegalAccessException {
        if (getLogin() == null) {
            setLogin(new Login());
        }
//        String passwordMD5 = Util.convertMD5(password);
        return getLogin().checkLogin(userName, password);
    }

    

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
}
