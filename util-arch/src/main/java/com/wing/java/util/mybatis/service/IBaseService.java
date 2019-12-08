package com.wing.java.util.mybatis.service;

import com.wing.java.util.param.page.Page;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wing
 * @create 2018-08-31 17:21
 */
public interface IBaseService<E, SE, WP> {


    /**
     * <p>
     * 插入一条记录，字段不为空的保存
     * </p>
     * @param entity 实体对象
     * @return entity
     */
    E insert(E entity);

    /**
     * <p>
     * 插入一条记录，保存所有字段
     * </p>
     *
     * @param entity 实体对象
     * @return entity
     */
    E insertAllColumn(E entity);

    /**
     * <p>
     * TableId 注解存在更新记录，否插入一条记录
     * </p>
     *
     * @param entity 实体对象
     * @return boolean
     */
//    boolean insertOrUpdate(E entity);

    /**
     * 插入或修改一条记录的全部字段
     *
     * @param entity 实体对象
     * @return boolean
     */
//    boolean insertOrUpdateAllColumn(E entity);

    /**
     * <p>
     * 批量插入
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
//    boolean insertBatch(List<E> entityList);

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  插入批次数量
     * @return boolean
     */
//    boolean insertBatch(List<E> entityList, int batchSize);

    /**
     * <p>
     * 批量插入
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
//    boolean insertAllColumnBatch(List<E> entityList);

    /**
     * <p>
     * 插入（批量）
     * </p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  插入批次数量
     * @return boolean
     */
//    boolean insertAllColumnBatch(List<E> entityList, int batchSize);

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
//    boolean insertOrUpdateBatch(List<E> entityList);

    /**
     * <p>
     * 批量修改插入
     * </p>
     *
     * @param entityList 实体对象列表
     * @param batchSize
     * @return boolean
     */
//    boolean insertOrUpdateBatch(List<E> entityList, int batchSize);

    /**
     * <p>
     * 批量修改或插入全部字段
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
//    boolean insertOrUpdateAllColumnBatch(List<E> entityList);

    /**
     * 批量修改或插入全部字段
     *
     * @param entityList 实体对象列表
     * @param batchSize
     * @return boolean
     */
//    boolean insertOrUpdateAllColumnBatch(List<E> entityList, int batchSize);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return int
     */
    boolean deleteById(Serializable id);

    /**
     * <p>
     * 删除（根据ID列表 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @return int
     */
    boolean deleteByIds(Collection<? extends Serializable> idList);

    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param whereParam 实体对象封装操作类（不可以为 null）
     * @return int
     */
    boolean delete(WP whereParam);

    /**
     * <p>
     * 根据 whereMap 条件，删除记录
     * </p>
     *
     * @param whereMap 表字段 map 对象
     * @return int
     */
    boolean deleteByMap(Map<String, Object> whereMap);

    /**
     * <p>
     * 根据 ID 修改不为空的字段
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    boolean updateById(E entity);

    /**
     * <p>
     * 根据 ID 修改所有字段
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    boolean updateAllColumnById(E entity);

    /**
     * <p>
     * 根据 whereParam 条件，更新记录
     * </p>
     *
     * @param entity  实体对象(更新的数据， name='Abc')
     * @param whereParam 实体对象封装更新条件，不可以为 null
     * @return
     */
    boolean update(E entity, WP whereParam);

    /**
     * <p>
     * 根据 whereParam 条件，更新记录
     * </p>
     *
     * @param entity  实体对象(更新的数据， name='Abc')
     * @param whereMap 条件Map
     * @return
     */
    boolean updateByMap(E entity, Map<String, Object> whereMap);

    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
//    boolean updateBatchById(List<E> entityList);

    /**
     * <p>
     * 根据ID 批量更新
     * </p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  更新批次数量
     * @return boolean
     */
//    boolean updateBatchById(List<E> entityList, int batchSize);

    /**
     * <p>
     * 根据ID 批量更新全部字段
     * </p>
     *
     * @param entityList 实体对象列表
     * @return boolean
     */
//    boolean updateAllColumnBatchById(List<E> entityList);

    /**
     * <p>
     * 根据ID 批量更新全部字段
     * </p>
     *
     * @param entityList 实体对象列表
     * @param batchSize  更新批次数量
     * @return boolean
     */
//    boolean updateAllColumnBatchById(List<E> entityList, int batchSize);

    /**
     * <p>
     * 根据 ID 查询
     * </p>
     *
     * @param id 主键ID
     * @return T
     */
    SE selectById(Serializable id);

    /**
     * <p>
     * 根据 entity 条件，查询一条记录
     * </p>
     *
     * @param whereParam 实体对象
     * @return T
     */
    SE selectOne(WP whereParam);

    /**
     * <p>
     * 查询（根据ID 批量查询）
     * </p>
     *
     * @param idList 主键ID列表
     * @return List<T>
     */
    List<SE> selectByIds(Collection<? extends Serializable> idList);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录
     * </p>
     *
     * @param whereParam 实体对象封装操作类（可以为 null）
     * @return List<T>
     */
    List<SE> selectList(WP whereParam);

    /**
     * <p>
     * 查询（根据 whereMap 条件）
     * </p>
     *
     * @param whereMap 表字段 map 对象
     * @return List<T>
     */
    List<SE> selectListByMap(Map<String, Object> whereMap);

    /**
     * <p>
     * 根据 entity 条件，查询全部记录（并翻页）
     * </p>
     *
     * @param page   实体对象封装操作类
     * @return  PageResp<E>
     */
    Page<SE, WP> selectPage(Page<SE, WP> page);

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     * </p>
     * @param whereParam   实体对象封装操作类
     * @return PageResp<Map<String, Object>>
     */
//    PageResp<Map<String, Object>> selectPageMap(PageReq<WP> whereParam);

}
