# Spring Boot y PostgreSql con Maven - [Developers]
Vamos a crear un ejemplo de API Spring Rest CRUD con Maven que usa Spring Data JPA para interactuar con la base de datos PostgreSQL.

| Métodos | Url|Comportamiento |
| --- | --- | --- |
| POST | http://localhost:8081/tecnologia | Crear tecnología |
| GET | http://localhost:8081/tecnologia | Desplegar tecnología |
| POST |http://localhost:8081/developers| Crear developers|
| GET |http://localhost:8081/developers| Desplegar developers|
| PUT |http://localhost:8081/developers/2| Modificar developers|
| DELETE |http://localhost:8081/developers/3| Eliminar developers|

### Tecnología
- Java 8
- Spring Boot 2.2.1 (con Spring Web MVC, Spring Data JPA)
- PostgreSQL
- Maven 3.6.1

### Estructura del proyecto
![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img9.png?raw=true)

### Crear y configurar el proyecto Spring Boot
Use la herramienta web Spring o su herramienta de desarrollo ( Spring Tool Suite , Eclipse, Intellij ) para crear un proyecto Spring Boot.

### Añadir lo siguiente a archivo pom.xml
```xml
<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.3.1.RELEASE</version>
		<relativePath />
	</parent>
```
```xml
<dependencies>
		<dependency>
			<groupId>javax.validation</groupId>
			<artifactId>validation-api</artifactId>
			<version>2.0.1.Final</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web-services</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
			<exclusions>
				<exclusion>
					<groupId>org.junit.vintage</groupId>
					<artifactId>junit-vintage-engine</artifactId>
				</exclusion>
			</exclusions>
		</dependency>
	</dependencies>
```

### Crear application.properties
Es en este archivo donde se le dan ciertas configuraciones al proyecto. 
La ubicación de este archivo, es la siguiente: 
```
Other sources --> src/main/resources
```
### application.properties
Configure Spring Datasource, JPA, Hibernate
En la carpeta src / main / resources, abra application.properties y escriba estas líneas.

```
server.port = 8081
spring.datasource.url=jdbc:postgresql://localhost:5432/developers
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.show-sql=true
## Hibernate Properties
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
# Hibernate ddl auto (create, create-drop, validate, update)
spring.jpa.hibernate.ddl-auto = update
```

## Definir modelo de datos
### Developers.java
Cambiar el paquete ```com.developers.spring.datajpa.model;``` por el que estimes conveniente.

```java
package com.developers.spring.datajpa.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Developers implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private long id;
	private String cedula;
	private String nombre;
	private String apellido;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinTable(name = "Developers_Tecnologia", joinColumns = {
			@JoinColumn(name = "idDevelopers", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "id_Tecnologia", referencedColumnName = "id") })
	private List<Tecnologia> tecnologia;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public List<Tecnologia> getTecnologia() {
		return tecnologia;
	}

	public void setTecnologia(List<Tecnologia> tecnologia) {
		this.tecnologia = tecnologia;
	}

}


```


### Tecnologia.java

Cambiar el paquete ```com.developers.spring.datajpa.model;``` por el que estimes conveniente.
```java
package com.developers.spring.datajpa.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tecnologia")

public class Tecnologia {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "descripcion", length = 45, nullable = false)
	private String descripcion;

	public Tecnologia() {

	}

	public Tecnologia(String descripcion) {
		this.descripcion = descripcion;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Tecnologia [id=" + id + ", descripcion=" + descripcion + "]";
	}
}

```
## Manejo de errores
### ErrorDetails.java
Cambiar el paquete ```com.developers.spring.datajpa.exception;``` por el que estimes conveniente.
```java 
package com.developers.spring.datajpa.exception;

import java.util.Date;

public class ErrorDetails {
	private Date timestamp;
	private String message;
	private String details;

	public ErrorDetails(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
}

```
### GlobalExceptionHandler.java
Cambiar el paquete ```com.developers.spring.datajpa.exception;``` por el que estimes conveniente.
```java 
package com.developers.spring.datajpa.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

```
### ResourceNotFoundException.java
Cambiar el paquete ```com.developers.spring.datajpa.exception;``` por el que estimes conveniente.
```java 
package com.developers.spring.datajpa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}

```
## Crear interfaz del repositorio
### DevelopersRepository.java
Cambiar el paquete ```com.developers.spring.datajpa.repository;``` por el que estimes conveniente.
```java 
package com.developers.spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developers.spring.datajpa.model.Developers;

@Repository
public interface DevelopersRepository extends JpaRepository<Developers, Long> {
}

```
### TecnologiaRepository.java
Cambiar el paquete ```com.developers.spring.datajpa.repository;``` por el que estimes conveniente.
```java 
package com.developers.spring.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.developers.spring.datajpa.model.Tecnologia;

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {
}

```

