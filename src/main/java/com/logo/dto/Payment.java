package com.logo.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

	private LocalDateTime paymentDate;
	private CurrencyType currencyType;
	private BigDecimal amount;

	public Payment(LocalDateTime paymentDate, CurrencyType currencyType, BigDecimal amount) {
		super();
		this.paymentDate = paymentDate;
		this.currencyType = currencyType;
		this.amount = amount;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public CurrencyType getCurrencyType() {
		return currencyType;
	}

	public void setCurrencyType(CurrencyType currencyType) {
		this.currencyType = currencyType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Payment [paymentDate=" + paymentDate + ", currencyType=" + currencyType + ", amount=" + amount + "]";
	}

}
