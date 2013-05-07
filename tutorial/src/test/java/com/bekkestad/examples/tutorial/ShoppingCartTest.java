package com.bekkestad.examples.tutorial;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class ShoppingCartTest {

	private static final List<Product> PRODUCT_LIST = new ArrayList<Product>();
	private static final int NR_OF_PRODUCTS_TO_TEST = 4;
	static {
		
		for(int i = 0; i < NR_OF_PRODUCTS_TO_TEST; i++){
			Product product = new Product();
			
			product.setId(i+1L);
			product.setName("PRODUCT" + (i + 1));
			product.setDescription("This is product " + (i+1));
			product.setPrice(10D * (i + 1));
			PRODUCT_LIST.add(product);
		}		
	}
	
	@Deployment
	public static JavaArchive createDeployment(){
		return ShrinkWrap.create(JavaArchive.class, "test.jar")
				.addClasses(ShoppingCart.class, OrderRepository.class, OrderRepositoryImpl.class, Product.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Inject
	ShoppingCart shoppingCart;
	
	@EJB
	OrderRepository order;
	
	@Test
	@InSequence(1)
	public void should_be_able_to_add_products_to_cart(){	
		addProductsToCart();
		
		List<Product> productsInCart = shoppingCart.getShoppingCart();		
		Assert.assertEquals(NR_OF_PRODUCTS_TO_TEST, productsInCart.size());					
	}
	
	@Test
	@InSequence(2)
	public void should_be_able_to_get_the_shopping_cart(){
		addProductsToCart();
		
		List<Product> productsInCart = shoppingCart.getShoppingCart();
		
		int temp = 0;
		for(Product product : productsInCart){
			Assert.assertEquals(PRODUCT_LIST.get(temp).getId(), product.getId());
			Assert.assertEquals(PRODUCT_LIST.get(temp).getName(), product.getName());
			Assert.assertEquals(PRODUCT_LIST.get(temp).getDescription(), product.getDescription());
			Assert.assertEquals(PRODUCT_LIST.get(temp).getPrice(), product.getPrice());
			temp++;
		}		
		if(temp == 0)
			Assert.fail("THE CART IS EMPTY");
	}
	
	@Test
	@InSequence(3)
	public void should_be_able_to_remove_product_from_cart(){		
		addProductsToCart();
		
		int nrOfProductsToRemove = 2;
		for(int i = 0; i < nrOfProductsToRemove; i++){
			shoppingCart.removeProduct((i + 1)*1L);
		}
		List<Product> productsInCart = shoppingCart.getShoppingCart();		
		Assert.assertEquals(NR_OF_PRODUCTS_TO_TEST-nrOfProductsToRemove, productsInCart.size());
	}
	
	@Test
	@InSequence(4)
	public void should_be_able_to_place_order(){
		addProductsToCart();
		
		Long id = shoppingCart.placeOrder();
		List<Product> productsInCart = shoppingCart.getShoppingCart();
		
		Assert.assertEquals(true, id>0);
		Assert.assertEquals(0, productsInCart.size());
		
	}
	
	@Test
	@InSequence(5)
	public void should_be_able_to_get_order_status(){
		Long orderId = order.getOrderId();
		String status = order.getOrder(orderId).getStatus();
		
		Assert.assertEquals("CREATED", status);
	}
	
	private void addProductsToCart(){		
		for(Product product : PRODUCT_LIST){
			shoppingCart.addProduct(product);
		}
	}
}
