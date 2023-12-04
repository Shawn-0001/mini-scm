<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-divider content-position="left"> 订单信息 </el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="订单编号" prop="orderNumber">
            {{ form.orderNumber }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="关联合同编号" prop="contractId">
            <el-input v-model="form.contractId" placeholder="请输入合同编号" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="所属部门" prop="department">
            <el-select v-model="form.department" placeholder="请选择所属部门">
              <el-option v-for="item in deptOptions" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="5">
          <el-form-item label="业务日期" prop="businessDate">
            <el-date-picker clearable v-model="form.businessDate" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择业务日期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="经办人" prop="handledBy">
            <el-input v-model="form.handledBy" placeholder="" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left"> 物料信息 </el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="物料编码" prop="materialCode">
            <el-select v-model="form.materialCode" filterable remote clearable placeholder="请输入查询关键字"
              :remote-method="remoteMethodMaterial" :loading="remoteLoadingMaterial" @change="handleMaterialChange">
              <el-option v-for="item in optionsMaterial" :key="item.value" :label="item.value" :value="item.value">
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
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="销售数量" prop="quantity">
            <el-input v-model="form.quantity" placeholder="请输入销售数量" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="计量单位" prop="unit">
            <el-select v-model="form.unit" placeholder="计量单位" clearable>
              <el-option v-for="dict in dict.type.cus_material_unit" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="单价" prop="unitPrice">
            <el-input v-model="form.unitPrice" placeholder="请输入单价" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left"> 客户信息 </el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="客户" prop="customerCode">
            <el-select v-model="form.customerCode" filterable remote clearable placeholder="请输入查询关键字"
              :remote-method="remoteMethodCustomer" :loading="remoteLoadingCustomer" @change="handleCustomerChange">
              <el-option v-for="item in optionsCustomer" :key="item.value" :label="item.label" :value="item.value">
                <el-tooltip class="item" effect="dark" placement="right" :content="item.label">
                  <div>{{ item.value }}</div>
                </el-tooltip>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="客户名称：" prop="customerName" style="margin-left: 20px;">
            {{ customerName }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="预计交货期" prop="deliveryDate">
            <el-date-picker clearable v-model="form.deliveryDate" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择预计交货期">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <!-- <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="账期" prop="accountPeriod">
            <el-input v-model="form.accountPeriod" placeholder="请输入账期"  />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="到账条件" prop="arrivalTerms">
            <el-select v-model="form.arrivalTerms" placeholder="到账条件" clearable style="width: 130px">
              <el-option v-for="dict in dict.type.purchasesale_arrival_terms" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
            <el-input v-model="form.arrivalTermsValue" placeholder="天数" style="margin-left: 10px; width: 60px" />（天）
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="结算方式" prop="settlementMethod">
            <el-select v-model="form.settlementMethod" placeholder="结算方式" clearable >
              <el-option v-for="dict in dict.type.purchasesale_settlement_method" :key="dict.value" :label="dict.label"
                :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-col> -->
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input v-model="form.remark" type="textarea" style="width: 86%" maxlength="128" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- <el-divider content-position="left"> 交货单明细 </el-divider> -->
    </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: center; margin-top: 50px;">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="returnPrePage">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { updateSo, getSo } from "@/api/oms/so";
import { listDept } from "@/api/system/dept"
import { listCustomer, getCustomerByCode } from "@/api/masterdata/customer"
import { getMaterialByCode, listMaterial } from "@/api/masterdata/material"

export default {
  name: "editSO",
  dicts: ['cus_tms_status', 'cus_material_unit'],
  data() {
    return {
      // 表单参数
      form: {},
      // 部门树选项
      deptOptions: [],
      // 客户名称选择用
      customerName: '',
      materialName: '',
      remoteLoadingCustomer: true,
      remoteLoadingMaterial: true,
      queryCustomer: {
        pageNum: 1,
        pageSize: 9999999,
        code: '',
        name: ''
      },
      queryMaterial: {
        pageNum: 1,
        pageSize: 9999999,
        code: '',
        name: ''
      },
      queryDept: {
        parentId: '100',
        status: 0
      },
      optionsCustomer: [],
      optionsMaterial: [],
      listCustomer: [],
      listMaterial: [],
      // 表单校验
      rules: {
        handledBy: [
          { required: true, message: "经办人不能为空", trigger: "blur" }
        ],
        department: [
          { required: true, message: "所属部门不能为空", trigger: "blur" }
        ],
        businessDate: [
          { required: true, message: "业务日期不能为空", trigger: "blur" }
        ],
        materialCode: [
          { required: true, message: "物料编码不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "销售数量不能为空", trigger: "blur" }
        ],
        customerCode: [
          { required: true, message: "客户编码不能为空", trigger: "blur" }
        ],
        unitPrice: [
          { required: true, message: "单价不能为空", trigger: "blur" }
        ],
        unit: [
          { required: true, message: "计量单位不能为空", trigger: "blur" }
        ],
      },
    };
  },
  created() {
    // get department information
    this.getDept()
    const soId = this.$route.params && this.$route.params.soId;
    if (soId != null && soId != '' && soId != 'null') {
      getSo(soId).then(response => {
        this.form = response.data;
        let customerCode = response.data.customerCode
        let materialCode = response.data.materialCode
        getCustomerByCode(customerCode).then(res => {
          this.customerName = res.data.name
        })
        getMaterialByCode(materialCode).then(res => {
          this.materialName = res.data.name
        })
      })
    } else {
      // set default date
      this.$set(this.form, 'businessDate', new Date())
    }
  },
  methods: {
    /** 根据输入客户姓名关键字，取得客户列表 */
    remoteMethodCustomer(query) {
      if (query !== '') {
        this.remoteLoadingCustomer = true;
        this.queryCustomer.code = query;
        this.customerName = ''
        listCustomer(this.queryCustomer).then(response => {
          this.remoteLoadingCustomer = false;
          this.listCustomer = response.rows;
          this.optionsCustomer = response.rows.map(item => {
            return { value: `${item.code}`, label: `${item.name}` };
          })
        });
      } else {
        this.optionsCustomer = [];
      }
    },
    //handleCustomerChange
    handleCustomerChange(selectValue) {
      if (selectValue != '') {
        let t = this.listCustomer.find(item => {
          return item.code == selectValue;
        });
        this.customerName = t.name; // 客户名称
      } else {
        this.listCustomer = []
      }
    },
    /** 根据输入物料编码关键字，取得物料列表 */
    remoteMethodMaterial(query) {
      if (query !== '') {
        this.remoteLoadingMaterial = true;
        this.queryMaterial.code = query;
        this.materialName = ''
        listMaterial(this.queryMaterial).then(response => {
          this.remoteLoadingMaterial = false;
          this.listMaterial = response.rows;
          this.optionsMaterial = response.rows.map(item => {
            return { value: `${item.code}`, label: `${item.name}` };
          })
        });
      } else {
        this.optionsMaterial = [];
      }
    },
    //handleMaterialChange
    handleMaterialChange(selectValue) {
      if (selectValue != '') {
        let t = this.listMaterial.find(item => {
          return item.code == selectValue;
        });
        this.materialName = t.name; // 客户名称
      } else {
        this.listMaterial = []
      }
    },
    // 返回销售管理
    returnPrePage() {
      this.$router.push({ path: "/oms/so/list" });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // if (this.form.orderNumber != '' && this.form.orderNumber != undefined) {
            updateSo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              // TO DO , show order detail
              const orderNumber = this.form.orderNumber;
              this.$store.dispatch('tagsView/delView', this.$route)
              // this.$router.push('/oms/???/' + orderNumber)
              this.$router.push('/oms/so/list')
            });
          // } else {
          //   addPo(this.form).then(response => {
          //     this.$modal.msgSuccess("新增成功");
          //     // TO DO , show order detail
          //     const orderNumber = this.form.orderNumber;
          //     this.$store.dispatch('tagsView/delView', this.$route)
          //     // this.$router.push('/oms/???/' + orderNumber)
          //     this.$router.push('/oms/so/list')
          //   });
          // }
        }
      });
    },
    /** 查询部门 */
    getDept() {
      listDept(this.queryDept).then(res => {
        this.deptOptions = res.data.map(item => {
          return { value: `${item.deptId}`, label: `${item.deptName}` };
        })
      })
    },
  }
}
</script>