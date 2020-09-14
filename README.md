**#NumberGenerator REST API**

NumberGenerator RESTful service provides the client application ability to:
```
  
  1. To generate output in a text file with given step,goal.
	
  2. To get the status of the task.
	
  3. To get the result of the task.
  
```

**## Basic Authentication for security to REST API**:

All the REST API's in this project are secured with Spring boot basic authentication.

Credential details :-- Username ===> "user"
                       Password ===> "password"


	
 **##POST Number Generator Request Information**:
	
###Input: The user/client application can do a POST request to "/api/generate" URI with below mentioned body to generate numbers in a output text file
                  with basic authentication credentials.
                  
              {
               "goal":"10",
               "step":"2"
              }

###Internal Working: When the API posts the request, details like step , goal are stored in H2 in-memory database in a table "task_detail" with Status as 'NEW'. UUID is generated for 
                      the request and stored in the db.Also stores rowCreated , rowUpdated time in the database for task. Like this for multiple requests UUID is generated and stored in the DB with Status "NEW". 
                      A scheduler service is up and running in the application which has thread pool which takes these tasks from DB with status 'NEW' to write numbers in to a output txt file in /tmp directory
                      based on the goal and step of the task. Once the process of number generation is done for each task in task_details table , status of the task is changed to 'SUCCESS' other wise 'ERROR'. Scheduler service checks for all 'NEW' status tasks in the DB to start processing of the tasks. Scheduler runs with a fixeddelay of 5 seconds after each successful tasks process completion.

###Output: Output returns the UUID generated for the task which is stored in the DB. Sample output below.

{
    "task": "ad0548e1-f681-11ea-aae2-3b9279d1ecd61"
}

###Errors/Validations: Validation of the request body is handled.

![](images/GETAPI.PNG)


**##GET Number Generator Task Status Information**:
	
###Input: The user/client application can do a GET request to "/api/tasks/{UUID of the task}/status" URI with UUID as pathparam of the task which status needs to be returned.
           GET request should be done with basic authentication credentials.
                  
             

###Internal Working: When the API GETS the request, internal service class check for status of the Task in Db for the given UUID. There are three possible statuses this API will return "SUCCESS","IN_PROGRESS","ERROR". Internal service class threadpool will pick the task from the db to write output in to a file. After the task is done status will be updated in the DB accordingly for the respective task.

###Output: Output returns the status of the task. Below is the sample output.

{
    "result": "SUCCESS"
}

###Errors/Validations: HTTP status 204 No content found will be returned if no UUID is found in the DB.

![](images/GETAPI.PNG)


**##GET Number Generator Task Result Information**:
	
###Input: The user/client application can do a GET request to "/api/tasks/{UUID of the task}?action=get_numlist" URI with UUID as pathparam of the task which result needs to be returned and with queryparam as action = get_numlist.GET request should be done with basic authentication credentials.
                  
             

###Internal Working: When the API GETS the request, internal service class checks the DB for filepath for the UUId given. Then content from the file is read and sent back  as result to the API. The file contains integers which are written in descending order based on the POST request. These integers will be sent as comma seperated string to the API.



###Output: Output returns the result of the task. Below is the sample output.

{
    "result": "10,8,6,4,2,0"
}

###Errors/Validations: HTTP status 400 BAD request will be returned if no UUID is found in the DB. Sample result in this case. Also throws 400 is service logic of file read fails.

{
    "result": "Incorrect uuid passed"
}

![](images/GETAPI.PNG)



**##Technologies Used**:

1. Spring Boot - https://projects.spring.io/spring-boot/
2. H2DB(In memory database.) - https://www.h2database.com/html/main.html
3. Swagger - http://swagger.io/
4. Maven - https://mvnrepository.com/

**##Instructions to Setup and start the application**

1.Install Maven - https://maven.apache.org/install.html
2.Clone the code or download from git repository - https://github.com/Pa1sathvik/MyRetail.git
3.Get the project in to eclipse workspace as "Import existing maven projects". This project is divided in to modules(authentication , webapp, service , repository , restcontroller) which are under NumberGenerator parent project as shown below.
![](images/ProjectStructure.PNG)
4.Run the class "NumberGeneratorApplication" as Java file which is in WebApp module.
5.Now application will be up and running on port number 8090 as server port is mentioned as 8090 in application.properties.
6.Now start testing API's as shown in the above screen shot. Use POSTMAN for testing of REST API's. All API's should be used with basic authentication.


##**Swagger UI:**
Swagger displays the following information for an API method by default.

![](images/SwaggerUI.PNG)



