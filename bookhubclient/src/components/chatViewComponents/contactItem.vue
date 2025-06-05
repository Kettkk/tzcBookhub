<template>
    <div id="contact" @mouseover="showButton" @mouseleave="hideButton" >

        <el-avatar id="contact-avatar" size='large' :src="props.item.avatar" />

        <div id="contact-name">{{ props.item.username }}</div>

    </div>
</template>

<script setup>

const props = defineProps({
    item: {
        type: Object,
        required: true
    },
    otherID: {
        type: Number,
        required: true
    }
})

</script>

<style scoped>
#contact {
    height: 80px;
    display: flex;
    align-items: center;
    background-color: white;
    border-width: 0px 0px 1px 0px;
    border-color: #DCDCDC;
    border-style: solid;
    cursor: pointer;

}


#contact-avatar {
    margin: 0px 18px 0px 15px;
}

#contact-name {
    line-height: 80px;
    height: 80px;
    font-size: 15px;
}
</style>