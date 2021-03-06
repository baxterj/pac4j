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
package org.pac4j.oauth.profile.orcid;

import org.pac4j.core.profile.AttributesDefinition;
import org.pac4j.oauth.profile.OAuth20Profile;

import java.util.Locale;

/**
 * <p>This class is the user profile for ORCiD with appropriate getters.</p>
 * <p>It is returned by the {@link org.pac4j.oauth.client.OrcidClient}.</p>
 *
 * @author Jens Tinglev
 * @since 1.6.0
 */

public class OrcidProfile extends OAuth20Profile {
    private static final long serialVersionUID = 7626472295622786149L;

    private transient final static AttributesDefinition ATTRIBUTES_DEFINITION = new OrcidAttributesDefinition();

    @Override
    public AttributesDefinition getAttributesDefinition() {
        return ATTRIBUTES_DEFINITION;
    }

    public String getOrcid() {
        return (String) getAttribute(OrcidAttributesDefinition.ORCID);
    }

    public boolean getClaimed() {
        return (Boolean) getAttribute(OrcidAttributesDefinition.CLAIMED);
    }

    public String getCreationMethod() {
        return (String) getAttribute(OrcidAttributesDefinition.CREATION_METHOD);
    }

    @Override
    public String getFirstName() {
        return (String) getAttribute(OrcidAttributesDefinition.FIRST_NAME);
    }

    @Override
    public String getFamilyName() {
        return (String) getAttribute(OrcidAttributesDefinition.FAMILY_NAME);
    }

    @Override
    public Locale getLocale() {
        return (Locale) getAttribute(OrcidAttributesDefinition.LOCALE);
    }

    @Override
    public String getProfileUrl() {
        return (String) getAttribute(OrcidAttributesDefinition.URI);
    }

}
