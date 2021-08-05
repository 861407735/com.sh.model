package com.sh.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import com.sh.model.model.FileEntity;

@Mapper
public interface FileMapper {

      @Select("show tables like 'pbmx_file'")
      String getTable();

      @Select("CREATE TABLE `pbmx_file` (\r\n"
      		+ "  `batch_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '批次id',\r\n"
      		+ "  `user_id` int(11) NOT NULL  COMMENT '用户id',\r\n"
      		+ "  `name` varchar(126) DEFAULT NULL COMMENT '文件名',\r\n"
      		+ "  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,\r\n"
      		+ "  PRIMARY KEY (`batch_id`)\r\n"
      		+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8")
      Boolean createTable();

      @Insert("insert into pbmx_file(user_id,name)values(#{userId},#{name})")
      @Options(useGeneratedKeys=true,keyColumn="batch_id",keyProperty="batchId")
      Boolean saveFileEntity(FileEntity fileEntity);

      @Delete("delete from pbmx_file where batch_id=#{batchId}")
      Boolean deleteFileEntity(Integer batchId);

      @UpdateProvider(method = "updatefileentity", type =fileentityProvider.class)
      Boolean updateFileEntity(FileEntity fileentity);
      //根据名字，日期查询
      @SelectProvider(method = "selectfileentity", type =fileentityProvider.class)
      List<FileEntity> selectFileEntity(String name,String time,Long userid);

      public class fileentityProvider {
            public String selectfileentity(String name,String time,Long userid) {
                   StringBuilder sql = new StringBuilder();
                   sql.append("select * from pbmx_file where 1=1 ");
                   if(null != name){
                       sql.append("and name like concat('%',#{name},'%') ");
                   }
                   if(null != time){
                       sql.append("and create_time like concat('%',#{time},'%') ");
                   }
                     return sql.append("and user_id=#{userid} order by batch_id desc").toString();
                   }

            public String updatefileentity(FileEntity fileentity) {
                   StringBuilder sql = new StringBuilder();
                   sql.append("update pbmx_file set ");
                   if(null != fileentity.batchId){
                      sql.append("batch_id=#{batchId}, ");
                   }
                   if(null != fileentity.name){
                      sql.append("name=#{name}, ");
                   }
                   if(null != fileentity.createTime){
                      sql.append("create_time=#{createTime}, ");
                   }
                   String temp = sql.substring(0, sql.length() - 2);
                   return temp += " where batch_id=#{batchId}";
            }
      }
}
