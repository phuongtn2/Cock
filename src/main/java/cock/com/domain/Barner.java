package cock.com.domain;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Barner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String image;
    private byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
