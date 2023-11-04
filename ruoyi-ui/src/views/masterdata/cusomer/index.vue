<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="客户编号" prop="code">
        <el-input
          v-model="queryParams.code"
          placeholder="请输入客户编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="公司名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入公司名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="版本号" prop="version">
        <el-input
          v-model="queryParams.version"
          placeholder="请输入版本号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['masterdata:customer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['masterdata:customer:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['masterdata:customer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['masterdata:customer:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="customerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增序列" align="center" prop="id" />
      <el-table-column label="客户编号" align="center" prop="code" />
      <el-table-column label="公司名称" align="center" prop="name" />
      <el-table-column label="注册城市" align="center" prop="registerLocation" />
      <el-table-column label="地址" align="center" prop="location" />
      <el-table-column label="邮编" align="center" prop="postCode" />
      <el-table-column label="企业法人" align="center" prop="legalPerson" />
      <el-table-column label="注册资金" align="center" prop="registeredCapital" />
      <el-table-column label="传真号码" align="center" prop="fax" />
      <el-table-column label="公司网址" align="center" prop="website" />
      <el-table-column label="联系人姓名" align="center" prop="contactsName" />
      <el-table-column label="联系人手机" align="center" prop="contactsPhone" />
      <el-table-column label="联系人电子邮箱" align="center" prop="contactsEmail" />
      <el-table-column label="办公地点" align="center" prop="contactsOfficeLocation" />
      <el-table-column label="开户行" align="center" prop="bankName" />
      <el-table-column label="账号" align="center" prop="bankAccount" />
      <el-table-column label="税号" align="center" prop="taxNumber" />
      <el-table-column label="发票地址" align="center" prop="invoiceAddress" />
      <el-table-column label="发票类型" align="center" prop="invoiceType" />
      <el-table-column label="收件人姓名" align="center" prop="recipientName" />
      <el-table-column label="收件人电话" align="center" prop="recipientPhone" />
      <el-table-column label="收件人地址" align="center" prop="recipientAddress" />
      <el-table-column label="版本号" align="center" prop="version" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['masterdata:customer:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['masterdata:customer:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改客户主数据对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="客户编号" prop="code">
          <el-input v-model="form.code" placeholder="请输入客户编号" />
        </el-form-item>
        <el-form-item label="公司名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入公司名称" />
        </el-form-item>
        <el-form-item label="注册城市" prop="registerLocation">
          <el-input v-model="form.registerLocation" placeholder="请输入注册城市" />
        </el-form-item>
        <el-form-item label="地址" prop="location">
          <el-input v-model="form.location" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="邮编" prop="postCode">
          <el-input v-model="form.postCode" placeholder="请输入邮编" />
        </el-form-item>
        <el-form-item label="企业法人" prop="legalPerson">
          <el-input v-model="form.legalPerson" placeholder="请输入企业法人" />
        </el-form-item>
        <el-form-item label="注册资金" prop="registeredCapital">
          <el-input v-model="form.registeredCapital" placeholder="请输入注册资金" />
        </el-form-item>
        <el-form-item label="传真号码" prop="fax">
          <el-input v-model="form.fax" placeholder="请输入传真号码" />
        </el-form-item>
        <el-form-item label="公司网址" prop="website">
          <el-input v-model="form.website" placeholder="请输入公司网址" />
        </el-form-item>
        <el-form-item label="联系人姓名" prop="contactsName">
          <el-input v-model="form.contactsName" placeholder="请输入联系人姓名" />
        </el-form-item>
        <el-form-item label="联系人手机" prop="contactsPhone">
          <el-input v-model="form.contactsPhone" placeholder="请输入联系人手机" />
        </el-form-item>
        <el-form-item label="联系人电子邮箱" prop="contactsEmail">
          <el-input v-model="form.contactsEmail" placeholder="请输入联系人电子邮箱" />
        </el-form-item>
        <el-form-item label="办公地点" prop="contactsOfficeLocation">
          <el-input v-model="form.contactsOfficeLocation" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="开户行" prop="bankName">
          <el-input v-model="form.bankName" placeholder="请输入开户行" />
        </el-form-item>
        <el-form-item label="账号" prop="bankAccount">
          <el-input v-model="form.bankAccount" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="税号" prop="taxNumber">
          <el-input v-model="form.taxNumber" placeholder="请输入税号" />
        </el-form-item>
        <el-form-item label="发票地址" prop="invoiceAddress">
          <el-input v-model="form.invoiceAddress" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="收件人姓名" prop="recipientName">
          <el-input v-model="form.recipientName" placeholder="请输入收件人姓名" />
        </el-form-item>
        <el-form-item label="收件人电话" prop="recipientPhone">
          <el-input v-model="form.recipientPhone" placeholder="请输入收件人电话" />
        </el-form-item>
        <el-form-item label="收件人地址" prop="recipientAddress">
          <el-input v-model="form.recipientAddress" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input v-model="form.version" placeholder="请输入版本号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCustomer, getCustomer, delCustomer, addCustomer, updateCustomer } from "@/api/masterdata/customer";

export default {
  name: "Customer",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 客户主数据表格数据
      customerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        code: null,
        name: null,
        version: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [
          { required: true, message: "客户编号不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "公司名称不能为空", trigger: "blur" }
        ],
        location: [
          { required: true, message: "地址不能为空", trigger: "blur" }
        ],
        legalPerson: [
          { required: true, message: "企业法人不能为空", trigger: "blur" }
        ],
        contactsName: [
          { required: true, message: "联系人姓名不能为空", trigger: "blur" }
        ],
        bankName: [
          { required: true, message: "开户行不能为空", trigger: "blur" }
        ],
        bankAccount: [
          { required: true, message: "账号不能为空", trigger: "blur" }
        ],
        taxNumber: [
          { required: true, message: "税号不能为空", trigger: "blur" }
        ],
        invoiceAddress: [
          { required: true, message: "发票地址不能为空", trigger: "blur" }
        ],
        invoiceType: [
          { required: true, message: "发票类型不能为空", trigger: "change" }
        ],
        createBy: [
          { required: true, message: "创建者不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        version: [
          { required: true, message: "版本号不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询客户主数据列表 */
    getList() {
      this.loading = true;
      listCustomer(this.queryParams).then(response => {
        this.customerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        name: null,
        registerLocation: null,
        location: null,
        postCode: null,
        legalPerson: null,
        registeredCapital: null,
        fax: null,
        website: null,
        contactsName: null,
        contactsPhone: null,
        contactsEmail: null,
        contactsOfficeLocation: null,
        bankName: null,
        bankAccount: null,
        taxNumber: null,
        invoiceAddress: null,
        invoiceType: null,
        recipientName: null,
        recipientPhone: null,
        recipientAddress: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        version: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加客户主数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getCustomer(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改客户主数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateCustomer(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCustomer(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除客户主数据编号为"' + ids + '"的数据项？').then(function() {
        return delCustomer(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('masterdata/customer/export', {
        ...this.queryParams
      }, `customer_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
