package @controllerPackage@;

import @modelPackage@.@ModelName@;
import @voPackage@.@ModelName@QryReqVo;
import @voPackage@.@ModelName@QryRespVo;
import @IServicePackage@.@IService@;
import com.wing.msa.app.common.BaseController;
import com.wing.msa.common.param.page.Page;
import com.wing.msa.common.param.http.HttpRespParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Map;
import java.util.Collection;
import java.util.List;

/**
 * @tableComment@管理
 */
@Api(value = "@serviceComment@接口",tags="@serviceComment@接口")
@RestController
@RequestMapping("/@ModelName@")
@SuppressWarnings("all")
public class @ModelName@Controller extends BaseController{

    @Autowired
    private @IService@ @iService@;


    /**
    * 增加接口
    * 
    */
    @PostMapping
    @ApiOperation(value="增加接口", notes="增加接口",response=@ModelName@.class)
    public HttpRespParam insert(@RequestBody @Validated(@ModelName@.add.class) @ModelName@ @modelName@){
        return success(@iService@.insert(@modelName@));
    }
    
    /**
     * 删除接口，通过id删除
     */
    @DeleteMapping("/{id}")
    @ApiOperation(value="删除接口，通过id删除", notes="删除接口，通过id删除",response=String.class)
    public HttpRespParam deleteById(@PathVariable("id") Long id){
        @iService@.deleteById(id);
        return success("删除成功");
    }

    /**
     * 删除接口，通过id列表方式，sql样例 in(1,2,3...)
     */
    @DeleteMapping("/ids")
    @ApiOperation(value="批量删除接口，通过id列表批量删除", notes="批量删除接口，通过id列表批量删除",response=String.class)
    public HttpRespParam deleteByIds(@RequestBody Collection<Long> ids){
        @iService@.deleteByIds(ids);
        return success("删除成功");
    }

    /**
     * 更新接口，通过id更新
     */
    @PutMapping
    @ApiOperation(value="更新接口，通过id更新", notes="更新接口，通过id更新",response=String.class)
    public HttpRespParam updateById(@RequestBody @Validated({@ModelName@.update.class}) @ModelName@ @modelName@){
        @iService@.updateById(@modelName@);
        return success("修改成功");
    }

    /**
     * 查询接口，通过id查找，只返回一个实体或者返回null
     */
    @GetMapping("/{id}")
    @ApiOperation(value="查询接口，通过id查找", notes="查询接口，通过id查找，只返回一个实体或者返回null",response= @ModelName@QryRespVo.class)
    public HttpRespParam selectById(@PathVariable("id") Long id){
        @ModelName@QryRespVo @modelName@QryRespVo = @iService@.selectById(id);
        return success(@modelName@QryRespVo);
    }

    /**
     * 查询接口，通过id数组查找
     */
    @PostMapping("/ids")
    @ApiOperation(value="查询接口，通过id数组查找", notes="查询接口，通过id数组查找，返回实体列表",response=@ModelName@QryRespVo.class)
    public HttpRespParam selectByIds(@RequestBody Collection<Long> ids){
        List<@ModelName@QryRespVo> @modelName@QryRespVos = @iService@.selectByIds(ids);
        return success(@modelName@QryRespVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     *
     */
    @PostMapping("/list")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response=@ModelName@QryRespVo[].class)
    public HttpRespParam selectList(@RequestBody @ModelName@QryReqVo @modelName@QryReqVo){
        List<@ModelName@QryRespVo> @modelName@QryRespVos = @iService@.selectList(@modelName@QryReqVo);
        return success(@modelName@QryRespVos);
    }

    /**
     * 查询接口，支持可变参数，返回实体列表
     */
    @PostMapping("/list/map")
    @ApiOperation(value="查询接口，多条件查询", notes="查询接口，多条件查询，返回实体列表",response=@ModelName@QryRespVo[].class)
    public HttpRespParam selectListByMap(@RequestBody Map map){
        List<@ModelName@QryRespVo> @modelName@QryRespVos = @iService@.selectListByMap(map);
        return success(@modelName@QryRespVos);
    }

    /**
    * 分页查询接口，支持可变参数，返回多个实体或者返回null
    */
    @PostMapping("/page")
    @ApiOperation(value="查询接口，分页查询", notes="分页查询接口，支持可变参数，返回实体列表",response=@ModelName@QryRespVo[].class)
    public HttpRespParam selectPage(@RequestBody Page<@ModelName@QryRespVo, @ModelName@QryReqVo> page){
        Page<@ModelName@QryRespVo, @ModelName@QryReqVo> PageResp = @iService@.selectPage(page);
        return success(PageResp);
    }

    

}