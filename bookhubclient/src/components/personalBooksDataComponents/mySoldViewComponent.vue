<script setup>
import { onMounted, ref } from 'vue'
import axios from "axios";
const soldData = ref([])

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
    axios.get('http://localhost:8000/api/soldGoods', {
        withCredentials: true
    })
        .then(function (response) {
            soldData.value = response.data
            soldData.value = response.data
            soldData.value = response.data.map(item => ({
                ...item,
                createTime: formatDate(item.createTime),
                lastUpdateTime: formatDate(item.lastUpdateTime),
            }))
            console.log(response.data);
        })
        .catch(function (error) {
            console.log(error);
        });
})


</script>

<template>
    <el-table :data="soldData" style="width: 100% " max-height="700px">
        <template #empty>暂未出售任何商品</template>
        <el-table-column prop="goodName" label="商品名称" style="width: 12.5%;" />
        <el-table-column prop="goodName" label="商品简介" style="width: 12.5%;" />
        <el-table-column prop="goodValue" label="商品价格" style="width: 12.5%;" />
        <el-table-column prop="createTime" label="商品发布时间" style="width: 12.5%;" />
        <el-table-column prop="lastUpdateTime" label="买家收货时间" style="width: 12.5%;" />
        <el-table-column prop="goodImg" label="商品图片" style="width: 12.5%;">
            <template v-slot="scope">
                <el-image style="width: 100px; height: 100px" :src="scope.row.goodImg" :fit="fill" />
            </template>
        </el-table-column>
    </el-table>
</template>

<style scoped></style>