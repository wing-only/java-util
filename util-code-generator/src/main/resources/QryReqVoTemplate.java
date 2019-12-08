package @voPackage@;

import @modelPackage@.@modelName@;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

@ApiModel(description = "@tableComment@查询参数" )
@Data
public class @modelName@QryReqVo extends @modelName@ {
     
@qryReqVoContent@

	/**
	 * 排序
	 */
	@ApiModelProperty(hidden = true, name="orderBy",value="排序",required=false)
	private String orderBy = " order by id desc ";
}
