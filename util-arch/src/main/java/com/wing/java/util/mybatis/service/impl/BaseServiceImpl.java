package com.wing.java.util.mybatis.service.impl;

import com.wing.java.util.CommonUtil;
import com.wing.java.util.exception.BusinessException;
import com.wing.java.util.id.IdUtil;
import com.wing.java.util.mybatis.dao.BaseDao;
import com.wing.java.util.mybatis.service.IBaseService;
import com.wing.java.util.param.page.Page;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wing
 * @create 2018-08-31 17:23
 */
public class BaseServiceImpl<M extends BaseDao<E, SE, WP>, E, SE, WP> implements IBaseService<E, SE, WP> {

    @Autowired
    protected M baseDao;



    private boolean ge0(Integer result) {
        return null != result && result >= 0;
    }
    private boolean ge1(Integer result) {
        return null != result && result >= 1;
    }

    private void setId(Object entity){
        Class clazz = entity.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            String fieldName = field.getName();
            if (fieldName.equalsIgnoreCase("id")) {
                try {
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, clazz);
                    Method readMethod = propertyDescriptor.getReadMethod();
                    Method writeMethod = propertyDescriptor.getWriteMethod();

                    String value = (String) readMethod.invoke(entity);

                    if (CommonUtil.isEmpty(value)) {
                        Class<?> propertyType = propertyDescriptor.getPropertyType();
                        if(propertyType.getName().equals(Long.class.getName())){
                            writeMethod.invoke(entity, IdUtil.getId());
                        }else if(propertyType.getName().equals(String.class.getName())){
                            writeMethod.invoke(entity, IdUtil.getIdStr());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public E insert(E entity) {
        setId(entity);
        Integer insert = baseDao.insert(entity);
        if(ge1(insert)){
            return entity;
        }
        return null;
    }

    @Override
    public E insertAllColumn(E entity) {
        setId(entity);
        Integer insert = baseDao.insertAllColumn(entity);
        if(ge1(insert)){
            return entity;
        }
        return null;
    }


//    @Override
//    public boolean insertBatch(List<E> entityList) {
//        return insertBatch(entityList, 30);
//    }

//    @Override
//    public boolean insertBatch(List<E> entityList, int batchSize) {
//        if (CommonUtil.isEmpty(entityList)) {
//            throw new IllegalArgumentException("Error: entityList must not be empty");
//        }
//        return true;
//    }

//    @Override
//    public boolean insertAllColumnBatch(List<E> entityList) {
//        return false;
//    }

//    @Override
//    public boolean insertAllColumnBatch(List<E> entityList, int batchSize) {
//        return false;
//    }

    @Override
    public boolean deleteById(Serializable id) {
        if(CommonUtil.isEmpty(id)){
            throw new BusinessException("id不能为空");
        }
        return ge1(baseDao.deleteById(id));
    }

    @Override
    public boolean deleteByIds(Collection<? extends Serializable> ids) {
        if(CommonUtil.isEmpty(ids)){
            throw new BusinessException("ids不能为空");
        }
        return ge1(baseDao.deleteByIds(ids));
    }

    @Override
    public boolean delete(WP whereParam) {
        return ge0(baseDao.delete(whereParam));
    }

    @Override
    public boolean deleteByMap(Map<String, Object> whereMap) {
        if(CommonUtil.isEmpty(whereMap)){
            throw new BusinessException("查询条件不能为空");
        }
        return ge0(baseDao.deleteByMap(whereMap));
    }

    @Override
    public boolean updateById(E entity) {
        return ge1(baseDao.updateById(entity));
    }

    @Override
    public boolean updateAllColumnById(E entity) {
        return ge1(baseDao.updateAllColumnById(entity));
    }

    @Override
    public boolean update(E entity, WP whereParam) {
        return ge0(baseDao.update(entity, whereParam));
    }

    @Override
    public boolean updateByMap(E entity, Map<String, Object> whereMap) {
        if(CommonUtil.isEmpty(whereMap)){
            throw new BusinessException("查询条件不能为空");
        }
        return ge0(baseDao.updateByMap(entity, whereMap));
    }

//    @Override
//    public boolean updateBatchById(List<E> entityList) {
//        return false;
//    }

//    @Override
//    public boolean updateBatchById(List<E> entityList, int batchSize) {
//        return false;
//    }

//    @Override
//    public boolean updateAllColumnBatchById(List<E> entityList) {
//        return false;
//    }

//    @Override
//    public boolean updateAllColumnBatchById(List<E> entityList, int batchSize) {
//        return false;
//    }

    @Override
    public SE selectById(Serializable id) {
        if(CommonUtil.isEmpty(id)){
            throw new BusinessException("ID不能为空");
        }
        return baseDao.selectById(id);
    }

    @Override
    public SE selectOne(WP whereParam) {
        return baseDao.selectOne(whereParam);
    }

    @Override
    public List<SE> selectByIds(Collection<? extends Serializable> ids) {
        if(CommonUtil.isEmpty(ids)){
            throw new BusinessException("ids不能为空");
        }
        return baseDao.selectByIds(ids);
    }

    @Override
    public List<SE> selectList(WP whereParam) {
        return baseDao.selectList(whereParam);
    }

    @Override
    public List<SE> selectListByMap(Map<String, Object> whereMap) {
        return baseDao.selectListByMap(whereMap);
    }

    @Override
    public Page<SE, WP> selectPage(Page<SE, WP> page) {
        List<SE> list = baseDao.selectPage(page);
        page.setDataList(list);
        return page;
    }

//    @Override
//    public PageResp<Map<String, Object>> selectPageMap(PageReq<WP> whereParam) {
//        return baseDao.selectPageMap(whereParam);
//    }
}
