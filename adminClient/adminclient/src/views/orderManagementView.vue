<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';
import { URL } from '@/ToolUrl';

interface Order {
    orderID: number;
    sellerName: string;
    consumerName: string;
    bookName: string;
    bookPicture: string;
    orderStatus: number;
    orderStatusString: string;
    orderCreateTime: string;
    orderLatestUpdateTime: string;
}

const tableData = ref<Order[]>([]);
const dialogVisible = ref(false);
const previewImageSrc = ref('');

onMounted(async () => {
    try {
        const response = await axios.get('http://' + URL + ':8000/api/admin/orders');
        tableData.value = response.data;
    } catch (error) {
        console.error('拉取数据时出错', error);
        ElMessage.error('拉取数据时出错');
    }
});

const tableRowClassName = ({ row }: { row: Order }) => {
    if (row.orderStatus === 0) {
        return 'warning-row';
    } else if (row.orderStatus === 1) {
        return 'success-row';
    }
    return '';
};

const handlePreview = (bookPicture: string) => {
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
    <el-table
        :data="tableData"
        style="width: 100%"
        :row-class-name="tableRowClassName"
    >
        <el-table-column prop="orderID" label="订单号" width="180" />
        <el-table-column prop="sellerName" label="卖家姓名" width="180" />
        <el-table-column prop="consumerName" label="买家姓名" width="180" />
        <el-table-column prop="goodName" label="书名" width="250" />
        <el-table-column label="图片" width="180">
            <template #default="{ row }">
                <el-image
                    :src="row.goodImg"
                    fit="cover"
                    style="width: 50px; height: 50px; cursor: pointer;"
                    @click="handlePreview(row.goodImg)"
                />
            </template>
        </el-table-column>
        <el-table-column prop="orderStatu" label="订单状态" width="180" />

        <el-table-column label="订单创建时间" width="180">
            <template #default="{ row }">
                {{ formatTime(row.createTime) }}
            </template>
        </el-table-column>

        <el-table-column label="订单成交时间" width="180">
            <template #default="{ row }">
                {{ formatTime(row.lastUpdateTime) }}
            </template>
        </el-table-column>
    </el-table>

    <el-dialog
        v-model="dialogVisible"
        :close-on-click-modal="true"
        :show-close="false"
        width="50%"
    >
        <img :src="previewImageSrc" style="width: 100%" />
    </el-dialog>
</template>

<style>
.el-table .warning-row {
    --el-table-tr-bg-color: var(--el-color-warning-light-9);
}
.el-table .success-row {
    --el-table-tr-bg-color: var(--el-color-success-light-9);
}
</style>
