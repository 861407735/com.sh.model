package com.sh.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;

import com.sh.model.model.PbmxEntity;

@Mapper
public interface PbmxResultMapper {

	/**
	 * @describe:ddl查询当前表是否存在结果表
	 * @author:john chen
	 * @date:2020年11月12日
	 * @return:String
	 */
	@Select("show tables like 'pbmx_lixian_result'")
	String getTable();
   
	//计算准确率
	@Update("update pbmx_lixian_result set zql = (SELECT\r\n"
			+ "IF (jgts IS NOT NULL, 1, 0) * 0.2 +\r\n"
			+ "IF (rczb IS NOT NULL, 1, 0) * 0.15 +\r\n"
			+ "IF (jezb IS NOT NULL, 1, 0) * 0.15 +\r\n"
			+ "IF (fyxmszb IS NOT NULL, 1, 0) * 0.05 +\r\n"
			+ "IF (scjzz IS NOT NULL, 1, 0) * 0.1 +\r\n"
			+ "IF (lkrxz IS NOT NULL, 1, 0) * 0.1 +\r\n"
			+ "IF (yhkxz IS NOT NULL, 1, 0) * 0.05 +\r\n"
			+ "IF (extrp LIKE '%可疑%', 1, 0) * 0.2 )\r\n"
			+ "WHERE\r\n"
			+ "	id = #{id}")
	void computZQL(Long id);
	
	//计算逻辑点
	@Update("update pbmx_lixian_result set ljpdd = (SELECT\r\n"
			+ "IF (jgts IS NOT NULL, 1, 0) +\r\n"
			+ "IF (rczb IS NOT NULL, 1, 0) +\r\n"
			+ "IF (jezb IS NOT NULL, 1, 0) +\r\n"
			+ "IF (fyxmszb IS NOT NULL, 1, 0) +\r\n"
			+ "IF (scjzz IS NOT NULL, 1, 0) +\r\n"
			+ "IF (lkrxz IS NOT NULL, 1, 0) +\r\n"
			+ "IF (yhkxz IS NOT NULL, 1, 0) +\r\n"
			+ "IF (extrp LIKE '%可疑%', 1, 0))\r\n"
			+ "WHERE\r\n"
			+ "	id = #{id}")
	void computLJD(Long id);
	
