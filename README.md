# Getting Started
## Device manager

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.1.1/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/3.1.1/gradle-plugin/reference/html/#build-image)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

# Create a user
POST http://SERVER:PORT /users
Content-Type: application/json

{
"firstName": "John",
"lastName": "Doe",
"address": "123 Main St",
"birthday": "1990-01-01",
"device": null
}

### Create a device
```POST http://SERVER:PORT/devices```
```
Content-Type: application/json
{
"serialNumber": "ABC123",
"uuid": "123e4567-e89b-12d3-a456-426614174000",
"phoneNumber": "1234567890",
"model": "XYZ Model"
}
```

### Assign device to user
```
POST http://SERVER:PORT /users/a214494b-8b1e-4b47-9204-46ba056f1bdc/assign/243b20fd-8c6b-4784-b3b9-1558a3b6f704
```
```
Content-Type: application/json

{
"serialNumber": "ABC123",
"uuid": "123e4567-e89b-12d3-a456-426614174000",
"phoneNumber": "1234567890",
"model": "XYZ Model"
}
```

### Get all users
```
GET http://SERVER:PORT /users/all
```

### Get users with device
```
GET http://SERVER:PORT /users
```

### Get all devices
```
GET http://localhost:8082/devices
```

### Docker
In order to run the application with docker, run the following command in project folder:
```
docker-compose up -d
```
