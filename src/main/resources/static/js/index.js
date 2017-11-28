$(function () {
    /**蔬菜页面*/
    var vegeEditing;//判断蔬菜是否处于编辑状态；
    var vegeFlag;//判断蔬菜新增和修改
    $('#vegeId').datagrid({
        title: '蔬菜列表',
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
                field: 'unitId',
                title: '蔬菜单位',
                width: 100,
                align: 'center',
                hidden: true,
                editor: {
                    type: 'combobox',
                    options: {
                        url: '/market/v1/get/unit',
                        method: 'get',
                        loadFilter: function (data) {
                            if (data.status == 0) {
                                return data.data;
                            }
                        },
                        valueField: 'id',
                        textField: 'unitName',
                        required: true,
                        editable: false,
                        missingMessage: '蔬菜单位必填'
                    }
                }
            },
            {
                field: 'unitName',
                title: '蔬菜单位',
                width: 100,
                align: 'center',
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
                        $('#vegeId').datagrid('hideColumn', 'unitName');
                        $('#vegeId').datagrid('showColumn', 'unitId');
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
                            $('#vegeId').datagrid('hideColumn', 'unitName');
                            $('#vegeId').datagrid('showColumn', 'unitId');
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
                        $('#vegeId').datagrid('showColumn', 'unitName');
                        $('#vegeId').datagrid('hideColumn', 'unitId');
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

    /**客户页面*/
    var customerEditing;//判断客户是否处于编辑状态
    var customerFlag;//判断客户新增和修改
    $('#customerId').datagrid({
        title: '客户列表',
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
                        validType: 'phone'
                    }
                }
            },
            {
                field: 'type',
                title: '客户类型',
                width: 100,
                hidden:true,
                align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        url: '/market/v1/get/type',
                        method: 'get',
                        loadFilter: function (data) {
                            if (data.status == 0) {
                                return data.data;
                            }
                        },
                        valueField: 'id',
                        textField: 'type',
                        required: true,
                        editable: false,
                        missingMessage: '客户类型必填'
                    }
                }
            },
            {
                field: 'typeName',
                title: '客户类型',
                width: 100,
                align: 'center',
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
                        $('#customerId').datagrid('hideColumn', 'typeName');
                        $('#customerId').datagrid('showColumn', 'type');
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
                            $('#customerId').datagrid('hideColumn', 'typeName');
                            $('#customerId').datagrid('showColumn', 'type');
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
                        $('#customerId').datagrid('showColumn', 'typeName');
                        $('#customerId').datagrid('hideColumn', 'type');
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
    $('#customerToolId').appendTo('.datagrid-toolbar');
    //客户查询
    $('#customerSearchBtn').click(function () {
        var typeId = $('#typeSelectId').val();
        $("#typeSelectId").combobox('clear');
        $('#customerId').datagrid('load', {
            customerName: $('#customernameid').val(),
            customerAddr: $('#customerAddrId').val(),
            phoneNum: $('#phoneNumId').val(),
            type: typeId
        });
    });


    /** 类型页面引用*/
    var typeEditing;//判断类型是否处于编辑状态；
    var typeFlag;//判断类型新增和修改
    $("#typeDiscountId").datagrid({
        title: "类型页面引用",
        fitColumns: true,
        autoRowHeight: false,
        toolbar: '#typeToolId',
        striped: true,
        pagination: false,
        url: '/market/v1/get/type',
        method: 'get',
        loadMsg: '数据努力加载中...',
        rownumbers: true,
        frozenColumns: [[
            {
                field: 'ck',
                checkbox: true
            }
        ]],
        idFiled: 'id',
        loadFilter: function (data) {
            if (data.status == 0) {
                return data.data;
            }
        },
        columns: [[
            {
                field: 'id',
                title: '主键',
                width: 100,
                hidden: true,
                align: 'center',

            }, {
                field: 'type',
                title: '类型',
                width: 100,
                align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true,
                        validType: 'letter'
                    }
                }
            }, {
                field: 'discount',
                title: '折扣',
                width: 100,
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: true,
                        missingMessage: '折扣必填最小为0最大为9.9',
                        min: 0,
                        max: 9.9,
                        precision: 1
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
        onAfterEdit: function (index, record) {
            if (typeFlag == 'add') {
                $.ajax({
                    url: '/market/v1/add/type',
                    type: 'post',
                    data: record,
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        $('#typeDiscountId').datagrid('reload');
                    }
                })
            } else {
                $.ajax({
                    url: '/market/v1/edit/type',
                    type: 'put',
                    data: record,
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        if (data.status != 0) {
                            $('#typeDiscountId').datagrid('reload');
                        }
                    }
                })
            }
        }
    })

    //新增类型
    $("#addTypeId").click(function () {
        if (typeEditing == undefined) {
            typeFlag = 'add';
            //1.取消所有的选中
            $('#typeDiscountId').datagrid('unselectAll')
            //1.追加一行
            $('#typeDiscountId').datagrid('appendRow', {createTime: new Date().Format("yyyy-MM-dd hh:mm:ss")});
            //2.得到当前列号
            typeEditing = $('#typeDiscountId').datagrid('getRows').length - 1;
            //3.开启编辑状态
            $('#typeDiscountId').datagrid('beginEdit', typeEditing);
        }
    });
    //修改类型
    $("#editTypeId").click(function () {
        var arr = $('#typeDiscountId').datagrid('getSelections');
        if (arr.length != 1) {
            $.messager.show({
                title: '提示信息',
                msg: '只能选择一条记录进行修改',
                timeout: 400
            });
        } else {
            if (typeEditing == undefined) {
                typeFlag = 'edit';
                //根据行记录对象，得到该行索引位置
                typeEditing = $('#typeDiscountId').datagrid('getRowIndex', arr[0]);
                //开启编辑状态
                $('#typeDiscountId').datagrid('beginEdit', typeEditing)
            }
        }
    });
    //删除类型
    $('#removeTypeId').click(function () {
        var arr = $('#typeDiscountId').datagrid('getSelections');
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
                        ids = ids.substring(0, ids.length - 1);
                        $.ajax({
                            url: "/market/v1/delete/type",
                            data: {"ids": ids, _method: 'DELETE'},
                            type: "post",
                            dataType: "json",
                            success: function (data) {
                                $.messager.show({
                                    title: '提示信息',
                                    msg: data.message,
                                    timeout: 400
                                });
                                $("#typeDiscountId").datagrid("clearSelections");
                                $('#typeDiscountId').datagrid('reload');
                            }
                        });
                    } else {
                        return;
                    }
                }
            });
        }
    })
    //保存类型
    $('#saveId').click(function () {
        //保存前进行数据验证，结束编辑，释放编辑状态
        if ($('#typeDiscountId').datagrid('validateRow', typeEditing)) {
            $('#typeDiscountId').datagrid('endEdit', typeEditing);
            typeEditing = undefined;
        }
    })
    //取消
    $('#cancelId').click(function () {
        $('#typeDiscountId').datagrid('reload');
    })

    //加载类型下拉框
    $('#typeSelectId').combobox({
        url: '/market/v1/get/type',
        method: 'get',
        loadFilter: function (data) {
            if (data.status == 0) {
                return data.data;
            }
        },
        valueField: 'id',
        textField: 'type'
    })


    /**
     *客户订单
     */
    var customerOrderEditing;
    ;//判断客户订单是否处于编辑状态；
    var customerOrderFlag;//判断客户订单新增和修改
    $('#customerOrderId').datagrid({
        title: '客户订单列表',
        fitColumns: true,
        striped: true,
        loadMsg: '数据加载中',
        url: '/market/v1/get/customerOrder',
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
                field: 'custmerId',
                title: '客户名',
                width: 100,
                align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        url: '/market/v1/customer/list',
                        loadFilter: function (data) {
                            if (data.status == 0) {
                                var datas = data.data
                                return datas;
                            }
                        },
                        valueField: 'id',
                        textField: 'customerName',
                        mode: 'remote',
                        method: 'get',
                        delay: '1000',
                        required: true,
                        missingMessage: '客户名称必填'
                    }
                }
            },
            {
                field: 'vegeId',
                title: '菜名',
                width: 100,
                align: 'center',
                editor: {
                    type: 'combobox',
                    options: {
                        url: '/market/v1/find/vegeList',
                        loadFilter: function (data) {
                            if (data.status == 0) {
                                var datas = data.data
                                return datas;
                            }
                        },
                        valueField: 'id',
                        textField: 'vegeName',
                        mode: 'remote',
                        method: 'get',
                        delay: '1000',
                        multiple: true,
                        required: true,
                        missingMessage: '菜名必填'
                    }
                }
            },
            {
                field: 'sendDate',
                title: '送货时间',
                width: 100,
                align: 'center',
                editor: {
                    type: 'datetimebox',
                    options: {
                        required: true,
                        missingMessage: '送货时间必填',
                        editable: false
                    }
                }
            },
            {
                field: 'price',
                title: '出售价格',
                width: 100,
                align: 'center'
            },
            {
                field: 'count',
                title: '数量',
                width: 100,
                align: 'center',
                editor: {
                    type: 'numberbox',
                    options: {
                        required: false,
                        min: 0,
                        max: 99999,
                        precision: 2
                    }
                }
            },
            {
                field: 'totlePrice',
                title: '单品总价格',
                width: 100,
                align: 'center',
            },
            {
                field: 'profit',
                title: '利润',
                width: 100,
                align: 'center',
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
                text: '新增',
                iconCls: 'icon-add',
                handler: function () {
                    if (vegeEditing == undefined) {
                        customerOrderFlag = 'add';
                        //1.取消所有的选中
                        $('#customerOrderId').datagrid('unselectAll')
                        //1.追加一行
                        $('#customerOrderId').datagrid('appendRow', {createTime: new Date().Format("yyyy-MM-dd hh:mm:ss")});
                        //2.得到当前列号
                        customerOrderEditing = $('#customerOrderId').datagrid('getRows').length - 1;
                        //3.开启编辑状态
                        $('#customerOrderId').datagrid('beginEdit', customerOrderEditing);
                    }
                }
            },
            {
                text: '修改',
                iconCls: 'icon-edit',
                handler: function () {
                    var arr = $('#customerOrderId').datagrid('getSelections');
                    console.log(arr)
                    if (arr.length != 1) {
                        $.messager.show({
                            title: '提示信息',
                            msg: '只能选择一条记录进行修改',
                            timeout: 200
                        });
                    } else {
                        if (customerOrderEditing == undefined) {
                            customerOrderFlag = 'edit';
                            //根据行记录对象，得到该行索引位置
                            customerOrderEditing = $('#customerOrderId').datagrid('getRowIndex', arr[0]);
                            //开启编辑状态
                            $('#customerOrderId').datagrid('beginEdit', customerOrderEditing)
                        }
                    }

                }
            },
            {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    var arr = $('#customerOrderId').datagrid('getSelections');
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
                                        url: '/market/v1/delete/customerOrder',
                                        data: {"ids": ids, _method: 'DELETE'},
                                        type: 'post',
                                        dataType: "json",
                                        success: function (data) {
                                            $.messager.show({
                                                title: '提示信息',
                                                msg: data.message,
                                                timeout: 400
                                            });
                                            $("#customerOrderId").datagrid("clearSelections");
                                            $('#customerOrderId').datagrid('reload');
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
                    if ($('#customerOrderId').datagrid('validateRow', customerOrderEditing)) {
                        $('#customerOrderId').datagrid('endEdit', customerOrderEditing);
                        customerOrderEditing = undefined;
                    }
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#customerOrderId').datagrid('reload');
                    customerOrderEditing = undefined;
                }
            }
        ],
        onDblClickCell: function (rowIndex, field, value) {
            debugger;
            if (customerOrderEditing != undefined) {
                $('#customerOrderId').datagrid('endEdit', customerOrderEditing);
                customerOrderEditing = undefined;
            }

            customerOrderFlag = 'edit';
            //根据行记录对象，得到该行索引位置
            customerOrderEditing = rowIndex;
            //开启编辑状态
            $('#customerOrderId').datagrid('beginEdit', customerOrderEditing)


        },
        onAfterEdit: function (index, record) {
            if (customerOrderFlag == 'add') {
                $.ajax({
                    url: '/market/v1/add/customerOrder',
                    data: record,
                    type: 'post',
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        $('#customerOrderId').datagrid('reload');
                    }
                })
            } else if (customerOrderFlag == 'edit') {
                $.ajax({
                    url: '/market/v1/edit/customerOrder',
                    data: record,
                    type: 'put',
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        // $('#customerOrderId').datagrid('reload');
                    }
                })
            }

        }
    });
    $('#customerOrderToolId').appendTo('.datagrid-toolbar');
    $('#customerOrderSearchBtn').click(function () {
        var startTime = $('#startTimeId').val()
        var endTime = $('#endTimeId').val()
        $('#startTimeId').datetimebox('clear');
        $('#endTimeId').datetimebox('clear');
        $('#customerOrderId').datagrid('load', {
            customerName: $('#customerOrderNameId').val(),
            vegeName: $('#customerOrderVegeId').val(),
            startTime: startTime,
            endTime: endTime
        });
    })


    /** 单位页面引用*/
    var unitEditing;//判断单位是否处于编辑状态；
    var unitFlag;//判断单位新增和修改
    $("#unitId").datagrid({
        title: "类型页面引用",
        fitColumns: true,
        autoRowHeight: false,
        striped: true,
        pagination: false,
        url: '/market/v1/get/unit',
        method: 'get',
        loadMsg: '数据努力加载中...',
        rownumbers: true,
        frozenColumns: [[
            {
                field: 'ck',
                checkbox: true
            }
        ]],
        idFiled: 'id',
        loadFilter: function (data) {
            if (data.status == 0) {
                return data.data;
            }
        },
        columns: [[
            {
                field: 'id',
                title: '主键',
                width: 100,
                hidden: true,
                align: 'center',

            }, {
                field: 'unitName',
                title: '单位',
                width: 100,
                align: 'center',
                editor: {
                    type: 'validatebox',
                    options: {
                        required: true
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
        toolbar: [
            {
                text: '新增',
                iconCls: 'icon-add',
                handler: function () {
                    if (unitEditing == undefined) {
                        unitFlag = 'add';
                        //1.取消所有的选中
                        $('#unitId').datagrid('unselectAll')
                        //1.追加一行
                        $('#unitId').datagrid('appendRow', {createTime: new Date().Format("yyyy-MM-dd hh:mm:ss")});
                        //2.得到当前列号
                        unitEditing = $('#unitId').datagrid('getRows').length - 1;
                        //3.开启编辑状态
                        $('#unitId').datagrid('beginEdit', unitEditing);
                    }
                }
            },
            {
                text: '修改',
                iconCls: 'icon-edit',
                handler: function () {
                    var arr = $('#unitId').datagrid('getSelections');
                    if (arr.length != 1) {
                        $.messager.show({
                            title: '提示信息',
                            msg: '只能选择一条记录进行修改',
                            timeout: 200
                        });
                    } else {
                        if (unitEditing == undefined) {
                            unitFlag = 'edit';
                            //根据行记录对象，得到该行索引位置
                            unitEditing = $('#unitId').datagrid('getRowIndex', arr[0]);
                            //开启编辑状态
                            $('#unitId').datagrid('beginEdit', unitEditing)
                        }
                    }

                }
            },
            {
                text: '删除',
                iconCls: 'icon-remove',
                handler: function () {
                    var arr = $('#unitId').datagrid('getSelections');
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
                                        url: '/market/v1/delete/unit',
                                        data: {"ids": ids, _method: 'DELETE'},
                                        type: 'post',
                                        dataType: "json",
                                        success: function (data) {
                                            $.messager.show({
                                                title: '提示信息',
                                                msg: data.message,
                                                timeout: 400
                                            });
                                            $("#unitId").datagrid("clearSelections");
                                            $('#unitId').datagrid('reload');
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
                    if ($('#unitId').datagrid('validateRow', unitEditing)) {
                        $('#unitId').datagrid('endEdit', unitEditing);
                        unitEditing = undefined;
                    }
                }
            },
            {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    if (unitFlag != "add") {
                        $('#unitId').datagrid('rejectChanges');
                    } else {
                        $('#unitId').datagrid('reload');
                    }
                    unitEditing = undefined;

                }
            }
        ],
        onAfterEdit: function (index, record) {
            if (unitFlag == 'add') {
                $.ajax({
                    url: '/market/v1/add/unit',
                    type: 'post',
                    data: record,
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        $('#unitId').datagrid('reload');
                    }
                })
            } else {
                $.ajax({
                    url: '/market/v1/edit/unit',
                    type: 'put',
                    data: record,
                    dataType: 'json',
                    success: function (data) {
                        $.messager.show({
                            title: '提示信息',
                            msg: data.message,
                            timeout: 400
                        });
                        if (data.status != 0) {
                            $('#unitId').datagrid('reload');
                        }
                    }
                })
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


/**validateBox扩展*/
$.extend($.fn.validatebox.defaults.rules, {

    // 字符最大长度（param传参）
    maxLength: {

        validator: function (value, param) {

            return value.length <= param[0];

        },

        message: '您输入的字数太长了,最多{0}个字'

    },

    // 验证姓名，可以是中文或英文

    name: {

        validator: function (value) {

            return /^[\u0391-\uFFE5]{1,20}$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);

        },

        message: '姓名字数过多或应为中文或者英文'

    },

    // 验证身份证

    idcard: {

        validator: function (value) {

            return /^\d{15}(\d{2}[Xx0-9])?$/i.test(value);

        },

        message: '身份证应为15位或者18位'

    },

    // 验证IP地址
    ip: {
        validator: function (value) {
            return /\d+\.\d+\.\d+\.\d+/.test(value);
        },
        message: 'IP地址格式不正确'
    },

    //年龄验证
    age: {

        validator: function (value) {

            return /^[0-9]{1,2}$/i.test(value);//0-99

        },

        message: '您输入的年龄不合法'

    },

    // 验证电话号码
    phone: {

        validator: function (value) {

            return /^1\d{10}$/i.test(value) || /^0\d{2,3}-?\d{7,8}$/i.test(value);

        },

        message: '电话号码正确格式:15288888888或020(0310)-88888888'

    },

    // 验证数字,整数或小数
    number: {

        validator: function (value) {

            return /^\d{1,10}(\.\d{0,4})?$/i.test(value);

        },

        message: '请输入正确的金额'

    },

    // 验证数字,只能为整数
    integer: {

        validator: function (value) {

            return /^\d{1,12}$/i.test(value);

        },

        message: '请输入一个整数'

    },


    // 时间验证
    //@author ren
    /* start */
    endToStart: {

        validator: function (value, param) {

            return value > $("#" + param[0] + " input[name='" + param[1] + "']").val();//结束时间>开始时间

        },

        message: '结束时间应晚于起始时间'

    },

    startToEnd: {

        validator: function (value, param) {

            return value > $("#" + param[0]).datetimebox('getValue');//结束时间>开始时间

        },

        message: '结束时间应晚于起始时间'

    },
    letter: {
        validator: function (value, param) {
            return /^[A-Z]{1}$/.test(value)
        },
        message: "只能输入单个大写字母"
    }
    /* end */
});

/**为dataGrid配置dataTimeBox*/
$.extend($.fn.datagrid.defaults.editors, {
    datatimebox: {
        init: function (container, options) {
            var input = $('<input/>').appendTo(container);
            options.editable = false;
            input.datetimebox(options);
            return input;
        },
        getValue: function (target) {
            return$(target).datetimebox("getValue");
        },
        setValue: function (target, value) {
            $(target).datetimebox("setValue", value);
        },
        resize: function (target, width) {
            $(target).datetimebox("resize", width);
        },
        destroy: function (target) {
            $(target).datetimebox("destroy");
        }
    }
});