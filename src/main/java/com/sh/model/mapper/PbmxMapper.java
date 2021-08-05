package com.sh.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import com.sh.model.model.PbmxEntity;
import com.sh.model.model.StatisData;

@Mapper
public interface PbmxMapper {

	/**
	 * @describe:ddl查询当前表是否存在
	 * @author:john chen
	 * @date:2020年11月12日
	 * @return
	 * @return:String
	 */
	@Select("show tables like 'pbmx_lixian'")
	String getTable();

	/**
	 * @describe:不存在则新建
	 * @author:john chen
	 * @date:2020年11月12日
	 * @return
	 * @return:Boolean
	 */
	@Select(" CREATE TABLE `pbmx_lixian`(`id` bigint(20) NOT NULL AUTO_INCREMENT,\r\n"
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
			+ "	  `zlpje` double DEFAULT NULL COMMENT '总理赔金额',\r\n" + "	  `zbf` double DEFAULT NULL COMMENT '总保费',\r\n"
			+ "	  `scdj` varchar(255) DEFAULT NULL COMMENT '伤残等级',\r\n"
			+ "	  `scpcj` double DEFAULT NULL COMMENT '伤残赔偿金',\r\n" + "	  `ylf` double DEFAULT NULL COMMENT '医疗费',\r\n"
			+ "	  `qtf` double DEFAULT NULL COMMENT '其他费',\r\n" + "	  `yyf` double DEFAULT NULL COMMENT '营养费',\r\n"
			+ "	  `wgf` double DEFAULT NULL COMMENT '误工费',\r\n" + "	  `zybz` double DEFAULT NULL COMMENT '住院补助',\r\n"
			+ "	  `qtf2` double DEFAULT NULL COMMENT '其他费2',\r\n"
			+ "	  `pfje` double DEFAULT NULL COMMENT '赔付金额',\r\n"
			+ "	  `bz` varchar(255) DEFAULT NULL COMMENT '备注',\r\n"
			+ "	  `jgts` bigint(20) DEFAULT NULL COMMENT '起保与出险间隔天数',\r\n"
			+ "	  `rczb` double DEFAULT NULL COMMENT '出险与投保人次占比',\r\n"
			+ "	  `jezb` double DEFAULT NULL COMMENT '保费与理赔金额占比',\r\n"
			+ "	  `fyxmszb` double DEFAULT NULL COMMENT '费用项目数占比',\r\n"
			+ "	  `scjzz` double DEFAULT NULL COMMENT '伤残金占比',\r\n"
			+ "	  `lkrxz` varchar(255) DEFAULT NULL COMMENT '领款人性质',\r\n"
			+ "	  `yhkxz` bigint(20) DEFAULT NULL COMMENT '银行卡性质',\r\n"
			+ "	  `sfky_jgts` double DEFAULT NULL COMMENT '是否可疑',\r\n" 
			+ "	  `sfky_rczb` double DEFAULT NULL,\r\n"
			+ "	  `sfky_jezb` double DEFAULT NULL,\r\n" + "	  `sfky_fyxmszb` double DEFAULT NULL,\r\n"
			+ "	  `sfky_ylf` double DEFAULT NULL,\r\n" + "	  `sfky_scjzz` double DEFAULT NULL,\r\n"
			+ "	  `sfky_lkrxz` double DEFAULT NULL,\r\n" + "	  `sfky_yhkxz` double DEFAULT NULL,\r\n"
			+ "	  `zxfs` double DEFAULT NULL COMMENT '置信度',\r\n" 
			+ "	  `zql` double DEFAULT NULL COMMENT '准确率',\r\n" 
			+ "	  `ljpdd` int(2) DEFAULT NULL COMMENT '逻辑判断点',\r\n" 
			+ "	  `batch_id` int(11) DEFAULT NULL ,\r\n"
			+ "  PRIMARY KEY (`id`)\r\n" + "	) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='骗保模型原始表' ")
	Boolean createTable();

