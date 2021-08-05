package com.sh.model.fileservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ship.web.webutil.Pager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sh.model.common.ExcelUtil;
import com.sh.model.fileinterface.FileService;
import com.sh.model.mapper.FileMapper;
import com.sh.model.mapper.PbmxMapper;
import com.sh.model.mapper.PbmxResultMapper;
import com.sh.model.model.FileEntity;
import com.sh.model.model.PbmxEntity;
import com.sh.model.model.StatisData;

@Service
public class PbmxService extends FileService<PbmxEntity> {

	@Autowired
	FileMapper fileMapper;

	@Autowired
	PbmxMapper pbmxMapper;
	
	@Autowired
	PbmxResultMapper pbmxResultMapper;
	
	/**
	 * @describe:创建三张表
	 * @author:john chen
	 * @date:2020年11月14日
	 */
	@PostConstruct
	public void getTable() {
		super.createTable();
		
		String pbmx = pbmxMapper.getTable();
		if (pbmx == null) {
			pbmxMapper.createTable();
		}
		
		String pbmxResult = pbmxResultMapper.getTable();
		if (pbmxResult == null) {
			pbmxResultMapper.createTable();
		}
	}

	/**
	 * @describe:上传文件时候，保存文件信息，和文件详情信息,运算结果，准确率，逻辑判断点
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param fileEntity
	 * @param pbmxEntitys
	 * @return
	 */
	@Override
	public Integer saveData(FileEntity fileEntity, List<PbmxEntity> pbmxEntitys) {
		//保存文件到数据库
		Boolean res1 = fileMapper.saveFileEntity(fileEntity);
		if (res1 == false) {
			return 0;
		}
		//保存文件详情到数据库
		pbmxEntitys.forEach(x -> {
			x.batchId = fileEntity.batchId;  //将文件id赋给详情信息的id  一个文件对应多个详情
			pbmxMapper.savePbmx(x);
		});
		//根据文件id获取结果并保存到数据库,并计算出准确率，逻辑判断点
		List<PbmxEntity> list = pbmxMapper.getCountData(fileEntity.batchId);
		list.forEach(x->{
			x.batchId=fileEntity.batchId;
			pbmxResultMapper.savePbmxResult(x);  // 将查询的结果 保存到 pbmx_lixian_result 表中
			//计算准确率
			pbmxResultMapper.computZQL(x.id);
			//计算逻辑判断点
			pbmxResultMapper.computLJD(x.id);
		});
		
		return fileEntity.batchId;
	}

	/**
	 * @describe:删除文件要删除文件，文件详情，文件运算结果
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param batchid
	 * @return
	 */
	@Override
	@Transactional
	public Boolean dropData(Integer batchid) {
		Boolean res1 = fileMapper.deleteFileEntity(batchid);
		Boolean res2 = pbmxMapper.deletePbmxByBatchid(batchid);
		Boolean res3 = pbmxResultMapper.deletePbmxResultByBatchid(batchid);
		if (res1 == true && res2 == true && res3==true) {
			return true;
		}
		return false;
	}

