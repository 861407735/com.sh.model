package com.sh.model.fileinterface;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ship.web.webutil.Pager;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sh.model.mapper.FileMapper;
import com.sh.model.model.FileEntity;

public abstract class FileService<T> implements FileOperate<T> {

	@Autowired
	FileMapper fileMapper;

	public void createTable() {
		String map = fileMapper.getTable();
		if (map == null) {
			fileMapper.createTable();
		}
	}

	public Pager<FileEntity> findData(String name, String time, Integer pagesize, Integer current, Long userid) {
		PageHelper.startPage(current, pagesize);
		Page<FileEntity> list = (Page<FileEntity>) fileMapper.selectFileEntity(name, time, userid);
		Pager<FileEntity> pager = new Pager<FileEntity>();
		pager.setCount(list.getTotal());
		pager.setList(list);
		return pager;
	}

	// 保存数据
	public abstract Integer saveData(FileEntity fileentity, List<T> obj);

	// 删除数据O
	public abstract Boolean dropData(Integer batchid);

	// 查询源数据
	public abstract Pager<T> findData(T obj, Integer pagesize, Integer pagenum);

	// 查询结果数据
	public abstract Pager<T> findResultData(T obj, Integer pagesize, Integer pagenum);

}
