Wildfly 25, Microprofile 4.0
----------------------------
see [https://www.wildfly.org/news/2021/10/05/WildFly25-Final-Released/](https://www.wildfly.org/news/2021/10/05/WildFly25-Final-Released/)
see [https://github.com/wildfly-extras/wildfly-jar-maven-plugin/tree/6.0.0.Final/examples](https://github.com/wildfly-extras/wildfly-jar-maven-plugin/tree/6.0.0.Final/examples)

First steps
--------------
Build project
```bash
mvn clean install
```

Start the server
```bash
mvn wildfly-jar:run
```

Request Rest-Endpoint
```bash
curl http://localhost:8080/hello
```

Dev-Mode (watch of your source files)
see [wildfly-jar-maven-plugin/.../examples/dev-mode](https://github.com/wildfly-extras/wildfly-jar-maven-plugin/tree/master/examples/dev-mode)
```bash
mvn wildfly-jar:dev-watch
```

Custom feature-packs
--------------------
[https://blogs.nologin.es/rickyepoderi/](https://blogs.nologin.es/rickyepoderi/)

### Open Issues

The layer-specs do not allow tweak all existing parameters such as JBoss-CLI tool.
Therefore, its nessesary to add custom cli-scripts to modify these parameters.
This leads to longer build-times and the cli-scripts cannot be included in the custom-feature-pack.

Activate Microprofile-Platform
------------------------------
```xml
<dependencyManagement>
    <!-- importing the microprofile BOM adds MicroProfile specs -->
    <dependency>
        <groupId>org.wildfly.bom</groupId>
        <artifactId>wildfly-microprofile</artifactId>
        <version>${microprofile.version}</version>
        <type>pom</type>
        <scope>import</scope>
    </dependency>
</dependencyManagement>
...
<plugin>
  <groupId>org.wildfly.plugins</groupId>
  <artifactId>wildfly-jar-maven-plugin</artifactId>
  <configuration>
    <layers>
      <layer>microprofile-platform</layer>
    </layers>
  </configuration>
</plugin>
```

Health-Checks
-------------
Required Maven-Module:
```xml
<dependency>
    <groupId>org.eclipse.microprofile.health</groupId>
    <artifactId>microprofile-health-api</artifactId>
    <scope>provided</scope>
</dependency>
```

The Readyness and Liveness -Checks can be provided per annotations and extending MP-Interfaces. See
* [LivenessEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/LivenessEndpoint.java)
* [ReadinessEndpoint.java](src/main/java/com/baloise/codecamp/poc/mp/ReadynessEndpoint.java)

The defaul health-apis can be reached with the following paths:
```bash
 curl http://localhost:9990/health
```
```bash
 curl http://localhost:9990/health/ready
```
```bash
 curl http://localhost:9990/health/live
```

Open API
--------
```bash
curl http://localhost:8080/openapi
```
Result:
```yaml
---
openapi: 3.0.3
info:
  title: ROOT.war
  version: "1.0"
servers:
- url: /
paths:
  /hello:
    get:
      responses:
        "200":
          description: OK
```

Metrics
-------
```xml
<dependency>
  <groupId>org.eclipse.microprofile.metrics</groupId>
  <artifactId>microprofile-metrics-api</artifactId>
  <scope>provided</scope>
</dependency>
```

Individual Metrics-Annotations provide dedicated metrics direct from the Endpoint-Implementations:
* see `@Metered` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Measure for throughput
  
* see `@Timed` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Measure for duration of requests
  
* see `@Counted` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Meaure for counter of requests
  
* see `@Gauge` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Exposes individual current state. 

Try the following curl statements to use the metrics-api
```bash
curl http://localhost:9990/metrics
curl -v http://localhost:9990/metrics --stderr - | grep hello
curl -v http://localhost:9990/metrics --stderr - | grep happyness
```