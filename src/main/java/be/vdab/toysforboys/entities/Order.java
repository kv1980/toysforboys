package be.vdab.toysforboys.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.valueobjects.Orderdetail;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@DateTimeFormat(style = "S-")
	private LocalDate ordered;
	@DateTimeFormat(style = "S-")
	private LocalDate required;
	private LocalDate shipped;
	private String comments;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "customerId")
	private Customer customer;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Version
	private long version;
	@ElementCollection
	@CollectionTable(name = "orderdetails", joinColumns = @JoinColumn(name = "orderId"))
	@OrderBy("productId")
	private Set<Orderdetail> orderdetails;

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

	public Set<Orderdetail> getOrderdetails() {
		return Collections.unmodifiableSet(orderdetails);
	}
}
