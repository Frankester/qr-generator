# QR GENERATOR API
This is a API for generate your own's QR for the links that you want.

You can cusomize the background and QR color, the size (in pixels) and the format of the QR output file.
If you don't pass any configuration, the system will take a default configuration (see below).
## Considerations

Types of files allowed are: `PNG`(default)  `SVG` `PDF`

Colors must be in hexadecimal format (by default: the background `#FFFFFF` and the QR `#000000`)

The size is in pixels (by default: `125x125 px`)

# Run Locally
 Clone the project
```bash
$ git clone https://github.com/Frankester/qr-generator.git
```
Go to the project directory
```bash
$ cd qr-generator
```
Start with docker compose
```bash
$ docker-compose up
```

__*To see the API endpoints go to:*__
``http://localhost:8080/swagger-ui.html``


# Tech Stack

**Database:** Mysql

**Spring Boot Starters:**  Web,  Data REST, Security, Data JPA

**Documentation**: [Springdoc openapi](https://springdoc.org/v2/)

**Authentication**: [JsonWebToken](https://github.com/jwtk/jjwt)

