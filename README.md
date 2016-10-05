# Image Microservice
This repository contains skeleton code for a microservice to handle image upload
and display functionality.  The service will handle requests from the main
application for the following functionality -

* Listing all images in cloud storage
* Saving an image to cloud storage
* Returning the image bytes for rendering


# The REST API

## Listing Images - GET /images/list

When a client accesses the /images/list URL, the image service will return a
list of image names that are stored in cloud storage.

### Params
    None

### Result
    [
      String
    ]



## Saving an Image - POST /images

A POST with base64 encoded image data to /images will store the image in
cloud storage.

### Base64
The image service's REST interface exchanges JSON objects.  The JSON format is text
based and cannot contain binary data.  A common solution is to base64 encode/decode the
binary data.

https://en.wikipedia.org/wiki/Base64

The Base64 Java API -

https://docs.oracle.com/javase/8/docs/api/java/util/Base64.html


### Params
    {
      base64Data: String
    }

### Result
    {
      imageId: String,
      base64Data: String
    }

## Getting an Image - GET /images/{id}

A GET to /images/{id} will return the base64 encoded image data.  The {id} param
is the id returned when listing images.

### Params
    {id} on the URL

### Result
    {
      base64Data: String
    }


# Configuring
The properties for the image service can be found in

    src/main/resources/sample-application.properties

Make a copy of this file and name the copy application.properties.  The
application.properties file is ignored by Git to avoid pushing in secret information
to GitHub.

The parameters to access the cloud storage service will be familiar to you.  The
values are the same that is used by the image gallery application.  

The following properties are new -

    spring.application.name - The name the service will register in Eureka

    # Credentials used to secure the service from outside access
    security.user.name = A user name to access the service
    security.user.password = A password to access the service

    eureka.client.serviceUrl.defaultZone = A URL to the Eureka server

The credentials will secure the service from unauthorized access and will be
used by the image gallery application to access the REST API.  Avoid characters that are special to URLS (@, :, /, %, ?).

The current Eureka server URL points to localhost for initial local testing.  This
value will need to be changed before building and deploying to CloudFoundry.

# Building
The image microservice can be built with the following command

    ./gradlew build


# Running Locally
First, start the Eureka server.  Then run the image service can be ran via -

    java -jar ./build/libs/image-microservice-0.1.0.jar

You should see the image service register with Eureka.


# Deploying to Cloud Foundry

## Eureka's URL

Change localhost:8761 in the following property to the URL reported by CloudFoundry -

    eureka.client.serviceUrl.defaultZone=http://${security.user.name}:${security.user.password}@localhost:8761/eureka

Make sure you keep the /eureka that is at the end of the URL.

## Forcing the Access URL
CloudFoundry runs a global router so that all applications with the same name
will have the same URL.  Unfortunately, a service will use its local container's
hostname when registering.  You will have to add some information to the properties file
to force the service register with the correct URL.  Add the following lines -

    eureka.instance.hostname = ${vcap.application.uris[0]}
    eureka.instance.nonSecurePort = 80

Add the lines as is

## Rebuild
Once the application is properly configured, rebuild the JAR file.

## pushing
The image service can be pushed with the following command -

    cf push
