package com.tensquare.friend.dao.pojo;



import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "tb_friend")
@IdClass(Friend.class)
@Entity
public class Friend implements Serializable {
    @Id
     private String friendid;
     @Id
     private String userid;

     private String islike;

    public Friend() {
    }

    public Friend(String friendid, String userid, String islike) {
        this.friendid = friendid;
        this.userid = userid;
        this.islike = islike;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getIslike() {
        return islike;
    }

    public void setIslike(String islike) {
        this.islike = islike;
    }
}
