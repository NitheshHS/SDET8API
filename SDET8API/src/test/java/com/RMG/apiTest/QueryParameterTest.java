package com.RMG.apiTest;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
public class QueryParameterTest {
	@Test
	public void QueryParamterTest() {
		Response res = given()
		.contentType(ContentType.JSON)
		.queryParam("page", "2")
		.when()
		.get("https://reqres.in/api/users");
		
		//ArrayList<String> pageNo = res.jsonPath().get("data.first_name");
//		for(String str:pageNo) {
//		System.out.println(str);
//		}
	res.then()
	.assertThat().body("data[5].first_name", Matchers.equalTo("Rachel12"))
	.and()
	.log().all();
	
	
	}
}
