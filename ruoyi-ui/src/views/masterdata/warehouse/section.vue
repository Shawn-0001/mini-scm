<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="库区编码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入库区编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="库区名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入库区名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['masterdata:warehouseSection:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['masterdata:warehouseSection:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['masterdata:warehouseSection:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['masterdata:warehouseSection:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" ref="table" @row-click="handleRowClick" :data="warehouseSectionList"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库区编码" align="center" prop="code" />
      <el-table-column label="库区名称" align="center" prop="name" />
      <el-table-column label="计量单位" align="center" prop="unit" />
      <el-table-column label="库区地址" align="center" prop="location" />
      <el-table-column label="管理人员" align="center" prop="contactPic" />
      <el-table-column label="联系方式1" align="center" prop="contactPhone1" />
      <el-table-column label="联系方式2" align="center" prop="contactPhone2" />
      <el-table-column label="库区类别" align="center" prop="category" />
      <el-table-column label="占地面积" align="center" prop="area" />
      <el-table-column label="最大容量" align="center" prop="volume" />
      <el-table-column label="备注" align="center" prop="comments" />
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改库区信息对话框 -->
    <el-dialog :title="title" v-dialog-drag :close-on-click-modal="false" :visible.sync="open" width="500px"
      append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <div v-if="title === '添加库区信息'">
          <el-form-item label="库区编码" prop="code">
            <el-input v-model="form.code" placeholder="请输入库区编码" />
          </el-form-item>
        </div>
        <div v-else>
          <el-form-item label="库区编码" prop="code">
            {{ form.code }}
          </el-form-item>
        </div>
        <el-form-item label="库区名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入库区名称" />
        </el-form-item>
        <el-form-item label="库区地址" prop="location">
          <el-input v-model="form.location" placeholder="请输入库区地址" />
        </el-form-item>
        <el-form-item label="管理人员" prop="contactPic">
          <el-input v-model="form.contactPic" placeholder="请输入管理人员" />
        </el-form-item>
        <el-form-item label="联系方式1" prop="contactPhone1">
          <el-input v-model="form.contactPhone1" placeholder="请输入联系方式1" />
        </el-form-item>
        <el-form-item label="联系方式2" prop="contactPhone2">
          <el-input v-model="form.contactPhone2" placeholder="请输入联系方式2" />
        </el-form-item>
        <el-form-item label="库区类别" prop="category">
          <el-input v-model="form.category" placeholder="请输入库区类别" />
        </el-form-item>
        <el-form-item label="占地面积" prop="area">
          <el-input v-model="form.area" placeholder="请输入占地面积" />
        </el-form-item>
        <el-form-item label="最大容量" prop="volume">
          <el-input v-model="form.volume" placeholder="请输入最大容量" />
        </el-form-item>
        <el-form-item label="计量单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入计量单位" />
        </el-form-item>
        <el-form-item label="备注" prop="comments">
          <el-input v-model="form.comments" type="textarea" placeholder="请输入内容" />
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
import { listWarehouseSection, getWarehouseSection, delWarehouseSection, addWarehouseSection, updateWarehouseSection } from "@/api/masterdata/warehouseSection";

export default {
  name: "WarehouseSection",
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
      // 库区信息表格数据
      warehouseSectionList: [],
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
        warehouseCode: this.$route.params && this.$route.params.warehouseCode
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [
          { required: true, message: "库区编码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "库区名称不能为空", trigger: "blur" }
        ],
        location: [
          { required: true, message: "库区地址不能为空", trigger: "blur" }
        ],
        contactPic: [
          { required: true, message: "管理人员不能为空", trigger: "blur" }
        ],
        contactPhone1: [
          { required: true, message: "联系方式1不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    const warehouseCode = this.$route.params && this.$route.params.warehouseCode;
    this.queryParams.warehouseCode = warehouseCode
    this.form.warehouseCode = warehouseCode
    this.getList();
  },
  methods: {
    /** 查询库区信息列表 */
    getList(code) {
      this.loading = true;
      listWarehouseSection(this.queryParams).then(response => {
        this.warehouseSectionList = response.rows;
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
        warehouseCode: this.$route.params && this.$route.params.warehouseCode,
        location: null,
        contactPic: null,
        contactPhone1: null,
        contactPhone2: null,
        category: null,
        area: null,
        volume: null,
        unit: null,
        comments: null
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
      this.ids = selection.map(item => item.code)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加库区信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const code = row.code || this.ids
      const warehouseCode = this.$route.params && this.$route.params.warehouseCode
      getWarehouseSection(warehouseCode, code).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改库区信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWarehouseSection(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWarehouseSection(this.form).then(response => {
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
      const codes = row.code || this.ids;
      const warehouseCode = this.$route.params && this.$route.params.warehouseCode
      this.$modal.confirm('是否确认删除库区编号为"' + codes + '"的数据项？').then(function () {
        return delWarehouseSection(warehouseCode, codes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('masterdata/warehouse/section/export', {
        ...this.queryParams
      }, `warehouseSection_${new Date().getTime()}.xlsx`)
    },
    // handle row click 
    handleRowClick(row, col, event) {
      this.$refs.table.toggleRowSelection(row)
    }
  }
};
</script>
