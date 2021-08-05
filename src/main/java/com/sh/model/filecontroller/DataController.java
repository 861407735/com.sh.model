package com.sh.model.filecontroller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ship.web.identify.authconfig.AuthAdvice;
import org.springframework.ship.web.webutil.Pager;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


import com.sh.model.common.ExcelUtil;
import com.sh.model.common.HttpUtil;
import com.sh.model.fileservice.PbmxService;
import com.sh.model.model.FileEntity;
import com.sh.model.model.PbmxEntity;
import com.sh.model.model.StatisData;
import com.sh.model.model.User;

import com.sh.model.ssologin.SsoConfig;

/**
 * @describe:
 * @author:john chen
 * @date:2020年11月17日
 */
@RestController
public class DataController {

	@Autowired
	PbmxService pbmxService;

	@Autowired
	AuthAdvice auth;
	
	@Autowired
	HttpUtil httputil;

	/**
	 * @describe:查询用户信息
	 * @author:john chen
	 * @date:2020年11月16日
	 * @return
	 * @throws IOException
	 */
	@GetMapping("getuserinfo")
	public User getUserInfo() throws IOException {
		User user=User.builder().id(auth.getUserId()).username(auth.getNickname()).build();
        return user;
	}
	
	//注销当前用户，返回是否注销成功
//    @RequestMapping("/logOff")
//    public void logOff(HttpServletRequest request, HttpServletResponse response, boolean autoRedirect) throws IOException{
//    	 String url = SsoConfig.casserver+"/jzsso/ticket/logOff/"+SsoConfig.serverip+"/" + request.getRemoteHost() + "/gzzrxgj/" + auth.getUserId();
//    	 httputil.httpGetRequest(url);
//    	 Cookie cookie = new Cookie("ssoTicket",null);
//    	 cookie.setMaxAge(0);//表示浏览器将当前cookie及之前保存的同名cookie删除
//    	 response.addCookie(cookie);
//    	 auth.removeAuth();
//    	 response.sendRedirect(SsoConfig.casserver+"/jzsso/login.jsp?appServerIP=38.80.34.16&appName=gzzrxgj&casRedirectUrl=http://38.80.34.16:9999/publish/5ee48d645e26e60f5ef8739a");
//    }
    
	/**
	 * @describe:上传文件到系统，并运算结果
	 * @author:john chen
	 * @date:2020年11月16日
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("uploadfile")
	public Integer uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		Long userid = auth.getUserId();
		String tablename = file.getOriginalFilename();
		List<LinkedHashMap<String, Object>> res = ExcelUtil.importExcel(file);
		Integer bachid = pbmxService.publishData(res, tablename, userid);
		return bachid;
	}

	/**
	 * @describe:统计上传的文件信息
	 * @author:john chen
	 * @date:2020年11月16日
	 * @param fileid
	 * @return
	 */
	@GetMapping("statisdata")
	public StatisData statisData(Integer fileid) {
		StatisData sd = pbmxService.statis(fileid);
		return sd;
	}

	
	/**
	 * @describe:分页预览数据记录
	 * @author:john chen
	 * @date:2020年11月16日
	 * @param fileid
	 * @param time
	 * @param pagesize
	 * @param current
	 * @return
	 * @throws Exception
	 */
	@GetMapping("getfiledetail")
	public Pager<PbmxEntity> getFileDetail(Integer fileid, String time, @RequestParam Integer pagesize,
			@RequestParam Integer current) throws Exception {
		PbmxEntity obj = PbmxEntity.builder().batchId(fileid).qbsj(time).build();
		Pager<PbmxEntity> res = pbmxService.findData(obj, pagesize, current);
		return res;
	}

	/**
	 * @describe:查询文件列表
	 * @author:john chen
	 * @date:2020年11月16日
	 * @param name 文件名
	 * @param time 上传时间
	 * @param pagesize
	 * @param current
	 * @return
	 * @throws Exception
	 */
	@GetMapping("getfilelist")
	public Pager<FileEntity> getFileList(String name, String time, @RequestParam Integer pagesize,
			@RequestParam Integer current) throws Exception {
		Long userid = auth.getUserId();
		Pager<FileEntity> res = pbmxService.findData(name, time, pagesize, current, userid);
		return res;
	}

	/**
	 * @describe:取消 删除文件记录和数据记录，确定 啥都不做，返回true
	 * @author:john chen
	 * @date:2020年11月16日
	 * @param fileid
	 * @return
	 * @throws NumberFormatException
	 * @throws UnsupportedEncodingException
	 * @throws InterruptedException
	 */
	@DeleteMapping("deletedata")
	public Boolean deleteFile(@RequestParam Integer fileid)
			throws NumberFormatException, UnsupportedEncodingException, InterruptedException {
		Boolean res = pbmxService.dropData(fileid);
		return res;
	}

	/**
	 * @describe:分页查询文件运算结果数据库，每条记录 三个参数 置信分
	 * @author:john chen
	 * @date:2020年11月16日
	 * @param pbmx
	 * @param pagesize
	 * @param current
	 * @return
	 */
	@GetMapping("getresult")
	public Pager<PbmxEntity> getResultData(PbmxEntity pbmx, @RequestParam Integer pagesize, @RequestParam Integer current) {
		Pager<PbmxEntity> res = pbmxService.findResultData(pbmx, pagesize, current);
		return res;
	}

	/**
	 * @describe:
	 * @author:john chen
	 * @date:2020年11月16日
	 * @param pbmx
	 * @param response
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@GetMapping("exportexcel")
	public void exportExcel(PbmxEntity pbmx, HttpServletResponse response)
			throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException {
		pbmxService.exportExcel(pbmx, response);
	}

}


