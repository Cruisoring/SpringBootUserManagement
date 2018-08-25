package com.springboot.user.data.model;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
public abstract class BaseModel implements Serializable, Identifier {
    private static final long serialVersionUID = 1L;

    /**
     * public static String random() {
     * String id = UUID.randomUUID().toString();
     * id = id.replaceAll("-", "");
     * return id;
     * }
     * <p>
     * protected String id = random();
     *
     * @Id
     * @GeneratedValue()
     * @Column(name="PK_ID", length = 32, nullable = false, unique = true)
     * public String getId() {
     * if("".equals(this.id))
     * return null;
     * return this.id;
     * }
     * <p>
     * public void setId(String id) {
     * this.id = "".equals(id) ? null : id;
     * }
     * /
     ***/
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;

    @Override
    public Long getId() {
        return id;
    }

    /***/

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        BaseModel that = (BaseModel) obj;
        if (that.getId() == null)
            return false;

        return this.id.equals(that.getId());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        return id == null ? 0 : id.hashCode();
    }
}
