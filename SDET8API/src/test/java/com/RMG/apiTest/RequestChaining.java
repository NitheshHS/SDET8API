package com.RMG.apiTest;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
public class RequestChaining {
	@Test
	public void requestChainingTest() {
		
		 Response response = given()
		.baseUri("http://localhost:8084")
		.contentType(ContentType.JSON)
		.when()
		.get("/projects");
		 
		response.then().log().all();
		
		ArrayList<String> projId=response.jsonPath().get("projectId");
		for(String str:projId) {
			System.out.println(str);
		given()
		.contentType(ContentType.JSON)
		.baseUri("http://localhost:8084")
		.pathParam("projectId", str)
		.when()
		.delete("/projects/{projectId}")
		.then()
		.assertThat().statusCode(204)
		.log().all();
		}
		
	}
	
	@Test
	public void createProjectTest() {
		String PROJECTNAME = "Amazon";
		  JSONObject obj=new JSONObject();
		  obj.put("createdBy", "deepak");
		  obj.put("createdOn", "02/01/2021");
		  obj.put("projectId", "RMG_API_04");
		  obj.put("projectName", PROJECTNAME);
		  obj.put("status", "Completed");
		  obj.put("teamSize", 10);
		given()
		.contentType(ContentType.JSON)
		.body(obj)
		.when()
		.post("http://localhost:8084/addProject")
		.then()
		.log().all();
		
		Response res = given()
		.contentType(ContentType.JSON)
		.when()
		.get("http://localhost:8084/projects");
		
		ArrayList<String> projectId = res.jsonPath().get("projectId");
		ArrayList<String> projectName=res.jsonPath().get("projectName");
		LinkedHashMap<ArrayList<String>, ArrayList<String>> map=new LinkedHashMap<ArrayList<String>, ArrayList<String>>();
		map.put(projectName, projectId);
		Iterator<Entry<ArrayList<String>, ArrayList<String>>> itr = map.entrySet().iterator();
		while(itr.hasNext()) {
			Entry<ArrayList<String>, ArrayList<String>> str = itr.next();
			ArrayList<String> projName = str.getKey();
			ArrayList<String> projID = str.getValue();
			for(int i=0;i<projName.size();i++) {
				if(projectName.get(i).equals(PROJECTNAME)){
					given()
						.contentType(ContentType.JSON)
						.pathParam("projectId", projID.get(i))
					.when()
					  .delete("http://localhost:8084/projects/{projectId}")
					  .then().log().all();
				
				}
			}
		}
		
	}

}
