package com.adobe.poc;

import com.adobe.granite.ui.components.rendercondition.RenderCondition;
import org.apache.jackrabbit.api.security.user.Authorizable;
import org.apache.jackrabbit.api.security.user.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.List;

/**
 * Created by raju on 11/9/15.
 */
public class UserGroupRenderCondition implements RenderCondition {

    private static final Logger log = LoggerFactory.getLogger(UserGroupRenderCondition.class);
    private final Authorizable authorizable;
    private final List<String> hiddenForGroups;
    public UserGroupRenderCondition(Authorizable authorizable, List<String> hiddenForGroups) {
        this.authorizable = authorizable;
        this.hiddenForGroups = hiddenForGroups;
    }

    /**
     * Renders the field based on hiddenForGroups configuration
     * @return true if the current user does not have any of the hiddenForGroup configured
     */
    public boolean check() {
        boolean showField = true;
        try {
            Iterator<Group> groupIt = authorizable.memberOf();
            while (groupIt.hasNext()) {
                Group group = groupIt.next();
                if (hiddenForGroups.contains(group.getPrincipal().getName())) {
                    // Hide this field, since it has one of the configured group
                    showField = false;
                    return false;
                }
            }
        } catch (Exception e) {
            showField = false;
            log.error("Exception in UserGroupRenderCondition {}",e.getStackTrace().toString());
        }
        // Render only for the non-configured group
        return showField;
    }

}
