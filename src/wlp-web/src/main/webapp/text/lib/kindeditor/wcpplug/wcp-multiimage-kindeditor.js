
var wcp_imgs_editor;
var wcpImgsKnowdialog;
KindEditor.plugin('wcpimgs', function(K) {
	wcp_imgs_editor = this, name = 'wcpimgs';
	// 点击图标时执行
	wcp_imgs_editor.clickToolbar(name, function() {
		wcpImgsKnowdialog = K
		.dialog( {
			width : 400,
			title : '批量上传图片',
			body : '<div style="margin:10px;height:400px;text-align: center;overflow:auto;">'+
			       '<div class="wcpMultiUploadButtonBox">'+
			       ' <span class="btn btn-info fileinput-button"> '+
			       '  <span>批量上传资源</span><span id="html5uploadProsess"></span>'+
			       '  <input id="fileupload" type="file" name="file" multiple>'+
			       ' </span>'+
			       ' <span class="btn btn-info insertEditor" style="height:30px;padding-top:6px;font-size:12px;"> '+ 
			       '  <span>全部插入编辑器中</span>'+  
			       ' </span>'+
			       '<br/>第1步.点击"批量上传资源"按钮，上传图片文件。<br/>第2步.点击"全部插入编辑器"按钮，将图片插入编辑器中。</div>'+ 
				   '<div class="panel-body" style="height:300px;overflow:auto;margin-top:12px;" id="wcpimgFileListId"><div id="fileQueue"></div></div>'+
				   '</div>',
			closeBtn : {
				name : '关闭',
				click : function(e) {
					wcpImgsKnowdialog.remove();
				}
			}
		});
		$('.wcpMultiUploadButtonBox .insertEditor').click(function(){
			$('.uploadedFileUnit').each(function(i,obj){
				//获得id
				var fileUrl=$(obj).children("input").val();
				//拼装img
				var tag='<img src="'+fileUrl+'" alt="" />';
				//插入img
				wcp_imgs_editor.insertHtml(tag);
			});
			wcpImgsKnowdialog.remove();
		});
		$('.wcpMultiUploadButtonBox #fileupload').fileupload({
			url : "upload/general.do?type=IMMED",
			dataType : 'json',
			done : function(e, data) {
				var url=data.result.url;
				var error=data.result.STATE;
				var message=data.result.MESSAGE;
				var id=data.result.id;
				var fileName=data.result.fileName;
				//alert("url:"+url);
				if(error!=0){
					wcpImgsKnowdialog.remove();
					alert("错误信息:"+message);
					return;
				}
				addFileNodeByWcpImg(url, decodeURIComponent(fileName), id,url);
			},
			progressall : function(e, data) {
				var progress = parseInt(data.loaded / data.total * 100, 10);
				$('#html5uploadProsess').text(progress + '%');
			}
		});
	});
});
function removeFile(fileId) {
	$("#file_" + fileId).remove();
} 
function addFileNodeByWcpImg(imgUrl, fileName, fileId,url) {
	var html = '<div class="col-md-12 file-block-box uploadedFileUnit" id="file_'+fileId+'">';
	html = html + '		<div class="stream-item" >';
	html = html + '				<div class="media">';
	html = html + '					<div class="pull-left">';
	html = html + '						<img  alt="'+fileName+'" src="'+imgUrl+'" style="width:64px;height:64px;">';
	html = html + '					</div>';
	html = html + '					<div class="media-body" >';
	html = html + '							<i onclick="removeFile(\'' + fileId
			+ '\');" class="glyphicon glyphicon-remove pull-right" ></i>';
	html = html + '						<div class="file-title" >' + fileName + '</div>';
	html = html + '					</div>';
	html = html + '				</div>';
	html = html + '		</div>';
	html = html
			+ '<input type="hidden" name="fileUrl" value="'+url+'" /></div>';
	$('#wcpimgFileListId').append(html);
}
KindEditor.lang({
	wcpimgs : '批量上传图片'
});
//kindeditor中粘贴图片直接上传到后台
//edit 在 kindeditor的事件回调中可以通过 this.edit来获取pasteImgHandle(this.edit);
function pasteImgHandle(editor,uploadUrl) {
	var doc = editor.edit.doc;
	var cmd = editor.edit.cmd;
	$(doc.body).bind('paste', function(ev) {
		var $this = $(this);
		var dataItem = ev.originalEvent.clipboardData.items[0];
		if (dataItem) {
			var file = dataItem.getAsFile();
			if (file) {
				var reader = new FileReader();
				reader.onload = function(evt) {
					var imageDataBase64 = evt.target.result;
					$.post(uploadUrl, {
						'base64' : imageDataBase64,
						'filename':'粘贴图片.png'
					}, function(json) {
						if(json.STATE==0){
							//var html = '<img src="' + json.url + '" alt="'+json.fileName+'" />';
							//cmd.inserthtml(html);
							editor.exec('insertimage', json.url, "粘贴图片", undefined, undefined, undefined, undefined);  
						}else{
							cmd.inserthtml(json.message);
						}
					},'json');
				};
				var event = ev || window.event;//兼容IE
				//取消事件相关的默认行为
				if (event.preventDefault) //标准技术
				{
					event.preventDefault();
				}
				if (event.returnValue) //兼容IE9之前的IE
				{
					event.returnValue = false;
				}
				reader.readAsDataURL(file);
			}
		}
	});
}