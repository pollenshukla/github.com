# AEM features project

This is a project template for AEM-based applications. It is build as per the archetype 10 best practices. This is a sample application to demonstrate:
1.  Page Template, iparsys and parsys
2.  Custom Multifield
3.  OSGi and custom component

The application can easily be deployed to AEM 6.1+ versions. Content is part of application for quick demonstration and should be remove from the package in future builds.

## Modules

The main parts of the template are:

* core: Java bundle containing core functionality like OSGi services as well as component-related Java code using Sling Model.
* ui.apps: contains the /apps (and /etc) parts of the project, ie JS&CSS clientlibs, components, templates, runmode specific configs.
* ui.content: contains sample content using the components from the ui.apps

## How to build

To build all the modules run in the project root directory the following command with Maven 3:

    mvn clean install

If you have a running AEM instance you can build and package the whole project and deploy into AEM with  

    mvn clean install -PautoInstallPackage
    
Or to deploy it to a publish instance, run

    mvn clean install -PautoInstallPackagePublish
    
Or to deploy only the bundle to the author, run

    mvn clean install -PautoInstallBundle

## Important files path

* Scenario 1: configure this paragraph system to accept these OOTB components par-one and par-two : /wb-features.ui.apps/src/main/content/jcr_root/etc/designs/wb-features/_jcr_content/page
Also, par paragraph system is to be used to configure the custom component example.

* Scenario 2: Create a custom Multi-Field component : /wb-features.ui.apps/src/main/content/jcr_root/apps/wb-features/components/content/article/_cq_dialog

* Scenario 3: Create a custom component that consume an OSGi bundle : /wb-features.core/src/main/java/com/pollen/aem/core/models/RelatedLinks.java
The OSGi Model class values are rendered on below component:
 /wb-features.ui.apps/src/main/content/jcr_root/apps/wb-features/components/content/relatedlinks




## Maven settings

The project comes with the auto-public repository configured. To setup the repository in your Maven settings, refer to:

    http://helpx.adobe.com/experience-manager/kb/SetUpTheAdobeMavenRepository.html
