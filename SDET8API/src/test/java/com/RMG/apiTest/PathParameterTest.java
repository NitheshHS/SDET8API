package com.RMG.apiTest;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static io.restassured.RestAssured.*;

import java.util.concurrent.TimeUnit;

public class PathParameterTest {
	
	@Test
	public void pathParamTest() {
		String projectId="TY_PROJ_1202";
		given()
		.contentType(ContentType.JSON)
		.baseUri("http://localhost:8084")
		.pathParam("projID", projectId)
		.when()
		.get("/projects/{projID}")
		.then()
		.assertThat().statusCode(200)
		.and().
		assertThat().header("Content-Type", "application/json")
		.and()
		.assertThat().time(Matchers.lessThan(2000L), TimeUnit.MILLISECONDS)
		.and()
		.assertThat().body("projectId", Matchers.equalTo(projectId))
		.log().all();
	}

}
