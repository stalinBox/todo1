# Plantilla Base para la coodificación de servicios.
## By Stalin Ramirez
## Codigo Fuente
https://github.com/stalinBox/todo1.git

## Requerimientos
* Servidor WildFly
* IDE STS

## Configuración del driver jdbc de PostgreSQL para la base de datos.


1.- Bajar el jdbc de la base de datos´postgresql-42.2.5.jar´ de la pagina oficial de postgresql y pegar en el path : *home_wildfly\modules\system\layers\base\org\postgresql\main*
> De ser necesario se debe crear las carpetas antes de crear el archivo module.xml

2.- Crear un archivo llamado module.xml en el path: *home_wildfly\modules\system\layers\base\org\postgresql\main*

3.- Pegar el codigo en el archivo module.xml
> El valor del atributo path de la etiqueta <resource-root debe ser el nombre del jar que se descargo y pego en la carpeta main.

''''
	<?xml version="1.0" encoding="UTF-8"?>
	<module xmlns="urn:jboss:module:1.3" name="org.postgresql">
	    <resources>
	        <resource-root path="postgresql-42.2.5.jar"/>
	        <!-- Make sure this matches the name of the JAR you are installing -->
	    </resources>
	    <dependencies>
	        <module name="javax.api"/>
	        <module name="javax.transaction.api"/>
	    </dependencies>
	</module>
''''
