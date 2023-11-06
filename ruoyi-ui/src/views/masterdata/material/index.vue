<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="物料编码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入物料编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="物料名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入物料名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['masterdata:material:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['masterdata:material:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['masterdata:material:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['masterdata:material:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="table" @row-click="handleRowClick"  v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="自增序列" align="center" prop="id" width="80px" />
      <el-table-column label="物料编码" align="center" prop="code" min-width="150px" />
      <el-table-column label="物料名称" align="center" prop="name" min-width="150px" />
      <el-table-column label="计量单位" align="center" prop="unit" min-width="50px">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.cus_material_unit" :value="scope.row.unit" />
        </template>
      </el-table-column>
      <el-table-column label="规格型号" align="center" prop="models" min-width="150px" />
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="100px">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['masterdata:material:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['masterdata:material:remove']">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改物料主数据对话框 -->
    <el-dialog v-dialog-drag :title="title" :visible.sync="open" width="500px" :close-on-click-modal="false"
      append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="20" class="mb8">
          <el-col :span="15" :offset="2">
            <div v-if="title === '添加物料主数据'">
              <el-form-item label="物料编码" prop="code">
                <el-input v-model="form.code" placeholder="请输入物料名编码" />
              </el-form-item>
            </div>
            <div v-else>
              <el-form-item label="物料编码" prop="code">
                <span> {{ form.code }}</span>
              </el-form-item>
            </div>
          </el-col>
        </el-row>
        <el-row :gutter="20" class="mb8">
          <el-col :span="15" :offset="2">
            <el-form-item label="物料名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入物料名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" class="mb8">
          <el-col :span="10" :offset="2">
            <el-form-item label="计量单位" prop="unit">
              <el-select v-model="form.unit" placeholder="请选择">
                <el-option v-for="dict in dict.type.cus_material_unit" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20" class="mb8">
          <el-col :span="15" :offset="2">
            <el-form-item label="规格型号" prop="models">
              <el-input v-model="form.models" placeholder="请输入规格型号" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMaterial, getMaterial, delMaterial, addMaterial, updateMaterial } from "@/api/masterdata/material";

export default {
  name: "Material",
  dicts: ['cus_material_unit'],
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
      // 物料主数据表格数据
      materialList: [],
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
        models: null,
        unit: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        code: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询物料主数据列表 */
    getList() {
      this.loading = true;
      listMaterial(this.queryParams).then(response => {
        this.materialList = response.rows;
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
        models: null,
        unit: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加物料主数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getMaterial(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物料主数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateMaterial(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMaterial(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除物料主数据编号为"' + ids + '"的数据项？').then(function () {
        return delMaterial(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('masterdata/material/export', {
        ...this.queryParams
      }, `material_${new Date().getTime()}.xlsx`)
    },
    // handle row click 
    handleRowClick(row, col, event) {
      this.$refs.table.toggleRowSelection(row)
    }
  }
};
</script>
