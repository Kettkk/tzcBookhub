<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { URL } from '@/ToolUrl';

const tableData = ref([]);
const dialogVisible = ref(false);
const previewImageSrc = ref('');

onMounted(async () => {
    try {
        const response = await axios.get('http://' + URL + ':8000/api/admin/goods');
        tableData.value = response.data;
    } catch (error) {
        console.error('拉取数据时出错', error);
        ElMessage.error('拉取数据时出错');
    }
});

const handlePreview = (bookPicture) => {
    previewImageSrc.value = bookPicture;
    dialogVisible.value = true;
};

const formatTime = (time: string | number | Date): string => {
    if (!time) return ''
    const date = new Date(time)
    const y = date.getFullYear()
    const m = String(date.getMonth() + 1).padStart(2, '0')
    const d = String(date.getDate()).padStart(2, '0')
    const h = String(date.getHours()).padStart(2, '0')
    const min = String(date.getMinutes()).padStart(2, '0')
    const s = String(date.getSeconds()).padStart(2, '0')
    return `${y}-${m}-${d} ${h}:${min}:${s}`
}
</script>

<template>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="goodID" label="商品编号" width="180" />
        <el-table-column prop="sellerName" label="卖家" width="180" />
        <el-table-column prop="goodName" label="书名" width="180" />
        <el-table-column prop="goodInfo" label="描述" width="180" />
        <el-table-column label="图片" width="180">
            <template #default="{ row }">
                <el-image :src="row.goodImg" fit="cover" style="width: 50px; height: 50px; cursor: pointer;"
                    @click="handlePreview(row.goodImg)" />
            </template>
        </el-table-column>

        <el-table-column prop="goodValue" label="价格" width="180" />
        <el-table-column label="发布时间" width="180">
            <template #default="{ row }">
                {{ formatTime(row.createTime) }}
            </template>
        </el-table-column>

        <el-table-column label="更新时间" width="180">
            <template #default="{ row }">
                {{ formatTime(row.lastUpdateTime) }}
            </template>
        </el-table-column>
    </el-table>


    <el-dialog v-model="dialogVisible" :close-on-click-modal="true" :show-close="false" width="50%">
        <img :src="previewImageSrc" style="width: 100%" alt="" />
    </el-dialog>
</template>

<style></style>