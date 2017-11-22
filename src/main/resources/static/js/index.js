$(function () {
    var vegeEditing;//判断蔬菜是否处于编辑状态；
    var vegeFlag;//判断蔬菜新增和修改
    /**蔬菜页面*/
    $('#vegeId').datagrid({
        title: '蔬菜列表',
        fitColumns: true,
        striped: true,
        fitColumns: true,
        striped: true,
        loadMsg: '数据加载中',
        url: '/market/v1/vege/list',
        method: 'get',
        loadFilter: function (data) {
            if (data.status == 0) {
                var datas = {"total": data.data.totalElements, "rows": data.data.content}
                return datas;
            }
        },
        rownumbers: true,
        idField: 'id',
        // singleSelect: true,
        frozenColumns: [[
            {
                field: 'ck',
                checkbox: true
            }
        ]],
        columns: [[
            {
                field: 'id',
                title: '主键',
                width: 100,
                align: 'center',
                hidden: true
            },
            {
                field: 'vegeName',
                title: '蔬菜名',
                width: 100,
                align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '蔬菜名必填'
                    }
                }
            },
            {
                field: 'vegeCode',
                title: '蔬菜编码',
                width: 100,
                align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '蔬菜名必填'
                    }
                }
            },
            {
                field: 'price',
                title: '蔬菜价格',
                width: 100,
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '蔬菜价格必填',
                        min: 0,
                        max: 99999,
                        precision: 2
                    }
                }
            },
            {
                field: 'createTime',
                title: '创建时间',
                width: 100,
                align: 'center',
            }
        ]],
        pagination: true,
        pageSize: 10,
        pageNumber: 1,
        pageList: [5, 10, 15, 20, 25],
        toolbar: [
            {
                text: '新增蔬菜',
                iconCls: 'icon-add',
                handler: function () {
                    if (vegeEditing == undefined) {
                        vegeFlag = 'add';
                        //1.取消所有的选中
                        $('#vegeId').datagrid('unselectAll')
                        //1.追加一行
                        $('#vegeId').datagrid('appendRow', {createTime: new Date().Format("yyyy-MM-dd hh:mm:ss")});
                        //2.得到当前列号
                        vegeEditing = $('#vegeId').datagrid('getRows').length - 1;
                        //3.开启编辑状态
                        $('#vegeId').datagrid('beginEdit', vegeEditing);
                    }
                }
            },
            {
                text: '修改蔬菜',
                iconCls: 'icon-edit',
                handler: function () {
                    var arr = $('#vegeId').datagrid('getSelections');
                    console.log(arr)
                    if (arr.length != 1) {
                        $.messager.show({
                            title: '提示信息',
                            msg: '只能选择一条记录进行修改',
                            timeout: 200
                        });
                    } else {
                        if (vegeEditing == undefined) {
                            vegeFlag = 'edit';
                            //根据行记录对象，得到该行索引位置
                            vegeEditing = $('#vegeId').datagrid('getRowIndex', arr[0]);
                            //开启编辑状态
                            $('#vegeId').datagrid('beginEdit', vegeEditing)
                        }
                    }

                }
            },
            {
                text: '删除蔬菜',
                iconCls: 'icon-remove',
                handler: function () {
                    var arr = $('#vegeId').datagrid('getSelections');
                    if (arr.length <= 0) {
                        $.messager.show({
                            title: '提示信息',
                            msg: '请选择数据',
                            timeout: 200
                        });
                    } else {
                        $.messager.confirm({
                            width: 380,
                            height: 160,
                            title: '操作确认',
                            msg: '确定删除数据？',
                            ok: '是',
                            cancel: '否',
                            fn: function (r) {
                                if (r) {
                                    var ids = '';
                                    //var ids=new Array();
                                    for (var i = 0; i < arr.length; i++) {
                                        ids += arr[i].id + ",";
                                        //ids.push(arr[i].id);
                                    }
                                    ids = ids.substring(0, ids.length - 1);
                                    console.log(ids);
                                    $.ajax({
                                        url: '/market/v1/delete/vege',
                                        data: {"ids": ids, _method: 'DELETE'},
                                        type: 'post',
                                        dataType: "json",
                                        success: function (data) {
                                            $.messager.show({
                                                title: '提示信息',
                                                msg: data.message,
                                                timeout: 400
                                            });
                                            $("#vegeId").datagrid("clearSelections");
                                            $('#vegeId').datagrid('reload');
                                        }
                                    })

                                } else {
                                    return;
                                }
                            }
                        });
                    }

                }
            },
            {
                text: '保存',
                iconCls: 'icon-save',
                handler: function () {
                    //保存前进行数据验证，结束编辑，释放编辑状态
                    if ($('#vegeId').datagrid('validateRow', vegeEditing)) {
                        $('#vegeId').datagrid('endEdit', vegeEditing);
                        vegeEditing = undefined;
                    }
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    if (vegeFlag != "add") {
                        $('#vegeId').datagrid('rejectChanges');
                    } else {
                        $('#vegeId').datagrid('reload');
                    }
                    vegeEditing = undefined;

                }
            },
            {
                text: '菜名：<input type="text" id="vegeNameId"/>'
            },
            {
                text: '编码：<input type="text" id="vegeCodeId"/>'
            },
            {
                id: 'searchBtn',
                text: '查询',
                iconCls: 'icon-search',
                handler: function () {
                    $('#vegeId').datagrid('load', {
                        vegeName: $('#vegeNameId').val(),
                        vegeCode: $('#vegeCodeId').val()
                    })
                }
            }

        ],
        onAfterEdit: function (index, record) {
            if (vegeFlag == 'add') {
                $.ajax({
                    url: '/market/v1/add/vege',
                    data: record,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        $('#vegeId').datagrid('reload');
                    }
                })
            } else if (vegeFlag == 'edit') {
                $.ajax({
                    url: '/market/v1/edit/vege',
                    data: record,
                    type: 'put',
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        $('#vegeId').datagrid('reload');
                    }
                })
            }

        }
    });


    var customerEditing;//判断客户是否处于编辑状态
    var customerFlag;//判断客户新增和修改
    /**客户页面*/

    $('#customerId').datagrid({
        title: '客户列表',
        fitColumns: true,
        striped: true,
        fitColumns: true,
        striped: true,
        loadMsg: '数据加载中',
        rownumbers: true,
        method: "get",
        idField: 'id',
        url: "/market/v1/get/customer",
        loadFilter: function (data) {
            if (data.status == 0) {
                var datas = {"total": data.data.totalElements, "rows": data.data.content}
                return datas;
            }
        },
        // singleSelect: true,
        frozenColumns: [[
            {
                field: 'ck',
                checkbox: true
            }
        ]],
        columns: [[
            {
                field: 'id',
                title: '主键',
                width: 100,
                align: 'center',
                hidden: true
            },
            {
                field: 'customerName',
                title: '客户名称',
                width: 100,
                align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '客户名称'
                    }
                }
            },
            {
                field: 'customerAddr',
                title: '客户地址',
                width: 100,
                align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '客户地址'
                    }
                }
            },
            {
                field: 'phoneNum',
                title: '联系方式',
                width: 100,
                align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        missingMessage: '联系方式必填'
                    }
                }
            },
            {
                field: 'type',
                title: '客户类型',
                width: 100,
                align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        data: [{id: 'A', val: 'A'}, {id: 'B', val: 'B'}, {id: 'C', val: 'C'}, {id: 'D', val: 'D'}],
                        valueField: 'id',
                        textField: 'val',
                        required: true,
                        editable: false,
                        missingMessage: '客户类型必填'
                    }
                }
            },
            {
                field: 'createTime',
                title: '创建时间',
                width: 100,
                align: 'center',
            }
        ]],
        pagination: true,
        pageSize: 10,
        pageList: [5, 10, 15, 20, 25],
        toolbar: [
            {
                text: '新增客户',
                iconCls: 'icon-add',
                handler: function () {
                    if (customerEditing == undefined) {
                        customerFlag = 'add';
                        //1.取消所有的选中
                        $('#customerId').datagrid('unselectAll')
                        //1.追加一行
                        $('#customerId').datagrid('appendRow', {createTime: new Date().Format("yyyy-MM-dd hh:mm:ss")});
                        //2.得到当前列号
                        customerEditing = $('#customerId').datagrid('getRows').length - 1;
                        //3.开启编辑状态
                        $('#customerId').datagrid('beginEdit', customerEditing);
                    }
                }
            },
            {
                text: '修改客户',
                iconCls: 'icon-edit',
                handler: function () {
                    var arr = $('#customerId').datagrid('getSelections');
                    if (arr.length != 1) {
                        $.messager.show({
                            title: '提示信息',
                            msg: '只能选择一条记录进行修改',
                            timeout: 400
                        });
                    } else {
                        if (customerEditing == undefined) {
                            customerFlag = 'edit';
                            //根据行记录对象，得到该行索引位置
                            customerEditing = $('#customerId').datagrid('getRowIndex', arr[0]);
                            //开启编辑状态
                            $('#customerId').datagrid('beginEdit', customerEditing)
                        }
                    }

                }
            },
            {
                text: '删除客户',
                iconCls: 'icon-remove',
                handler: function () {
                    var arr = $('#customerId').datagrid('getSelections');
                    if (arr.length <= 0) {
                        $.messager.show({
                            title: '提示信息',
                            msg: '请选择数据',
                            timeout: 400
                        });
                    } else {
                        $.messager.confirm({
                            width: 380,
                            height: 160,
                            title: '操作确认',
                            msg: '确定删除数据？',
                            ok: '是',
                            cancel: '否',
                            fn: function (r) {
                                if (r) {
                                    var ids = "";
                                    for (var i = 0; i < arr.length; i++) {
                                        ids += arr[i].id + ",";
                                    }
                                    ids = ids.substring(0, ids.length - 1)
                                    $.ajax({
                                        url: "/market/v1/delete/customer",
                                        data: {"ids": ids, _method: 'DELETE'},
                                        type: "post",
                                        dataType: "json",
                                        success: function (data) {
                                            $.messager.show({
                                                title: '提示信息',
                                                msg: data.message,
                                                timeout: 400
                                            });
                                            $("#customerId").datagrid("clearSelections");
                                            $('#customerId').datagrid('reload');
                                        }
                                    });
                                } else {
                                    return;
                                }
                            }
                        });
                    }

                }
            },
            {
                text: '保存',
                iconCls: 'icon-save',
                handler: function () {
                    //保存前进行数据验证，结束编辑，释放编辑状态
                    if ($('#customerId').datagrid('validateRow', customerEditing)) {
                        $('#customerId').datagrid('endEdit', customerEditing);
                        customerEditing = undefined;
                    }
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    if (customerFlag != "add") {
                        $('#customerId').datagrid('rejectChanges');
                    } else {
                        $('#customerId').datagrid('reload');
                    }
                    customerEditing = undefined;

                }
            },
            {
                text: '客户名称：<input type="text" id="customerNameId"/>'
            },
            {
                text: '客户地址：<input type="text" id="customerAddrId"/>'
            },
            {
                text: '电话号码：<input type="text" id="phoneNumId"/>'
            },
            {
                text: '类型：<select id="typeId"><option></option><option value="A">A</option> <option value="B">B</option> <option value="C">C</option> <option value="D">D</option></select>'
            },
            {
                id: 'searchBtn',
                text: '查询',
                iconCls: 'icon-search',
                handler: function () {
                    $('#customerId').datagrid('load', {
                        customerName: $('#customerNameId').val(),
                        customerAddr: $('#customerAddrId').val(),
                        phoneNum: $('#phoneNumId').val(),
                        type: $('#typeId').val()
                    })
                }
            }
        ],
        onAfterEdit: function (index, record) {
            if (customerFlag == 'add') {
                $.ajax({
                    url: "/market/v1//add/customer",
                    data: record,
                    type: "POST",
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        $('#customerId').datagrid('reload');
                    }
                });

            } else if (customerFlag == 'edit') {
                $.ajax({
                    url: "/market/v1/edit/customer",
                    data: record,
                    type: "put",
                    dataType: "json",
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                    }
                });

            }

        }

    })
});

/**时间格式转化*/
Date.prototype.Format = function (fmt) { //author: meizz
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}