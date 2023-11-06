<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="运单编号" prop="shippingOrder">
        <el-input
          v-model="queryParams.shippingOrder"
          placeholder="请输入运单编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="车号" prop="tractorNumber">
        <el-input
          v-model="queryParams.tractorNumber"
          placeholder="请输入车号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="经办人" prop="handledBy">
        <el-input
          v-model="queryParams.handledBy"
          placeholder="请输入经办人"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="业务日期" prop="businessDate">
        <el-date-picker clearable
          v-model="queryParams.businessDate"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择业务日期">
        </el-date-picker>
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
          v-hasPermi="['tms:shippingInfo:add']"
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
          v-hasPermi="['tms:shippingInfo:edit']"
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
          v-hasPermi="['tms:shippingInfo:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['tms:shippingInfo:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="shippingInfoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增序列" align="center" prop="id" />
      <el-table-column label="运单编号" align="center" prop="shippingOrder" />
      <el-table-column label="类型" align="center" prop="shippingType" />
      <el-table-column label="批次号" align="center" prop="batchNumber" />
      <el-table-column label="车号" align="center" prop="tractorNumber" />
      <el-table-column label="发货仓库" align="center" prop="shippingFrom" />
      <el-table-column label="卸货仓库" align="center" prop="shippingTo" />
      <el-table-column label="重量" align="center" prop="weight" />
      <el-table-column label="经办人" align="center" prop="handledBy" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="物料编号" align="center" prop="materialCode" />
      <el-table-column label="业务日期" align="center" prop="businessDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.businessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="单价" align="center" prop="unitPrice" />
      <el-table-column label="卸货重量" align="center" prop="landingWeight" />
      <el-table-column label="核算重量" align="center" prop="effectiveWeight" />
      <el-table-column label="核算单价" align="center" prop="effectiveUnitPrice" />
      <el-table-column label="卸货日期" align="center" prop="landingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.landingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="运输单状态" align="center" prop="status" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['tms:shippingInfo:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tms:shippingInfo:remove']"
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

    <!-- 添加或修改运单详细信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="运单编号" prop="shippingOrder">
          <el-input v-model="form.shippingOrder" placeholder="请输入运单编号" />
        </el-form-item>
        <el-form-item label="批次号" prop="batchNumber">
          <el-input v-model="form.batchNumber" placeholder="请输入批次号" />
        </el-form-item>
        <el-form-item label="车号" prop="tractorNumber">
          <el-input v-model="form.tractorNumber" placeholder="请输入车号" />
        </el-form-item>
        <el-form-item label="发货仓库" prop="shippingFrom">
          <el-input v-model="form.shippingFrom" placeholder="请输入发货仓库" />
        </el-form-item>
        <el-form-item label="卸货仓库" prop="shippingTo">
          <el-input v-model="form.shippingTo" placeholder="请输入卸货仓库" />
        </el-form-item>
        <el-form-item label="重量" prop="weight">
          <el-input v-model="form.weight" placeholder="请输入重量" />
        </el-form-item>
        <el-form-item label="经办人" prop="handledBy">
          <el-input v-model="form.handledBy" placeholder="请输入经办人" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="物料编号" prop="materialCode">
          <el-input v-model="form.materialCode" placeholder="请输入物料编号" />
        </el-form-item>
        <el-form-item label="业务日期" prop="businessDate">
          <el-date-picker clearable
            v-model="form.businessDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择业务日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="单价" prop="unitPrice">
          <el-input v-model="form.unitPrice" placeholder="请输入单价" />
        </el-form-item>
        <el-form-item label="卸货重量" prop="landingWeight">
          <el-input v-model="form.landingWeight" placeholder="请输入卸货重量" />
        </el-form-item>
        <el-form-item label="核算重量" prop="effectiveWeight">
          <el-input v-model="form.effectiveWeight" placeholder="请输入核算重量" />
        </el-form-item>
        <el-form-item label="核算单价" prop="effectiveUnitPrice">
          <el-input v-model="form.effectiveUnitPrice" placeholder="请输入核算单价" />
        </el-form-item>
        <el-form-item label="卸货日期" prop="landingDate">
          <el-date-picker clearable
            v-model="form.landingDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择卸货日期">
          </el-date-picker>
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
import { listShippingInfo, getShippingInfo, delShippingInfo, addShippingInfo, updateShippingInfo } from "@/api/tms/shippingInfo";

export default {
  name: "ShippingInfo",
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
      // 运单详细信息表格数据
      shippingInfoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        shippingOrder: null,
        tractorNumber: null,
        handledBy: null,
        businessDate: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        shippingOrder: [
          { required: true, message: "运单编号不能为空", trigger: "blur" }
        ],
        shippingType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
        batchNumber: [
          { required: true, message: "批次号不能为空", trigger: "blur" }
        ],
        tractorNumber: [
          { required: true, message: "车号不能为空", trigger: "blur" }
        ],
        handledBy: [
          { required: true, message: "经办人不能为空", trigger: "blur" }
        ],
        phone: [
          { required: true, message: "联系电话不能为空", trigger: "blur" }
        ],
        materialCode: [
          { required: true, message: "物料编号不能为空", trigger: "blur" }
        ],
        businessDate: [
          { required: true, message: "业务日期不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "运输单状态不能为空", trigger: "change" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询运单详细信息列表 */
    getList() {
      this.loading = true;
      listShippingInfo(this.queryParams).then(response => {
        this.shippingInfoList = response.rows;
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
        shippingOrder: null,
        shippingType: null,
        batchNumber: null,
        tractorNumber: null,
        shippingFrom: null,
        shippingTo: null,
        weight: null,
        handledBy: null,
        phone: null,
        materialCode: null,
        businessDate: null,
        unitPrice: null,
        landingWeight: null,
        effectiveWeight: null,
        effectiveUnitPrice: null,
        landingDate: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.title = "添加运单详细信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getShippingInfo(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改运单详细信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateShippingInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addShippingInfo(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除运单详细信息编号为"' + ids + '"的数据项？').then(function() {
        return delShippingInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tms/shippingInfo/export', {
        ...this.queryParams
      }, `shippingInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
