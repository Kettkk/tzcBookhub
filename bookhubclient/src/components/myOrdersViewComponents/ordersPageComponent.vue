<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

// 订单数据
const orderData = ref([])

// 获取所有订单
const getOrderData = () => {
  axios.get('http://localhost:8000/api/getAllOrders')
      .then(response => {
        orderData.value = response.data
      })
      .catch(error => {
        console.error('获取订单数据失败：', error)
      })
}

// 点击确认收货按钮，发送状态更新请求
const confirmAccept = (orderID) => {
  axios.post('http://localhost:8000/api/updateOrderStatus', null, {
    params: { orderId: orderID }
  })
      .then(() => {
        ElMessage.success('收货成功，订单状态已更新为已完成')
        getOrderData()
      })
      .catch(error => {
        console.error('收货失败：', error)
        ElMessage.error('收货失败，请稍后重试')
      })
}

onMounted(() => {
  getOrderData()
})
</script>

<template>
  <div class="table-wrapper">
    <el-table
        :data="orderData"
        style="width: 100%; height: 100%"
        border
    >
      <template #empty>暂无订单数据</template>

      <el-table-column prop="orderID" label="订单号" width="100" />
      <el-table-column prop="sellerID" label="卖家ID" width="100" />
      <el-table-column prop="consumerID" label="买家ID" width="100" />
      <el-table-column prop="goodID" label="商品ID" width="100" />
      <el-table-column prop="orderStatu" label="订单状态" width="120" />
      <el-table-column prop="createTime" label="创建时间" width="180" />
      <el-table-column prop="lastUpdateTime" label="最后更新时间" width="180" />

      <!-- 操作列 -->
      <el-table-column label="操作" width="120" fixed="right">
        <template #default="scope">
          <el-button
              type="primary"
              size="small"
              @click="confirmAccept(scope.row.orderID)"
              :disabled="scope.row.orderStatu !== '未完成'"
          >
            确认收货
          </el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<style scoped>
.table-wrapper {
  width: 100%;
  height: calc(100vh - 120px);
  overflow: auto;
  padding: 16px;
  box-sizing: border-box;
}
</style>