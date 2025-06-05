<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'
import { ElMessage } from 'element-plus'

const star = ref(0)

function formatDate(isoString) {
  const date = new Date(isoString)
  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')
  const seconds = String(date.getSeconds()).padStart(2, '0')
  return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
}


// 订单数据
const orderData = ref([])

// 获取所有订单
const getOrderData = () => {
  axios.get('http://localhost:8000/api/getOrderInfoByConsumerId', {
    withCredentials: true
  })
    .then(response => {
      console.log(response.data)
      const formattedOrders = response.data.map(order => ({
        ...order,
        createTime: formatDate(order.createTime),
      }))
      orderData.value = formattedOrders
    })
    .catch(error => {
      console.error('获取订单数据失败：', error)
    })
}


// 点击确认收货按钮，发送状态更新请求
const confirmAccept = (orderID) => {
  axios.post('http://localhost:8000/api/updateOrderStatus', null, {
    params: { orderId: orderID, star: star.value }
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
    <el-table :data="orderData" style="width: 100%; height: 100%" border>
      <template #empty>暂无订单数据</template>

      <el-table-column prop="orderID" label="订单号" width="200" />
      <el-table-column prop="sellerName" label="卖家姓名" width="250" />
      <el-table-column prop="goodName" label="商品名称" width="250" />
      <el-table-column prop="orderStatu" label="订单状态" width="250" />
      <el-table-column prop="createTime" label="购买时间" width="200" />
      <el-table-column label="评分" width="250">
        <template #default="scope">
          <el-rate v-model="star" :disabled="scope.row.orderStatu !== '未完成'" />
        </template>

      </el-table-column>
      <!-- 操作列 -->
      <el-table-column label="确认收货" width="200" fixed="right">
        <template #default="scope">
          <el-button type="primary" size="small" @click="confirmAccept(scope.row.orderID)"
            :disabled="scope.row.orderStatu !== '未完成'">
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