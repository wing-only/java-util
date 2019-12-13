package @daoPackage@;

import @modelPackage@.@entityName@;
import @voPackage@.@entityName@QryReqVo;
import @voPackage@.@entityName@QryRespVo;
import java.io.Serializable;
import java.util.List;

import com.wing.java.util.mybatis.dao.BaseDao;
import com.wing.java.util.param.page.Page;

public interface @entityName@Dao extends BaseDao<@entityName@, @entityName@QryRespVo, @entityName@QryReqVo> {

}
