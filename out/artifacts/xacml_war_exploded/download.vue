<template>
    <div id="box">
        <input type="button" @click="getdata()" value=" 小熊点点它">
        <!-- <h1>site : {{data}}</h1> -->
    </div>
</template>

<script type="text/javascript">
    var data = "";

    export default {
        data() {
            return {
                data
            };
        },
        methods: {
            getdata() {
                // get请求
                this.$axios.get("这里写请求的url").then(res => {
                    alert("请求成功，response=" + res.data);
                    // data = res.data;
                    console.log(res.data.navList);
                });
            }
        }
    };
</script>