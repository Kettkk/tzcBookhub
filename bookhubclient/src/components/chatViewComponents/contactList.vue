<template>
  <div>
    <el-scrollbar max-height="540px">
      <contactItem v-for="(item, index) in contactList" :item="item" :key="index" :otherID="item.otherID"
                   :class="{ selected: index === selectedIndex }" @click="select(index)"></contactItem>
    </el-scrollbar>
  </div>
</template>

<script setup>
import contactItem from './contactItem.vue';
import { ref ,onMounted, defineEmits} from 'vue';
import axios from "axios";

let contactList = ref([]);
let selectedIndex = ref(0);

const emits = defineEmits(["getContactName"]);

onMounted(() => {
  axios.get('http://localhost:8000/api/chat/getChat', {
    withCredentials: true
  }).then(response => {
    console.log(response.data);
    contactList.value = response.data;

    
    if (contactList.value.length > 0) {
      select(0);
    }
  }).catch(error => {
    console.error(error)
  });
});


const select = (index) => {
  selectedIndex.value = index;
  emits("getContactName", contactList.value[index].username);
  emits("getContactId", contactList.value[index].userID)
}


</script>

<style scoped>
.selected {
  box-shadow: inset 0px 0px 10px #D8D8D8;
}
</style>
