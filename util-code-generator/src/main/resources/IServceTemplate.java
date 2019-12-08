package @IServicePackage@;

import com.wing.msa.common.service.IBaseService;
import com.wing.msa.common.param.page.Page;
import java.io.Serializable;
import java.util.List;
import @modelPackage@.@ModelName@;
import @voPackage@.@ModelName@QryReqVo;
import @voPackage@.@ModelName@QryRespVo;

public interface I@modelName@Service extends IBaseService<@modelName@, @modelName@QryRespVo, @modelName@QryReqVo>{

}