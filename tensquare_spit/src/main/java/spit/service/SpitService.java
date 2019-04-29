package spit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import spit.dao.SpitDao;
import spit.pojo.Spit;
import util.IdWorker;

import java.util.Date;
import java.util.List;

@Service
public class SpitService  {
    @Autowired
    private SpitDao spitDao;

    @Autowired
    private  IdWorker IdWorker;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Spit> findAll(){
        return spitDao.findAll();
    }
    public Spit findById(String id){
        return spitDao.findById(id).get();

    }
    public void  save(Spit spit){
        spit.set_id(IdWorker.nextId()+"");

        spit.setPublishtime(new Date());//发布日期
        spit.setVisits(0);//浏览量
        spit.setShare(0);//分享数
        spit.setThumbup(0);//点赞数
        spit.setComment(0);//回复数
        spit.setState("1");//状态
        if(spit.getParentid()!=null && !"".equals(spit.getParentid())){//  如果存在上级ID,评论
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));
            Update update=new Update();
            update.inc("comment",1);
            mongoTemplate.updateFirst(query,update,"spit");
        }
        spitDao.save(spit);
    }

    public void  update(Spit spit){

        spitDao.save(spit);
    }

    public void deleteById(String id){
        spitDao.deleteById(id);

    }

    public Page<Spit> findByParentid(String parentid, int page, int size){

        PageRequest pageRequest = PageRequest.of(page-1, size);

        return spitDao.findByParentid(parentid, pageRequest);
    }

    public void updatethumbup(String id){
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        Update update = new Update();
         update.inc("thumbup", 1);
         mongoTemplate.updateFirst(query, update, "spit");

    }

}
