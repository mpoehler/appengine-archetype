appengine-archetype
===================

This is a maven archetype creating an appengine project with the following features:

- the spring framework (mvc with REST capabilities)
- spring security with google login configured for authentication and enabled method and URL based authorization.
- using objectify as datastore access layer
- the use of selenium tests by starting the devserver in the intergration phase and work with a remotely controlled firefox on it.
- normal unit tests with gae service stubs initialized.
- demo of a secured cron service
- bower to load all js dependencies
- grunt to do concatenation and minification of javascript files
- splitted maven profiles (dev (default), production for use with concatenation and minification of javascript files)

USAGE
-----

- clone this project: ` git clone git@github.com:mpoehler/appengine-archetype.git `
- go into that project: ` cd appengine-archetype `
- build and install the archetype: ` mvn clean install `
- steo one directory up: ` cd .. `
- create a new project based on the archetype (replace <my.groupid> and <my-artifactId> with values of your own): ` mvn archetype:generate -DarchetypeGroupId=eu.tuxoo -DarchetypeArtifactId=appengine-archetype -DarchetypeVersion=1.0 -DinteractiveMode=false -Dversion=1.0-SNAPSHOT -DgroupId=<my.groupid> -DartifactId=<my-artifactId>`
- go into the newly created project: ` cd <my-artifactId> `
- build the new project (see requirements below in case of errors): ` mvn clean install ` 
- run the local devserver: ` cd <my-artifactId>-war; mvn appengine:devserver `

See the README.md file in the created project for details of the several features.

Requirements
------------
The generated project requires the following external software to be present on your system:

- maven version 3 or greater
- npm - Node package manager (needed for bower and grunt)
- firefox - the web browser (needed to run selenium tests)


