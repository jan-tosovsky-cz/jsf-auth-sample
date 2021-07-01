package jsf.auth.sample;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.pac4j.core.authorization.authorizer.DefaultAuthorizers;
import org.pac4j.core.client.Clients;
import org.pac4j.core.config.Config;
import org.pac4j.jee.filter.CallbackFilter;
import org.pac4j.jee.filter.SecurityFilter;
import org.pac4j.jee.util.FilterHelper;
import org.pac4j.oidc.client.GoogleOidcClient;
import org.pac4j.oidc.config.OidcConfiguration;

@WebListener
public class AuthConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
       
        OidcConfiguration oidcConfiguration = new OidcConfiguration();
        oidcConfiguration.setClientId("167480702619-8e1lo80dnu8bpk3k0lvvj27noin97vu9.apps.googleusercontent.com");
        oidcConfiguration.setSecret("MhMme_Ik6IH2JMnAT6MFIfee");
        oidcConfiguration.setUseNonce(true);
        oidcConfiguration.addCustomParam("prompt", "consent");
        final GoogleOidcClient oidcClient = new GoogleOidcClient(oidcConfiguration);
       
        final Clients clients = new Clients(
                "http://localhost:8080/callback",
                oidcClient
        );

        final Config config = new Config(clients);

        final FilterHelper filterHelper = new FilterHelper(event.getServletContext());

        final CallbackFilter callbackFilter = new CallbackFilter(config, "/");
        callbackFilter.setRenewSession(true);
        filterHelper.addFilterMapping("callbackFilter", callbackFilter, "/callback/*");
        
        final SecurityFilter oidcFilter = new SecurityFilter(config, "GoogleOidcClient", DefaultAuthorizers.NONE);
        filterHelper.addFilterMapping("oidcFilter", oidcFilter, "/app/*");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // Do stuff during server shutdown.
    }
}
