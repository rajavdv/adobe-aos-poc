# AOS POC - To demonstrate AEM customizable options

This a content package project generated using the AEM Multimodule Lazybones template.

## Building

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.

## Using with AEM Developer Tools for Eclipse

To use this project with the AEM Developer Tools for Eclipse, import the generated Maven projects via the Import:Maven:Existing Maven Projects wizard. Then enable the Content Package facet on the _content_ project by right-clicking on the project, then select Configure, then Convert to Content Package... In the resulting dialog, select _src/main/content_ as the Content Sync Root.

## Using with VLT

To use vlt with this project, first build and install the package to your local CQ instance as described above. Then cd to `content/src/main/content/jcr_root` and run

    vlt --credentials admin:admin checkout -f ../META-INF/vault/filter.xml --force http://localhost:4502/crx

Once the working copy is created, you can use the normal ``vlt up`` and ``vlt ci`` commands.

## Specifying CRX Host/Port

The CRX host and port can be specified on the command line with:
mvn -Dcrx.host=otherhost -Dcrx.port=5502 <goals>

## For customizers information

I have done this POC to demonstrate the usage of  “rendercondition” with respect to TouchUI overlay. Please treat this as a reference implementation and put your thoughts around, use similar or better approach to implement solution for AOS. 

Approach used in POC : Allow all permission to dam-user group including replicate permission. Through “rendercondition” blacklist those user group ( dam-user ) from viewing the public / unpublished options in sites.html [2]

Steps I have used in POC
Overlay assets.html [0] into apps. In POC I have copied complete assets.html from /libs to /apps. Implementation team needs to find a way to overlay only the necessary nodes into apps. 
Created custom render condition “usergroup” [1]
Added rendercondition for following nodes. This is only for POC, evaluate for your business need add more entries / ignore entries from below list.
/apps/dam/gui/content/assets/jcr:content/body/assetscontent/header/items/selection/items/publish/rendercondition
/apps/dam/gui/content/assets/jcr:content/body/assetscontent/header/items/selection/items/unpublish/rendercondition
/apps/dam/gui/content/assets/jcr:content/body/assetscontent/header/items/publishpopovercontent/items/list/items/publishlater/rendercondition
/apps/dam/gui/content/assets/jcr:content/body/assetscontent/header/items/unpublishpopovercontent/items/list/items/unpublish/rendercondition
/apps/dam/gui/content/assets/jcr:content/body/assetscontent/header/items/unpublishpopovercontent/items/list/items/unpublishlater/rendercondition
Configured “hiddenForGroups” with the blacklisted groups – UserGroup which should not see this option ( group–id from useradmin – for example ‘dam–admin’)
Configured sling:resourceType with /apps/adobe-poc/components/renderconditions/usergroup
Installed the bundle which has java code for UserGroupRenderCondition.java which is referenced in [1]
User permissions setup: Allow standard permission to dam-user group, including replicate permission

Tests done :
Login as admin user and notice, publish / unpublished option in sites.html [2]
Create new-user and add to dam-user group.
Login with new-user
Notice publish / unpublished options are not available in sites.html [2]
So one possible solution for 80146 is addressed by using rendercondition for appropriate nodes.

Artifacts : I have up
Disclaimer : Since it is POC, I have tested in my local with limited scope in mind, Implementation team should use this as reference implementation only.  Bug / Error in this POC will not be addressed. 

[0] /libs/dam/gui/content/assets
[1] /apps/adobe-poc/components/renderconditions/usergroup/usergroup.jsp
[2] http://localhost:7502/assets.html/content/dam


