/*
  Copyright 2012 - 2015 pac4j organization

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.pac4j.oauth.client;

import com.github.scribejava.apis.DropBoxApi;
import com.github.scribejava.core.builder.api.Api;
import com.github.scribejava.core.model.Token;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.AttributesDefinition;
import org.pac4j.oauth.credentials.OAuthCredentials;
import org.pac4j.oauth.profile.JsonHelper;
import org.pac4j.oauth.profile.dropbox.DropBoxProfile;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * <p>This class is the OAuth client to authenticate users in DropBox.</p>
 * <p>It returns a {@link org.pac4j.oauth.profile.dropbox.DropBoxProfile}.</p>
 * <p>More information at https://www.dropbox.com/developers/reference/api#account-info</p>
 * 
 * @author Jerome Leleu
 * @since 1.2.0
 */
public class DropBoxClient extends BaseOAuth10Client<DropBoxProfile> {
    
    public DropBoxClient() {
    }
    
    public DropBoxClient(final String key, final String secret) {
        setKey(key);
        setSecret(secret);
    }

    @Override
    protected Api getApi() {
        return DropBoxApi.instance();
    }

    @Override
    protected String getProfileUrl(final Token accessToken) {
        return "https://api.dropbox.com/1/account/info";
    }
    
    @Override
    protected OAuthCredentials getOAuthCredentials(final WebContext context) {
        // get tokenRequest from session
        final Token tokenRequest = (Token) context.getSessionAttribute(getRequestTokenSessionAttributeName());
        logger.debug("tokenRequest: {}", tokenRequest);
        // don't get parameters from url
        // token and verifier are equals and extracted from saved request token
        final String token = tokenRequest.getToken();
        logger.debug("token = verifier: {}", token);
        return new OAuthCredentials(tokenRequest, token, token, getName());
    }
    
    @Override
    protected DropBoxProfile extractUserProfile(final String body) {
        final DropBoxProfile profile = new DropBoxProfile();
        JsonNode json = JsonHelper.getFirstNode(body);
        final AttributesDefinition definition = profile.getAttributesDefinition();
        if (json != null) {
            profile.setId(JsonHelper.getElement(json, "uid"));
            for (final String attribute : definition.getPrimaryAttributes()) {
                profile.addAttribute(attribute, JsonHelper.getElement(json, attribute));
            }
            json = (JsonNode) JsonHelper.getElement(json, "quota_info");
            if (json != null) {
                for (final String attribute : definition.getSecondaryAttributes()) {
                    profile.addAttribute(attribute, JsonHelper.getElement(json, attribute));
                }
            }
        }
        return profile;
    }
}
