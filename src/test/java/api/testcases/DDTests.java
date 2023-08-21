package api.testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import api.endpoints.userEndPoints;
import api.payload.user;
import api.utilities.DataProviders;
import io.restassured.response.Response;

public class DDTests {
	
	
	@Test(priority=1,dataProvider="Data",dataProviderClass=DataProviders.class)
	public void testPostuser(String userID, String userName, String fname, String lname, String useremail, String pwd, String ph)
	{
		user userPayload=new user();
		
		userPayload.setId(Integer.parseInt(userID));
		userPayload.setUsername(userName);
		userPayload.setFirstName(fname);
		userPayload.setLastName(lname);
		userPayload.setEmail(useremail);
		userPayload.setPassword(pwd);
		userPayload.setPhone(ph);
		
        Response response = userEndPoints.createUser(userPayload);
		
		System.out.println("Create User Data.");
		response.then().log().all();
	    Assert.assertEquals(response.getStatusCode(),200);
	}
	
	@Test(priority=2, dataProvider="UserNames", dataProviderClass=DataProviders.class)
	public void testDeleteUserByName(String userName)
	{
		
		Response response= userEndPoints.deleteUser(userName);
	    Assert.assertEquals(response.getStatusCode(),200);

		
		
	}

}
