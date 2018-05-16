# FileOps

# Configure Email Settings
Used Gmail SMTP configurations to send emails. Please use gmail credentials. Follow below steps to set properties and get email updates of files added in last one hour.

1) Open file /src/main/resources/application.properties
2) Observe line email.to=XXXX@XX.com. Email updates will be recieved to this email address. Replace the email XXXX@XX.com with a valid email.
3) Observe line spring.mail.username=XXXX@XX.com. Add a valid gmail id in the place of XXXX@XX.com.
4) Observe line spring.mail.password=XXXX. Add gmail password in place of XXXX.

# Run Application

1) Install Maven, follow these steps https://maven.apache.org/install.html
2) Go to the project folder
3) Run command mvn -U clean install. All the required dependencies will be downloaded.
4) Run command mvn spring-boot:run, this command triggers the application deployment

# Database Details

1) Open your favorite browser
2) Access URL http://localhost:6452/h2
3) Change JDBC Url in form to jdbc:h2:mem:testdb
4) Click on Connect
5) You can see table File in the tab

# Test Application

Upload a File 
-------------
curl -X POST \
  http://localhost:6452/uploadFile \
  -H 'cache-control: no-cache' \
  -H 'content-type: multipart/form-data; boundary=----WebKitFormBoundary7MA4YWxkTrZu0gW' \
  -H 'postman-token: 28c3c85e-c60f-709e-1b5c-559aedc3ef34' \
  -F file=@someFile.pdf
  
Get File Metadata
------------------
curl -X GET \
  'http://localhost:6452/getFile?id=123' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: c8926625-b2b8-7c96-89c0-c5f65b10d186'

Search a File
--------------
curl -X GET \
  'http://localhost:6452/searchFile?id=123' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: de96aed7-eb45-70da-0b41-6da699f4ebb6'
  
Download File
--------------
curl -X GET \
  'http://localhost:6452/download?id=123' \
  -H 'cache-control: no-cache' \
  -H 'postman-token: cdd2c661-2fe5-719f-7ac3-b13252517025'

Scheduler 
---------
If the email configuration is done. Then for every one hour an email will be recieved with files saved in last one hour in the below format,

----------------------------------------------------------------------------------------------
Hi,
        In last one hour application stored  3files.

Files List:

ID&nbsp;&nbsp;&nbsp;&nbsp;FILE NAME&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;CREATED DATE</br>
0&nbsp;&nbsp;&nbsp;&nbsp;someFile.pdf&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2018-05-17 00:29:25.078</br>
1&nbsp;&nbsp;&nbsp;&nbsp;someFile.pdf&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2018-05-17 00:29:27.953</br>
2&nbsp;&nbsp;&nbsp;&nbsp;someFile.pdf&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2018-05-17 00:29:28.958</br>

-----------------------------------------------------------------------------------------------
