
package com.amrut.prabhu.producer;

import com.fasterxml.jackson.annotation.JsonInclude;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionEventPayload {

	public TransactionEventPayload () {
	}
	public TransactionEventPayload (
		String transactionId, 
		Integer amount) {
		this.transactionId = transactionId;
		this.amount = amount;
	}


	private String transactionId;
	private Integer amount;

	public String getTransactionId() {
		return transactionId;
	}

	public TransactionEventPayload setTransactionId(String transactionId) {
		this.transactionId = transactionId;
		return this;
	}


	public Integer getAmount() {
		return amount;
	}

	public TransactionEventPayload setAmount(Integer amount) {
		this.amount = amount;
		return this;
	}


	public String toString() {
		return "TransactionEventPayload ["
		+ " transactionId: " + transactionId
		+ " amount: " + amount
		+ " ]";
	}
}