	/**
	 * @describe:不存在则新建结果表
	 * @author:john chen
	 * @date:2020年11月12日
	 * @return:Boolean
	 */
	@Select(" CREATE TABLE `pbmx_lixian_result`(`id` bigint(20) NOT NULL AUTO_INCREMENT,\r\n"
			+ "   `lyxt` varchar(255) DEFAULT NULL COMMENT '来源系统',\r\n"
			+ "	  `bdh` varchar(255) DEFAULT NULL COMMENT '保单号',\r\n"
			+ "	  `bah` varchar(255) DEFAULT NULL COMMENT '报案号',\r\n"
			+ "	  `lkrxm` varchar(255) DEFAULT NULL COMMENT '领款人姓名',\r\n"
			+ "	  `lkrkhh` varchar(255) DEFAULT NULL COMMENT '领款人银行开户行',\r\n"
			+ "	  `lkryhkh` varchar(255) DEFAULT NULL COMMENT '领款人银行卡号',\r\n"
			+ "	  `zhifu` varchar(255) DEFAULT NULL COMMENT '赔付方式',\r\n"
			+ "	  `gyxm` varchar(255) DEFAULT NULL COMMENT '雇员姓名',\r\n"
			+ "	  `gynl` bigint(20) DEFAULT NULL COMMENT '雇员年龄',\r\n"
			+ "	  `gysfzhm` varchar(255) DEFAULT NULL COMMENT '雇员身份证号码',\r\n"
			+ "	  `gysjh` varchar(255) DEFAULT NULL COMMENT '雇员手机号',\r\n"
			+ "	  `zffs` varchar(255) DEFAULT NULL COMMENT '支付方式',\r\n"
			+ "	  `gyyhkkhh` varchar(255) DEFAULT NULL COMMENT '雇员银行开户行',\r\n"
			+ "	  `gyyhkh` varchar(255) DEFAULT NULL COMMENT '雇员银行卡号',\r\n"
			+ "	  `tbr` varchar(255) DEFAULT NULL COMMENT '投保人',\r\n"
			+ "	  `bbxr` varchar(255) DEFAULT NULL COMMENT '被保险人',\r\n"
			+ "	  `qbsj` datetime DEFAULT NULL COMMENT '起保时间',\r\n"
			+ "	  `cxsj` datetime DEFAULT NULL COMMENT '出险时间',\r\n"
			+ "	  `cxdd` varchar(255) DEFAULT NULL COMMENT '出险地点',\r\n"
			+ "	  `hyfxfl` varchar(255) DEFAULT NULL COMMENT '行业风险分类',\r\n"
			+ "	  `zcxrc` bigint(20) DEFAULT NULL COMMENT '总出险人次',\r\n"
			+ "	  `ztbrc` bigint(20) DEFAULT NULL COMMENT '总投保人次',\r\n"
			+ "	  `zlpje` double DEFAULT NULL COMMENT '总理赔金额',\r\n" + "	  `zbf` double(11,2) DEFAULT NULL COMMENT '总保费',\r\n"
			+ "	  `scdj` varchar(255) DEFAULT NULL COMMENT '伤残等级',\r\n"
			+ "	  `scpcj` double(11,2) DEFAULT NULL COMMENT '伤残赔偿金',\r\n" + "	  `ylf` double(11,2) DEFAULT NULL COMMENT '医疗费',\r\n"
			+ "	  `qtf` double(11,2) DEFAULT NULL COMMENT '其他费',\r\n" + "	  `yyf` double(11,2) DEFAULT NULL COMMENT '营养费',\r\n"
			+ "	  `wgf` double(11,2) DEFAULT NULL COMMENT '误工费',\r\n" + "	  `zybz` double(11,2) DEFAULT NULL COMMENT '住院补助',\r\n"
			+ "	  `qtf2` double(11,2) DEFAULT NULL COMMENT '其他费2',\r\n"
			+ "	  `pfje` double(11,2) DEFAULT NULL COMMENT '赔付金额',\r\n"
			+ "	  `bz` varchar(255) DEFAULT NULL COMMENT '备注',\r\n"
			+ "	  `jgts` bigint(20) DEFAULT NULL COMMENT '起保与出险间隔天数',\r\n"
			+ "	  `rczb` double(6,2) DEFAULT NULL COMMENT '出险与投保人次占比',\r\n"
			+ "	  `jezb` double(6,2) DEFAULT NULL COMMENT '保费与理赔金额占比',\r\n"
			+ "	  `fyxmszb` double(6,2) DEFAULT NULL COMMENT '费用项目数占比',\r\n"
			+ "	  `scjzz` double(6,2) DEFAULT NULL COMMENT '伤残金占比',\r\n"
			+ "	  `lkrxz` varchar(255) DEFAULT NULL COMMENT '领款人性质',\r\n"
			+ "	  `yhkxz` bigint(20) DEFAULT NULL COMMENT '银行卡性质',\r\n"
			+ "	  `sfky_jgts` double(6,2) DEFAULT NULL COMMENT '是否可疑',\r\n" 
			+ "	  `extrp` varchar(32) DEFAULT NULL COMMENT '是否现金支付',\r\n" 
			+ "	  `sfky_rczb` double(6,2) DEFAULT NULL,\r\n"
			+ "	  `sfky_jezb` double(6,2) DEFAULT NULL,\r\n" + "	  `sfky_fyxmszb` double(11,2) DEFAULT NULL,\r\n"
			+ "	  `sfky_ylf` double(6,2) DEFAULT NULL,\r\n" + "	  `sfky_scjzz` double(11,2) DEFAULT NULL,\r\n"
			+ "	  `sfky_lkrxz` double(6,2) DEFAULT NULL,\r\n" + "	  `sfky_yhkxz` double(11,2) DEFAULT NULL,\r\n"
			+ "	  `zxfs` double(6,2) DEFAULT NULL COMMENT '置信度',\r\n" 
			+ "	  `zql` double(6,2) DEFAULT NULL COMMENT '准确率',\r\n" 
			+ "	  `ljpdd` int(2) DEFAULT NULL COMMENT '逻辑判断点',\r\n" 
			+ "	  `batch_id` int(11) DEFAULT NULL ,\r\n"
			+ "  PRIMARY KEY (`id`)\r\n" + "	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='骗保模型原始表' ")
	Boolean createTable();

	

