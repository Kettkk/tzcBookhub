<!--顶部通栏开始-->
<script setup>
import { ref, onMounted } from 'vue';
import router from "@/router/index.js";
import LoginComponent from "@/components/homeViewComoponents/loginComponent.vue";
import RegisterComponent from "@/components/homeViewComoponents/registerComponent.vue";
import { ElMessage } from "element-plus";

// 登录状态
const isLoggedIn = ref(false);

// 检查 jwt cookie 是否存在
const checkLoginStatus = () => {
    const cookies = document.cookie.split(';').map(cookie => cookie.trim());
    isLoggedIn.value = cookies.some(cookie => cookie.startsWith('jwt1=') && cookie.split('=')[1] !== '');
};

onMounted(() => {
    checkLoginStatus();
});

const go2YourProfile = () => router.push({ name: 'userProfile' });
const go2ChatRoom = () => router.push({ name: 'chatRoom' });
const go2Assistant = () => router.push({ name: 'assistant' });

const logout = () => {
    document.cookie = "jwt1=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/";
    isLoggedIn.value = false;

    if (router.currentRoute.value.name === 'home') {
        window.location.reload();
    } else {
        router.push({ name: 'home' });
    }

    ElMessage({
        message: '已成功退出登录',
        type: 'success',
    });
};
</script>


<template>
    <div id="navHeader" class="nav-header-box">
        <div class="header-wrap">
            <div class="slogan-box">
                <span class="text">网罗天下图书</span>
                <span>传承中华文明</span>
            </div>
            <div class="user-info-box clearfix">
                <!-- 登录/注册按钮仅未登录时显示 -->
                <template v-if="!isLoggedIn">
                    <login-component />
                    <register-component />
                </template>

                <!-- 登录后可见按钮 -->
                <template v-else>
                    <el-button class="button" @click="go2YourProfile" color="#e2c8ca" :dark="true" plain
                        link>个人中心</el-button>
                    <el-button class="button" @click="go2ChatRoom" color="#e2c8ca" :dark="true" plain
                        link>我的消息</el-button>
                    <el-button class="button" @click="go2Assistant" color="#e2c8ca" :dark="true" plain
                        link>AI客服</el-button>
                    <el-button class="button" @click="logout" color="#e2c8ca" :dark="true" plain link>退出登录</el-button>
                </template>
            </div>
        </div>
    </div>
</template>
<style>
.nav-header-box {
    height: 36px;
    background-color: #8c222c;
}

.header-wrap {
    position: relative;
    width: 1200px;
    height: 100%;
    margin: 0 auto;
}

.slogan-box {
    position: absolute;
    top: 0;
    left: 0;
    padding-left: 6px;
    height: 36px;
    line-height: 36px;
    font-size: 14px;
    color: #e2c8ca;
}

.text {
    margin-right: 4px;
}

.user-info-box {
    position: absolute;
    top: 0;
    right: 0;
    height: 36px;
    font-size: 0;
}

.user-info-box {
    position: absolute;
    top: 0;
    right: 0;
    height: 36px;
    font-size: 0;
    line-height: 36px;
}

.button {
    margin-left: 12px;
    padding: 2px;
}
</style>