package jsf.auth.sample;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.pac4j.core.context.JEEContext;
import org.pac4j.core.context.session.JEESessionStore;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.oidc.profile.google.GoogleOidcProfile;

@ManagedBean
@RequestScoped
public class AuthProfileBean implements Serializable {

    private final ProfileManager profileManager;

    public AuthProfileBean() {
        
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();
        
        JEEContext jeeContext = new JEEContext(request, response);
        
        profileManager = new ProfileManager(jeeContext, JEESessionStore.INSTANCE);
    }

    public List getProfiles() {
        return profileManager.getProfiles();
    }

    public GoogleOidcProfile getProfile() {
        return (GoogleOidcProfile) profileManager.getProfile().orElse(null);
    }
}
