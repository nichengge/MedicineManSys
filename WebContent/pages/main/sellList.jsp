<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/others/easyui/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/others/easyui/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/others/easyui/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/others/easyui/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/others/easyui/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<title>订单管理</title>

<script type="text/javascript">

	var url;

	function searchMedicine(){
		$("#dg").datagrid('load',{
			"cName":$("#s_trueName").val()
		});
	}

	function refresh(){
		$("#dg").datagrid("load",{//使用load方法
			   data: "cName"//传到后台的变量
			});
		}
	
	function payMoney(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","<font size='3' color='green'>请选择需要结账的订单！<font>");
			return;
		}
		var totalMoney = 0;
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			totalMoney = totalMoney + selectedRows[i].totalMoney;
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm('确认','总计共需支付'+totalMoney+'元，是否支付？',function(r){
		    if (r){
		        $.post("${pageContext.request.contextPath}/sellOrder/pay.html",{ids:ids},function(result){
		        	var result=eval('('+result+')');
					if(result.success){
						$("#dg").datagrid("reload");
	 					$.messager.show({
							title:'系统提示',
							msg:"<font size='3' color='green'>支付成功！</font>",
							timeout:2000,
							showType:'slide',
							width:300,
							height:150,
							style:{
								top:'',
								left:'',
								right:0,
								bottom:document.body.scrollTop+document.documentElement.scrollTop
							}
						});
					}else{
	 					$.messager.show({
							title:'系统提示',
							msg:"<font size='3' color='green'>支付失败！</font>",
							timeout:2000,
							showType:'slide',
							width:300,
							height:150,
							style:{
								top:'',
								left:'',
								right:0,
								bottom:document.body.scrollTop+document.documentElement.scrollTop
							}
						});
						return;
					}
		        });
		    }
		});
	}



	function openMedicineBugDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","<font size='3' color='green'>请选择需要出售的药品！<font>");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","完善销售订单信息");
		$("#cName").val(row.cName);
		$("#eName").val(row.eName);
		$("#price").val(row.price);
		$("#nums").val(row.nums);
		url="${pageContext.request.contextPath }/sellOrder/add.html?userId=${currentUser.id}&id="+row.id;
	}

	function sellMedicine(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				var nums = $("#nums").val();
				var buyNums = $("#buyNums").val();
				if(buyNums>nums){
					$.messager.alert("系统提示","<font size='3' color='green'>库存药品数量不够！<font>");
					return false;
				}
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
 					$.messager.show({
						title:'系统提示',
						msg:"<font size='3' color='green'>订单生成成功，请前往订单管理界面完成结账！</font>",
						timeout:2000,
						showType:'slide',
						width:300,
						height:150,
						style:{
							top:'',
							left:'',
							right:0,
							bottom:document.body.scrollTop+document.documentElement.scrollTop
						}
					});
				}else{
 					$.messager.show({
						title:'系统提示',
						msg:"<font size='3' color='green'>订单生成失败</font>",
						timeout:2000,
						showType:'slide',
						width:300,
						height:150,
						style:{
							top:'',
							left:'',
							right:0,
							bottom:document.body.scrollTop+document.documentElement.scrollTop
						}
					});
					return;
				}
			}
		});
	}

	function deleteSellOrder(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","<font size='3' color='green'>请选择要删除的订单！<font>");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删除这<font color=red>"+selectedRows.length+"</font>条订单吗？",function(r){
			if(r){
				$.post("${pageContext.request.contextPath }/sellOrder/delete.html",{ids:ids},function(result){
					if(result.success){
						$("#dg").datagrid("reload");
	 					$.messager.show({
							title:'系统提示',
							msg:"<font size='3' color='green'>该订单已删除成功！</font>",
							timeout:2000,
							showType:'slide',
							width:300,
							height:150,
							style:{
								top:'',
								left:'',
								right:0,
								bottom:document.body.scrollTop+document.documentElement.scrollTop
							}
						});
					}else{
	 					$.messager.show({
							title:'系统提示',
							msg:"<font size='3' color='green'>删除该订单失败</font>",
							timeout:2000,
							showType:'slide',
							width:300,
							height:150,
							style:{
								top:'',
								left:'',
								right:0,
								bottom:document.body.scrollTop+document.documentElement.scrollTop
							}
						});
					}
				},"json");
			}
		});
	}

	function openModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","<font size='3' color='green'>请选择需要编辑的订单！<font>");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑订单信息");
		$("#cName").val(row.cName);
		$("#eName").val(row.eName);
		$("#price").val(row.price);
		$("#buyNums").val(row.buyNums);
		url="${pageContext.request.contextPath }/sellOrder/update.html?id="+row.id;
	}

	function modify(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
			success:function(result){
				var result=eval('('+result+')');
				if(result.success){
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
 					$.messager.show({
						title:'系统提示',
						msg:"<font size='3' color='green'>订单信息修改成功！</font>",
						timeout:2000,
						showType:'slide',
						width:300,
						height:150,
						style:{
							top:'',
							left:'',
							right:0,
							bottom:document.body.scrollTop+document.documentElement.scrollTop
						}
					});
				}else{
 					$.messager.show({
						title:'系统提示',
						msg:"<font size='3' color='green'>订单信息修改失败！</font>",
						timeout:2000,
						showType:'slide',
						width:300,
						height:150,
						style:{
							top:'',
							left:'',
							right:0,
							bottom:document.body.scrollTop+document.documentElement.scrollTop
						}
					});
					return;
				}
			}
		});
	}

	function closeSellMedicineDialog(){
		$("#dlg").dialog("close");
		$("#buyNums").val("");
	}
