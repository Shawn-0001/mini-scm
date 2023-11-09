<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-divider content-position="left"> 运单信息 </el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="运单编号：" prop="shippingOrder">
            {{ form.shippingOrder }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择">
              <el-option v-for="dict in dict.type.cus_tms_status" :key="dict.value" :label="dict.label"
                :value="dict.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="车号" prop="tractorNumber">
            <el-input v-model="form.tractorNumber" placeholder="请输入车号" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="物料编码" prop="materialCode">
            <el-select v-model="form.materialCode" filterable remote clearable placeholder="请输入查询关键字"
              :remote-method="remoteMethodMaterialName" :loading="remoteLoadingMaterialName" @change="selChangeMaterial"
              style="width: 160px">
              <el-option v-for="item in optionsMaterialName" :key="item.value" :label="item.label" :value="item.value">
                <el-tooltip class="item" effect="dark" placement="right" :content="item.label">
                  <div>{{ item.value }}</div>
                </el-tooltip>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="物料名称：" prop="materialName" style="margin-left: 20px;">
            {{ materialName }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="发货仓库编码" prop="shippingFrom">
            <el-select v-model="form.shippingFrom" filterable remote clearable placeholder="请输入查询关键字"
              :remote-method="remoteMethodWarehouseList" :loading="remoteLoadingWarehouseListFrom"
              @change="selChangeWarehouseFrom" style="width: 160px">
              <el-option v-for="item in optionsWarehouseList" :key="item.value" :label="item.label" :value="item.value">
                <el-tooltip class="item" effect="dark" placement="right" :content="item.label">
                  <div>{{ item.value }}</div>
                </el-tooltip>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="发货仓库名称：" prop="shippingFromName" style="margin-left: 20px;">
            {{ shippingFrom }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="卸货仓库编码" prop="shippingTo">
            <el-select v-model="form.shippingTo" filterable remote clearable placeholder="请输入查询关键字"
              :remote-method="remoteMethodWarehouseList" :loading="remoteLoadingWarehouseListTo"
              @change="selChangeWarehouseTo" style="width: 160px">
              <el-option v-for="item in optionsWarehouseList" :key="item.value" :label="item.label" :value="item.value">
                <el-tooltip class="item" effect="dark" placement="right" :content="item.label">
                  <div>{{ item.value }}</div>
                </el-tooltip>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="卸货仓库名称：" prop="shippingToName" style="margin-left: 20px;">
            {{ shippingTo }}
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="批次号" prop="batchNumber">
            <el-input v-model="form.batchNumber" placeholder="请输入批次号" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="重量" prop="weight">
            <el-input v-model="form.weight" placeholder="请输入重量" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="单价" prop="unitPrice">
            <el-input v-model="form.unitPrice" placeholder="请输入单价" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="业务日期" prop="businessDate">
            <el-date-picker clearable v-model="form.businessDate" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择业务日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left"> 经办人信息 </el-divider>

      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="经办人" prop="handledBy">
            <el-input v-model="form.handledBy" placeholder="请输入经办人" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="联系电话" prop="phone">
            <el-input v-model="form.phone" placeholder="请输入联系电话" />
          </el-form-item>
        </el-col>
      </el-row>

      <el-divider content-position="left"> 结算信息 </el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="卸货重量" prop="landingWeight">
            <el-input v-model="form.landingWeight" placeholder="请输入卸货重量" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="核算重量" prop="effectiveWeight">
            <el-input v-model="form.effectiveWeight" placeholder="请输入核算重量" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="核算单价" prop="effectiveUnitPrice">
            <el-input v-model="form.effectiveUnitPrice" placeholder="请输入核算单价" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="卸货日期" prop="landingDate">
            <el-date-picker clearable v-model="form.landingDate" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择卸货日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer" align="center">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button v-if="buttonFlag" @click="reset">重 置</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getShippingInfo, addShippingInfo, updateShippingInfo } from "@/api/tms/shippingInfo";
import { listMaterial, getMaterialByCode } from "@/api/masterdata/material"
import { listWarehouse, getWarehouseByCode } from "@/api/masterdata/warehouse"

export default {
  name: "newShippingOrder",
  dicts: ['cus_tms_status'],
  data() {
    return {
      // 表单参数
      form: {},
      // reset button flag
      buttonFlag: false,
      // 仓库名称选择用
      // >>仓库名称显示
      shippingFrom: '',
      optionsWarehouseList: [],
      listWarehouse: [],
      remoteLoadingWarehouseListFrom: false,
      shippingTo: '',
      remoteLoadingWarehouseListTo: false,
      // 物料名称选择用
      materialName: '',
      optionsMaterialName: [],
      listMaterialName: [],
      remoteLoadingMaterialName: false,
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
    const shippingOrderId = this.$route.params && this.$route.params.shippingOrderId;
    if (shippingOrderId != null && shippingOrderId != '' && shippingOrderId != 'null') {
      getShippingInfo(shippingOrderId).then(response => {
        this.form = response.data;
        this.materialName = response.data.materialName
        this.shippingFrom = response.data.shippingFromName
        this.shippingTo = response.data.shippingToName
      })
    } else {
      // set the default date
      this.buttonFlag = true
      // this.form.status = 10
      this.$set(this.form, 'shippingOrder', new Date().getTime())
      this.$set(this.form, 'businessDate', new Date())
    }
  },
  methods: {
    // 取消按钮
    cancel() {
      this.$store.dispatch('tagsView/delView', this.$route)
      this.$router.push('/tms/shippingInfo')
      // this.$router.go(-1)
    },
    // 表单重置
    reset() {
      this.form = {
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
      };
      this.resetForm("form");
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.$modal.loading("处理中...")
          if (this.form.id != null) {
            updateShippingInfo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              const shippingOrderId = this.form.shippingOrderId;
              this.$store.dispatch('tagsView/delView', this.$route)              
              this.$router.push('/tms/shippingInfo/shippingOrderDetail/' + shippingOrderId)
            })
              .then(
                this.$modal.closeLoading()
              )
          } else {
            addShippingInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.$store.dispatch('tagsView/delView', this.$route)
              // this.$router.go(-1)
              this.$router.push('/tms/shippingInfo/')
            })
              .then(this.$modal.closeLoading())
          }
        }
      });
    },
    /** 根据输入物料名称关键字，取得物料名称列表 */
    remoteMethodMaterialName(query) {
      if (query !== '') {
        let queryParams = {
          pageNum: 1,
          pageSize: 999999999,
          code: query,
        }
        this.materialName = ''
        this.remoteLoadingMaterialName = true;
        listMaterial(queryParams).then(response => {
          this.remoteLoadingMaterialName = false;
          this.listMaterialName = response.rows;
          this.optionsMaterialName = response.rows.map(item => {
            return { value: `${item.code}`, label: `${item.name}` };
          })
        });
      } else {
        this.optionsClientName = [];
      }
    },
    /** 物料名称下拉列表框，选择值改变后回调方法 */
    selChangeMaterial(selValue) {
      if (selValue != '') {
        let selMaterial = this.listMaterialName.find(item => {
          return item.code == selValue;
        });
        this.materialName = selMaterial.name; // 物料名称
      }
    },
    /** 发货仓库下拉列表框，选择值改变后回调方法 */
    selChangeWarehouseFrom(selectValue) {
      if (selectValue != '') {
        let t = this.listWarehouse.find(item => {
          return item.code == selectValue;
        });
        this.shippingFrom = t.name; // 物料名称
      } else {
        this.listWarehouse = []
      }
    },
    /** 根据输入仓库关键字，取得仓库列表 */
    remoteMethodWarehouseList(query) {
      if (query !== '') {
        let queryParams = {
          pageNum: 1,
          pageSize: 999999999,
          code: query,
        }
        this.remoteLoadingWarehouseListFrom = true;
        this.remoteLoadingWarehouseListTo = true;
        listWarehouse(queryParams).then(response => {
          this.remoteLoadingWarehouseListFrom = false;
          this.remoteLoadingWarehouseListTo = false;
          this.listWarehouse = response.rows;
          this.optionsWarehouseList = response.rows.map(item => {
            return { value: `${item.code}`, label: `${item.name}` };
          })
        });
      } else {
        this.optionsWarehouseList = [];
      }
    },
    /** 收货仓库下拉列表框，选择值改变后回调方法 */
    selChangeWarehouseTo(selectValue) {
      if (selectValue != '') {
        let t = this.listWarehouse.find(item => {
          return item.code == selectValue;
        });
        this.shippingTo = t.name; // 物料名称
      } else {
        this.listWarehouse = []
      }
    },
  }
};
</script>
