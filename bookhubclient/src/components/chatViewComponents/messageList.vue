<template>
  <div>
    <div v-for="(message, index) in props.messages" :key="index">
      <messageSent
        v-if="String(message.senderId) === String(senderId)"
        :messageSentText="message.content"
      />
      <messageReceived
        v-else
        :messageReceivedText="message.content"
      />
    </div>
  </div>
</template>


<script setup>
import { ref } from "vue";
import MessageReceived from "@/components/chatViewComponents/messageReceived.vue";
import MessageSent from "@/components/chatViewComponents/messageSent.vue";

const props = defineProps({
  messages: {
    type: Array,
    required: true,
  },
});

const senderId = ref("");

senderId.value = decodeURIComponent(
  document.cookie
    .split("; ")
    .find((item) => item.startsWith("jwt1="))
    ?.split("=")[1] || ""
);

</script>
