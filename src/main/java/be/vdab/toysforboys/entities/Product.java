package be.vdab.toysforboys.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "products")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private long inStock;
	private long inOrder;
	@Version
	private long version;

	protected Product() {
	}

	public Product(String name, long inStock, long inOrder) {
		this.name = name;
		this.inStock = inStock;
		this.inOrder = inOrder;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public long getInStock() {
		return inStock;
	}

	public long getInOrder() {
		return inOrder;
	}

	public void subtractInStockAndInOrder(long quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		inStock -= quantity;
		inOrder -= quantity;
	}

	public boolean isDeliverableInQuantity(long quantity) {
		if (quantity <= 0) {
			throw new IllegalArgumentException();
		}
		return quantity <= inStock;
	}
}