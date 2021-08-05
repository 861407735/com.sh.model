package com.sh.model.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	public Integer batchId;// 批次id
	public Long userId;//用户id
	public String name;// 文件名
	public Date createTime;// 创建时间
}
