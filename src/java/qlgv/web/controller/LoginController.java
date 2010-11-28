/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package qlgv.web.controller;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import javax.faces.component.html.HtmlInputSecret;
import javax.faces.component.html.HtmlInputText;
import javax.faces.model.SelectItem;
import org.ajax4jsf.component.html.HtmlAjaxCommandLink;
import qlgv.web.session.LoginSession;
import queenb.net.entitybean.Login;
import org.apache.log4j.Logger;
import qlgv.web.store.LoginStore;


/**
 *
 * @author truong
 */
public class LoginController{
    private Logger logger = Logger.getLogger(LoginController.class);
    private String userName;
    private String password;
    private HtmlInputText inputUserName;
    private HtmlInputSecret inputPassword;
    private HtmlAjaxCommandLink loginCommandLink;
    private GiaoVuController giaoVuController;
    private LoginSession loginSession;
    private LoginStore loginStore;


    /** Creates a new instance of LoginController */
    public LoginController() {
        if (loginStore == null) {
            loginStore = new LoginStore();
        }
    }

    public void actionlogin(){
        if(this.userName.equals("qlgvcn1")&&this.password.equals("orcl")){
            loginSession.setLogged(true);
            getGiaoVuController().sendRedirect("mark.jsp");
        }
        else{
            getGiaoVuController().sendRedirect("login.jsp");
        }
    }
     public void checkLogin() {
        //int loginInt = -1;
        logger.debug("Begin CheckLogin");
        Login lg;
        String userNameP = inputUserName.getValue().toString();
        String passwordP = inputPassword.getValue().toString();
        //password = "201506077";
        try {
            logger.debug("login module@userName:"+userNameP);
            System.out.println("Username Login: "+userNameP);
            lg = new Login();
            getLoginSession().setLogin(lg);
            lg = loginStore.checkLogin(userNameP, passwordP);
            logger.debug("login status:" + lg.userName);
            System.out.println("login status:" + lg.userName);
            String userNameLg = lg.getUserName();
            if (userNameLg==null||userNameLg.equals("")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "wrong user name or password. Try again", null));
            } else {                
                getLoginSession().setLogin(lg);                               
                loginSession.setLogged(true);
                getGiaoVuController().sendRedirect("sinhvien.jsp");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Connect error. Try again", "Connect error. Try again:" + ex.getMessage()));
            logger.debug("checkLogin:Connect error. Try again" + ex.getMessage());

        }
    }
     public void doLogout() {
        try {
            ExternalContext ext = FacesContext.getCurrentInstance().getExternalContext();
            HttpSession session = (HttpSession) ext.getSession(true);
            session.invalidate();
            ext.getSessionMap().clear();
            getGiaoVuController().getResponse().sendRedirect("login.jsp");
        } catch (Exception ex) {
            getGiaoVuController().setMessages(getGiaoVuController().getResourceString("errorLogout") + ex.getMessage(), null);
        }
    }
     
    

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the inputUserName
     */
    public HtmlInputText getInputUserName() {
        if(inputUserName==null){
            inputUserName= new  HtmlInputText();
        }
        return inputUserName;
    }

    /**
     * @param inputUserName the inputUserName to set
     */
    public void setInputUserName(HtmlInputText inputUserName) {
        this.inputUserName = inputUserName;
    }

    /**
     * @return the inputPassword
     */
    public HtmlInputSecret getInputPassword() {
        if(inputPassword==null){
            inputPassword= new org.richfaces.component.html.HtmlInputSecret();
        }
        return inputPassword;
    }

    /**
     * @param inputPassword the inputPassword to set
     */
    public void setInputPassword(HtmlInputSecret inputPassword) {
        this.inputPassword = inputPassword;
    }

    /**
     * @return the giaoVuController
     */
    public GiaoVuController getGiaoVuController() {
        if(giaoVuController==null){
            giaoVuController= new GiaoVuController();
        }
        return giaoVuController;
    }

    /**
     * @param giaoVuController the giaoVuController to set
     */
    public void setGiaoVuController(GiaoVuController giaoVuController) {
        this.giaoVuController = giaoVuController;
    }

    /**
     * @return the loginSession
     */
    public LoginSession getLoginSession() {
        if(loginSession==null){
            loginSession= new LoginSession();
        }
        return loginSession;
    }

    /**
     * @param loginSession the loginSession to set
     */
    public void setLoginSession(LoginSession loginSession) {
        this.loginSession = loginSession;
    }

    /**
     * @return the loginCommandLink
     */
    public HtmlAjaxCommandLink getLoginCommandLink() {
        if(loginCommandLink==null){
            loginCommandLink= new HtmlAjaxCommandLink();
        }
        return loginCommandLink;
    }

    /**
     * @param loginCommandLink the loginCommandLink to set
     */
    public void setLoginCommandLink(HtmlAjaxCommandLink loginCommandLink) {
        this.loginCommandLink = loginCommandLink;
    }

   
  

}