</script>

</head>
<body style="margin:1px;">
	<table id="dg" title="订单管理" class="easyui-datagrid"
	 fitColumns="true" pagination="true" rownumbers="true"
	 url="${pageContext.request.contextPath }/sellOrder/Nlist.html" fit="true" toolbar="#tb">
	 <thead>
	 	<tr>
	 		<th field="cb" checkbox="true" align="center"></th>
	 		<th field="id" width="30" align="center">编号</th>
	 		<th field="cName" width="120" align="center">中文名称</th>
	 		<th field="price" width="30" align="center">价格</th>
	 		<th field="buyNums" width="30" align="center">购买数量</th>
	 		<th field="totalMoney" width="30" align="center">总金额</th>
	 		<th field="createDate" width="80" align="center">下单日期</th>
	 		<th field="user.trueName" width="50" align="center">售药者</th>
	 	</tr>
	 </thead>
	</table>
	<div id="tb" style="padding-bottom:5px; padding-top:5px; ">
		<div style="width:300px;">
			<a href="javascript:payMoney()" class="easyui-linkbutton" iconCls="icon-add" plain="true">结账</a>
			<a href="javascript:deleteSellOrder()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
			<a href="javascript:openModifyDialog()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">修改</a>
		</div>
		<div style="padding-left:176px;margin-top:-25px;">
			<input type="text" id="s_trueName" style="width:220px;" size="20" onkeydown="if(event.keyCode==13) searchMedicine()" placeholder="请输入您要查找的订单的中文药名"/>
		<a href="javascript:searchMedicine()" class="easyui-linkbutton" iconCls="icon-search" plain="true" style="outline: none">搜索</a>
		</div>
		
		<div style="margin-left:465px;margin-top:-25px;"><a id="refreshbtn" href="javascript:refresh()" class="easyui-linkbutton"
				iconCls="icon-reload" plain="true">刷新</a>
				</div>
		
	</div>

	<div id="dlg" class="easyui-dialog" style="width:370px;height:200px;padding:10px 20px"
	  closed="true" buttons="#dlg-buttons">
	 	<form id="fm" method="post">
	 		<table cellspacing="8px">
	 			<tr>
	 				<td>中文名称：</td>
	 				<td><input type="text" id="cName" style="width:200px;" name="cName" class="easyui-validatebox" readonly="readonly"/></td>
	 			</tr>
	 			<tr>
	 				<td>药品单价：</td>
	 				<td><input type="text" id="price" style="width:200px;" name="price" class="easyui-validatebox" readonly="readonly"/></td>
	 			</tr>
	 			<tr>
	 				<td>购买数量：</td>
	 				<td><input type="text" id="buyNums" name="buyNums" style="width:200px;" name="nums" class="easyui-validatebox" required="true"/></td>
	 			</tr>
	 		</table>
	 	</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:modify()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeSellMedicineDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>
