FROM johnnyjayjay/leiningen:openjdk11 AS build
WORKDIR /usr/src/decoy-link
COPY . .
RUN lein ring uberjar

FROM openjdk:11
WORKDIR /usr/app/decoy-link
COPY --from=build /usr/src/decoy-link/target/*-standalone.jar .
COPY resources/public/ public/
CMD java -jar *-standalone.jar
