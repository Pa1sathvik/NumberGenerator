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


	
 **##Post Number Generator Request Information**:
	
###Input: The user/client application can do a POST request to "/api/generate" URI with below mentioned body to generate numbers in a output text file
                  with basic authentication credentials.
                  
              {
               "goal":"10",
               "step":"2"
              }

###Internal Working: When the API posts the request, details like step , goal are stored in H2 runtime database in a table "task_detail" with Status as 'NEW'. UUID is generated for 
                      the request and stored in the db.Also stores rowCreated , rowUpdated time in the database for task. Like this for multiple requests UUID is generated and stored in the DB with Status "NEW". 
                      A scheduler service is up and running in the application which has thread pool which takes these tasks from DB with status 'NEW' to write numbers in to a output txt file in /tmp directory
                      based on the goal and step of the task. Once the process of number generation is done for each task in task_details table , status of the task is changed to 'SUCCESS' other wise 'ERROR'.

###Output: Output returns the UUID generated for the task which is stored in the DB. Sample output below.

{"id":13860428,"name":"The Big Lebowski (Blu-ray) (Widescreen)","current_price":{"value": 13.49,"currency_code":"USD"}}

###Errors/Validations: Validation of the request body is handled.

![](images/GETAPI.PNG)
