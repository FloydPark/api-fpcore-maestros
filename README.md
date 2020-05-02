# api-fpcore-maestros

Microservicio que provee las funcionalidades de los recursos maestros, tales como 
- Productos
- Proveedores
- Puntos de venta
- Otros

## Requisitos ##

- Open JDK 8

## Iniciar API ##

- Primero se construye el artefacto:

`./gradlew assemble`

- Luego se lanza la aplicación:

`java -jar build/libs/api-fpcore-maestros*.jar`

## Unit testing ##

`./gradlew test`