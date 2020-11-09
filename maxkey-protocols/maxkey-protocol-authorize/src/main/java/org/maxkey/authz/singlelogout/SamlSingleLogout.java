package org.maxkey.authz.singlelogout;

import java.util.HashMap;
import java.util.UUID;

import org.maxkey.domain.apps.Apps;
import org.maxkey.util.DateUtils;
import org.springframework.security.core.Authentication;

/**
 * SamlSingleLogout
 * https://apereo.github.io/cas/6.2.x/installation/Logout-Single-Signout.html
 * @author Crystal.Sea
 *
 */
public class SamlSingleLogout extends SingleLogout{

    /**
     * The parameter name that contains the logout request.
     */
    public static final String LOGOUT_REQUEST_PARAMETER = "logoutRequest";
    
    public static final String logoutRequestMessage=
            "<samlp:LogoutRequest xmlns:samlp=\"urn:oasis:names:tc:SAML:2.0:protocol\" ID=\"%s\" Version=\"2.0\" "
            + "IssueInstant=\"%s\"><saml:NameID xmlns:saml=\"urn:oasis:names:tc:SAML:2.0:assertion\">%s"
            + "</saml:NameID><samlp:SessionIndex>%s</samlp:SessionIndex></samlp:LogoutRequest>";

    @Override
    public void sendRequest(Authentication authentication,Apps logoutApp) {
        String requestMessage = String.format(logoutRequestMessage, 
                UUID.randomUUID().toString(),
                DateUtils.getCurrentDateAsString(DateUtils.FORMAT_DATE_ISO_TIMESTAMP),
                authentication.getName(),
                logoutApp.getOnlineTicket()
                );
        
        HashMap<String,Object> logoutParameters  = new HashMap<String,Object>();
        logoutParameters.put(LOGOUT_REQUEST_PARAMETER, requestMessage);
        postMessage(logoutApp.getLogoutUrl(),logoutParameters);
    }

    public SamlSingleLogout() {
        super();
    }
    
}
