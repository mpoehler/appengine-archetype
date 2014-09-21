Project-Draft for a AngularJS App with a backend based on the Spring-Framework
==============================================================================

This is a Google App Engine project, already configured to use:

- the spring framework (mvc with REST capabilities)
- spring security with google login configured for authentication and enabled method and URL based authorization.
- using objectify as datastore access layer
- the use of selenium tests by starting the devserver in the intergration phase and work with a remotely controlled firefox on it.
- normal unit tests with gae service stubs initialized.
- demo of a cron service
- bower to load all js dependencies
- grunt to do concatenation and minification of javascript files
- splitted maven profiles (dev (default), production for use with concatenation and minification of javascript files)

Requirements
============
The project requires the following external software to be present on your system:

- maven version 3 or greater
- npm - Node package manager (needed for bower and grunt)
- firefox - the web browser (needed to run selenium tests)

Quickstart
==========

Build the project in devmode: ` mvn clean install `

Build the project for production: ` mvn clean install -P production`

Run a local dev server: ` cd ${rootArtifactId}-war; mvn appengine:devserver `

Deploy to appengine:

- set application name in ${rootArtifactId}/${rootArtifactId}-war/src/main/webapp/WEB-INF/appengine-web.xm
- and run ` mvn appengine:update -P production ` 

Description
===========

Spring MVC
----------
Spring MVC is primarily used to provide REST-Controller for the AngularJS Application. The PersonController is the 
example for this. For security reasons, controllers never access the datastore directly or perform other security 
relevant actions, because method based security is applied to the service layer and NOT to the controllers. 

Objectify
---------
Objectify is used for persistence. A Service, OfyService, must be used by other Services for datastore operations. 
Objectify needs to be configured for each class it should be able to persist. All entity classnames must be listed 
in ${rootArtifactId}/${rootArtifactId}-war/src/main/webapp/WEB-INF/applicationContext-security.xml. 

Security
--------
Spring security is configured to secure URLs AND secure method on the service layer. You can see the configuration 
for secured URLs in /WEB-INF/applicationContext-security.xml which shows restictions on roles. 

The method based security is demonstrated in the PersonService, which protects save and update methods to be used only
by users in ROLE_ADMIN. 

The default ROLE for all authenticated users is ROLE_USER, which is set in GaeUser.

The ROLE_ADMIN is only given to users that are registered developer accounts for the gae application. 
That is set in GoogleAccountsAuthenticationProvider.

This part based on the work of Luke Tailor and [his blog post](http://spring.io/blog/2010/08/02/spring-security-in-google-app-engine/ "Go to spring security on gae blog post")  
and [the sample code provided](https://github.com/spring-projects/spring-security/tree/master/samples/gae-xml "Go to gae spring security sample code"). The sample there offers 
some more features like enrich the google authentication with own user entites.  

Cron
----
Cron Jobs in Google App Engine will be executed using a configuration in ${rootArtifactId}/${rootArtifactId}-war/src/main/webapp/WEB-INF/cron.xml, which causes 
a call to the given URL. The CronController is attached to that URL and calls the CronService. The cron is not 
executed in the devserver and you will need to comment the security setting (see ${rootArtifactId}/${rootArtifactId}-war/src/main/webapp/WEB-INF/applicationContext-security.xml) 
for the url if you want to test it locally within the devserver.  

Unit-Tests
----------

A normal Unit-Test with enabled GAE stubs is shown in ${rootArtifactId}/${rootArtifactId}-war/src/main/test/${package}/PersonServiceTest.java 

Selenium
--------

A Selenium integration test is shown in SeleniumTest. The integration tests can be run using ` mvn verify `. 
You need to have a Firefox browser installed to your system.

Maven profiles
--------------
There are two maven profiles created. The ` dev ` profile (default) is the profile to choose for development. There will 
be no modifications on the JS files or dependencies. That is great for development, because the development server can 
be used just be update the resources, without a time consuming build.
 
The ` production ` profile is used if you want to create a version for deployment or want to test the deployment-ready 
version. 

You can call every one with the P option, for example: ` mvn clean install -P production ` will build a production 
version with minified JS and combined CSS files.

Bower
-----
Bower will simply fetch all JS dependencies declared in packages.json.
Bower and also Grunt needs the npm (Node Package Manager) to be installed. 

Grunt
-----
Grunt does different things, depending on the active profile (see Gruntfile for details). In the dev profile grunt will 
simply copy the bower dependencies into the expanded war directory and make them accessible for the other js files.
 
In the production profile grunt will concat the different javascript files together, minify and uglify them, copy 
them to the target webapp folder and updates all references from the HTML-Files. The same for the CSS files.

Starting the local devserver
============================

Go into the war directory and type ` mvn appengine:devserver ` This will start the local devserver with the dev (default) 
profile. All javascript files are taken unmodified to simplify development.
 
Deployment on appengine
=======================
For deployment on appengine you need to create the application here: https://appengine.google.com/ and then update 
the application name in ${rootArtifactId}/${rootArtifactId}-war/src/main/webapp/WEB-INF/appengine-web.xml and then 
create a production ready version ` mvn clean appengine:update -P production `  