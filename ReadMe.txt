EmployWise Java Assignment

EmployWise Java Assignment is a Spring boot managing employee information using MongoDB.

To run EmployWise you need to have MongoDB installed.
Visit the official MongoDB website and download the appropriate version for your operating system.

MongoDB username and password are admin

MongoDB port - 27017
MongoDB database - employWise

Run the spring application on port "8081"

The application will be accessible at - http://localhost:8081/employee


API Endpoints:
1. Get all employees:
	Endpoint: GET /findAll

2. Update Employee by Id:
	Endpoint : PUT /update/{id}

3. Upload new Employee:
	Endpoint: POST /

4. Delete Employee by Id:
	Endpoint: DELETE /delete/{id}

5. Get Nth level manager:
	Endpoint: GET /{id}/manager/{level}

6. Get Employees with pagination and sorting:
	Endpoint: GET /advanceSearch/{pageNumber}/{pageSize}/{sortBy}


To use the API endpoint you can use platforms like Postman










