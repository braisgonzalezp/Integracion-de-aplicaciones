[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/qLz2mrby)
# Running the project example
---------------------------------------------------------------------

## Running the deliveries service with Maven/Jetty.

    cd rs-deliveries/rs-deliveries-service
    mvn jetty:run


## Running the deliveries client application

- Configure `rs-deliveries/rs-deliveries-client/src/main/resources/ConfigurationParameters.properties`
  for specifying the client project service implementation (XML or JSON) and the port number 
  of the web server in the endpoint address (7070 for Jetty)
  
- Change to `rs-deliveries-client` folder

    cd rs-deliveries/rs-deliveries-client


- AddCustomer

    mvn exec:java -Dexec.mainClass="es.udc.rs.deliveries.client.ui.DeliveryServiceClient" -Dexec.args="-addCustomer 'New Customer'"

- RemoveCustomer

    mvn exec:java -Dexec.mainClass="es.udc.rs.deliveries.client.ui.DeliveryServiceClient" -Dexec.args="-removeCustomer 1"

