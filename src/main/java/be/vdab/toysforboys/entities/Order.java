package be.vdab.toysforboys.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
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
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

import be.vdab.toysforboys.enums.Status;
import be.vdab.toysforboys.valueobjects.Orderdetail;

@Entity
@Table(name = "orders")
@NamedEntityGraph(name = "Order.metCustomer", attributeNodes = @NamedAttributeNode("customer"))
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
	private List<Orderdetail> orderdetails;

	protected Order() {
	}

	public Order(LocalDate required, Customer customer, List<Orderdetail> orderdetails) {
		this.ordered = LocalDate.now();
		this.required = required;
		this.customer = customer;
		this.status = Status.PROCESSING;
		this.orderdetails = orderdetails;
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

	public List<Orderdetail> getOrderdetails() {
		return Collections.unmodifiableList(orderdetails);
	}

	@NumberFormat(pattern = "#,##0.00")
	public BigDecimal getValue() {
		return orderdetails.stream().map(detail -> detail.getValue()).reduce(BigDecimal.ZERO,
				(previousSum, value) -> previousSum.add(value));
	}

	public boolean isShippable() {
		for (Orderdetail detail : orderdetails) {
			if (!detail.isDeliverable()) {
				return false;
			}
		}
		return true;
	}
	
	public void ship() {
		this.status = Status.SHIPPED;
		this.shipped = LocalDate.now();
		for (Orderdetail detail : orderdetails) {
			Product product = detail.getProduct();
			long quantity = detail.getOrdered();
			product.deliver(quantity);
		}
	}
}