package be.vdab.toysforboys.entities;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	private Product product;
	
	@Before
	public void before(){
		product = new Product("testName");
		product.addInStock(20);
		product.addInOrder(15);
	}
	
	@Test
	public void subtractInStockAndInOrder() {
		product.subtractInStockAndInOrder(5);		
		assertEquals(15,product.getInStock());
		assertEquals(10,product.getInOrder());
	}
}