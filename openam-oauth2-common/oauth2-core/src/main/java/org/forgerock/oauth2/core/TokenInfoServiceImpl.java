/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2014-2015 ForgeRock AS.
 */

package org.forgerock.oauth2.core;

import static org.forgerock.oauth2.core.AccessTokenVerifier.*;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Named;
import org.forgerock.json.JsonValue;
import org.forgerock.oauth2.core.exceptions.BadRequestException;
import org.forgerock.oauth2.core.exceptions.ExpiredTokenException;
import org.forgerock.oauth2.core.exceptions.InvalidGrantException;
import org.forgerock.oauth2.core.exceptions.InvalidRequestException;
import org.forgerock.oauth2.core.exceptions.InvalidTokenException;
import org.forgerock.oauth2.core.exceptions.NotFoundException;
import org.forgerock.oauth2.core.exceptions.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Service to return the full information of a OAuth2 token.
 *
 * @since 12.0.0
 */
public class TokenInfoServiceImpl implements TokenInfoService {

    private final Logger logger = LoggerFactory.getLogger("OAuth2Provider");
    private final OAuth2ProviderSettingsFactory providerSettingsFactory;
    private final AccessTokenVerifier headerTokenVerifier;
    private final AccessTokenVerifier queryTokenVerifier;

    /**
     * Constructs a new TokenInfoServiceImpl.
     *
     * @param providerSettingsFactory An instance of the OAuth2ProviderSettingsFactory.
     * @param headerTokenVerifier Basic HTTP access token verification.
     * @param queryTokenVerifier Query string access token verification.
     */
    @Inject
    public TokenInfoServiceImpl(OAuth2ProviderSettingsFactory providerSettingsFactory,
            @Named(REALM_AGNOSTIC_HEADER) AccessTokenVerifier headerTokenVerifier,
            @Named(REALM_AGNOSTIC_QUERY_PARAM) AccessTokenVerifier queryTokenVerifier) {
        this.providerSettingsFactory = providerSettingsFactory;
        this.headerTokenVerifier = headerTokenVerifier;
        this.queryTokenVerifier = queryTokenVerifier;
    }

    /**
     * {@inheritDoc}
     */
    public JsonValue getTokenInfo(OAuth2Request request) throws InvalidTokenException, InvalidRequestException,
            ExpiredTokenException, ServerException, BadRequestException, InvalidGrantException, NotFoundException {

        final AccessTokenVerifier.TokenState headerToken = headerTokenVerifier.verify(request);
        final AccessTokenVerifier.TokenState queryToken = queryTokenVerifier.verify(request);
        final Map<String, Object> response = new HashMap<String, Object>();

        if (!headerToken.isValid() && !queryToken.isValid()) {
            logger.error("Access Token not valid");
            throw new InvalidRequestException("Access Token not valid");
        } else if (headerToken.isValid() && queryToken.isValid()) {
            logger.error("Access Token provided in both query and header in request");
            throw new InvalidRequestException("Access Token cannot be provided in both query and header");
        } else {
            final AccessToken accessToken = request.getToken(AccessToken.class);

            logger.trace("In Validator resource - got token = " + accessToken);

            final OAuth2ProviderSettings providerSettings = providerSettingsFactory.get(request);
            final Map<String, Object> scopeEvaluation = providerSettings.evaluateScope(accessToken);
            response.putAll(accessToken.getTokenInfo());
            response.putAll(scopeEvaluation);

            return new JsonValue(response);
        }
    }
}
