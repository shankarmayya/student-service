# student-service
Student service is a Spring Boot application which provides various Rest APIs for performing
CRUD operation. Service connects to MongoDB for storing the student information.

Before running service, one must install MongoDB on local, Please refer below link
for installing MongoDB using HomeBrew on macOS.
https://www.mongodb.com/docs/manual/tutorial/install-mongodb-on-os-x/

Following are the sample Rest Endpoints
1) Save student information (POST /api/v1/student)
2) Retrieve all student information (GET /api/v1/student)
3) Retrieve student by email (GET /api/v1/student/{email})
4) Student lookup using first name, last name and phone number (GET /api/v1/student/lookup)



