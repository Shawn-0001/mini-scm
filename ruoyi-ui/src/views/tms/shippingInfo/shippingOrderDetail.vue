<template>
  <div class="app-container">
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
        <el-date-picker clearable v-model="form.businessDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择业务日期">
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
        <el-date-picker clearable v-model="form.landingDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择卸货日期">
        </el-date-picker>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm">确 定</el-button>
      <el-button @click="cancel">取 消</el-button>
    </div>
  </div>
</template>

<script>
import { getShippingInfo, updateShippingInfo } from "@/api/tms/shippingInfo";

export default {
  name: "newShippingOrder",
  data() {
    return {
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
    const shippingOrderId = this.$route.params && this.$route.params.shippingOrderId;
    this.getShippingInfo(shippingOrderId).then(response => {
      this.form = response.data;
    })
  },
  methods: {
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
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
            })
            .then(
              this.$modal.closeLoading()
            )
          } else {
            addShippingInfo(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");

            })
            .then(this.$modal.closeLoading())
          }
        }
      });
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
      }, `shippingInfo_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