## Crear controladores
### DevelopersController.java
Cambiar el paquete ```com.developers.spring.datajpa.controller;``` por el que estimes conveniente.
```java 
package com.developers.spring.datajpa.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developers.spring.datajpa.exception.ResourceNotFoundException;
import com.developers.spring.datajpa.model.Developers;
import com.developers.spring.datajpa.repository.DevelopersRepository;

@Controller
public class DevelopersController {
	@Autowired
	DevelopersRepository developersRepository;

	@RequestMapping(value = "/developers", method = RequestMethod.GET)
	@ResponseBody
	public List<Developers> getDevelopers() {
		return developersRepository.findAll();
	}

	@RequestMapping(value = "/developers", method = RequestMethod.POST)
	@ResponseBody
	public Developers createDevelopers(@Valid @RequestBody Developers developers) {
		return developersRepository.save(developers);
	}

	@RequestMapping(value = "/developers/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<Developers> updateDevelopers(@PathVariable(value = "id") Long developerId,
			@Valid @RequestBody Developers developersDetails) throws ResourceNotFoundException {
		Developers developers = developersRepository.findById(developerId)
				.orElseThrow(() -> new ResourceNotFoundException("Developers no se encuentra : " + developerId));
		developers.setCedula(developersDetails.getCedula());
		developers.setNombre(developersDetails.getNombre());
		developers.setApellido(developersDetails.getApellido());
		final Developers updatedDevelopers = developersRepository.save(developers);
		return ResponseEntity.ok(updatedDevelopers);
	}

	@RequestMapping(value = "/developers/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public Map<String, Boolean> deleteDevelopers(@PathVariable(value = "id") Long developerId)
			throws ResourceNotFoundException {
		Developers developers = developersRepository.findById(developerId)
				.orElseThrow(() -> new ResourceNotFoundException("Developers no se encuentra : " + developerId));
		developersRepository.delete(developers);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
```
### TecnologiaController.java
Cambiar el paquete ```com.developers.spring.datajpa.controller;``` por el que estimes conveniente.

```java 
package com.developers.spring.datajpa.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.developers.spring.datajpa.model.Tecnologia;
import com.developers.spring.datajpa.repository.TecnologiaRepository;

@Controller
public class TecnologiaController {

	@Autowired
	TecnologiaRepository tecnologiaRepository;

	@RequestMapping(value = "/tecnologia", method = RequestMethod.GET)
	@ResponseBody
	public List<Tecnologia> getTecnologia() {
		return tecnologiaRepository.findAll();
	}

	@RequestMapping(value = "/tecnologia", method = RequestMethod.POST)
	@ResponseBody
	public Tecnologia createTecnologia(@Valid @RequestBody Tecnologia tecnologia) {
		return tecnologiaRepository.save(tecnologia);
	}
}
```

## Configuraciones
### Dockerfile
```java 
FROM openjdk:8-jdk-slim
COPY "./target/developers-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
```
### DevelopersApplication.java
Cambiar el paquete ```com.developers;``` por el que estimes conveniente.
```java 
package com.developers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DevelopersApplication {
	public static void main(String[] args) {
		SpringApplication.run(DevelopersApplication.class, args);
	}
}
```
## Ejecutar y probar
La aplicación se encuentra publicada en HEROKU

### Añadir Tecnología - POST
https://developersfreddytorres.herokuapp.com/tecnologia
```json
{
                "id": "1",
                "descripcion": "SPRING BOOT"
}

```
![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img1.png?raw=true)

### Desplegar Tecnología - GET
https://developersfreddytorres.herokuapp.com/tecnologia

![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img2.png?raw=true)

### Añadir Developers - POST
https://developersfreddytorres.herokuapp.com/developers
```json
{
        "cedula": "1745892659",
        "nombre": "FREDDY",
        "apellido": "TORRES",
        "tecnologia": [
            {
                "id": "1",
                "descripcion": "SPRING BOOT"
            }
        ]
}
```
![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img3.png?raw=true)

### Desplegar Developers - GET
https://developersfreddytorres.herokuapp.com/developers

![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img4.png?raw=true)


### Actualizar Developers - PUT
https://developersfreddytorres.herokuapp.com/developers/2
```json
{
        "cedula": "1745892659",
        "nombre": "RUBEN",
        "apellido": "TORRES",
        "tecnologia": [
            {
                "id": "1",
                "descripcion": "SPRING BOOT"
            }
        ]
}
```
![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img5.png?raw=true)


### Eliminar Developers - DELETE
https://developersfreddytorres.herokuapp.com/developers/3

![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img6.png?raw=true)

![Latest Version on Packagist](https://github.com/freddytorres/developers/blob/master/src/main/java/com/developers/spring/datajpa/imagenes/img7.png?raw=true)
## Autor
Freddy Rubén Torres T.

