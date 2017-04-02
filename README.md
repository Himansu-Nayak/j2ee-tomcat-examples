Apache TomEE, pronounced "Tommy", is an all-Apache Java EE 6 Web Profile certified stack where Apache Tomcat is top dog.   
Apache TomEE is assembled from a vanilla Apache Tomcat zip file. We start with Apache Tomcat, add our jars and zip up the rest.   
The result is Tomcat with added EE features - TomEE.  
  
TomEE  
The Web Profile version of TomEE contains  
  
CDI - Apache OpenWebBeans  
EJB - Apache OpenEJB  
JPA - Apache OpenJPA  
JSF - Apache MyFaces  
JSP - Apache Tomcat  
JSTL - Apache Tomcat  
JTA - Apache Geronimo Transaction  
Servlet - Apache Tomcat  
Javamail - Apache Geronimo JavaMail  
Bean Validation - Apache BVal  
  
TomEE+  
The TomEE Plus distribution adds the following:  
  
JAX-RS - Apache CXF  
JAX-WS - Apache CXF  
JMS - Apache ActiveMQ  
Connector - Apache Geronimo Connector  
  
Title: Buildling Instructions  
  
# Basic Usage  
  
Apache TomEE is built with Apache Maven.  
  
Simply use  
  
`$&gt; mvn clean install`  
on your commandline to kick off the compile process of TomEE  
  
  
If you intend building in environments where multicast is not allowed  
then build with:  
  
`$&gt; mvn clean install -DskipMulticastTests=true`  
Full build can be executed with (will execute arquillian test on all TomEE distributions)  
  
`$&gt; mvn clean install -Pall-adapters`  
# Quick Build  
  
If you only like to compile all classes and package up TomEE *without* running tests  
then you can use the following build options  
  
`mvn -Pquick -Dsurefire.useFile=false -DdisableXmlReport=true -DuniqueVersion=false -ff -Dassemble -DskipTests -DfailIfNoTests=false clean install`  
# Direct builds  
  
To build TomEE just execute:  
  
`$&gt; mvn clean install -pl tomee/apache-tomee -am -Dmaven.test.skip=true`  
TomEE zip/tar.gz will be in tomee/apache-tomee/target  
  
To build TomEE Embedded to be able to develop with its maven plugin execute:

some test may fail so use mvn clean install -Dmaven.test.failure.ignore=true

`$&gt; mvn clean install -pl maven/tomee-embedded-maven-plugin -am -Dmaven.test.skip=true`

http://stackoverflow.com/questions/30535565/what-is-the-difference-between-tomcat-and-tomee-tomee-and-tomee-plus