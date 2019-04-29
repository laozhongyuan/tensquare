package com.tensquare.friend.dao;

import com.tensquare.friend.dao.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendDao extends JpaRepository<Friend,String>{
    public Friend findByUseridAndFriendid(String userId,String friendId);

    @Modifying
    @Query(value = "update tb_friend f set f.islike=?3 where f.userid=?1 and f.friendid=?2",nativeQuery = true)
    public  void updateIsLike(String userId, String friendId, String isLike);

    @Modifying
    @Query(value = "delete from tb_friend where userid =? and friendid=?",nativeQuery = true)
   public void deletefriend(String userid, String friendid);
}
