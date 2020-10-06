<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/view/conf/farmtag.tld" prefix="PF"%>
<!--标签类别表单-->
<div class="easyui-layout" data-options="fit:true">
	<div class="TableTitle" data-options="region:'north',border:false">
		<span class="label label-primary"> <c:if
				test="${pageset.operateType==1}">新增${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==2}">修改${JSP_Messager_Title}记录</c:if> <c:if
				test="${pageset.operateType==0}">浏览${JSP_Messager_Title}记录</c:if>
		</span>
	</div>
	<div data-options="region:'center'">
		<form id="dom_formTagtype">
			<input type="hidden" id="entity_id" name="id" value="${entity.id}">
			<table class="editTable">
				<tr>
					<td class="title">名称:</td>
					<td ><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:[,'maxLength[16]']"
						id="entity_name" name="name" value="${entity.name}"></td>
					<td class="title">排序:</td>
					<td ><input type="text" style="width: 120px;"
						class="easyui-validatebox"
						data-options="required:true,validType:['integer','maxLength[5]']"
						id="entity_sort" name="sort" value="${entity.sort}"></td>
				</tr>
				<tr>
					<td class="title">状态:</td>
					<td colspan="3"><select name="pstate" id="entity_pstate"
						val="${entity.pstate}" style="width: 120px;">
							<option value="1">可用</option>
							<option value="0">禁用</option>
					</select></td>
				</tr>
				<tr>
					<td class="title">备注:</td>
					<td colspan="3"><input type="text" style="width: 360px;"
						class="easyui-validatebox"
						data-options="validType:[,'maxLength[64]']" id="entity_pcontent"
						name="pcontent" value="${entity.pcontent}"></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="div_button" style="text-align: center; padding: 4px;">
			<c:if test="${pageset.operateType==1}">
				<a id="dom_add_entityTagtype" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">增加</a>
			</c:if>
			<c:if test="${pageset.operateType==2}">
				<a id="dom_edit_entityTagtype" href="javascript:void(0)"
					iconCls="icon-save" class="easyui-linkbutton">修改</a>
			</c:if>
			<a id="dom_cancle_formTagtype" href="javascript:void(0)"
				iconCls="icon-cancel" class="easyui-linkbutton"
				style="color: #000000;">取消</a>
		</div>
	</div>
</div>
<script type="text/javascript">
	var submitAddActionTagtype = 'tagtype/add.do';
	var submitEditActionTagtype = 'tagtype/edit.do';
	var currentPageTypeTagtype = '${pageset.operateType}';
	var submitFormTagtype;
	$(function() {
		//表单组件对象
		submitFormTagtype = $('#dom_formTagtype').SubmitForm({
			pageType : currentPageTypeTagtype,
			grid : gridTagtype,
			currentWindowId : 'winTagtype'
		});
		//关闭窗口
		$('#dom_cancle_formTagtype').bind('click', function() {
			$('#winTagtype').window('close');
		});
		//提交新增数据
		$('#dom_add_entityTagtype').bind('click', function() {
			submitFormTagtype.postSubmit(submitAddActionTagtype);
		});
		//提交修改数据
		$('#dom_edit_entityTagtype').bind('click', function() {
			submitFormTagtype.postSubmit(submitEditActionTagtype);
		});
	});
//-->
</script>