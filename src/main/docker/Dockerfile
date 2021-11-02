FROM library/openjdk:17-jdk-alpine3.13

WORKDIR app

ADD target/*-bootable.jar app.jar

USER 1001

ENV GC_METASPACE_SIZE=96
ENV GC_MAX_METASPACE_SIZE=256

EXPOSE 8080
EXPOSE 9990

ENTRYPOINT ["java" \
    , "-Duser.home=/home" \
    , "-Duser.timezone=Europe/Zurich" \
    , "-Djava.awt.headless=true" \
    , "-server" \
    # , "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8787" \
    , "-XX:MaxRAMPercentage=75" \
    , "-XX:InitiatingHeapOccupancyPercent=75" \
    , "-XX:+HeapDumpOnOutOfMemoryError" \
    , "-jar","./app.jar" \
    ]
