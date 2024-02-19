# Memoria justificativa del proyecto
---------------------------------------------------------------------
 
## Iteración 1
---------------------------------------------------------------------
 
### Pruebas WS-BPEL
- URL a los documentos WSDL que es necesario utilizar:
 
    http://localhost:7070/rs-deliveries-wscontrib/services/CrmService?wsdl
 
    http://localhost:7070/rs-deliveries-wscontrib/services/RewardService?wsdl
 
    http://localhost:7070/rs-deliveries-wscontrib/services/DeliveryService?wsdl

    http://localhost:5050/CompositeApp1/BusinessClient?wsdl
  - 
    http://localhost:5050/CompositeApp1/ShipmentConfirmClient?wsdl 

    http://localhost:5050/CompositeApp1/DeliveredConfirmationStatus?wsdl


- Nombre del fichero SoapUI con las peticiones: Practica-soapui-project.xml
 
### Justificaciones de diseño
-En cuanto al modelo simplemente seguimos las pautas del enunciado para sacar los casos de uso que nos parecían adecuados para poder realizar las acciones descritas, y creamos las excepciones que también se detallaban en la práctica, después de eso, comprobamos que el código creado era funcional mediante la creación de cada uno de los test para cada uno de los casos de uso, intentando que no quedara ningún posible caso sin probar. Para BPEL creamos la estructura del flujo asi como el mapeo de variables siguiendo el enunciado para posteriormente crear los bindings y la CompositeApp. Por último creamos las pruebas SoapUI para comprobar que el funcionamiento del propio flujo.
 
 
### Problemas conocidos en el diseño / implementación de la práctica
- No existe ningún problema conocido, solo tenemos algún warning en el flujo de BPEL de variables que no están en uso a pesar de que esto no supone ningún problema en la ejecución del mismo.
 
### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- Raúl Fernández del Blanco: En la capa modelo se encargó de crear, tanto el caso de uso de actualizar un envío como los dos casos de buscar envios, también se encargó de crear los test de añadir y buscar un cliente por id, como los de buscar un cliente por texto y buscar varios envíos. En cuanto a BPEL, se encargó de crear los clientes y servicios, el uso del servicio reward y la creación de las pruebas de casos de uso en SoapUI.  
- Armando Martínez Noya: En la capa modelo se encargó de crear las excepxiones así como los casos de uso de crear, eliminar y actualizar un cliente, además de crear los test para eliminar un cliente y buscar y añadir un envío. En cuanto a BPEL se encargó de crear la estructura del flujo y de terminar la lógica del flujo.
- Brais González Piñeiro: En la capa modelo se encargó de crear los casos de uso de buscar cliente tanto por id como por texto y de añadir envíos, asi como los test de actualizar un cliente y actualizar el estatdo de un pedido. En cuanto a BPEL, se encargó del uso del servicio crm y de crear el compositeApp.
 
## Iteración 2
---------------------------------------------------------------------

### Partes opcionales incluidas y miembros del grupo que han participado
- .

### Pruebas WS REST
- Nombre del fichero SoapUI/colección Postman con las peticiones a probar:
- Comandos maven necesarios para ejecutar las pruebas

### Pruebas WS-BPEL
- URL a los documentos WSDL que es necesario utilizar:
- Nombre del fichero SoapUI con las peticiones:

### Justificaciones de diseño
- .

### Problemas conocidos en el diseño / implementación de la práctica
- .

### Resumen de contribución de cada miembro del grupo (consistente con commits en repositorio GIT)
- .
