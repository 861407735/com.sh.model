package com.sh.model.fileinterface;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.ship.web.webutil.Pager;

import com.sh.model.model.FieldDescInject;
import com.sh.model.model.FileEntity;

public interface FileOperate<T> {
	default public Object mapToObject(LinkedHashMap<String, Object> map, Class<?> aClass) throws InstantiationException, IllegalAccessException {
		if (null == map || map.size() <= 0) {
			return null;
		}
		Object o = aClass.newInstance();
		Field[] declaredFields = o.getClass().getDeclaredFields();  //通过反射获取所有字段
		for (Field field : declaredFields) {
			int modifiers = field.getModifiers();  //获取修饰符
			if (Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)) {
				continue;
			}
			field.setAccessible(true);   //此设置后 可以为字段赋值
			if(field.isAnnotationPresent(FieldDescInject.class)) {
				Object obj=map.get(field.getDeclaredAnnotation(FieldDescInject.class).value()); //通过注解内容查询出相应对象
				if(obj=="" ||  null ==obj) {
					continue;
				}
				if(field.getType() == Integer.class) {
					field.set(o, Integer.valueOf(obj.toString())); //转换为Integer类型存储
				}else {
					field.set(o, obj);
				}
			}
		}
		return o;
	}

	// 保存数据
	public Integer saveData(FileEntity fileentity, List<T> obj);

	// 删除数据O
	public Boolean dropData(Integer batchid);

	// 查询源数据
	public Pager<T> findData(T obj,Integer pagesize, Integer pagenum);
	
	// 查询结果数据
	public Pager<T> findResultData(T obj,Integer pagesize, Integer pagenum);
}
