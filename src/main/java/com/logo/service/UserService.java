package com.logo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.logo.client.PaymentClient;
import com.logo.dto.CurrencyType;
import com.logo.dto.EmailDto;
import com.logo.dto.Payment;
import com.logo.exception.IsbasiException;
import com.logo.model.Address;
import com.logo.model.Customer;
import com.logo.model.User;
import com.logo.model.enums.FirmType;
import com.logo.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RabbitMQService rabbitMQService;

	@Autowired
	private AmqpTemplate rabbitTemplate;

	@Autowired
	private PaymentClient paymentClient;

	public User createUser(User user) {
		
		Address address = new Address("Türkiye", "İstanbul", "Açık address");

		// user.setAddress(address);

		List<Customer> customers = new ArrayList<>();
		customers.add(Customer.builder().name("Şenol").build());
		customers.add(Customer.builder().name("Onur").build());
		customers.add(Customer.builder().name("Müjdat").build());
		customers.add(Customer.builder().name("Sevim").build());
		customers.add(Customer.builder().name("Gizem").build());
		customers.add(Customer.builder().name("Çağla").build());

		Payment payment = paymentClient
				.createPayment(new Payment(LocalDateTime.now(), CurrencyType.TL, BigDecimal.TEN));

		if (payment.getAmount().intValue() > 1000) {
			user.setFirmType(FirmType.CORPORATE);
			rabbitTemplate.convertAndSend("isbasi.email", new EmailDto("user@gmail.com", "Java Dev",
					"Patika eğitimleri ile java developer yetiştirilmek istenmektedir."));

		} else {
			user.setFirmType(FirmType.INDIVIUAL);
		}

		log.info(payment.toString());

		// user.setCustomerList(customers);

		// hata çıktığını varsayımı
		// throw new FileNotFoundException();

		return userRepository.save(user);
	}

	public void deleteUserById(int id) {

		// throw new ArithmeticException();

		/*
		 * deleteById metodu da olabilir.
		 */
		User foundUser = userRepository.findById(id).orElseThrow(RuntimeException::new);

		userRepository.delete(foundUser);

		// userRepository.deleteAll();

		// userRepository.deleteById(id);
	}

	public void deleteUserByEmail(String email) {

		Optional<User> foundUser = userRepository.findByEmail(email);
		if (!foundUser.isPresent()) {
			log.info("user not found");
		}

		userRepository.delete(foundUser.get());

	}

	public User updateUser(User user) {

		// String sql = "Update User set email = yeniemail where id =1";

		User foundUser = userRepository.findById(user.getId()).get();

		foundUser.setEmail(user.getEmail());
		foundUser.setSurname(user.getSurname());

		return userRepository.save(foundUser);
	}

	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	public User getUserByEmail(String email) {

		// throw new NullPointerException();

		/*
		 * kullanıcı bulunamadığında hata verilmeli. Aşağıdaki iki kullanım da olabilir.
		 * Kendi Exception classımızı oluşturmamız gerek
		 */

		User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new IsbasiException("user not found"));

		// userRepository.findByEmail(email).orElseThrow(RuntimeException::new);

		// Optional<User> foundUser = userRepository.findByEmail(email);

//		boolean isPresent = foundUser.isPresent();
//		if (isPresent) {
//			return foundUser.get();
//		}
		// null dönme!
		return foundUser;

	}
//
//	public List<Customer> getCustomersByEmail(String email) {
//		User foundUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException());
//
//		return foundUser.getCustomerList();
//	}

}
