package @ServiceImplPackage@;

import java.io.Serializable;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wing.java.util.exception.BusinessException;
import com.wing.java.util.param.page.Page;
import com.wing.java.util.mybatis.service.impl.BaseServiceImpl;
import com.wing.java.util.CommonUtil;
import @IServicePackage@.I@ModelName@Service;
import @modelPackage@.@ModelName@;
import @daoPackage@.@ModelName@Dao;
import @voPackage@.@ModelName@QryRespVo;
import @voPackage@.@ModelName@QryReqVo;

@Service
@Transactional
@Slf4j
public class @modelName@ServiceImpl extends BaseServiceImpl<@modelName@Dao,@modelName@,@modelName@QryRespVo,@modelName@QryReqVo> implements I@modelName@Service {

      @Autowired
      @modelName@Dao @variableName@Dao;

}