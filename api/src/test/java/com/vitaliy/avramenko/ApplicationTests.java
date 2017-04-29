package com.vitaliy.avramenko;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {

	@LocalServerPort
	private Integer port;

	@Before
	public void setUp() {
		RestAssured.port = port;
	}

	@Test
	public void rateTest() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String currentDate = simpleDateFormat.format(DateUtils.addDays(new Date(), -1));
		given()
				.contentType(ContentType.JSON)
		.when()
				.get("/rates?startDate=" + currentDate + "&endDate=" + currentDate)
		.then()
				.body("[0]", notNullValue())
				.statusCode(HttpStatus.SC_OK);
	}

}
