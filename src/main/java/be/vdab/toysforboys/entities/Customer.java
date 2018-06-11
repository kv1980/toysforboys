package be.vdab.toysforboys.entities;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import be.vdab.toysforboys.valueobjects.Adress;

@Entity
@Table(name="customers")
public class Customer implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	@Embedded
	private Adress adress;
	protected Customer() {
	}
	public Customer(long id, String name, Adress adress) {
		this.id = id;
		this.name = name;
		this.adress = adress;
	}
	public long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Adress getAdress() {
		return adress;
	}
}