	/**
	 * @describe:将运算结果保存到结果表
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param pbmx
	 * @return
	 */
	@Insert("insert into pbmx_lixian_result(id,lyxt,bdh,bah,lkrxm,lkrkhh,lkryhkh,zhifu,gyxm,gynl,gysfzhm,gysjh,zffs,gyyhkkhh,gyyhkh,tbr,bbxr,"
			+ "qbsj,cxsj,cxdd,hyfxfl,zcxrc,ztbrc,zlpje,zbf,scdj,scpcj,ylf,qtf,yyf,wgf,zybz,qtf2,pfje,bz,jgts,rczb,jezb,fyxmszb,scjzz,"
			+ "lkrxz,yhkxz,sfky_jgts,sfky_rczb,sfky_jezb,sfky_fyxmszb,sfky_ylf,sfky_scjzz,sfky_lkrxz,sfky_yhkxz,zxfs,extrp,batch_id)values("
			+ "#{id},#{lyxt},#{bdh},#{bah},#{lkrxm},#{lkrkhh},#{lkryhkh},#{zhifu},#{gyxm},#{gynl},#{gysfzhm},#{gysjh},#{zffs},#{gyyhkkhh}"
			+ ",#{gyyhkh},#{tbr},#{bbxr},#{qbsj},#{cxsj},#{cxdd},#{hyfxfl},#{zcxrc},#{ztbrc},#{zlpje},#{zbf},#{scdj},#{scpcj},#{ylf},"
			+ "#{qtf},#{yyf},#{wgf},#{zybz},#{qtf2},#{pfje},#{bz},#{jgts},#{rczb},#{jezb},#{fyxmszb},#{scjzz},#{lkrxz},#{yhkxz},#{sfkyJgts}"
			+ ",#{sfkyRczb},#{sfkyJezb},#{sfkyFyxmszb},#{sfkyYlf},#{sfkyScjzz},#{sfkyLkrxz},#{sfkyYhkxz},#{zxfs},#{extrp},#{batchId})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Boolean savePbmxResult(PbmxEntity pbmx);

	/**
	 * @describe:根据文件id删除运算结果
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param batchid
	 * @return
	 */
	@Delete("delete from pbmx_lixian_result where batch_id=#{batchid}")
	Boolean deletePbmxResultByBatchid(Integer batchid);


	/**
	 * @describe:根据条件筛选运算结果
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param pbmx
	 * @return
	 */
	@SelectProvider(method = "selectpbmx", type = pbmxResultProvider.class)
	List<PbmxEntity> selectPbmxResult(PbmxEntity pbmx);


	public class pbmxResultProvider {
		public String selectpbmx(PbmxEntity pbmx) {
			 StringBuilder sql = new StringBuilder();
             sql.append("select * from pbmx_lixian_result where 1=1 ");
             //置信分
             if(null != pbmx.zxfs){
                 sql.append("and zxfs >= #{zxfs} ");
             }//准确率
             if(null != pbmx.zql){
                 sql.append("and zql >= #{zql}/100 ");
             }//逻辑判断点
             if(null != pbmx.ljpdd){
                 sql.append("and ljpdd >= #{ljpdd} ");
             }
             if(null != pbmx.batchId){
                 sql.append("and batch_id=#{batchId} ");
             }
               return sql.toString();
             }
	}
}
