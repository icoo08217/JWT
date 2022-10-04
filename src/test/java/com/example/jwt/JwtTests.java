package com.example.jwt;

import com.example.jwt.app.jwt.JwtProvider;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Base64;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class JwtTests {

	@Autowired
	private JwtProvider jwtProvider;

	@Value("${custom.jwt.secretKey}")
	private String secretKeyPlain;

	@Test
	@DisplayName("secretKey 키가 존재해야한다.")
	void Test1() {
		assertThat(secretKeyPlain).isNotNull();
	}

	@Test
	@DisplayName("secretKey 원문으로 hmac 암호화 알고리즘에 맞는 SecretKey 객체를 만들 수 있다.")
	void Test2() {
		String keyBase64Encoded = Base64.getEncoder().encodeToString(secretKeyPlain.getBytes());
		SecretKey secretKey = Keys.hmacShaKeyFor(keyBase64Encoded.getBytes());
		// hmacShakeyFor => 알고리즘는 size가 256 bits 이상을 가져야 한다.

		System.out.println(secretKey.getAlgorithm());
		assertThat(secretKey).isNotNull();
	}

	@Test
	@DisplayName("JwtProvider 객체로 시크릿키 객체를 생성할 수 있다.")
	void Test3() {
//		SecretKey secretKey = jwtProvider.getSecretKey();
		SecretKey secretKey = TestUtil.callMethod(jwtProvider , "getSecretKey");
		System.out.println(secretKey.getClass());

		assertThat(secretKey).isNotNull();

		// key가 한번만 만들어졌으면 좋겠다.
		// getSecretKey 를 호출할 때마다 객체를 생성한다.
	}

	@Test
	@DisplayName("Secret 객체는 단 한번만 생성되어야 한다.")
	void Test4() {

		// key가 한번만 만들어졌으면 좋겠다.
		// getSecretKey 를 호출할 때마다 객체를 생성한다.
//		SecretKey secretKey1 = jwtProvider.getSecretKey();
		SecretKey secretKey1 = TestUtil.callMethod(jwtProvider , "getSecretKey");
//		SecretKey secretKey2 = jwtProvider.getSecretKey();
		SecretKey secretKey2 = TestUtil.callMethod(jwtProvider , "getSecretKey");

		System.out.println(secretKey1);
		System.out.println(secretKey2);

		assertThat(secretKey1 == secretKey2).isTrue();
	}
}
