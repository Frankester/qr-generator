# API GENERADOR DE QR
Esta es una API para generar tus propios códigos QR para los enlaces que desees.

Puedes personalizar el fondo y el color del QR, el tamaño (en píxeles) y el formato del archivo de salida del QR.  
Si no proporcionas ninguna configuración, el sistema usará una configuración predeterminada (ver más abajo).

## Consideraciones

- Tipos de archivos permitidos: `PNG` (predeterminado), `SVG`, `PDF`
- Los colores deben estar en formato hexadecimal (por defecto: fondo `#FFFFFF`, QR `#000000`)
- El tamaño está en píxeles (por defecto: `125×125 px`)

# Ejecución local

Clona el proyecto
```bash
$ git clone https://github.com/Frankester/qr-generator.git
```
Ve al directorio del proyecto
```bash
$ cd qr-generator
```
Inicia con Docker Compose
```bash
$ docker-compose up
```

__*Para ver los endpoints de la API, visita:*__
``http://localhost:8080/swagger-ui.html``


# Stack Tecnológico

**Base de datos:** Mysql

**Spring Boot Starters:**  Web,  Data REST, Security, Data JPA

**Documentación**: [Springdoc openapi](https://springdoc.org/v2/)

**Autenticación**: [JsonWebToken](https://github.com/jwtk/jjwt)

