<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="订单编号" prop="orderNumber">
        <el-input v-model="queryParams.orderNumber" placeholder="请输入订单编号" clearable @keyup.enter.native="handleQuery"
          style="width:150px" />
      </el-form-item>
      <el-form-item label="合同编号" prop="contractId">
        <el-input v-model="queryParams.contractId" placeholder="请输入合同编号" clearable @keyup.enter.native="handleQuery"
          style="width:150px" />
      </el-form-item>
      <el-form-item label="经办人" prop="handledBy">
        <el-input v-model="queryParams.handledBy" placeholder="请输入经办人" clearable @keyup.enter.native="handleQuery"
          style="width:150px" />
      </el-form-item>
      <el-form-item label="物料编码" prop="materialCode">
        <el-input v-model="queryParams.materialCode" placeholder="请输入物料编码" clearable @keyup.enter.native="handleQuery"
          style="width:150px" />
      </el-form-item>
      <el-form-item label="供应商代码" prop="supplierCode">
        <el-input v-model="queryParams.supplierCode" placeholder="请输入供应商代码" clearable @keyup.enter.native="handleQuery"
          style="width:150px" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['oms:po:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['oms:po:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['oms:po:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['oms:po:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="table"   @row-click="handleRowClick" v-loading="loading" :data="poList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增序列" align="center" prop="id" />
      <el-table-column label="订单编号" align="center" prop="orderNumber" />
      <el-table-column label="订单状态" align="center" prop="status" />
      <el-table-column label="订单类型" align="center" prop="type" />
      <el-table-column label="合同编号" align="center" prop="contractId" />
      <el-table-column label="经办人" align="center" prop="handledBy" />
      <el-table-column label="所属部门" align="center" prop="department" />
      <el-table-column label="业务日期" align="center" prop="businessDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.businessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物料编码" align="center" prop="materialCode" />
      <el-table-column label="采购数量" align="center" prop="quantity" />
      <el-table-column label="采购单价" align="center" prop="unitPrice" />
      <el-table-column label="供应商代码" align="center" prop="supplierCode" />
      <el-table-column label="预计到货日期" align="center" prop="deliveryDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
  </div>
</template>

<script>
import { listPo, getPo, delPo, addPo, updatePo } from "@/api/oms/po";

export default {
  name: "Po",
  data() {
    return {
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
      // 采购订单表格数据
      poList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNumber: null,
        status: null,
        type: null,
        contractId: null,
        handledBy: null,
        materialCode: null,
        supplierCode: null,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询采购订单列表 */
    getList() {
      this.loading = true;
      listPo(this.queryParams).then(response => {
        this.poList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push('/oms/po/add')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const poId = row.id || this.ids;
      this.$router.push('/oms/po/edit/' + poId)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除采购订单编号为"' + ids + '"的数据项？').then(function () {
        return delPo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('oms/po/export', {
        ...this.queryParams
      }, `po_${new Date().getTime()}.xlsx`)
    },
    // handle row click 
    handleRowClick(row, col, event) {
      this.$refs.table.toggleRowSelection(row)
    },
  }
};
</script>
