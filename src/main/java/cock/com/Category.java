package cock.com;

import cock.com.domain.Product;

import java.util.List;

/**
 * Created by phuon on 11/05/2017.
 */
public class Category {
	private List<Product> productList;

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}
}
