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

onMounted(() => {
  const userId = getCookie('jwt1')  // 获取 Cookie 中的 jwt1
  if (!userId) {
    console.error("未检测到用户登录信息");
    return;
  }

  const url = `http://localhost:8000/api/getGoodsBySellerId?sellerId=${userId}`
  axios.get(url)
      .then(response => {
        goodsData.value = response.data
        console.log('获取商品成功:', goodsData.value)
      })
      .catch(error => {
        console.error('获取商品失败:', error)
      })
})
</script>

<template>
  <el-table :data="goodsData" style="width: 100%" max-height="700px">
    <template #empty>您尚未发布任何商品</template>

    <el-table-column prop="goodName" label="商品名称" />
    <el-table-column prop="goodInfo" label="商品信息" />
    <el-table-column prop="goodValue" label="价格" />
    <el-table-column prop="createTime" label="创建时间" />
    <el-table-column prop="lastUpdateTime" label="更新时间" />
    <el-table-column label="商品图片">
      <template #default="scope">
        <el-image
            style="width: 100px; height: 100px"
            :src="scope.row.goodImg"
            :fit="'fill'"
        />
      </template>
    </el-table-column>
  </el-table>
</template>

<style scoped>
</style>