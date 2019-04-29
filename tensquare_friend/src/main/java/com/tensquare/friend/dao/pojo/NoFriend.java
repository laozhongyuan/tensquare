package com.tensquare.friend.dao.pojo;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_nofriend")
@IdClass(NoFriend.class)
@Entity
public class NoFriend implements Serializable {
     @Id
     private String nofriendid;
     @Id
     private String userid;

    public String getNofriendid() {
        return nofriendid;
    }

    public void setNofriendid(String nofriendid) {
        this.nofriendid = nofriendid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
