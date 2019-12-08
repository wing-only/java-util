package @mapperPackage@;

import @modelPackage@.@entityName@;
import @voPackage@.@entityName@QryReqVo;
import @voPackage@.@entityName@QryRespVo;
import java.io.Serializable;
import java.util.List;

import com.wing.msa.common.mapper.BaseMapper;
import com.wing.msa.common.param.page.Page;

public interface @entityName@Mapper extends BaseMapper<@entityName@, @entityName@QryRespVo, @entityName@QryReqVo> {

}
