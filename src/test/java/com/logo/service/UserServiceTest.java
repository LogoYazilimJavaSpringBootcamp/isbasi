package com.logo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

import com.logo.client.PaymentClient;
import com.logo.dto.CurrencyType;
import com.logo.dto.EmailDto;
import com.logo.dto.Payment;
import com.logo.exception.IsbasiException;
import com.logo.model.User;
import com.logo.model.enums.FirmType;
import com.logo.repository.UserRepository;

@SpringBootTest
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private RabbitTemplate rabbitTemplate;

	@Mock
	private PaymentClient paymentClient;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void init() {
		// Mockito.when(paymentClient.createPayment(Mockito.anyInt())).thenReturn(null);
	}

	@Test
	// @DisplayName("it should create user")
	void it_should_create_user_amount_grather_then_1000() {

		// When

		Payment payment = new Payment(LocalDateTime.now(), CurrencyType.TL, new BigDecimal(1001));

		Mockito.when(paymentClient.createPayment(Mockito.any())).thenReturn(payment);

		User user = prepareUser();

		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

		// given

		User response = userService.createUser(user);

		// then

		verify(userRepository, times(1)).save(Mockito.any());
		verify(rabbitTemplate, times(1)).convertAndSend(Mockito.eq("isbasi.email"), Mockito.any(EmailDto.class));

		assertThat(response.getName()).isEqualTo(user.getName());
		assertThat(response.getEmail()).isEqualTo("patika@gmail.com");
		assertThat(response.getFirmType()).isEqualTo(FirmType.CORPORATE);
		// verify(userRepository.save(Mockito.anyString()));
		// verify(userRepository.save(Mockito.anyInt()));

	}

	@Test
	// @DisplayName("it should create user")
	void it_should_create_user_amount__then_1000() {

		// When

		Payment payment = new Payment(LocalDateTime.now(), CurrencyType.TL, new BigDecimal(100));

		Mockito.when(paymentClient.createPayment(Mockito.any())).thenReturn(payment);

		User user = prepareUser();

		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

		// given

		User response = userService.createUser(user);

		// then

		verify(userRepository, times(1)).save(Mockito.any());
		verifyNoInteractions(rabbitTemplate);

		assertThat(response.getName()).isEqualTo(user.getName());
		assertThat(response.getEmail()).isEqualTo("patika@gmail.com");
		assertThat(response.getFirmType()).isEqualTo(FirmType.INDIVIUAL);

	}

	private User prepareUser() {
		User user = new User();
		user.setName("patika");
		user.setEmail("patika@gmail.com");
		return user;
	}

	@Test
	void it_should_return_user_by_email() {

		String email = "patika@gmail.com";

		User user = prepareUser();

		Mockito.when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

		User response = userService.getUserByEmail(email);

		verify(userRepository, times(1)).findByEmail(email);

		assertThat(response).isNotNull();
		assertThat(response.getEmail()).isEqualTo(user.getEmail());

	}

	@Test
	void it_should_throw_isbasiexception_when_user_not_found() {

		IsbasiException expectedException = new IsbasiException("user not found");

		Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenThrow(expectedException);

		Throwable exception = catchThrowable(() -> userRepository.findByEmail(Mockito.anyString()));

		assertThat(exception).isInstanceOf(IsbasiException.class);
		assertThat(exception.getMessage()).isEqualTo(expectedException.getMessage());

	}

}
