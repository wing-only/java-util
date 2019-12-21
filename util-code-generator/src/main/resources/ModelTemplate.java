package @modelPackage@ ;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import com.wing.java.util.validator.group.AddGroup;
import com.wing.java.util.validator.group.UpdateGroup;
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
   private static final long serialVersionUID = 1L;

@modelContents@


}
