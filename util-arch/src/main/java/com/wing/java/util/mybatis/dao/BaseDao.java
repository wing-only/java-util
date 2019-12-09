package com.wing.java.util.mybatis.dao;

import com.wing.java.util.param.page.Page;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author wing
 * @create 2018-08-31 17:17
 */
public interface BaseDao<E, SE, WP> {

    /**
     * <p>
     * 插入一条记录，字段不为空的保存
     * </p>
     * @param entity 实体对象
     * @return entity
     */
    Integer insert(E entity);

    /**
     * <p>
     * 插入一条记录，保存所有字段
     * </p>
     *
     * @param entity 实体对象
     * @return entity
     */
    Integer insertAllColumn(E entity);

    /**
     * <p>
     * 根据 ID 删除
     * </p>
     *
     * @param id 主键ID
     * @return int
     */
    Integer deleteById(Serializable id);

    /**
     * <p>
     * 删除（根据ID列表 批量删除）
     * </p>
     *
     * @param idList 主键ID列表
     * @return int
     */
    Integer deleteByIds(Collection<? extends Serializable> idList);

    /**
     * <p>
     * 根据 entity 条件，删除记录
     * </p>
     *
     * @param whereParam 实体对象封装操作类（不可以为 null）
     * @return int
     */
    Integer delete(WP whereParam);

    /**
     * <p>
     * 根据 whereMap 条件，删除记录
     * </p>
     *
     * @param whereMap 表字段 map 对象
     * @return int
     */
    Integer deleteByMap(Map<String, Object> whereMap);

    /**
     * <p>
     * 根据 ID 修改不为空的字段
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer updateById(E entity);

    /**
     * <p>
     * 根据 ID 修改所有字段
     * </p>
     *
     * @param entity 实体对象
     * @return int
     */
    Integer updateAllColumnById(E entity);

    /**
     * <p>
     * 根据 whereParam 条件，更新记录
     * </p>
     *
     * @param entity  实体对象(更新的数据， name='Abc')
     * @param whereParam 实体对象封装更新条件，不可以为 null
     * @return
     */
    Integer update(@Param("entity") E entity, @Param("param") WP whereParam);

    /**
     * <p>
     * 根据 whereParam 条件，更新记录
     * </p>
     *
     * @param entity  实体对象(更新的数据， name='Abc')
     * @param whereParam 实体对象封装更新条件，不可以为 null
     * @return
     */
    Integer updateAllColumn(@Param("entity") E entity, @Param("param") WP whereParam);

    /**
     * <p>
     * 根据 whereParam 条件，更新记录
     * </p>
     *
     * @param entity  实体对象(更新的数据， name='Abc')
     * @param whereMap 条件Map
     * @return
     */
    Integer updateByMap(@Param("entity") E entity, @Param("param") Map<String, Object> whereMap);

    /**
     * <p>
     * 根据 whereParam 条件，更新记录
     * </p>
     *
     * @param entity  实体对象(更新的数据， name='Abc')
     * @param whereMap 条件Map
     * @return
     */
    Integer updateAllColumnByMap(@Param("entity") E entity, @Param("param") Map<String, Object> whereMap);

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
     * @return  List<E>
     */
    List<SE> selectPage(Page<SE, WP> page);

    /**
     * <p>
     * 根据 Wrapper 条件，查询全部记录（并翻页）
     * </p>
     * @param whereParam   实体对象封装操作类
     * @return List<Map<String, Object>>
     */
//    List<Map<String, Object>> selectPageMap(PageReq<WP> page);

}
