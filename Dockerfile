FROM docker.io/eclipse-temurin:17-jre-alpine

LABEL org.opencontainers.image.source=https://github.com/extact-io/msa-rms-service-user

WORKDIR /msa-service-user

COPY ./target/msa-rms-service-user.jar ./
COPY ./target/libs ./libs

CMD ["java", "-jar", "msa-rms-service-user.jar"]

EXPOSE 7004
