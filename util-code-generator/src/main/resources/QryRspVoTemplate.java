package @voPackage@;

import @modelPackage@.@modelName@;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "@tableComment@查询结果" )
@Data
public class @modelName@QryRespVo extends @modelName@ {

@qryRespVoContent@
}
