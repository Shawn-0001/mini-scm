<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="运单编号" prop="shippingOrder">
        <el-input v-model="queryParams.shippingOrder" placeholder="请输入运单编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="车号" prop="tractorNumber">
        <el-input v-model="queryParams.tractorNumber" placeholder="请输入车号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="经办人" prop="handledBy">
        <el-input v-model="queryParams.handledBy" placeholder="请输入经办人" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="业务日期" prop="businessDate">
        <el-date-picker clearable v-model="queryParams.businessDate" type="date" value-format="yyyy-MM-dd"
          placeholder="请选择业务日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status" min-width="100px">
        <el-select v-model="queryParams.status" placeholder="请选择运输单状态" clearable>
          <el-option v-for="dict in dict.type.cus_tms_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['tms:shippingInfo:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['tms:shippingInfo:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['tms:shippingInfo:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['tms:shippingInfo:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="table" v-loading="loading" :data="shippingInfoList" @row-click="handleRowClick"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="运单编号" align="center" prop="shippingOrder" min-width="150px" />
      <el-table-column label="业务日期" align="center" prop="businessDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.businessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="车号" align="center" prop="tractorNumber" min-width="100px" />
      <el-table-column label="物料编号" align="center" prop="materialCode" min-width="150px" />
      <el-table-column label="物料编号" align="center" prop="materialName" min-width="150px" />
      <el-table-column label="批次号" align="center" prop="batchNumber" min-width="100px" />
      <el-table-column label="状态" align="center" prop="status" min-width="100px">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.cus_tms_status" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="经办人" align="center" prop="handledBy" min-width="100px" />
      <el-table-column label="联系电话" align="center" prop="phone" min-width="150px" />
      <el-table-column label="发货仓库" align="center" prop="shippingFrom" min-width="150px" />
      <el-table-column label="发货仓库名称" align="center" prop="shippingFromName" show-overflow-tooltip min-width="150px" />
      <el-table-column label="卸货仓库" align="center" prop="shippingTo" show-overflow-tooltip min-width="150px" />
      <el-table-column label="卸货仓库名称" align="center" prop="shippingToName" min-width="150px" />
      <el-table-column label="重量" align="center" prop="weight" min-width="50px" />
      <el-table-column label="类型" align="center" prop="shippingType" min-width="50px" />
      <el-table-column label="单价" align="center" prop="unitPrice" min-width="100px" />
      <el-table-column label="卸货日期" align="center" prop="landingDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.landingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="卸货重量" align="center" prop="landingWeight" min-width="100px" />
      <el-table-column label="核算重量" align="center" prop="effectiveWeight" min-width="100px" />
      <el-table-column label="核算单价" align="center" prop="effectiveUnitPrice" min-width="100px" />
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['tms:shippingInfo:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['tms:shippingInfo:remove']">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />
  </div>
</template>

<script>
import { listShippingInfo, getShippingInfo, delShippingInfo, addShippingInfo, updateShippingInfo } from "@/api/tms/shippingInfo";

export default {
  name: "ShippingInfo",
  dicts: ['cus_tms_status'],
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
      let shippingOrderId = 'null'
      this.$router.push('/tms/shippingInfo/newShippingOrder/' + shippingOrderId)
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      const shippingOrderId = row.id || this.ids;
      this.$router.push('/tms/shippingInfo/newShippingOrder/' + shippingOrderId)
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除运单详细信息编号为"' + ids + '"的数据项？').then(function () {
        return delShippingInfo(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('tms/shippingInfo/export', {
        ...this.queryParams
      }, `运单管理_运单列表_${new Date().getFullYear()}年${new Date().getMonth() + 1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    // handle row click 
    handleRowClick(row, col, event) {
      this.$refs.table.toggleRowSelection(row)
    }
  }
};
</script>
