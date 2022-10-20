package com.eastcom_sw.demo.mapper;

import java.util.List;

import com.eastcom_sw.demo.domain.DemoConfig;
import org.apache.ibatis.annotations.Mapper;

/**
 * 参数配置 数据层
 *
 */
@Mapper
public interface DemoConfigMapper
{
    /**
     * 查询参数配置信息
     * 
     * @param config 参数配置信息
     * @return 参数配置信息
     */
    public DemoConfig selectConfig(DemoConfig config);

    /**
     * 查询参数配置列表
     * 
     * @param config 参数配置信息
     * @return 参数配置集合
     */
    public List<DemoConfig> selectConfigList(DemoConfig config);

    /**
     * 根据键名查询参数配置信息
     * 
     * @param configKey 参数键名
     * @return 参数配置信息
     */
    public DemoConfig checkConfigKeyUnique(String configKey);

    /**
     * 新增参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int insertConfig(DemoConfig config);

    /**
     * 修改参数配置
     * 
     * @param config 参数配置信息
     * @return 结果
     */
    public int updateConfig(DemoConfig config);

    /**
     * 删除参数配置
     * 
     * @param configId 参数ID
     * @return 结果
     */
    public int deleteConfigById(String configId);

    /**
     * 批量删除参数信息
     * 
     * @param configIds 需要删除的参数ID
     * @return 结果
     */
    public int deleteConfigByIds(String[] configIds);

    /**
     * 根绝configId查询配置信息
     * @param configId
     * @return
     */
    public DemoConfig xwtSelectConfigById(String configId);

    /**
     * 根据configName 查询配置
     * @param configName
     * @return
     */
    public List<DemoConfig> xwtSelectConfigByName(String configName);

    /**
     * xwt 插入新的数据
     * @param config
     * @return
     */
    int xwtInsertConfig(DemoConfig config);

    /**
     * 分页查询
     */
    public List<DemoConfig> xwtSelectAll();
}