package com.farm.parameter.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.farm.parameter.domain.AloneDictionaryEntity;
import com.farm.parameter.service.DictionaryEntityServiceInter;
import com.farm.core.page.OperateType;
import com.farm.core.page.RequestMode;
import com.farm.core.page.ViewMode;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DBSort;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.web.WebUtils;
import com.farm.web.easyui.EasyUiUtils;

 
@RequestMapping("/dictionary")
@Controller
public class DictionaryController extends WebUtils{
	private static final Logger log = Logger
			.getLogger(DictionaryController.class);
	
	@Resource
	DictionaryEntityServiceInter dictionaryEntityServiceImpl;
	
	 
	@RequestMapping("/query")
	@ResponseBody
	public Map<String, Object> queryall(DataQuery query,
			HttpServletRequest request) {
		try {
			query = EasyUiUtils.formatGridQuery(request, query);
			query = DataQuery.init(query, "alone_dictionary_entity",
					"id,name,entityindex,type,type as types,comments");
			query.addRule(new DBRule("state", "1","="));
			query.addDefaultSort(new DBSort("utime", "DESC"));
			DataResult result = query.search();
			result.runDictionary("1:序列,0:树,2:文件組", "TYPE");
			return ViewMode.getInstance()
					.putAttrs(EasyUiUtils.formatGridData(result))
					.returnObjMode();
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}
	
	 
	@RequestMapping("/list")
	public ModelAndView index(HttpSession session) {
		return ViewMode.getInstance()
				.returnModelAndView("parameter/pAloneDictionaryEntityLayout");
	}


	 
	@RequestMapping("/edit")
	@ResponseBody
	public Map<String, Object> editSubmit(AloneDictionaryEntity dictionary, HttpSession session) {
		try {
			AloneDictionaryEntity entity = dictionaryEntityServiceImpl.editEntity(dictionary, getCurrentUser(session));
			return ViewMode.getInstance().putAttr("entity", entity)
					.returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}

	 
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> addSubmit(AloneDictionaryEntity dictionary, HttpSession session) {
		
		try {
			AloneDictionaryEntity entity = dictionaryEntityServiceImpl.insertEntity(dictionary,
					getCurrentUser(session));
			return ViewMode.getInstance().setOperate(OperateType.ADD)
					.putAttr("entity", entity).returnObjMode();
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}

	 
	@RequestMapping("/del")
	@ResponseBody
	public Map<String, Object> delSubmit(String ids, HttpSession session) {
		try {
			for (String id : parseIds(ids)) {
				dictionaryEntityServiceImpl.deleteEntity(id, getCurrentUser(session));
			}
			return ViewMode.getInstance().returnObjMode();
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnObjMode();
		}
	}


	 
	@RequestMapping("/form")
	public ModelAndView view(RequestMode pageset, String ids) {
		try {
				switch (pageset.getOperateType()) {
				case (1): {// 新增
					return ViewMode.getInstance().putAttr("pageset", pageset)
							.returnModelAndView("parameter/pAloneDictionaryEntityEntity");
				}
				case (0): {// 展示
					return ViewMode.getInstance().putAttr("pageset", pageset)
							.putAttr("entity", dictionaryEntityServiceImpl.getEntity(ids))
							.returnModelAndView("parameter/pAloneDictionaryEntityEntity");
				}
				case (2): {// 修改
					return ViewMode.getInstance().putAttr("pageset", pageset)
							.putAttr("entity", dictionaryEntityServiceImpl.getEntity(ids))
							.putAttr("ids", ids)
							.returnModelAndView("parameter/pAloneDictionaryEntityEntity");
				}
				default:
					break;
				}
		} catch (Exception e) {
			return ViewMode.getInstance().setError(e.getMessage(),e)
					.returnModelAndView("parameter/pAloneDictionaryEntityEntity");
		}
		return ViewMode.getInstance().returnModelAndView(
				"parameter/pAloneDictionaryEntityEntity");
	}

	 
	@RequestMapping("/ALONEDictionary_validateIsRepeatKey")
	@ResponseBody
	public Map<String, Object> validateIsRepeatKey(String key,String ids) {
		boolean  isRepeatKey = dictionaryEntityServiceImpl.validateIsRepeatKey(key, ids);
		return ViewMode.getInstance()
				.putAttr("isRepeatKey", isRepeatKey)
				.returnObjMode();
	}

}
