package com.example.jwt;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JwtTests {

	@Value("${custom.jwt.secretKey}")
	private String secretKey;

	@Test
	@DisplayName("secretKey 키가 존재해야한다.")
	void Test1() {
		assertThat(secretKey).isNotNull();
	}

}
