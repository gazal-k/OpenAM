/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006 Sun Microsystems Inc. All Rights Reserved
 *
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://opensso.dev.java.net/public/CDDLv1.0.html or
 * opensso/legal/CDDLv1.0.txt
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at opensso/legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * $Id: LogSSOTokenDetails.java,v 1.4 2008/09/05 00:51:01 ww203982 Exp $
 *
 * Portions Copyrighted 2013-2015 ForgeRock AS
 * Portions Copyrighted 2013 Nomura Research Institute, Ltd
 */

package com.sun.identity.log.s1is;

import com.iplanet.sso.SSOException;
import com.iplanet.sso.SSOToken;
import com.sun.identity.log.LogConstants;
import com.sun.identity.log.LogRecord;
import com.sun.identity.log.Logger;
import com.sun.identity.log.spi.Debug;
import com.sun.identity.shared.Constants;

/**
 * This is a DSAME specific util class which takes in LogRecord
 * and SSOToken. Extracts details from SSOToken like ClientDomain,
 * HostName, IPAddress etc. and adds these info to the logInfo table of
 * LogRecord.
 */
public class LogSSOTokenDetails {

    /**
     * Creates new LogSSOTokenDetails object.
     */
    public LogSSOTokenDetails() {
    }

    /**
     * This method extracts the information from SSOToken and adds each of the
     * HashTable of LogRecord.
     *
     * @param lr the LogRecord
     * @param ssoToken the SSOToken for the requester
     * @return a LogRecord which contains all the SSOToken related info.
     */
    public static LogRecord logSSOTokenInfo(LogRecord lr, SSOToken ssoToken) {
        String clientDomain = null;
        String clientID     = null;
        String ipAddress    = null;
        String hostName     = null;

        /**
         * these are the compulsory fields ... to be logged even if there are
         * exceptions while getting domain, loginid, ipaddr, hostname
         */
        String tokenID = ssoToken.getTokenID().toString();
        lr.addLogInfo(LogConstants.LOGIN_ID_SID, tokenID);

        try {
            hostName = ssoToken.getHostName();
            lr.addLogInfo(LogConstants.HOST_NAME, hostName);

            /*
             *  if com.sun.identity.log.resolveHostName=false,
             *  then IPAddr field will end up "Not Available"
             */
            if (Logger.resolveHostNameEnabled()) {
                java.net.InetAddress ipAddr = ssoToken.getIPAddress();
                if (ipAddr != null) {
                    /*
                     *  getting a leading "/" from InetAddress.getByName(host)
                     *  in SSOTokenImpl.java when "host" is an IPaddress.
                     */
                    ipAddress = ipAddr.getHostAddress();
                    /*
                     *  if no hostname returned, or only IP address,
                     *  try getting hostname from InetAddr
                     */
                    if ((hostName == null) ||
                        ((ipAddress != null) && (ipAddress.equals(hostName))))
                    {
                        hostName = ipAddr.getHostName();
                    }
                }
            }
            lr.addLogInfo(LogConstants.IP_ADDR, ipAddress);

            //clientDomain = ssoToken.getProperty("cdomain");
            clientDomain = ssoToken.getProperty("Organization");
            lr.addLogInfo(LogConstants.DOMAIN, clientDomain);

            clientID = ssoToken.getPrincipal().getName();
            lr.addLogInfo(LogConstants.LOGIN_ID, clientID);

            String contextId = ssoToken.getProperty(Constants.AM_CTX_ID);
            lr.addLogInfo(LogConstants.CONTEXT_ID, contextId);
        } catch (SSOException ssoe) {
            Debug.error("LogSSOTokenDetails:logSSOTokenInfo:SSOException: ",
                ssoe);
            return lr;
        }
        return lr;
    }
}
