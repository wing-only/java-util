package @ServiceImplPackage@;

import java.io.Serializable;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wing.msa.common.exception.BusinessException;
import com.wing.msa.common.param.page.Page;
import com.wing.msa.common.service.impl.BaseServiceImpl;
import com.wing.msa.common.util.CommonUtil;
import @IServicePackage@.I@ModelName@Service;
import @modelPackage@.@ModelName@;
import @mapperPackage@.@ModelName@Mapper;
import @voPackage@.@ModelName@QryRespVo;
import @voPackage@.@ModelName@QryReqVo;

@Service
@Transactional
@Slf4j
public class @modelName@ServiceImpl extends BaseServiceImpl<@modelName@Mapper,@modelName@,@modelName@QryRespVo,@modelName@QryReqVo> implements I@modelName@Service {

      @Autowired
      @modelName@Mapper @variableName@Mapper;

}