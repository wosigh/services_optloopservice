

      ,---.     |    |                   ,---.                .
      |   |,---.|--- |    ,---.,---.,---.`---.,---.,---..    ,.,---.,---.
      |   ||   ||    |    |   ||   ||   |    ||---'|     \  / ||    |---'
      `---'|---'`---'`---'`---'`---'|---'`---'`---'`      `'  ``---'`---'
           |                        |


INTRODUCTION
------------

   Firstly, thank you for taking interest in this community project.
   If you would just like to start using this program, you don't have to build
   this if you have the prebuilt .jar. You can simply put the prebuilt .jar in
      /usr/lib/luna/java
   on your device. The .service file needs to go in
      /usr/share/dbus-1/system-services.

   NOTE THAT THIS SERVICE IS NOT FINISHED, AND IS LIKELY TO DO NEXT TO NOTHING.


PROGRESS
--------

   This service is not done yet! There is still work to be done with regards to
   actually allowing mounting and unmounting. Dropbear, OpenSSH, and any other
   processes running from /opt need to be killed before the VFS can be
   unmounted. We need Java methods to do this. You can help by translating the
   bash script 1lnxraider provided to Java. The bash script is located at
   http://www.webos-internals.org/wiki/Tutorials_Linux_opt_on_loopback


BUILD PREREQUISITES
-------------------

   The following .jar's are required to build this service:

      * serviceframework.jar
      * lunaservice.jar
      * json.jar
      * Utils.jar

   You can find them in /usr/lib/luna/java on an actual Palm Pre or in the
   VirtualBox image provided in the Palm Pre SDK.


BUILDING
--------

   The easiest way to build is to use Ant with 'ant clean build jar' (minus
   quotes) at a command prompt to build the .java to .jar using build.xml.

   You can also build the .java more manually using
      javac -classpath json.jar;lunaservice.jar;serviceframework.jar;Utils.jar OptLoopService.java
   at the command line. This will generate a .class file. Then, .jar it:
      jar -cvf OptLoopService.class
   Make sure the .jar goes in /usr/lib/luna/java
   and the .service in /usr/share/dbus-1/system-services.


TESTING
-------

   Once everything is in place, and you have rebooted, for testing purposes you
   should be able to make calls to the service using a command like this:
      /usr/bin/luna-send -n 1 palm://optloopservice/METHOD {PARAMETERS}
   For instance,
      /usr/bin/luna-send -n 1 palm://optloopservice/version {}
   should return a JSON object with the name of the service, and its version.


CREDITS
-------

   I would like to thank all of you for your tremendous help.
   Wicked props for this project go out to:

    - The Apache Ant Project
    - ActiveState's free text editor Komodo Edit
    - 1lnxraider for documenting the /opt on loopback method at PreCentral.net
         as well as webOS-internals.org/wiki
    - aonic, a.k.a. Raja Kapur, for example My Tether app which this is based
         slightly upon
    - destinal for helping troubleshoot
    - egaudet for helping troubleshoot and for example AccelService
    - PuffTheMagic for the initial loopback procedure
    - rwhitby for the service skeleton
    - Templarian
    - Everyone on #webos-internals (PM ultraBlack at PreCentral.net to be added)


CONTACT
-------

   You can PM ultraBlack at PreCentral.net regarding this project.
   You can also talk about the virtual file system project in this thread:
   http://forums.precentral.net/webos-apps-software/192673-sorry-not-enough-memory.html

   Hop on #webos-internals on the FreeNode IRC network--you may not catch
   ultraBlack personally, but you'll meet many other helpful people.
