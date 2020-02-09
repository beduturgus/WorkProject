FROM ubuntu:18.04
COPY . /BenasProject
RUN make /BenasProject
CMD mvn spring-boot:run