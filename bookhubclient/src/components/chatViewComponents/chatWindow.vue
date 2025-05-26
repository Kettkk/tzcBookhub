<template>
    <div>
        <el-container id="window">

            <!--聊天窗口左侧-->
            <el-aside id="window-side">
                <el-header id="side-title">
                    <div style="height: 25px;">
                        <el-icon size="25px">
                            <Message />
                        </el-icon>
                        <div style="font-size: 18px;width: 75px; height: 25px; float: right;margin-left: 5px;">
                            消息中心
                        </div>
                    </div>
                </el-header>

                <!--联系人列表-->
                <el-main id="contact-list">
                    <contactList @getContactName="emitGetContactName" @getContactId="emitGetContactId"></contactList>
                </el-main>
            </el-aside>


            <el-container id="window-main">

                <el-header id="main-title">
                    <div style="font-size: 18px;height: 25px;text-align: center;">{{ contactName }}
                    </div>
                </el-header>


                <el-main id="message-list">
                    <messageList :messages="messages" :receiverId="receiverId"></messageList>
                </el-main>

                <el-footer id="message-input">
                    <el-input v-model="messageText" maxlength="500" style="width: 650px" placeholder="请输入消息..."
                        resize="none" :rows="6" show-word-limit type="textarea" @keyup.enter="sendMessage" />
                </el-footer>

            </el-container>
        </el-container>
    </div>
</template>

<script setup>
import { ref, onMounted, watch,onBeforeUnmount } from 'vue'
import contactList from './contactList.vue';
import { Message } from '@element-plus/icons-vue';
import messageList from './messageList.vue'
import axios from "axios";
const messageText = ref('')
const contactName = ref('')

const senderId = ref('')
const receiverId = ref('')
const messages = ref([])

senderId.value = decodeURIComponent(
  document.cookie
    .split('; ')
    .find(item => item.startsWith('jwt1='))
    ?.split('=')[1] || ''
);

let socket = null;

onMounted(() => {
  socket = new WebSocket(`ws://localhost:8000/ws/message?userId=${senderId.value}`);
  socket.onopen = () => console.log('WebSocket 连接成功');
  socket.onmessage = (e) => {
    const msg = JSON.parse(e.data);
    // 防止重复，可以加简单判断，比如ID或者时间戳
    if (!messages.value.find(m => m.id === msg.id)) {
      messages.value.push(msg)
    }
    console.log('收到消息', msg);
  };
});

// 监听接收者变化，加载历史消息
watch(receiverId, (newId) => {
  if (!newId) return;
  messages.value = []; // 清空旧消息
  axios.get('http://localhost:8000/api/message/getMessageByChatId', {
    params: {
      senderId: senderId.value,
      receiverId: newId,
    }
  }).then(res => {
    console.log(res.data)
    messages.value = res.data || [];
  }).catch(err => {
    console.error('获取历史消息失败', err);
  });
})

const emitGetContactName = (data) => {
  contactName.value = data;
};

const emitGetContactId = (data) => {
  receiverId.value = data;
};

const sendMessage = () => {
  if (!messageText.value.trim()) return;

  const msgObj = {
    senderId: senderId.value,
    receiverId: receiverId.value,
    content: messageText.value.trim(),
  };

  socket.send(JSON.stringify(msgObj));
  messageText.value = '';
};

onBeforeUnmount(() => {
  if (socket) {
    socket.close();
    console.log('WebSocket 已关闭');
  }
});

</script>

<style scoped>
#window {
    width: 901px;
    height: 601px;
    display: flex;
    border-radius: 4px;
    overflow: hidden;
    background-color: white;
}

#window-side {
    width: 250px;
    height: 601px;
    overflow: hidden;
}

#side-title {
    width: 250px;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-width: 0px 0px 1px 0px;
    border-style: solid;
    border-color: #DCDCDC;
}

#contact-list {
    width: 250px;
    height: 540px;
}

#window-main {
    width: 650px;
    height: 600px;
    border-width: 0px 0px 0px 1px;
    border-style: solid;
    border-color: #DCDCDC;
}

#main-title {
    width: 650px;
    height: 60px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-width: 0px 0px 1px 0px;
    border-style: solid;
    border-color: #DCDCDC;
}

#message-list {
    height: 405px;
}


#message-input {
    height: 135px;
}

.el-container,
.el-header,
.el-main,
.el-footer {
    padding: 0px;
}
</style>