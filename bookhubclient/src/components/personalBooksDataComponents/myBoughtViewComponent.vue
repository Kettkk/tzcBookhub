<script setup>
import { onMounted, ref } from 'vue'
import axios from 'axios'

// 从 Cookie 中提取 jwt1 (即 user_id)
function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) return parts.pop().split(';').shift();
}

const goodsData = ref([])

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

onMounted(() => {
  const userId = getCookie('jwt1')  // 获取 Cookie 中的 jwt1
  if (!userId) {
    console.error("未检测到用户登录信息");
    return;
  }


  axios.get('http://localhost:8000/api/boughtGoods', {
    withCredentials: true
  })
    .then(response => {
      goodsData.value = response.data
      goodsData.value = response.data.map(item => ({
        ...item,
        createTime: formatDate(item.createTime),
        lastUpdateTime: formatDate(item.lastUpdateTime),
      }))
      console.log('获取商品成功:', goodsData.value)
    })
    .catch(error => {
      console.error('获取商品失败:', error)
    })
})
</script>

<template>
  <el-table :data="goodsData" style="width: 100%" max-height="700px">
    <template #empty>您尚未购买任何商品</template>

    <el-table-column prop="goodName" label="商品名称" />
    <el-table-column prop="goodInfo" label="商品简介" />
    <el-table-column prop="goodValue" label="价格（元）" />
    <el-table-column prop="createTime" label="购买时间" />
    <el-table-column prop="lastUpdateTime" label="收货时间" />
    <el-table-column label="商品图片">
      <template #default="scope">
        <el-image style="width: 100px; height: 100px" :src="scope.row.goodImg" :fit="'fill'" />
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped></style>