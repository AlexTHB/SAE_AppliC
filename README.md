# HOW TO USE API

## SUMMARY

- Installation
- HTTP Request

## Installation

Requirements :
  - Java 21
  - Maven
  - Wireshark
  - Database

First of all, we need to copy the source code of frame_analysis, then we need to move in the folder. 

Before, we generate the jar executable you need to set your credentials of the database and have the tables required installed (which are located in src/main/java/gharach_aw/frame_analysis/persistence/tables)

You can set your credentials in src/main/java/gharach_aw/frame_analysis/config/DatasourceConfig. The setup is already configured for mysql database. 

You may need to make several change for run in other database.

Required change : 
 - Import the driver of your database in pom.xml
 - Change the dialect in application.yml (located in src/main/resources)
 - and finally, change the driver in DatasourceConfig

We need to execute this command to generate the jar executable : 
```
mvn clean install
```
This will generate a jar executable in the target folder. It's path is target/frame_analysis.jar.

Now, we can execute this with the command as follow :

```
java -jar target/frame_analysis.jar
```

(If you're always on the root of the folder)

This run the API with embedded server tomcat (so not need to have a web server installed) on the port 8080 by default (You can change this in application.yml).  

## HTTP Request 

We can now make these http request :

POST Request :

For POST request work, you need to have json file extracted from wireshark.
This request will save all packets from packet capture in the database. 
(If you send a file too large, it's not going to work. Limit accepted : 10 GB. You change this in application.yml)

With command line :

```
curl --location 'http://localhost:8080/packetCapture/upload' \
--form 'file=@"./packetCapture.json"'
```

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/9d2e7239-51d6-4aee-a023-8cbc5982dd9d)

With Postman :

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/acd5935a-2733-452e-8d7f-ec9078387c6d)

GET Request :

GET Request permit to retrieve the packets of packet capture you save via POST request.
Here are the different requests :

http://localhost:8080/packetCaptures : retrieve all packetCaptures saved in database with these properties (id, name, date of upload)

With browser :

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/03b3d269-27f0-418d-98ef-0166bff72544)

With Postman : 

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/ffb9b646-4122-4445-aeab-48259c1988bd)

http://localhost:8080/packetCapture/{id} : retrieve one packetCaptures saved in database according to the id

With browser :

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/47c12d5b-ddb9-4bc3-84a6-41c30178cbd1)

With Postman : 

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/5ac57db6-66e8-4ead-9509-ab47ce17c659)

http://localhost:8080/packets/{packetcaptureid} : retrieve all packets with the id of packet capture given

With browser :

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/77308971-284b-4739-a436-8b45d6800302)


With Postman : 

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/53d9f7a0-1e47-46fa-b424-fafde3cdc6fe)

DELETE Request :

DELETE Request permit to delete packet capture and packets with the id of packet capture.

With command line 

```
curl --location --request DELETE 'http://localhost:8080/packetCapture/1'
```

With Postman :

![image](https://github.com/AlexTHB/SAE_AppliC/assets/69178823/1ddbf4a1-85a3-4a57-9f22-54fd53dad139)
