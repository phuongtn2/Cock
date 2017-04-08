package cock.com.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "product_relation")
public class ProductRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productRelationId;
    private long productId;
    private String image;
    private byte status;

    public long getProductRelationId() {
        return productRelationId;
    }

    public void setProductRelationId(long productRelationId) {
        this.productRelationId = productRelationId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }
}