	/**
	 * @describe:查询关于文件详情的
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param obj
	 * @param pagesize
	 * @param pagenum
	 * @return
	 */
	@Override
	public Pager<PbmxEntity> findData(PbmxEntity obj, Integer pagesize, Integer pagenum) {
		PageHelper.startPage(pagenum, pagesize);
		Page<PbmxEntity> list = (Page<PbmxEntity>) pbmxMapper.selectPbmx(obj);
		Pager<PbmxEntity> pager = new Pager<PbmxEntity>();
		pager.setCount(list.getTotal());
		pager.setList(list);
		return pager;
	}
	/**
	 * @describe:查询关于文件运算结果的
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param obj
	 * @param pagesize
	 * @param pagenum
	 * @return
	 */
	@Override
	public Pager<PbmxEntity> findResultData(PbmxEntity obj, Integer pagesize, Integer pagenum) {
		PageHelper.startPage(pagenum, pagesize);
		Page<PbmxEntity> list = (Page<PbmxEntity>) pbmxResultMapper.selectPbmxResult(obj);
		Pager<PbmxEntity> pager = new Pager<PbmxEntity>();
		pager.setCount(list.getTotal());
		pager.setList(list);
		return pager;
	}
	/**
	 * @describe:文件上传，解析，保存文件和文件详情
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param res
	 * @param tablename
	 * @param userid
	 * @return
	 */
	@Transactional
	public Integer publishData(List<LinkedHashMap<String, Object>> res, String tablename,Long userid) {
		FileEntity fe = FileEntity.builder().name(tablename).userId(userid).build();
		List<PbmxEntity> pes = new LinkedList<>();
		res.forEach(x -> {
			PbmxEntity pe = null;
			try {
				pe = (PbmxEntity)this.mapToObject(x, PbmxEntity.class); //x是LinkedHashMap的元素 类似表中的一行
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			pes.add(pe);
		});
		Integer result = saveData(fe, pes);  //将传来的表存入数据库
		return result;
	}
	
	/**
	 * @describe:对文件详情进行简单统计
	 * @author:john chen
	 * @date:2020年11月14日
	 * @param fileid
	 * @return
	 */
	public StatisData statis(Integer fileid) {
		StatisData sd=pbmxMapper.statis(fileid);
		return sd;
	}
	
	public void exportExcel(PbmxEntity pbmxentity,HttpServletResponse response) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		//获取输出字段
		String[] pbmx = { "来源系统", "保单号", "报案号", "领款人姓名", "领款人开户行","领款人银行卡号", "赔付雇员方式", "雇员姓名", "雇员年龄", "雇员身份证",
				"雇员手机号", "保险方赔付方式", "雇员开户行", "雇员银行卡号", "投保人", "被投保人", "起保时间", "出险时间", "出现地点", "行业风险分类", "伤残等级", "伤残赔偿金",
				"医疗费", "其他费", "营养费", "误工费", "住院补助", "其他费2", "赔付金额", "备注", "间隔时间", "人次占比", "金额占比", "费用项目占比", "伤残金占比",
				"领款人性质", "银行卡性质", "是否使用现金", "置信分" };
		List<String> listtitle = Arrays.asList(pbmx);
		//获取数据
		List<PbmxEntity> res = pbmxResultMapper.selectPbmxResult(pbmxentity);
		//获取输出值
		List<LinkedList<Object>> resList = new ArrayList<>(res.size());
		for (int y = 0; y < res.size(); y++) {
			LinkedList<Object> resdest=new LinkedList<>();
			resdest.add(res.get(y).lyxt);
			resdest.add(res.get(y).bdh);
			resdest.add(res.get(y).bah);
			resdest.add(res.get(y).lkrxm);
			resdest.add(res.get(y).lkrkhh);
			resdest.add(res.get(y).lkryhkh);
			resdest.add(res.get(y).zhifu);
			resdest.add(res.get(y).gyxm);
			resdest.add(res.get(y).gynl);
			resdest.add(res.get(y).gysfzhm);
			resdest.add(res.get(y).gysjh);
			resdest.add(res.get(y).zffs);
			resdest.add(res.get(y).gyyhkkhh);
			resdest.add(res.get(y).gyyhkh);
			resdest.add(res.get(y).tbr);
			resdest.add(res.get(y).bbxr);
			resdest.add(res.get(y).qbsj);
			resdest.add(res.get(y).cxsj);
			resdest.add(res.get(y).cxdd);
			resdest.add(res.get(y).hyfxfl);
			resdest.add(res.get(y).scdj);
			resdest.add(res.get(y).scpcj);
			resdest.add(res.get(y).ylf);
			resdest.add(res.get(y).qtf);
			resdest.add(res.get(y).yyf);
			resdest.add(res.get(y).wgf);
			resdest.add(res.get(y).zybz);
			resdest.add(res.get(y).qtf2);
			resdest.add(res.get(y).pfje);
			resdest.add(res.get(y).bz);
			resdest.add(res.get(y).jgts);
			resdest.add(res.get(y).rczb);
			resdest.add(res.get(y).jezb);
			resdest.add(res.get(y).fyxmszb);
			resdest.add(res.get(y).scjzz);
			resdest.add(res.get(y).lkrxz);
			resdest.add(res.get(y).yhkxz);
			resdest.add(res.get(y).extrp);
			resdest.add(res.get(y).zxfs);
			resList.add(resdest);
		}
		ExcelUtil.exportExcel(listtitle, resList, response);
	}




}
