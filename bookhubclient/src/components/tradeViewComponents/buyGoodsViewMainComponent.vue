<script setup>
import router from "@/router/index.js";
import axios from "axios";
import { reactive, ref } from 'vue';
import { useRoute } from 'vue-router';
import { ElMessage } from 'element-plus'

const route = useRoute();

const information = reactive({
  sellerName: '',
  sellerAvatar: '',
  value: 0,
  bookName: '',
  price: '',
  goodDescription: '',
  bookImg: '',
});

const purchaseBookID = ref('');
const purchasesellerID = ref('')
purchaseBookID.value = route.query.bookID
purchasesellerID.value = route.query.sellerID

console.log(purchaseBookID.value, purchasesellerID.value)

// ✅ 修改为使用本地接口并使用 GET 请求传递 goodID 参数
const url = 'http://localhost:8000/api/getGoodById?id=' + purchaseBookID.value;

axios.get(url)
  .then(function (response) {
    const goodData = response.data.good;
    const sellerData = response.data.seller;

    information.bookName = goodData.goodName;
    information.price = goodData.goodValue;
    information.goodDescription = goodData.goodInfo;
    information.bookImg = goodData.goodImg;

    information.sellerName = sellerData.username;
    information.sellerAvatar = sellerData.avatar;
    information.value = sellerData.star;

    console.log(response.data);
  })

const go2ChatView = () => {
  axios.post('http://localhost:8000/api/chat/save', {
    receiverId: purchasesellerID.value
  }, {
    withCredentials: true
  })
    .then(response => {
      console.log(response);
      if (response.data != -1) {
        router.push('/chatRoom');
      }

    })
    .catch(error => {
      console.error(error);
    });
}

const go2otherProfileView = () => {
  router.push({
    path: '/userProfile/otherProfile',
    query: {
      sellerID: purchasesellerID.value
    }
  });
}

const purchaseThisBook = () => {
  const userID = document.cookie.split('=')[1]
  console.log('userID:', userID, 'purchasesellerID:', purchasesellerID.value, 'purchaseBookID:', purchaseBookID.value)
  //如果userID = purchasesellerID.value，则提示错误
  if (userID == purchasesellerID.value) {
    ElMessage({
      message: '无法购买自己发布的商品',
      type: 'error',
      plain: true,
    })
    return;
  }
  // 向后端发送购买请求
  axios.post('http://localhost:8000/api/Purchase', {
    userId: userID,
    sellerID: purchasesellerID.value,
    goodId: purchaseBookID.value
  })
    .then(function (response) {
      if (response.data == "余额不足，无法购买商品") {
        ElMessage({
          message: '余额不足，无法购买商品',
          type: 'error',
          plain: true,
        })
      } else {
        ElMessage({
          message: '购买成功',
          type: 'success',
          plain: true,
        })
        router.replace('/userProfile/MyOrders?personalID=' + response.data)
      }
    })
    .catch(function (error) {
      console.log(error);
    });
}
</script>

<template>
  <div>
    <div id="titleContainer">书籍购买 &emsp;>> &emsp;{{ information.bookName }}</div>

    <div style="display: flex">
      <el-aside>
        <div id="goodImgContainer">
          <img :src="information.bookImg" alt="书籍图片" width="300px" height="300px">
        </div>

        <div id="goodPriceContainer">价格&emsp;
          <div style="color: #e33546;font-size: 22px;">{{ information.price }} &ensp;¥</div>
        </div>
      </el-aside>

      <el-main>
        <div id="sellerInfoContainer">
          <div @click="go2otherProfileView" id="avatarContainer">
            <el-avatar :src="information.sellerAvatar" :size="'large'" />
          </div>
          <div @click="go2otherProfileView" id="sellerNameAndStarsContainer">
            <div style="display: flex;margin-top: 8px;">
              <div style="font-size: 12px;margin-top: 8px">用户名:&ensp;</div>
              {{ information.sellerName }}
            </div>

            <div style="margin-top: 8px">
              <el-rate v-model="information.value" :size="'small'" disabled text-color="#ff9900" />
            </div>
          </div>

          <div id="sendMessageBtn">
            <el-button round :size="'large'" @click="go2ChatView" id="sendMessageBtn">联系卖家</el-button>
          </div>
        </div>



        <div id="goodDescriptionContainer">

          <div id="scrollable-container">
            <el-card style="max-width: 800px; min-height: 300px">
              <template #header>商品描述</template>
              {{ information.goodDescription }}
            </el-card>
          </div>
        </div>

        <div style="margin: 50px 0px 0px 1070px;">
          <el-button type="primary" size="large" @click="purchaseThisBook">购买</el-button>
        </div>
      </el-main>

    </div>
  </div>
</template>

<style scoped>
#titleContainer {
  height: 40px;
  background-color: #ededee;
  line-height: 40px;
  padding-left: 10px;
  font-size: 15px;
  font-family: 'Apple Braille';
  color: #3d3939;
}

#goodImgContainer {
  width: 300px;
  height: 300px;
  background-color: #f6f8fa;
  margin-top: 35px;
  margin-left: 50px;
  border-radius: 4px
}

#goodPriceContainer {
  width: 300px;
  height: 40px;
  background-color: #e9e9ea;
  margin-left: 50px;
  font-size: 16px;
  font-family: "Microsoft YaHei";
  color: #939292;
  display: flex;
  padding-left: 35px;
  padding-top: 20px;
}

#sellerInfoContainer {
  height: 80px;
  width: 100%;
  background-color: #e9e9ea;
  margin-top: 25px;
  display: flex;
}

#avatarContainer {
  height: 70px;
  width: 70px;
  background-color: #e9e9ea;
  padding-top: 10px;
  padding-left: 10px;
  cursor: pointer;
}

#sellerNameAndStarsContainer {
  height: 70px;
  width: 200px;
  background-color: #e9e9ea;
  padding-top: 10px;
  padding-left: 20px;
  font-size: 22px;
  font-family: 'Apple Braille';
  color: #3d3939;
  cursor: pointer;
}

#sendMessageBtn {
  margin-top: 10px;
  margin-left: 370px;
}

#goodDescriptionContainer {
  height: 300px;
  width: 800px;
  background-color: #e9e9ea;
  margin-top: 25px;
  margin-left: 50px;
  border-radius: 4px;
}

#scrollable-container {
  height: 300px;
  overflow-y: auto;
}
</style>