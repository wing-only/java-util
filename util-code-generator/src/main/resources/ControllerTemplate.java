package @controllerPackage@;

import @modelPackage@.@ModelName@;
import @voPackage@.@ModelName@QryReqVo;
import @voPackage@.@ModelName@QryRespVo;
import @IServicePackage@.@IService@;
import lombok.extern.slf4j.Slf4j;
import com.wing.java.util.BaseController;
import com.wing.java.util.param.page.Page;
import com.wing.java.util.param.http.HttpRspParam;
import com.wing.java.util.validator.group.AddGroup;
import com.wing.java.util.validator.group.UpdateGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Map;
import java.util.Collection;
import java.util.List;

/**
 * @tableComment@管理
 */
@Api(value = "@serviceComment@接口",tags="@serviceComment@接口")
@RestController
@RequestMapping("/@ModelName@")
@Slf4j
@SuppressWarnings("all")
public class @ModelName@Controller extends BaseController{

    @Autowired
    private @IService@ @iService@;


    /**
    * 增加接口
    */
    @PostMapping("/insert")
    @ApiOperation(value="增加接口", notes="增加接口",response=@ModelName@.class)
    public HttpRspParam insert(@RequestBody @Validated(AddGroup.class) @ModelName@ @modelName@){
        return success(@iService@.insert(@modelName@));
    }
    
    /**
     * 删除接口，通过id删除
     */
    @PostMapping("/deleteById/{id}")
    @ApiOperation(value="删除接口，通过id删除", notes="删除接口，通过id删除",response=String.class)
    public HttpRspParam deleteById(@PathVariable("id") Long id){
        @iService@.deleteById(id);
        return success("删除成功");
    }

    /**
     * 删除接口，通过id列表方式，sql样例 in(1,2,3...)
     */
    @PostMapping("/deleteByIds")
    @ApiOperation(value="批量删除接口，通过id列表批量删除", notes="批量删除接口，通过id列表批量删除",response=String.class)
    public HttpRspParam deleteByIds(@RequestBody Collection<Long> ids){
        @iService@.deleteByIds(ids);
        return success("删除成功");
    }

    /**
     * 更新接口，通过id更新
     */
    @PostMapping("/updateById")
    @ApiOperation(value="更新接口，通过id更新", notes="更新接口，通过id更新",response=String.class)
    public HttpRspParam updateById(@RequestBody @Validated({UpdateGroup.class}) @ModelName@ @modelName@){
        @iService@.updateById(@modelName@);
        return success("修改成功");
    }

    /**
     * 查询接口，通过id查找，只返回一个实体或者返回null
     */
    @PostMapping("/selectById/{id}")
    @ApiOperation(value="查询接口，通过id查找", notes="查询接口，通过id查找，只返回一个实体或者返回null",response= @ModelName@QryRespVo.class)
    public HttpRspParam selectById(@PathVariable("id") Long id){
        @ModelName@QryRespVo @modelName@QryRespVo = @iService@.selectById(id);
        return success(@modelName@QryRespVo);
    }

    /**
     * 查询接口，通过id数组查找
     */
    @PostMapping("/selectByIds")
    @ApiOperation(value="查询接口，通过id数组查找", notes="查询接口，通过id数组查找，返回实体列表",response=@ModelName@QryRespVo[].class)
    public HttpRspParam selectByIds(@RequestBody Collection<Long> ids){
        List<@ModelName@QryRespVo> @modelName@QryRespVos = @iService@.selectByIds(ids);
        return success(@modelName@QryRespVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectList")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response=@ModelName@QryRespVo[].class)
    public HttpRspParam selectList(@RequestBody @ModelName@QryReqVo @modelName@QryReqVo){
        List<@ModelName@QryRespVo> @modelName@QryRespVos = @iService@.selectList(@modelName@QryReqVo);
        return success(@modelName@QryRespVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/selectListByMap")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response=@ModelName@QryRespVo[].class)
    public HttpRspParam selectListByMap(@RequestBody Map map){
        List<@ModelName@QryRespVo> @modelName@QryRespVos = @iService@.selectListByMap(map);
        return success(@modelName@QryRespVos);
    }

    /**
    * 分页查询接口，支持可变参数，返回多个实体或者返回null
    */
    @PostMapping("/page")
    @ApiOperation(value="查询接口，分页查询", notes="分页查询接口，支持可变参数，返回实体列表",response=@ModelName@QryRespVo[].class)
    public HttpRspParam selectPage(@RequestBody Page<@ModelName@QryRespVo, @ModelName@QryReqVo> page){
        Page<@ModelName@QryRespVo, @ModelName@QryReqVo> PageResp = @iService@.selectPage(page);
        return success(PageResp);
    }

    

}