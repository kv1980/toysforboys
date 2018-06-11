package be.vdab.toysforboys.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import be.vdab.toysforboys.enums.Status;

@Entity
@Table(name="orders")
public class Order implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@DateTimeFormat(style ="S-")
	private LocalDate ordered;
	@DateTimeFormat(style ="S-")
	private LocalDate required;
	private LocalDate shipped;
	private String comments;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customerId")
	private Customer customer;
	private Status status;
	@Version
	private long version;	
	
	protected Order() {
	}
	
	public Order(LocalDate required, String comments, Customer customer) {
		this.ordered = LocalDate.now();
		this.required = required;
		this.comments = comments;
		this.customer = customer;
	}

	public long getId() {
		return id;
	}

	public LocalDate getOrdered() {
		return ordered;
	}

	public LocalDate getRequired() {
		return required;
	}

	public LocalDate getShipped() {
		return shipped;
	}

	public String getComments() {
		return comments;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Status getStatus() {
		return status;
	}
}
