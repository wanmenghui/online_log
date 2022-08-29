package com.meng.excel.mapper;

import com.meng.excel.dao.Log;
import com.meng.excel.dao.MessageSchedule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author menghui.wan
 */
@Mapper
public interface MessageScheduleMapper {


    Integer insert(@Param("messageSchedule") MessageSchedule messageSchedule);

    List<MessageSchedule> query();

    MessageSchedule queryById(@Param("id") Integer id);

}
