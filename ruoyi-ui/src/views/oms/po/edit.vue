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
          <el-form-item label="物料名称" prop="materialName">
            <el-input v-model="form.materialName" placeholder="请输入物料名称" />
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="采购数量" prop="quantity">
            <el-input v-model="form.quantity" placeholder="请输入采购数量" />
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
      <el-divider content-position="left"> 供应商信息 </el-divider>
      <el-row :gutter="10" class="mb8">
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="供应商" prop="supplierCode">
            <el-select v-model="form.supplierCode" filterable remote clearable placeholder="请输入查询关键字"
              :remote-method="remoteMethodSupplier" :loading="remoteLoadingSupplier" @change="handleSupplierChange">
              <el-option v-for="item in optionsSupplier" :key="item.value" :label="item.label" :value="item.value">
                <el-tooltip class="item" effect="dark" placement="right" :content="item.label">
                  <div>{{ item.value }}</div>
                </el-tooltip>
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="供应商名称：" prop="supplierName" style="margin-left: 20px;">
            {{ supplierName }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="12" :md="12" :lg="6" :xl="4">
          <el-form-item label="预计到货期" prop="arrivalDate">
            <el-date-picker clearable v-model="form.arrivalDate" type="date" value-format="yyyy-MM-dd"
              placeholder="请选择预计到货期">
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
          <el-form-item label="备注" prop="orderRemark">
            <el-input v-model="form.orderRemark" type="textarea" style="width: 86%" maxlength="128" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <el-divider content-position="left"> 交货单明细 </el-divider>
    </el-form>
    <div slot="footer" class="dialog-footer" style="text-align: center; margin-top: 50px;">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="returnPrePage">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { addPo, updatePo, getPo} from "@/api/oms/po";
import { listDept } from "@/api/system/dept"
import { listSupplier } from "@/api/masterdata/supplier"

export default {
  name: "editPO",
  dicts: ['cus_tms_status', 'cus_material_unit'],
  data() {
    return {
      // 表单参数
      form: {},
      // 部门树选项
      deptOptions: [],
      // 供应商名称选择用
      supplierName: '',
      remoteLoadingSupplier: true,
      querySupplier: {
        pageNum: 1,
        pageSize: 9999999,
        code: '',
        name: ''
      },
      queryDept: {
        parentId: '100',
        status: 0
      },
      optionsSupplier: [],
      listSupplier: [],
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
        materialName: [
          { required: true, message: "物料名称不能为空", trigger: "blur" }
        ],
        quantity: [
          { required: true, message: "采购数量不能为空", trigger: "blur" }
        ],
        supplierCode: [
          { required: true, message: "供应商名称不能为空", trigger: "blur" }
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
    // set default date
    this.$set(this.form, 'businessDate', new Date())
    // get department information
    this.getDept()
    const poId = this.$route.params && this.$route.params.poId;
    if (poId != null && poId != '' && poId != 'null') {
      getPo(poId).then(response => {
        this.form = response.data;
        // this.materialName = response.data.materialName
        // this.shippingFrom = response.data.shippingFromName
        // this.shippingTo = response.data.shippingToName
      })
    }
  },
  methods: {
    /** 根据输入供应商姓名关键字，取得供应商列表 */
    remoteMethodSupplier(query) {
      if (query !== '') {
        this.remoteLoadingSupplier = true;
        this.querySupplier.code = query;
        this.supplierName = ''
        listSupplier(this.querySupplier).then(response => {
          this.remoteLoadingSupplier = false;
          this.listSupplier = response.rows;
          this.optionsSupplier = response.rows.map(item => {
            return { value: `${item.code}`, label: `${item.name}` };
          })
        });
      } else {
        this.optionsSupplier = [];
      }
    },
    //handleSupplierChange
    handleSupplierChange(selectValue) {
      if (selectValue != '') {
        let t = this.listSupplier.find(item => {
          return item.code == selectValue;
        });
        this.supplierName = t.name; // 供应商名称
      } else {
        this.listSupplier = []
      }
    },
    // 返回采购管理
    returnPrePage() {
      this.$router.push({ path: "/oms/po" });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderNumber != ''&& this.form.orderNumber != undefined) {
            updatePo(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              // TO DO , show order detail
              const orderNumber = this.form.orderNumber;
              this.$store.dispatch('tagsView/delView', this.$route)
              // this.$router.push('/oms/???/' + orderNumber)
              this.$router.push('/oms/po')
            });
          } else {
            addPo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              // TO DO , show order detail
              const orderNumber = this.form.orderNumber;
              this.$store.dispatch('tagsView/delView', this.$route)
              // this.$router.push('/oms/???/' + orderNumber)
              this.$router.push('/oms/po')
            });
          }
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