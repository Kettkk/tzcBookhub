<!-- 中间导航栏首页内容 -->
<script lang="ts" setup>
import { ref } from 'vue'
import axios from "axios";
import router from '@/router';

const goldUserList = ref([])

// 获取金牌用户列表
axios.get('http://localhost:8000/api/getGeldUser').then(response => {
  console.log(response.data)
  goldUserList.value = response.data;
}).catch(error => {
  console.log(error)
});

// 点击跳转到用户详情页
const go2otherProfileView = (goldUser) => {
  console.log('go2otherProfileView');
  router.push({
    path: '/userProfile/otherProfile',
    query: {
      sellerID: goldUser.userID
    }
  });
}
</script>

<template>
  <div class="left-column">
    <!-- 公告栏 -->
    <div class="list-bulletinBoard">
      <div class="bulletinBoard-titleBox">
        <div class="bulletinBoard-text"><strong>公告栏</strong></div>
      </div>
      <div class="bulletinBoard-content">
        该网站仍处于测试阶段...<br><br>
        若发现网站有任何问题，请通过意见反馈栏内的邮箱和电话来联系我们。<br><br>
        十分感谢！
      </div>
    </div>

    <!-- 金牌用户 -->
    <div class="list-bestUsers">
      <div class="bestUsers-titleBox">
        <div class="bestUsers-text"><strong>金牌用户</strong></div>
      </div>
      <div class="bestUsers-content" v-if="goldUserList && goldUserList.length > 0">
        <div
            class="block"
            v-for="goldUser in goldUserList"
            :key="goldUser.userID"
            @click="go2otherProfileView(goldUser)"
        >
          <div class="block-picture">
            <el-avatar :src="goldUser.avatar" />
          </div>
          <div class="block-text">{{ goldUser.username }}</div>
          <div class="block-score">
            <el-rate v-model="goldUser.star" disabled text-color="#ff9900" />
          </div>
        </div>
      </div>
    </div>

    <!-- 意见反馈 -->
    <div class="list-feedback">
      <div class="feedback-titleBox">
        <div class="feedback-text"><strong>意见反馈</strong></div>
      </div>
      <div class="feedback-content">
        客服邮箱：<br>TZCBookHub@outlook.com<br>
        客服电话：<br>18868272792
      </div>
    </div>
  </div>
</template>

<style scoped>
.list-bulletinBoard,
.list-bestUsers,
.list-feedback {
  width: 200px;
}

.bulletinBoard-titleBox,
.bestUsers-titleBox,
.feedback-titleBox {
  height: 36px;
  line-height: 36px;
  background-color: #8c222c;
}

.bulletinBoard-text,
.bestUsers-text,
.feedback-text {
  color: white;
  width: 64px;
  margin: 0 auto;
}

.bulletinBoard-content,
.bestUsers-content,
.feedback-content {
  border: 2px solid #ddd;
  font-size: 13px;
}

.bulletinBoard-content {
  height: 400px;
  display: flex;
  justify-content: center;
}

.bestUsers-content {
  height: 285px;
}

.feedback-content {
  height: 400px;
}

.block {
  margin-top: 5px;
  height: 50px;
  cursor: pointer;
}

.block-picture {
  margin: 6px 8px 6px 8px;
  height: 40px;
  width: 40px;
  float: left;
}

.block-text {
  margin-top: 3px;
  height: 20px;
  line-height: 22px;
  float: left;
}

.block-score {
  margin-top: 25px;
  margin-left: -70px;
}
</style>