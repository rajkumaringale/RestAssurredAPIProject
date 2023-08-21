package api.testcases;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.github.javafaker.Faker;

import api.endpoints.userEndPoints;
import api.payload.user;
import io.restassured.response.Response;

public class UserTests {
    
	Faker faker;
	user userPayload;
	
	public Logger logger;
	
	@BeforeClass
	public  void setup()
	{
		faker= new Faker();
		userPayload = new user();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5,10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
		
		//logs
		logger=LogManager.getLogger(this.getClass());
		
	}
	@Test(priority=1)
	public void testPostUser()
	{   
		logger.info("****************** Creating User ***********************");
		Response response = userEndPoints.createUser(userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("****************** User is Created ***********************");

    }   
	@Test(priority=2)
	public void testGetUser()
  {     
		logger.info("****************** Reading User Info ***********************");

		Response response = userEndPoints.getUser(this.userPayload.getUsername());
		System.out.println("Get User Data.");
		response.then().log().all();
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("****************** User Info is Displayed ***********************");

} 
	@Test(priority=3)
	public void testUpdateUser()
	{  
		logger.info("****************** Updating User***********************");

		
		//Update data using payload
		userPayload.setFirstName(faker.name().firstName());
		userPayload.setLastName(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		
		Response response = userEndPoints.updateUser(this.userPayload.getUsername(),userPayload);
		response.then().log().all();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("****************** User is Updated ***********************");

		//Checking data after update if firstname,lastname,email is updated
		Response responseafterupdate = userEndPoints.getUser(this.userPayload.getUsername());
		System.out.println("After Update User Data.");
		responseafterupdate.then().log().all();
		Assert.assertEquals(responseafterupdate.getStatusCode(),200);
	}
	@Test(priority=4)
	public void testDeleteUser()
	{   
		logger.info("****************** Deleting User***********************");
        Response response = userEndPoints.deleteUser(this.userPayload.getUsername());
		
		//log response
		response.then().log().all();
		//validations
		Assert.assertEquals(response.getStatusCode(),200);
		
		logger.info("****************** User is Deleted ***********************");

		
		//Checking data after delete
		Response responseafterdelete = userEndPoints.getUser(this.userPayload.getUsername());
		Assert.assertEquals(responseafterdelete.getStatusCode(),404);
}}