	/**
	 * @describe:统计导出
	 * @author:john chen
	 * @date:2020年11月12日
	 * @return
	 * @return:List<LinkedList<PbmxEntity>>
	 */
	@Select("   select\r\n"
			+ "       DISTINCT \r\n"
			+ "  		t3.lyxt\r\n"
			+ "		, t3.bdh\r\n"
			+ "		, t3.bah\r\n"
			+ "		, t3.lkrxm\r\n"
			+ "		, t3.lkrkhh\r\n"
			+ "		, t3.lkryhkh\r\n"
			+ "		, t3.fklx\r\n"
			+ "		, t3.gyxm\r\n"
			+ "		, t3.gynl\r\n"
			+ "		, t3.gysfzhm\r\n"
			+ "		, t3.gysjh\r\n"
			+ "		, t3.zffs\r\n"
			+ "		, t3.gyyhkkhh\r\n"
			+ "		, t3.gyyhkh\r\n"
			+ "		, t3.tbr\r\n"
			+ "		, t3.bbxr\r\n"
			+ "		, t3.qbsj\r\n"
			+ "		, t3.cxsj\r\n"
			+ "		, t3.cxdd\r\n"
			+ "		, t3.hyfxfl\r\n"
			+ "		, t3.scdj\r\n"
			+ "		, t3.scpcj\r\n"
			+ "		, t3.ylf\r\n"
			+ "		, t3.qtf\r\n"
			+ "		, t3.yyf\r\n"
			+ "		, t3.wgf\r\n"
			+ "		, t3.zybz\r\n"
			+ "		, t3.qtf2\r\n"
			+ "		, t3.pfje\r\n"
			+ "		, t3.bz\r\n"
			+ "		, t3.jgts\r\n"
			+ "		, t3.rczb\r\n"
			+ "		, t3.jezb\r\n"
			+ "		, t3.fyxmszb\r\n"
			+ "		, t3.scjzz\r\n"
			+ "		, t3.lkrxz\r\n"
			+ "		, t3.yhkxz\r\n"
			+ "		, t3.extrp\r\n"
			+ "		, case when t3.lkryhkh in (select leo.* from (select DISTINCT t.lkryhkh from\r\n"  //lkryhkh领款人银行卡号
			+ "  (SELECT  t.lkryhkh FROM \r\n"
			+ "	(SELECT a.lkryhkh FROM (SELECT t2.lyxt\r\n"   //来源系统
			+ "	, t2.bdh\r\n"
			+ "	, t2.bah\r\n"
			+ "	, t2.lkrxm\r\n"
			+ "	, t2.lkrkhh\r\n"
			+ "	, t2.lkryhkh\r\n"
			+ "	, t2.fklx\r\n"
			+ "	, t2.gyxm\r\n"
			+ "	, t2.gynl\r\n"
			+ "	, t2.gysfzhm\r\n"
			+ "	, t2.gysjh\r\n"
			+ "	, t2.zffs\r\n"
			+ "	, t2.gyyhkkhh\r\n"
			+ "	, t2.gyyhkh\r\n"
			+ "	, t2.tbr\r\n"
			+ "	, t2.bbxr\r\n"
			+ "	, t2.qbsj\r\n"
			+ "	, t2.cxsj\r\n"
			+ "	, t2.cxdd\r\n"
			+ "	, t2.hyfxfl\r\n"
			+ "	, t2.scdj\r\n"
			+ "	, t2.scpcj\r\n"
			+ "	, t2.ylf\r\n"
			+ "	, t2.qtf\r\n"
			+ "	, t2.yyf\r\n"
			+ "	, t2.wgf\r\n"
			+ "	, t2.zybz\r\n"
			+ "	, t2.qtf2\r\n"
			+ "	, t2.pfje\r\n"
			+ "	, t2.bz\r\n"
			+ "	, t2.jgts\r\n"
			+ "	, t2.rczb\r\n"
			+ "	, t2.jezb\r\n"
			+ "	, t2.fyxmszb\r\n"
			+ "	, round(t2.scjzz,2) as scjzz\r\n"
			+ "	, t2.lkrxz\r\n"
			+ "	, t2.yhkxz\r\n"
			+ "	, t2.fklxfz\r\n"
			+ "	, case when t2.fklxfz=1 then '公司赔付给个人的方式为现金，高度可疑'\r\n"
			+ "	  else '公司赔付给个人的方式为银行转账，正常' end as extrp\r\n"
			+ "	, case when t2.fklxfz=1 then  \r\n"
			+ "	100*ROUND(((t2.fklxfz * 0.2 + 0.2 * t2.sfky_jgts + 0.1 * t2.sfky_rczb + 0.2 * t2.sfky_jezb + 0.1 * t2.sfky_ylf + 0.2 * t2.sfky_scjzz + 0.1 * t2.sfky_lkrxz + 0.1 * t2.sfky_yhkxz)/1.2),2) \r\n"
			+ "	else 100*ROUND((0.2 * t2.sfky_jgts + 0.1 * t2.sfky_rczb + 0.2 * t2.sfky_jezb + 0.1 * t2.sfky_ylf + 0.2 * t2.sfky_scjzz + 0.1 * t2.sfky_lkrxz + 0.1 * t2.sfky_yhkxz),2)\r\n"
			+ "	END AS zxfs\r\n"
			+ "from(select \r\n"
			+ "          t1.lyxt\r\n"
			+ "		, t1.bdh\r\n"
			+ "		, t1.bah\r\n"
			+ "		, t1.lkrxm\r\n"
			+ "		, t1.lkrkhh\r\n"
			+ "		, t1.lkryhkh\r\n"
			+ "		, t1.fklx\r\n"
			+ "		, t1.gyxm\r\n"
			+ "		, t1.gynl\r\n"
			+ "		, t1.gysfzhm as gysfzhm\r\n"
			+ "		, t1.gysjh\r\n"
			+ "		, t1.zffs\r\n"
			+ "		, t1.gyyhkkhh\r\n"
			+ "		, t1.gyyhkh\r\n"
			+ "		, t1.tbr\r\n"
			+ "		, t1.bbxr\r\n"
			+ "		, t1.qbsj\r\n"
			+ "		, t1.cxsj\r\n"
			+ "		, t1.cxdd\r\n"
			+ "		, t1.hyfxfl\r\n"
			+ "		, t1.zcxrc\r\n"
			+ "		, t1.ztbrc\r\n"
			+ "		, t1.zlpje\r\n"
			+ "		, t1.zbf\r\n"
			+ "		, t1.scdj\r\n"
			+ "		, t1.scpcj\r\n"
			+ "		, t1.ylf\r\n"
			+ "		, t1.qtf\r\n"
			+ "		, t1.yyf--\r\n"
			+ "		, t1.wgf--\r\n"
			+ "		, t1.zybz--\r\n"
			+ "		, t1.qtf2--\r\n"
			+ "		, t1.pfje\r\n"
			+ "		, t1.bz\r\n"
			+ "		, t1.jgts\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.hyfxfl = '普通行业' THEN t1.rczb * 1.2\r\n"
			+ "			ELSE t1.rczb * 1\r\n"
			+ "		END AS rczb\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.hyfxfl = '普通行业' THEN t1.jezb * 1.2\r\n"
			+ "			ELSE t1.jezb * 1\r\n"
			+ "		END AS jezb\r\n"
			+ "		, t1.fyxmszb\r\n"
			+ "		, t1.scjzz\r\n"
			+ "		, t1.lkrxz\r\n"
			+ "		, t1.yhkxz\r\n"
			+ "		, t1.fklxfz\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.jgts <= 31  THEN 1\r\n"
			+ "			WHEN t1.jgts > 32  and t1.jgts <=186 THEN 0.6\r\n"
			+ "			ELSE 0.3\r\n"
			+ "		END AS sfky_jgts\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.rczb > 0.3 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_rczb\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.jezb >= 1 and t1.jezb <1.5 THEN 0.3\r\n"
			+ "            WHEN t1.jezb >=1.5 and t1.jezb <3 THEN 0.6\r\n"
			+ "			WHEN t1.jezb >= 3 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_jezb\r\n"
			+ "		, CASE WHEN t1.pfje > 10000 and t1.fyxmszb < 0.3 THEN 1\r\n"
			+ "            ELSE 0 \r\n"
			+ "        END AS sfky_fyxmszb\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.pfje > 10000\r\n"
			+ "			AND t1.ylf < 1000 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_ylf\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.pfje > 10000 and t1.scdj!='死亡'\r\n"
			+ "			AND t1.scjzz > 0.9 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_scjzz\r\n"
			+ "		, t1.lkrxz AS sfky_lkrxz\r\n"
			+ "		, t1.yhkxz AS sfky_yhkxz\r\n"
			+ "		, t1.zxfs\r\n"
			+ "from\r\n"
			+ " (select \r\n"
			+ "  t.lyxt\r\n"
			+ "	, t.bdh\r\n"
			+ "	, t.bah\r\n"
			+ "	, t.lkrxm\r\n"
			+ "	, t.lkrkhh\r\n"
			+ "	, t.lkryhkh\r\n"
			+ "	, t.fklx     \r\n"
			+ "	, t.gyxm\r\n"
			+ "	, t.gynl\r\n"
			+ "	, t.gysfzhm\r\n"
			+ "	, t.gysjh\r\n"
			+ "	, t.zffs\r\n"
			+ "	, t.gyyhkkhh\r\n"
			+ "	, t.gyyhkh\r\n"
			+ "	, t.tbr\r\n"
			+ "	, t.bbxr\r\n"
			+ "	, t.qbsj\r\n"
			+ "	, t.cxsj\r\n"
			+ "	, t.cxdd\r\n"
			+ "	, t.hyfxfl\r\n"
			+ "	, t.zcxrc\r\n"
			+ "	, t.ztbrc\r\n"
			+ "	, t.zlpje\r\n"
			+ "	, t.zbf\r\n"
			+ "	, t.scdj\r\n"
			+ "	, t.scpcj\r\n"
			+ "	, t.ylf\r\n"
			+ "	, case WHEN t.scpcj = 0\r\n"
			+ "		  AND t.ylf = 0\r\n"
			+ "		  AND t.qtf = 0 THEN -1\r\n"
			+ "		  ELSE t.qtf\r\n"
			+ "		 END AS qtf\r\n"               //其他费
			+ "	, t.yyf\r\n"
			+ "	, t.wgf\r\n"
			+ "	, t.zybz\r\n"
			+ "	, case when t.yyf =0 \r\n"
			+ "			and t.wgf =0\r\n"
			+ "			and t.zybz=0 then -1\r\n"
			+ "			else t.qtf2 end as qtf2\r\n"          //其他费用2
			+ "	, t.pfje\r\n"
			+ "	, t.bz\r\n"
			+ "	, case when t.qbsj is not null and t.cxsj is not null then datediff(t.cxsj,t.qbsj)\r\n"   //datediff 返回起保时间出险时间日期之间的天数
			+ "	  		else null end AS jgts\r\n"
			+ "	, case when t.ztbrc != 0.0 then abs(t.zcxrc / t.ztbrc) \r\n"  //总出险人次/总投保人次
			+ "		 	else null end AS rczb \r\n"
			+ "	, case when t.zbf !=0.0 then abs(t.pfje / t.zbf)  \r\n"
			+ "			else null end AS jezb\r\n"
			+ "	, t.fyxmszb\r\n"
			+ "	, CASE \r\n"
			+ "			WHEN t.scpcj <> 0.0 and t.pfje <> 0.0 THEN t.scpcj / t.pfje\r\n"
			+ "			ELSE 1 - (t.ylf + t.yyf + t.wgf + t.zybz + t.qtf2) / (t.scpcj + t.ylf + t.yyf + t.wgf +t.zybz + t.qtf2) \r\n"
			+ "		END AS scjzz\r\n"
			+ "	, t.lkrxz\r\n"
			+ "	, t.yhkxz\r\n"
			+ "    , t.fklxfz\r\n"
			+ "	, t.sfky_jgts\r\n"
			+ "	, t.sfky_rczb\r\n"
			+ "	, t.sfky_jezb\r\n"
			+ "	, t.sfky_fyxmszb\r\n"
			+ "	, t.sfky_ylf\r\n"
			+ "	, t.sfky_scjzz\r\n"
			+ "	, t.sfky_lkrxz\r\n"
			+ "	, t.sfky_yhkxz\r\n"
			+ "	, t.zxfs\r\n"
			+ " FROM (\r\n"
			+ "	SELECT d.lyxt\r\n"
			+ "		, d.bdh\r\n"
			+ "		, d.bah\r\n"
			+ "		, d.lkrxm\r\n"
			+ "		, d.lkrkhh\r\n"
			+ "		, d.lkryhkh\r\n"
			+ "		, d.zhifu as fklx\r\n"
			+ "		, d.gyxm\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN d.gynl = 0 THEN NULL\r\n"
			+ "			ELSE d.gynl\r\n"
			+ "		END AS gynl\r\n"
			+ "		, d.gysfzhm\r\n"
			+ "		, d.gysjh\r\n"
			+ "		, d.zffs\r\n"
			+ "		, d.gyyhkkhh\r\n"
			+ "		, d.gyyhkh\r\n"
			+ "		, d.tbr\r\n"
			+ "		, d.bbxr\r\n"
			+ "		, d.qbsj\r\n"
			+ "		, d.cxsj\r\n"
								//出险地点
			+ "		, case when d.cxdd like '上海市%' and length(d.cxdd) > 3 then substr(d.cxdd,1,7) \r\n"
			+ "			  when d.cxdd not like '上海市%' and cxdd like '%省%' then substr(d.cxdd,1,3)\r\n"
			+ "			  else d.cxdd end as cxdd \r\n"
						//行业风险分类
			+ "		,case when substr(d.hyfxfl,1,2) in ('52','57','60','51','83','26','48','49','47','28','39','31') \r\n"
			+ "	or d.hyfxfl like '%高危%'\r\n"
			+ "	 then '高危行业'\r\n"
			+ "	else '普通行业'\r\n"
			+ "	end as hyfxfl\r\n"
			+ "		, case when d.zcxrc is not null then d.zcxrc\r\n"
			+ "			else 0.0 end as zcxrc\r\n"
			+ "		,case when d.ztbrc is not null then d.ztbrc\r\n"
			+ "			else 0.0 end as ztbrc\r\n"
			+ "		, case when d.zlpje is not null then d.zlpje\r\n"
			+ "			else 0.0 end as zlpje\r\n"
			+ "		, case when d.zbf is not null then d.zbf\r\n"
			+ "			else 0.0 end as zbf\r\n"
			+ "		, case when d.scdj is not null then d.scdj\r\n"
			+ "			else 0.0 end as scdj\r\n"
			+ "		, case when d.scpcj is not null then d.scpcj\r\n"
			+ "			else 0.0 end as scpcj\r\n"
			+ "		, case when d.ylf is not null then d.ylf\r\n"
			+ "			else 0.0 end as ylf\r\n"
			+ "		, case\r\n"
			+ "			when d.qtf is null then 0.0 else d.qtf end as qtf\r\n"
			+ "		, case when d.yyf is not null then d.yyf \r\n"
			+ "			else 0.0 end as yyf\r\n"
			+ "		, case when d.wgf is not null then d.wgf\r\n"
			+ "			else 0.0 end as wgf\r\n"
			+ "		, case when d.zybz is not null then d.zybz\r\n"
			+ "			else 0.0 end as zybz\r\n"
			+ "		, case \r\n"
			+ "			when d.qtf2 is null then 0.0 else d.qtf2 end as qtf2\r\n"
			+ "		, case when d.pfje is not null then d.pfje\r\n"
			+ "			else 0.0 end as pfje\r\n"
			+ "		, d.bz\r\n"
			+ "		, d.jgts\r\n"
			+ "		, d.rczb\r\n"
			+ "		, d.jezb\r\n"
			+ "		, (if(d.scpcj<>0,1,0)+if(d.ylf<>0,1,0)+if(d.yyf<>0,1,0)+if(d.wgf<>0,1,0)+if(d.zybz<>0,1,0))/5 as fyxmszb\r\n"
			+ "		, d.scjzz\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN d.lkrxm = d.gyxm THEN 1 \r\n"
			+ "			ELSE CASE \r\n"
			+ "				WHEN d.lkrxm LIKE '%公司' THEN 0\r\n"
			+ "				ELSE 0.5 END\r\n"
			+ "		END AS lkrxz\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN tt.cn > 1 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS yhkxz \r\n"
			+ "        , IF(d.zhifu='现金',1,0) AS fklxfz \r\n"
			+ "		, d.sfky_jgts\r\n"
			+ "		, d.sfky_rczb\r\n"
			+ "		, d.sfky_jezb\r\n"
			+ "		, d.sfky_fyxmszb\r\n"
			+ "		, d.sfky_ylf\r\n"
			+ "		, d.sfky_scjzz\r\n"
			+ "		, d.sfky_lkrxz\r\n"
			+ "		, d.sfky_yhkxz\r\n"
			+ "		, d.zxfs\r\n"
			+ "		, d.batch_id\r\n"
			+ "	FROM (select * from pbmx_lixian where batch_id = #{batchid}) d\r\n"
			+ "	LEFT OUTER JOIN (SELECT COUNT(*) AS cn, lkryhkh AS 领款人银行卡号 FROM pbmx_lixian   \r\n"
			+ " GROUP BY lkrxm, lkryhkh) tt\r\n"
			+ "	ON d.lkryhkh = tt.领款人银行卡号 )t) t1)t2) a where a.zxfs>=65)t \r\n"
			+ "	 UNION\r\n"
			+ "     SELECT c.lkryhkh from\r\n"
			+ "	  (select t.*,t2.qtgsgm from pbmx_lixian t left join (\r\n"
			+ "select a.gysfzhm,group_concat(',',b.lyxt) as qtgsgm \r\n"
			+ "from \r\n"
			+ "(select distinct t1.gysfzhm from \r\n"
			+ "( select DISTINCT  a.gysfzhm ,a.lyxt ,a.cxsj from (select gysfzhm ,lyxt ,cxsj from pbmx_lixian where gysfzhm!='') a  INNER JOIN (select * from pbmx_lixian where gysfzhm !='') b on a.gysfzhm = b.gysfzhm and a.lyxt != b.lyxt \r\n"
			+ "and abs(datediff(a.cxsj,b.cxsj))<30 ) t1 ) a\r\n"
			+ "join ( select gysfzhm,lyxt from pbmx_lixian ) b on a.gysfzhm = b.gysfzhm  group by a.gysfzhm) t2\r\n"
			+ "on t.gysfzhm = t2.gysfzhm) c where c.qtgsgm != '') t) leo) and t3.zxfs <=65\r\n"
			+ "			THEN (t3.zxfs+10) else t3.zxfs end as zxfs\r\n"
			+ "		FROM (SELECT t2.lyxt\r\n"
			+ "	, t2.bdh\r\n"
			+ "	, t2.bah\r\n"
			+ "	, t2.lkrxm\r\n"
			+ "	, t2.lkrkhh\r\n"
			+ "	, t2.lkryhkh\r\n"
			+ "	, t2.fklx\r\n"
			+ "	, t2.gyxm\r\n"
			+ "	, t2.gynl\r\n"
			+ "	, t2.gysfzhm\r\n"
			+ "	, t2.gysjh\r\n"
			+ "	, t2.zffs\r\n"
			+ "	, t2.gyyhkkhh\r\n"
			+ "	, t2.gyyhkh\r\n"
			+ "	, t2.tbr\r\n"
			+ "	, t2.bbxr\r\n"
			+ "	, t2.qbsj\r\n"
			+ "	, t2.cxsj\r\n"
			+ "	, t2.cxdd\r\n"
			+ "	, t2.hyfxfl\r\n"
			+ "	, t2.scdj\r\n"
			+ "	, t2.scpcj\r\n"
			+ "	, t2.ylf\r\n"
			+ "	, t2.qtf\r\n"
			+ "	, t2.yyf\r\n"
			+ "	, t2.wgf\r\n"
			+ "	, t2.zybz\r\n"
			+ "	, t2.qtf2\r\n"
			+ "	, t2.pfje\r\n"
			+ "	, t2.bz\r\n"
			+ "	, t2.jgts\r\n"
			+ "	, t2.rczb\r\n"
			+ "	, t2.jezb\r\n"
			+ "	, t2.fyxmszb\r\n"
			+ "	, round(t2.scjzz,2) as scjzz\r\n"
			+ "	, t2.lkrxz\r\n"
			+ "	, t2.yhkxz\r\n"
			+ "	, t2.fklxfz\r\n"
			+ "	, case when t2.fklxfz=1 then '公司赔付给个人的方式为现金，高度可疑'\r\n"
			+ "	  else '公司赔付给个人的方式为银行转账，正常' end as extrp\r\n"
			+ "	, case when t2.fklxfz=1 then  \r\n"
			+ "	100*ROUND(((t2.fklxfz * 0.2 + 0.2 * t2.sfky_jgts + 0.1 * t2.sfky_rczb + 0.2 * t2.sfky_jezb + 0.1 * t2.sfky_ylf + 0.2 * t2.sfky_scjzz + 0.1 * t2.sfky_lkrxz + 0.1 * t2.sfky_yhkxz)/1.2),2) \r\n"
			+ "	else 100*ROUND((0.2 * t2.sfky_jgts + 0.1 * t2.sfky_rczb + 0.2 * t2.sfky_jezb + 0.1 * t2.sfky_ylf + 0.2 * t2.sfky_scjzz + 0.1 * t2.sfky_lkrxz + 0.1 * t2.sfky_yhkxz),2)\r\n"
			+ "	END AS zxfs\r\n"
			+ "from(select \r\n"
			+ "          t1.lyxt\r\n"
			+ "		, t1.bdh\r\n"
			+ "		, t1.bah\r\n"
			+ "		, t1.lkrxm\r\n"
			+ "		, t1.lkrkhh\r\n"
			+ "		, t1.lkryhkh\r\n"
			+ "		, t1.fklx\r\n"
			+ "		, t1.gyxm\r\n"
			+ "		, t1.gynl\r\n"
			+ "		, t1.gysfzhm as gysfzhm\r\n"
			+ "		, t1.gysjh\r\n"
			+ "		, t1.zffs\r\n"
			+ "		, t1.gyyhkkhh\r\n"
			+ "		, t1.gyyhkh\r\n"
			+ "		, t1.tbr\r\n"
			+ "		, t1.bbxr\r\n"
			+ "		, t1.qbsj\r\n"
			+ "		, t1.cxsj\r\n"
			+ "		, t1.cxdd\r\n"
			+ "		, t1.hyfxfl\r\n"
			+ "		, t1.zcxrc\r\n"
			+ "		, t1.ztbrc\r\n"
			+ "		, t1.zlpje\r\n"
			+ "		, t1.zbf\r\n"
			+ "		, t1.scdj\r\n"
			+ "		, t1.scpcj\r\n"
			+ "		, t1.ylf\r\n"
			+ "		, t1.qtf\r\n"
			+ "		, t1.yyf\r\n"
			+ "		, t1.wgf\r\n"
			+ "		, t1.zybz\r\n"
			+ "		, t1.qtf2\r\n"
			+ "		, t1.pfje\r\n"
			+ "		, t1.bz\r\n"
			+ "		, t1.jgts\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.hyfxfl = '普通行业' THEN t1.rczb * 1.2\r\n"
			+ "			ELSE t1.rczb * 1\r\n"
			+ "		END AS rczb\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.hyfxfl = '普通行业' THEN t1.jezb * 1.2\r\n"
			+ "			ELSE t1.jezb * 1\r\n"
			+ "		END AS jezb\r\n"
			+ "		, t1.fyxmszb\r\n"
			+ "		, t1.scjzz\r\n"
			+ "		, t1.lkrxz\r\n"
			+ "		, t1.yhkxz\r\n"
			+ "		, t1.fklxfz\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.jgts <= 31  THEN 1\r\n"
			+ "			WHEN t1.jgts > 32  and t1.jgts <=186 THEN 0.6\r\n"
			+ "			ELSE 0.3\r\n"
			+ "		END AS sfky_jgts\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.rczb > 0.3 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_rczb\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.jezb >= 1 and t1.jezb <1.5 THEN 0.3\r\n"
			+ "            WHEN t1.jezb >=1.5 and t1.jezb <3 THEN 0.6\r\n"
			+ "			WHEN t1.jezb >= 3 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_jezb\r\n"
			+ "		, CASE WHEN t1.pfje > 10000 and t1.fyxmszb < 0.3 THEN 1\r\n"
			+ "            ELSE 0 \r\n"
			+ "        END AS sfky_fyxmszb\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.pfje > 10000\r\n"
			+ "			AND t1.ylf < 1000 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_ylf\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN t1.pfje > 10000 and t1.scdj!='死亡'\r\n"
			+ "			AND t1.scjzz > 0.9 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS sfky_scjzz\r\n"
			+ "		, t1.lkrxz AS sfky_lkrxz\r\n"
			+ "		, t1.yhkxz AS sfky_yhkxz\r\n"
			+ "		, t1.zxfs\r\n"
			+ "from\r\n"
			+ " (select \r\n"
			+ "  t.lyxt\r\n"
			+ "	, t.bdh\r\n"
			+ "	, t.bah\r\n"
			+ "	, t.lkrxm\r\n"
			+ "	, t.lkrkhh\r\n"
			+ "	, t.lkryhkh\r\n"
			+ "	, t.fklx     \r\n"
			+ "	, t.gyxm\r\n"
			+ "	, t.gynl\r\n"
			+ "	, t.gysfzhm\r\n"
			+ "	, t.gysjh\r\n"
			+ "	, t.zffs\r\n"
			+ "	, t.gyyhkkhh\r\n"
			+ "	, t.gyyhkh\r\n"
			+ "	, t.tbr\r\n"
			+ "	, t.bbxr\r\n"
			+ "	, t.qbsj\r\n"
			+ "	, t.cxsj\r\n"
			+ "	, t.cxdd\r\n"
			+ "	, t.hyfxfl\r\n"
			+ "	, t.zcxrc\r\n"
			+ "	, t.ztbrc\r\n"
			+ "	, t.zlpje\r\n"
			+ "	, t.zbf\r\n"
			+ "	, t.scdj\r\n"
			+ "	, t.scpcj\r\n"
			+ "	, t.ylf\r\n"
			+ "	, case WHEN t.scpcj = 0\r\n"
			+ "		  AND t.ylf = 0\r\n"
			+ "		  AND t.qtf = 0 THEN -1\r\n"
			+ "		  ELSE t.qtf\r\n"
			+ "		 END AS qtf\r\n"
			+ "	, t.yyf\r\n"
			+ "	, t.wgf\r\n"
			+ "	, t.zybz\r\n"
			+ "	, case when t.yyf =0 \r\n"
			+ "			and t.wgf =0\r\n"
			+ "			and t.zybz=0 then -1\r\n"
			+ "			else t.qtf2 end as qtf2\r\n"
			+ "	, t.pfje\r\n"
			+ "	, t.bz\r\n"
			+ "	, case when t.qbsj is not null and t.cxsj is not null then datediff(t.cxsj,t.qbsj)\r\n"
			+ "	  		else null end AS jgts\r\n"
			+ "	, case when t.ztbrc != 0.0 then abs(t.zcxrc / t.ztbrc) \r\n"
			+ "		 	else null end AS rczb \r\n"
			+ "	, case when t.zbf !=0.0 then abs(t.pfje / t.zbf) \r\n"
			+ "			else null end AS jezb\r\n"
			+ "	, t.fyxmszb\r\n"
			+ "	, CASE \r\n"
			+ "			WHEN t.scpcj <> 0.0 and t.pfje <> 0.0 THEN t.scpcj / t.pfje \r\n"
			+ "			ELSE 1 - (t.ylf + t.yyf + t.wgf + t.zybz + t.qtf2) / (t.scpcj + t.ylf + t.yyf + t.wgf +t.zybz + t.qtf2) \r\n"
			+ "		END AS scjzz\r\n"
			+ "	, t.lkrxz\r\n"
			+ "	, t.yhkxz\r\n"
			+ "    , t.fklxfz\r\n"
			+ "	, t.sfky_jgts\r\n"
			+ "	, t.sfky_rczb\r\n"
			+ "	, t.sfky_jezb\r\n"
			+ "	, t.sfky_fyxmszb\r\n"
			+ "	, t.sfky_ylf\r\n"
			+ "	, t.sfky_scjzz\r\n"
			+ "	, t.sfky_lkrxz\r\n"
			+ "	, t.sfky_yhkxz\r\n"
			+ "	, t.zxfs\r\n"
			+ " FROM (\r\n"
			+ "	SELECT d.lyxt\r\n"
			+ "		, d.bdh\r\n"
			+ "		, d.bah\r\n"
			+ "		, d.lkrxm\r\n"
			+ "		, d.lkrkhh\r\n"
			+ "		, d.lkryhkh\r\n"
			+ "		, d.zhifu as fklx\r\n"
			+ "		, d.gyxm\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN d.gynl = 0 THEN NULL\r\n"
			+ "			ELSE d.gynl\r\n"
			+ "		END AS gynl\r\n"
			+ "		, d.gysfzhm\r\n"
			+ "		, d.gysjh\r\n"
			+ "		, d.zffs\r\n"
			+ "		, d.gyyhkkhh\r\n"
			+ "		, d.gyyhkh\r\n"
			+ "		, d.tbr\r\n"
			+ "		, d.bbxr\r\n"
			+ "		, d.qbsj \r\n"
			+ "		, d.cxsj\r\n"
			+ "		, case when d.cxdd like '上海市%' and length(d.cxdd) > 3 then substr(d.cxdd,1,7) \r\n"
			+ "			  when d.cxdd not like '上海市%' and d.cxdd like '%省%' then substr(d.cxdd,1,3)\r\n"
			+ "			  else d.cxdd end as cxdd \r\n"
			+ "		,case when substr(d.hyfxfl,1,2) in ('52','57','60','51','83','26','48','49','47','28','39','31') \r\n"
			+ "	or d.hyfxfl like '%高危%'\r\n"
			+ "	 then '高危行业'\r\n"
			+ "	else '普通行业'\r\n"
			+ "	end as hyfxfl\r\n"
			+ "		, case when d.zcxrc is not null then d.zcxrc\r\n"
			+ "			else 0.0 end as zcxrc\r\n"
			+ "		,case when d.ztbrc is not null then d.ztbrc\r\n"
			+ "			else 0.0 end as ztbrc\r\n"
			+ "		, case when d.zlpje is not null then d.zlpje\r\n"
			+ "			else 0.0 end as zlpje\r\n"
			+ "		, case when d.zbf is not null then d.zbf\r\n"
			+ "			else 0.0 end as zbf\r\n"
			+ "		, case when d.scdj is not null then d.scdj\r\n"
			+ "			else 0.0 end as scdj\r\n"
			+ "		, case when d.scpcj is not null then d.scpcj\r\n"
			+ "			else 0.0 end as scpcj\r\n"
			+ "		, case when d.ylf is not null then d.ylf\r\n"
			+ "			else 0.0 end as ylf\r\n"
			+ "		, case\r\n"
			+ "			when d.qtf is null then 0.0 else d.qtf end as qtf\r\n"
			+ "		, case when d.yyf is not null then d.yyf \r\n"
			+ "			else 0.0 end as yyf\r\n"
			+ "		, case when d.wgf is not null then d.wgf\r\n"
			+ "			else 0.0 end as wgf\r\n"
			+ "		, case when d.zybz is not null then d.zybz\r\n"
			+ "			else 0.0 end as zybz\r\n"
			+ "		, case \r\n"
			+ "			when d.qtf2 is null then 0.0 else d.qtf2 end as qtf2\r\n"
			+ "		, case when d.pfje is not null then pfje\r\n"
			+ "			else 0.0 end as pfje\r\n"
			+ "		, d.bz\r\n"
			+ "		, d.jgts\r\n"
			+ "		, d.rczb\r\n"
			+ "		, d.jezb\r\n"
			+ "		, (if(d.scpcj<>0,1,0)+if(d.ylf<>0,1,0)+if(d.yyf<>0,1,0)+if(d.wgf<>0,1,0)+if(d.zybz<>0,1,0))/5 as fyxmszb\r\n"
			+ "		, d.scjzz\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN d.lkrxm = d.gyxm THEN 1 \r\n"   //
			+ "			ELSE CASE \r\n"
			+ "				WHEN d.lkrxm LIKE '%公司' THEN 0\r\n"
			+ "				ELSE 0.5 END\r\n"
			+ "		END AS lkrxz\r\n"
			+ "		, CASE \r\n"
			+ "			WHEN tt.cn > 1 THEN 1\r\n"
			+ "			ELSE 0\r\n"
			+ "		END AS yhkxz \r\n"
			+ "        , IF(d.zhifu='现金',1,0) AS fklxfz \r\n"
			+ "		, d.sfky_jgts\r\n"
			+ "		, d.sfky_rczb\r\n"
			+ "		, d.sfky_jezb\r\n"
			+ "		, d.sfky_fyxmszb\r\n"
			+ "		, d.sfky_ylf\r\n"
			+ "		, d.sfky_scjzz\r\n"
			+ "		, d.sfky_lkrxz\r\n"
			+ "		, d.sfky_yhkxz\r\n"
			+ "		, d.zxfs\r\n"
			+ "		, d.batch_id\r\n"
			+ "	FROM (select * from pbmx_lixian where batch_id =#{batchid} ) d\r\n"
			+ "	LEFT OUTER JOIN (SELECT COUNT(*) AS cn, lkryhkh AS 领款人银行卡号 FROM pbmx_lixian   \r\n"
			+ " GROUP BY lkrxm, lkryhkh) tt\r\n"
			+ "	ON d.lkryhkh = tt.领款人银行卡号 )t) t1)t2) t3")
	List<PbmxEntity> getCountData(Integer batchid);

