package @modelPackage@ ;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.math.BigDecimal;

/**
* @创建时间：@currenttime@
* @文件描述：@tableName@ @tableComment@
*/
@ApiModel(description = "@tableComment@" )
@Data
public class @entityName@ implements Serializable {


@modelContents@  


   public interface add {};
   public interface update {};


}
