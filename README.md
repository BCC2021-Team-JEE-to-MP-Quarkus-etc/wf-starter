Wildfly 25, Microprofile 4.0
----------------------------
see [https://www.wildfly.org/news/2021/10/05/WildFly25-Final-Released/](https://www.wildfly.org/news/2021/10/05/WildFly25-Final-Released/)
see [https://github.com/wildfly-extras/wildfly-jar-maven-plugin/tree/6.0.0.Final/examples](https://github.com/wildfly-extras/wildfly-jar-maven-plugin/tree/6.0.0.Final/examples)

Erste Schritte
--------------
Projekt bauen
```bash
mvn clean install
```

Server starten
```bash
mvn wildfly-jar:run
```

Rest-Endpoint aufrufen
```bash
curl http://localhost:8080/hello
```

Dev-Mode (watch of your source files)
see [wildfly-jar-maven-plugin/.../examples/dev-mode](https://github.com/wildfly-extras/wildfly-jar-maven-plugin/tree/master/examples/dev-mode)
```bash
mvn wildfly-jar:dev-watch
```

Benutzerdefinierte feature-packs für die Konfiguration des Wildfly
------------------------------------------------------------------
[https://blogs.nologin.es/rickyepoderi/](https://blogs.nologin.es/rickyepoderi/)

### Open Issues

Die layer-specs lassen leider nicht alle Einstellungen zu, die mit dem JBoss-CLI tool gemacht werden können.
so ist es erforderlich, dass nachträglich noch cli-scripts gestartet werden müssen.
Diese sind nicht im feature-pack integrierbar und werden erst eim Applikationsbau ausgeführt.


Aktivieren der Microprofile-Platform im Wildfly
-----------------------------------------------
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
Benötigtes Maven-Modul:
```xml
<dependency>
    <groupId>org.eclipse.microprofile.health</groupId>
    <artifactId>microprofile-health-api</artifactId>
    <scope>provided</scope>
</dependency>
```

Annotation-basiert, können die Readyness und Liveness Checks bereitgestellt werden:
* [LivenessEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/LivenessEndpoint.java)
* [ReadinessEndpoint.java](src/main/java/com/baloise/codecamp/poc/mp/ReadynessEndpoint.java)

Default-mässig kann das Health-API dann über den Management-Port 9990 am Wildfly abgefragt werden:
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
Es wird ein yaml zurückgeliefert, mit welchem ein Rest-Client generiert werden kann:
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

Annotation-basiert, können individuelle metrics eingerichtet werden werden:
* siehe `@Metered` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Misst den Durchsatz einer Methode
  
* siehe `@Timed` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Misst die Ausführungszeit einer Methode
  
* siehe `@Counted` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Misst die Anzahl Aufrufe einer 
  
* siehe `@Gauge` in [HelloWorldEndpoint.java](src/main/java/com/baloise/codecamp/wildfly/HelloWorldEndpoint.java):
  
  Gibt Auskunft über einen aktuellen Zustand des Systems 

Default-mässig kann das Metrics-API dann über den Management-Port 9990 am Wildfly abgefragt werden. Die Metrics werden
erst beauskunftet, wenn sie über das API angesprochen wurden (siehe Beispiel-Calls weiter oben):
```bash
curl http://localhost:9990/metrics
curl -v http://localhost:9990/metrics --stderr - | grep hello
curl -v http://localhost:9990/metrics --stderr - | grep happyness
```