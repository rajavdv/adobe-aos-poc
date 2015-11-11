<%@include file="/libs/granite/ui/global.jsp" %><%
%><%@page session="false"
          import="com.adobe.poc.UserGroupRenderCondition,
    		      java.util.Arrays,
                  java.util.List,
                  org.apache.jackrabbit.api.security.user.Authorizable,
                  com.adobe.granite.ui.components.Config,
                  com.adobe.granite.ui.components.rendercondition.RenderCondition"%><%
/**
A condition that decides based on group membership.
@name Privilege
@location adobe-poc/components/renderconditions/usergroup
@property {String[]} [hiddenForGroups] The group id's for which the option needs to be hidden
@example
+ mybutton
  - sling:resourceType = "granite/ui/components/foundation/button"
  + rendercondition
    - sling:resourceType = "adobe-poc/components/renderconditions/usergroup"
    - groups = ["dam-users"]
*/
    Config cfg = cmp.getConfig();
    List<String> groups = Arrays.asList(cfg.get("hiddenForGroups", new String[0]));
    if (groups.isEmpty()) {
        return;
    }
    Authorizable authorizable = resourceResolver.adaptTo(Authorizable.class);
    if (authorizable != null) {
        request.setAttribute(RenderCondition.class.getName(), new UserGroupRenderCondition(authorizable, groups));
    }
%>