	@Insert("insert into pbmx_lixian(id,lyxt,bdh,bah,lkrxm,lkrkhh,lkryhkh,zhifu,gyxm,gynl,gysfzhm,gysjh,zffs,gyyhkkhh,gyyhkh,tbr,bbxr,"
			+ "qbsj,cxsj,cxdd,hyfxfl,zcxrc,ztbrc,zlpje,zbf,scdj,scpcj,ylf,qtf,yyf,wgf,zybz,qtf2,pfje,bz,jgts,rczb,jezb,fyxmszb,scjzz,"
			+ "lkrxz,yhkxz,sfky_jgts,sfky_rczb,sfky_jezb,sfky_fyxmszb,sfky_ylf,sfky_scjzz,sfky_lkrxz,sfky_yhkxz,zxfs,batch_id)values("
			+ "#{id},#{lyxt},#{bdh},#{bah},#{lkrxm},#{lkrkhh},#{lkryhkh},#{zhifu},#{gyxm},#{gynl},#{gysfzhm},#{gysjh},#{zffs},#{gyyhkkhh}"
			+ ",#{gyyhkh},#{tbr},#{bbxr},#{qbsj},#{cxsj},#{cxdd},#{hyfxfl},#{zcxrc},#{ztbrc},#{zlpje},#{zbf},#{scdj},#{scpcj},#{ylf},"
			+ "#{qtf},#{yyf},#{wgf},#{zybz},#{qtf2},#{pfje},#{bz},#{jgts},#{rczb},#{jezb},#{fyxmszb},#{scjzz},#{lkrxz},#{yhkxz},#{sfkyJgts}"
			+ ",#{sfkyRczb},#{sfkyJezb},#{sfkyFyxmszb},#{sfkyYlf},#{sfkyScjzz},#{sfkyLkrxz},#{sfkyYhkxz},#{zxfs},#{batchId})")
	@Options(useGeneratedKeys = true, keyColumn = "id", keyProperty = "id")
	Boolean savePbmx(PbmxEntity pbmx);

