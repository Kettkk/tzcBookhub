<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import axios from "axios";
import { ElDialog, ElMessage } from "element-plus";
import { URL } from '@/ToolUrl';

const errorHandler = () => true;
// 删除用户
const confirmEvent = async (id: number) => {
    console.log('confirm!', id);
    try {
        await axios.delete('http://' + URL + ':8000/api/admin/deleteUser/' + id);
        await fetchData();
    } catch (error) {
        console.error('删除数据时出错', error);
    }
};


const tableData = ref([]);
const fetchData = async () => {
    try {
        const response = await axios.get('http://' + URL + ':8000/api/admin/users');
        tableData.value = response.data;
    } catch (error) {
        console.error('拉取数据时出错', error);
    }
};
onMounted(fetchData);


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

//#region 修改个人信息功能JS
const cmpData = {
    editedName1: '',
    editedPassword1: '',
    editedEmail1: '',
    editedMoney1: 0,
    editedStar1: 0,
    currentUserID1: 0,
};
const editedName = ref('');
const editedPassword = ref('');
const editedEmail = ref('');
let editedMoney = ref(0);
const editedStar = ref(0);
const dialogFormVisible = ref(false);
let currentUserID = ref(0);

//打开编辑个人信息窗口
const openEditDialog = (row: any) => {
    currentUserID.value = row.userID;
    editedName.value = row.username;
    editedPassword.value = row.password;
    editedEmail.value = row.email;
    editedMoney.value = row.money;
    editedStar.value = row.star;

    cmpData.editedName1 = row.username;
    cmpData.editedPassword1 = row.password;
    cmpData.editedEmail1 = row.email;
    cmpData.editedMoney1 = row.money;
    cmpData.editedStar1 = row.star;
    dialogFormVisible.value = true;
};

//关闭编辑个人信息窗口，清空信息
const closeEditInfoDialog = () => {
    editedName.value = '';
    editedPassword.value = '';
    editedEmail.value = '';
    editedMoney.value = 0;
    editedStar.value = 0;
    dialogFormVisible.value = false;
};
//提交修改内容的方法
const submitUserInfo = async () => {
    if (editedName.value === cmpData.editedName1 && editedPassword.value === cmpData.editedPassword1 && editedEmail.value === cmpData.editedEmail1
        && editedMoney.value === cmpData.editedMoney1 && editedStar.value === cmpData.editedStar1) {
        ElMessage({
            message: '请至少修改一项内容',
            type: 'error',
            plain: true
        })
    } else {
        try {
            await axios.put('http://' + URL + ':8000/api/admin/updateUser', {
                userID: currentUserID.value,
                username: editedName.value,
                password: editedPassword.value,
                email: editedEmail.value,
                money: editedMoney.value,
                star: editedStar.value,
                avatar: "",
            });
            closeEditInfoDialog();
            await fetchData();
            ElMessage({
                message: '修改成功',
                type: 'success',
                plain: true,
            });
            console.log('修改成功');
        } catch (error) {
            console.error('修改数据时出错', error);
            ElMessage({
                message: '修改失败',
                type: 'error',
                plain: true,
            });
        }
    }
};
//#endregion
</script>

<template>
    <el-table :data="tableData" style="width: 100%">
        <el-table-column prop="userID" label="用户ID" width="150" />
        <el-table-column prop="username" label="用户名" width="150" />
        <el-table-column prop="password" label="密码" width="150" />
        <el-table-column prop="email" label="邮箱" width="150" />
        <el-table-column prop="money" label="钱包余额" width="150" />
        <el-table-column prop="star" label="评分" width="150" />
        <el-table-column prop="avatar" label="头像" width="150">
            <template #default="{ row }">
                <el-avatar :size="50" :src="row.avatar" @error="errorHandler" shape="circle">
                    <img src="https://cube.elemecdn.com/e/fd/0fc7d20532fdaf769a25683617711png.png" alt="Avatar" />
                </el-avatar>
            </template>
        </el-table-column>

        <el-table-column label="创建时间" width="180">
            <template #default="{ row }">
                {{ formatTime(row.createTime) }}
            </template>
        </el-table-column>

        <el-table-column label="更新时间" width="180">
            <template #default="{ row }">
                {{ formatTime(row.lastUpdateTime) }}
            </template>
        </el-table-column>

        <el-table-column fixed="right" label="管理员操作" width="150">
            <template #default="{ row }">
                <el-popconfirm title="你确定要删除该用户吗？" @confirm="() => confirmEvent(row.userID)">
                    <template #reference>
                        <el-button link type="primary" size="small">
                            删除用户
                        </el-button>
                    </template>
                </el-popconfirm>
                <el-button link type="primary" size="small" @click="openEditDialog(row)">编辑用户</el-button>
            </template>
        </el-table-column>
    </el-table>
    <el-backtop :right="100" :bottom="100" />

    <!-- #region 修改个人信息组件 -->
    <el-dialog v-model="dialogFormVisible" width="360px" :before-close="closeEditInfoDialog">
        <template #title>
            <div style="text-align: left;">修改个人信息</div>
        </template>
        <div style="display: flex;margin-bottom: 15px;margin-top: 10px;">
            <span style="margin-right: 5px;">用户名</span>
            <el-input v-model="editedName" style="width: 240px" />
        </div>
        <div style="display: flex;margin-bottom:15px ;">
            <span style="margin-right: 18px;">密码</span>
            <el-input v-model="editedPassword" style="width: 240px" />
        </div>
        <div style="display: flex;margin-bottom:15px ;">
            <span style="margin-right: 18px;">邮箱</span>
            <el-input v-model="editedEmail" style="width: 240px" />
        </div>
        <div style="display: flex;margin-bottom:15px ;">
            <span style="margin-right: 18px;">余额</span>
            <el-input v-model="editedMoney" style="width: 240px" />
        </div>
        <div style="display: flex;margin-bottom:15px ;">
            <span style="margin-right: 18px;">星级</span>
            <el-input v-model="editedStar" style="width: 240px" />
        </div>

        <div style="margin-top: 15px;">
            <el-button type="primary" @click="submitUserInfo">修改完成</el-button>
        </div>
    </el-dialog>
    <!-- #endregion -->

</template>

<style scoped></style>