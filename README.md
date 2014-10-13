tibco-maven-plugin
==================

Automate the build of TIBCO BusinessWorks

Generate-sources
----------------
Use mvn generate-sources to create .designtimelibs, this file is replaced by Maven's pom.xml and can therefore be generated.

Building
--------
A projlib or EAR file is created based on the first Enterprise Archive or Library Builder resource found in a folder with the name _Builds.

<b>mvn install</b>
Creates the SNAPSHOT archive and puts it in local Maven repository.

<b>mvn deploy</b>
Creates the SNAPSHOT archive and puts it in local Maven as well as remote Nexus repository.

<b>mvn release:prepare</b>
Creates the FINAL archive and tags the source code in VCS.

<b>mvn release:perform</b>
Puts the archive that was build in the release:prepare step in remote Nexus repository.

Cleaning
--------
Use mvn clean to remove all byMaven generated artifacts like the .designtimelibs file.
