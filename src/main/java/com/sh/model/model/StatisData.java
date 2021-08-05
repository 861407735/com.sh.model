package com.sh.model.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class StatisData implements Serializable{
	private static final long serialVersionUID = 1L;
	public double zbf;//总保费
	public double zlpje;//总理赔金额
	public double ztbrc;//总投保人数
	public double zcxrc;//总出险人数
}
	
