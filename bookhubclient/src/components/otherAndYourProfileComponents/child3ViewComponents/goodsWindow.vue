<script setup>
import { useRouter } from 'vue-router'
const router = useRouter()

const props = defineProps({
  good: {
    type: Object,
    required: true
  }
})

const go2BuyGoodsView = () => {
  router.push({
    name: "buyGoods",
    query: {
      bookID: props.good.goodID,
      sellerID: props.good.sellerID
    }
  })
}
</script>

<template>
  <div id="window" @click="go2BuyGoodsView">
    <el-card style="max-width: 480px">
      <div class="image-container">
        <!-- 直接显示 public 目录下的图片 -->
        <img :src="good.goodImg" class="image" />
      </div>

      <hr class="separator" />
      <div style="padding-top: 5px">
        <span><strong>{{ good.goodName }}</strong></span>
        <br />
        <span style="font-size: 20px; color: #5d85ad; padding-top: 8px">
          <strong>{{ good.goodValue }} ¥</strong>
        </span>
      </div>
    </el-card>
  </div>
</template>

<style scoped>
.separator {
  margin: 5px 0;
  border: none;
  background-color: #f6f8fa;
  border-top: 1px solid #d9d8d8;
}
#window {
  background-color: #f6f8fa;
  width: 220px;
  height: 280px;
  margin-left: 20px;
  margin-top: 40px;
  cursor: pointer;
}
.image-container {
  width: 100%;
  height: 200px;
  overflow: hidden;
}
.image {
  width: 100%;
  height: 100%;
  object-fit: cover;
}
</style>