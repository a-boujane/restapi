
#To test (All operations support JSON only):

this api requires Basic authentication. 
Feel free to use this username and password: 
username:gituser@git.com
password:gitpassword

 - Send a GET req to http://www.salsaeternity.com:8081/api/users to retrieve a list of all users in the DB (Supports JSON)

 - Send a POST req to http://www.salsaeternity.com:8081/api/users with a JSON data to add a new user (the body would be the user's JSON) Make sure you specify the "Content-Type" : "application/json"
 
 - Send a GET req to http://www.salsaeternity.com:8081/api/users/ (Here Goes the user's emailAddres) to retrieve one user
 
 - Sent a PUT req to http://www.salsaeternity.com:8081/api/users/ (Here Goes the user's emailAddress) to modify an existing user (body : JSON of the user)
 
 - Send a DELETE req  http://www.salsaeternity.com:8081/api/users/ (Here Goes the user's emailAddress) to delete one user



# REST Api
This is a simple lightweight RESTful web service that uses the following frameworks/technologies:

JAX-rs, 
Jersey (as a Servlet/Framework for JAX-rs),
Jackson (for JSON support),
JAXB (for XML/JSON binding),
Morphia (for the MongoDB connection)

It implements CRUD operations to interact with a nosql db (MongoDB in this case)


Tested Postman (https://chrome.google.com/webstore/detail/postman/fhbjgbiflinjbdggehcddcbncdddomop/related?hl=en)


