package be.vdab.toysforboys.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import be.vdab.toysforboys.entities.Product;

@Embeddable
public class Orderdetail implements Serializable {
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "productId")
	private Product product;
	private long ordered;
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal priceEach;

	protected Orderdetail() {
	}

	public Orderdetail(Product product, long ordered, BigDecimal priceEach) {
		this.product = product;
		this.ordered = ordered;
		this.priceEach = priceEach;
	}

	public Product getProduct() {
		return product;
	}

	public long getOrdered() {
		return ordered;
	}

	public BigDecimal getPriceEach() {
		return priceEach;
	}
	
	public boolean isDeliverable() {
		return ordered <= product.getInStock();
	}
	
	@NumberFormat(pattern = "#,##0.00")
	public BigDecimal getValue() {
		return priceEach.multiply(BigDecimal.valueOf(ordered));
	}
	
	@Override
	public int hashCode() {
		return product.getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Orderdetail)) {
			return false;
			}
		Orderdetail other = (Orderdetail) obj;
		return product.getName().equals(other.getProduct().getName());
	}
}