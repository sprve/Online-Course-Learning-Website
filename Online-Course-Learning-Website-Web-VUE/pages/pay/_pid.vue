<template>
  <div class="cart py-container">
    <div class="checkout py-container pay">
      <div ref="alipay" class="imgBox" style="line-height:24px;padding: 10px;" v-html="model"></div>
    </div>
  </div>
</template>
<script>
import ordersApi from "@/api/orders";
export default {
  asyncData({ params, error }) {
    return ordersApi.createNative(params.pid).then((response) => {
      return {
        payObj: response.data.data,
      };
    });
  },
  data() {
    return {
      model:"",
    };
  },
  mounted() {
    this.model=this.payObj.data;
    this.queryOrderStatus(this.payObj.out_trade_no);
    this.$nextTick(()=>{
      this.$refs.alipay.children[0].submit();
    });
  },
  methods: {
    queryOrderStatus(orderNo) {
      ordersApi.queryPayStatus(orderNo).then((response) => {
          this.$message({
            type: "success",
            message: "支付成功!",
          });
      });
    }
  }
};
</script>
