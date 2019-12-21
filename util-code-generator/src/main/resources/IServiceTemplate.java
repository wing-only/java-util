package @IServicePackage@;

import com.wing.java.util.mybatis.service.IBaseService;
import com.wing.java.util.param.page.Page;
import java.io.Serializable;
import java.util.List;
import @modelPackage@.@ModelName@;
import @voPackage@.@ModelName@QryReqVo;
import @voPackage@.@ModelName@QryRespVo;

public interface I@modelName@Service extends IBaseService<@modelName@, @modelName@QryRespVo, @modelName@QryReqVo>{

}