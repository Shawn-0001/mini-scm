<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库编码" prop="code">
        <el-input v-model="queryParams.code" placeholder="请输入仓库编码" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="仓库名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入仓库名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['masterdata:warehouse:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
          v-hasPermi="['masterdata:warehouse:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['masterdata:warehouse:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport"
          v-hasPermi="['masterdata:warehouse:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="table" @row-click="handleRowClick" v-loading="loading" :data="warehouseList"
      @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="仓库编码" align="center" prop="code" min-width="80px" />
      <el-table-column label="仓库名称" align="center" prop="name" min-width="100px" />
      <el-table-column label="库区维护" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/masterdata/warehouse-section/section/' + scope.row.code" class="link-type">
            <!-- <span>{{ scope.row.code }}</span> -->
            <span>库区维护</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="行政区域" align="center" prop="region" :show-overflow-tooltip="true" min-width="150px">
        <template slot-scope="scope">
          {{ parseregion(scope.row.region) }}
        </template>
      </el-table-column>
      <el-table-column label="仓库地址" align="center" prop="location" :show-overflow-tooltip="true" min-width="120px" />
      <el-table-column label="管理人员" align="center" prop="contactPic" min-width="80px" />
      <el-table-column label="联系方式1" align="center" prop="contactPhone1" min-width="80px" />
      <el-table-column label="联系方式2" align="center" prop="contactPhone2" min-width="80px" />
      <el-table-column label="仓库类别" align="center" prop="category" min-width="80px" />
      <el-table-column label="占地面积" align="center" prop="area" min-width="80px" />
      <el-table-column label="最大容量" align="center" prop="volume" min-width="80px" />
      <el-table-column label="计量单位" align="center" prop="unit" min-width="80px">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.cus_material_unit" :value="scope.row.unit" />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="comments" :show-overflow-tooltip="true" min-width="200px" />
      <!-- <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['masterdata:warehouse:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['masterdata:warehouse:remove']">删除</el-button>
        </template>
      </el-table-column> -->
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 添加或修改仓库主数据对话框 -->
    <el-dialog v-dialog-drag :title="title" :visible.sync="open" width="70%" :close-on-click-modal="false" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row :gutter="10" class="mb8">
          <el-col :span="4">
            <div v-if="title === '添加仓库主数据'">
              <el-form-item label="仓库编码" prop="code">
                <el-input v-model="form.code" placeholder="请输入仓库编码" />
              </el-form-item>
            </div>
            <div v-else>
              <el-form-item label="仓库编码" prop="code">
                {{ form.code }}
              </el-form-item>
            </div>
          </el-col>
          <el-col :span="5">
            <el-form-item label="仓库名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入仓库名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="行政区域" prop="region">
              <el-cascader size="large" :options="regionOptions" v-model="form.region" filterable
                @change="handleRegionChange">
              </el-cascader>
              <!-- <el-input v-model="form.region" placeholder="请输入行政区域" /> -->
            </el-form-item>
          </el-col>
          <el-col :span="7">
            <el-form-item label="仓库地址" prop="location">
              <el-input v-model="form.location" placeholder="请输入仓库地址" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10" class="mb8">
          <el-col :span="4">
            <el-form-item label="管理人员" prop="contactPic">
              <el-input v-model="form.contactPic" placeholder="请输入管理人员" />
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="联系方式1" prop="contactPhone1">
              <el-input v-model="form.contactPhone1" placeholder="请输入联系方式1" />
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="联系方式2" prop="contactPhone2">
              <el-input v-model="form.contactPhone2" placeholder="请输入联系方式2" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="仓库类别" prop="category">
              <el-input v-model="form.category" placeholder="请输入仓库类别" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10" class="mb8">
          <el-col :span="4">
            <el-form-item label="占地面积" prop="area">
              <el-input v-model="form.area" placeholder="请输入占地面积" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="最大容量" prop="volume">
              <el-input v-model="form.volume" placeholder="请输入最大容量" />
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="计量单位" prop="unit">
              <el-select v-model="form.unit" placeholder="请选择">
                <el-option v-for="dict in dict.type.cus_material_unit" :key="dict.value" :label="dict.label"
                  :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="10" class="mb8">
          <el-col :span="22">
            <el-form-item label="备注" prop="comments">
              <el-input v-model="form.comments" type="textarea" placeholder="请输入内容" />
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
import { listWarehouse, getWarehouse, delWarehouse, addWarehouse, updateWarehouse } from "@/api/masterdata/warehouse";
import { regionData, codeToText } from "element-china-area-data"

export default {
  name: "Warehouse",
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
      // 仓库主数据表格数据
      warehouseList: [],
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
      },
      // 表单参数
      form: {},
      // 省市区级联数据
      regionOptions: regionData,
      // 表单校验
      rules: {
        code: [
          { required: true, message: "仓库编码不能为空", trigger: "blur" }
        ],
        name: [
          { required: true, message: "仓库名称不能为空", trigger: "blur" }
        ],
        region: [
          { required: true, message: "行政区域不能为空", trigger: "blur" }
        ],
        location: [
          { required: true, message: "仓库地址不能为空", trigger: "blur" }
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
    this.getList();
  },
  methods: {
    /** 查询仓库主数据列表 */
    getList() {
      this.loading = true;
      listWarehouse(this.queryParams).then(response => {
        this.warehouseList = response.rows;
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
        region: null,
        location: null,
        contactPic: null,
        contactPhone1: null,
        contactPhone2: null,
        category: null,
        area: null,
        volume: null,
        unit: null,
        comments: null,
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
      this.title = "添加仓库主数据";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getWarehouse(id).then(response => {
        this.form = response.data;
        this.form.region = this.form.region.split('-');
        this.open = true;
        this.title = "修改仓库主数据";
      });
    },
    /** 提交按钮 */
    submitForm() {
      // 省市区级联选择器数组转字符串
      let changgedregion = this.form.region;
      console.log(changgedregion)
      if (changgedregion) {
        this.form.region = changgedregion.join('-');
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateWarehouse(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addWarehouse(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除仓库主数据编号为"' + ids + '"的数据项？').then(function () {
        return delWarehouse(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('masterdata/warehouse/export', {
        ...this.queryParams
      }, `主数据管理_仓库列表_${new Date().getFullYear()}年${new Date().getMonth() + 1}月${new Date().getDate()}日 ${new Date().getHours()}:${new Date().getMinutes()}:${new Date().getSeconds()}.xlsx`)
    },
    /** 省市区级联选择器变更后 */
    handleRegionChange(value) {
      console.log(value)
    },
    //回显注册城市
    parseregion(region) {
      if (region.indexOf('-')) {
        let locationArray = region.split('-')
        let area = ''
        switch (locationArray.length) {
          case 1: area += codeToText[locationArray[0]]; break;
          case 2: area += codeToText[locationArray[0]] + ' / ' + codeToText[locationArray[1]]; break;
          case 3: area += codeToText[locationArray[0]] + ' / ' + codeToText[locationArray[1]] + ' / ' + codeToText[locationArray[2]]; break
          default: break;
        }
        return area;
      }
    },
    // handle row click 
    handleRowClick(row, col, event) {
      this.$refs.table.toggleRowSelection(row)
    }
  }
};
</script>