	@Delete("delete from pbmx_lixian where batch_id=#{id}")
	Boolean deletePbmxByBatchid(Integer id);

	@SelectProvider(method = "selectpbmx", type = pbmxProvider.class)
	List<PbmxEntity> selectPbmx(PbmxEntity pbmx);

	@Select("select a.* from pbmx_lixian a ,pbmx_file b where a.batch_id = b.batch_id and b.user_id =#{userid}")
	List<PbmxEntity> selectPbmxList(Long userid);

	@Select(" select sum(zcxrc) as zcxrc,SUM(ztbrc) as ztbrc,SUM(zlpje) as zlpje,sum(zbf) as zbf from pbmx_lixian"
			+ " where batch_id=#{fileid}")
	StatisData statis(Integer fileid);

	public class pbmxProvider {
		public String selectpbmx(PbmxEntity pbmx) {
			 StringBuilder sql = new StringBuilder();
             sql.append("select * from pbmx_lixian where 1=1 ");
             if(null != pbmx.id){
                 sql.append("and id=#{id} ");
             }
             if(null != pbmx.lyxt){
                 sql.append("and lyxt=#{lyxt} ");
             }
             if(null != pbmx.bdh){
                 sql.append("and bdh=#{bdh} ");
             }
             if(null != pbmx.bah){
                 sql.append("and bah=#{bah} ");
             }
             if(null != pbmx.lkrxm){
                 sql.append("and lkrxm=#{lkrxm} ");
             }
             if(null != pbmx.lkrkhh){
                 sql.append("and lkrkhh=#{lkrkhh} ");
             }
             if(null != pbmx.lkryhkh){
                 sql.append("and lkryhkh=#{lkryhkh} ");
             }
             if(null != pbmx.zhifu){
                 sql.append("and zhifu=#{zhifu} ");
             }
             if(null != pbmx.gyxm){
                 sql.append("and gyxm=#{gyxm} ");
             }
             if(null != pbmx.gynl){
                 sql.append("and gynl=#{gynl} ");
             }
             if(null != pbmx.gysfzhm){
                 sql.append("and gysfzhm=#{gysfzhm} ");
             }
             if(null != pbmx.gysjh){
                 sql.append("and gysjh=#{gysjh} ");
             }
             if(null != pbmx.zffs){
                 sql.append("and zffs=#{zffs} ");
             }
             if(null != pbmx.gyyhkkhh){
                 sql.append("and gyyhkkhh=#{gyyhkkhh} ");
             }
             if(null != pbmx.gyyhkh){
                 sql.append("and gyyhkh=#{gyyhkh} ");
             }
             if(null != pbmx.tbr){
                 sql.append("and tbr=#{tbr} ");
             }
             if(null != pbmx.bbxr){
                 sql.append("and bbxr=#{bbxr} ");
             }
             if(null != pbmx.qbsj){
                 sql.append("and qbsj=#{qbsj} ");
             }
             if(null != pbmx.cxsj){
                 sql.append("and cxsj=#{cxsj} ");
             }
             if(null != pbmx.cxdd){
                 sql.append("and cxdd=#{cxdd} ");
             }
             if(null != pbmx.hyfxfl){
                 sql.append("and hyfxfl=#{hyfxfl} ");
             }
             if(null != pbmx.zcxrc){
                 sql.append("and zcxrc=#{zcxrc} ");
             }
             if(null != pbmx.ztbrc){
                 sql.append("and ztbrc=#{ztbrc} ");
             }
             if(null != pbmx.zlpje){
                 sql.append("and zlpje=#{zlpje} ");
             }
             if(null != pbmx.zbf){
                 sql.append("and zbf=#{zbf} ");
             }
             if(null != pbmx.scdj){
                 sql.append("and scdj=#{scdj} ");
             }
             if(null != pbmx.scpcj){
                 sql.append("and scpcj=#{scpcj} ");
             }
             if(null != pbmx.ylf){
                 sql.append("and ylf=#{ylf} ");
             }
             if(null != pbmx.qtf){
                 sql.append("and qtf=#{qtf} ");
             }
             if(null != pbmx.yyf){
                 sql.append("and yyf=#{yyf} ");
             }
             if(null != pbmx.wgf){
                 sql.append("and wgf=#{wgf} ");
             }
             if(null != pbmx.zybz){
                 sql.append("and zybz=#{zybz} ");
             }
             if(null != pbmx.qtf2){
                 sql.append("and qtf2=#{qtf2} ");
             }
             if(null != pbmx.pfje){
                 sql.append("and pfje=#{pfje} ");
             }
             if(null != pbmx.bz){
                 sql.append("and bz=#{bz} ");
             }
             if(null != pbmx.jgts){
                 sql.append("and jgts=#{jgts} ");
             }
             if(null != pbmx.rczb){
                 sql.append("and rczb=#{rczb} ");
             }
             if(null != pbmx.jezb){
                 sql.append("and jezb=#{jezb} ");
             }
             if(null != pbmx.fyxmszb){
                 sql.append("and fyxmszb=#{fyxmszb} ");
             }
             if(null != pbmx.scjzz){
                 sql.append("and scjzz=#{scjzz} ");
             }
             if(null != pbmx.lkrxz){
                 sql.append("and lkrxz=#{lkrxz} ");
             }
             if(null != pbmx.yhkxz){
                 sql.append("and yhkxz=#{yhkxz} ");
             }
             if(null != pbmx.sfkyJgts){
                 sql.append("and sfky_jgts=#{sfkyJgts} ");
             }
             if(null != pbmx.sfkyRczb){
                 sql.append("and sfky_rczb=#{sfkyRczb} ");
             }
             if(null != pbmx.sfkyJezb){
                 sql.append("and sfky_jezb=#{sfkyJezb} ");
             }
             if(null != pbmx.sfkyFyxmszb){
                 sql.append("and sfky_fyxmszb=#{sfkyFyxmszb} ");
             }
             if(null != pbmx.sfkyYlf){
                 sql.append("and sfky_ylf=#{sfkyYlf} ");
             }
             if(null != pbmx.sfkyScjzz){
                 sql.append("and sfky_scjzz=#{sfkyScjzz} ");
             }
             if(null != pbmx.sfkyLkrxz){
                 sql.append("and sfky_lkrxz=#{sfkyLkrxz} ");
             }
             if(null != pbmx.sfkyYhkxz){
                 sql.append("and sfky_yhkxz=#{sfkyYhkxz} ");
             }
             if(null != pbmx.zxfs){
                 sql.append("and zxfs=#{zxfs} ");
             }
             if(null != pbmx.batchId){
                 sql.append("and batch_id=#{batchId} ");
             }
               return sql.toString();
          }
		
	}
}
