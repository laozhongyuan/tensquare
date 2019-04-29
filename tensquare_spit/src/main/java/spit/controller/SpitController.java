package spit.controller;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import spit.pojo.Spit;
import spit.service.SpitService;

@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    @Autowired
    private RedisTemplate redisTemplate;

     @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功",spitService.findAll());
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public Result findById(@PathVariable String id){

            return new Result(true, StatusCode.OK,"查询成功",spitService.findById(id));
        }
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
            return new Result(true, StatusCode.OK,"保存成功");
        }
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Result update(@PathVariable String id,@RequestBody Spit spit){
           spitService.update(spit);
            return new Result(true, StatusCode.OK,"更新成功");
        }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
           spitService.deleteById(id);
            return new Result(true, StatusCode.OK,"删除成功");
             }
    @RequestMapping(value = "/comment/{parentId}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParent(@PathVariable String parentId,int page,int size){
        Page<Spit> pagelist = spitService.findByParentid(parentId, page, size);
        return new Result(true, StatusCode.OK,"查询成功",new PageResult<Spit>(pagelist.getTotalElements(),pagelist.getContent()));
    }

    @RequestMapping(value = "/thumbup/{id}",method = RequestMethod.PUT)
    public Result updatethumbup(@PathVariable String id){
                  String userid ="2023";
                  if (redisTemplate.opsForValue().get("thumbup_"+userid+"_"+id)!=null){
                      return new Result(false,StatusCode.REMOTEERROR,"你已经点过赞了");
                  }
        spitService.updatethumbup(id);
        redisTemplate.opsForValue().set( "thumbup_"+userid+"_"+ id,"1");
        return new Result(true, StatusCode.OK,"点赞成功");
    }

}
