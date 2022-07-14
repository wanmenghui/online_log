<template>
  <div id="app" :style="attribute">
   
    <!-- 日志demo代码begin -->
    <el-button type="primary" @click="queryMuch">查询</el-button>

    <el-button type="primary" @click="update">修改</el-button>

    <el-button type="danger" plain @click="begin">开始录制</el-button>

    <el-button type="danger" plain @click="end">结束录制</el-button>

    


    <h3>sql信息</h3>
    <el-table
      :data="sqlTableData"
      style="width: 100%">
      <el-table-column
        prop="id"
        label="id"
        width="180">
      </el-table-column>
      <el-table-column
        prop="sqlContent"
        label="sql信息"
        width="1200">
      </el-table-column>
      <el-table-column
        prop="resultNumber"
        label="结果集">
      </el-table-column>
    </el-table>


    <h3>java堆栈信息</h3>
    <el-table
      :data="javaTableData"
      class="test">
      <el-table-column
        prop="id"
        label="id"
        width="180">
      </el-table-column>
      <el-table-column
        prop="stack"
        label="堆栈信息"
        width="1200">
      </el-table-column>
      <el-table-column
        prop="resultNumber"
        label="结果集">
      </el-table-column>
    </el-table>

    <!-- 日志demo代码end -->

  </div>
</template>

<script>
import Index from './components/Index.vue'

export default {
  name: 'App',
  components: {
    Index
  },
  data(){
    return{
      attribute: {
        backgroundImage: "url(" + require("../public/bg.jpg") + ")",
        backgroundRepeat: "no-repeat",
        backgroundSize: "100% 100%",
        width: "100%",
      },
      javaTableData: [{
            id: "",
            stack: ''
          }],
      sqlTableData: [{
          id: "",
          sqlContent: "",
          resultNumber: ''
      }],    
       beginTime: "",
       endTime: ""   
    }
  },
  methods: {
    queryMuch(){
      this.axios({
            url: '/queryMuch',   
            method: 'post'      
        }).then(res => {
          alert("查询成功");
          console.log(res);
        })
    },
    update() {
      this.axios({
            url: '/update',   
            method: 'post'      
        }).then(res => {
          alert("更新成功");
          console.log(res);
        })
    },
    begin() {
      let _this = this;
      this.axios({
            url: '/begin',   
            method: 'post'      
        }).then(res => {
          _this.beginTime = this.dateFtt("yyyy-MM-dd hh:mm:ss",new Date());
          alert("开始录制。。。。。。");
          console.log(res);
        })
    },
    end() {
      let _this = this;
      this.axios({
            url: '/end',   
            method: 'get',
            params: {              
              beginTime: this.beginTime,
              endTime: this.dateFtt("yyyy-MM-dd hh:mm:ss",new Date()),
              userId: "abc111"
            }     
        }).then(res => {
          console.log("结束录制返回数据： " + res)
          for(var key in res.data) {
            if(key == 0) {
              let tempData = res.data[key];

              console.log("aaaaaa"+tempData);


              tempData.forEach(item => {
                console.log(item);
              })


              for(var i = 0; i < tempData.length; i++){
                let obj = {
                  id: tempData[i].id,
                  stack: tempData[i].stack
                };
                _this.javaTableData.push(obj);
              }
            }else if(key == 1) {
              let tempData = res.data[key];
                for(var j = 0; j < tempData.length; j++){
                let obj2 = {
                  id: tempData[j].id,
                  sqlContent: tempData[j].sqlContent,
                  resultNumber: tempData[j].resultNumber
                };
                _this.sqlTableData.push(obj2);
              }
            }
          }
          alert("结束录制。。。。。。");
        })
    },
    dateFtt(fmt, date) { 
      var o = {
          "M+": date.getMonth() + 1,     //月份
          "d+": date.getDate(),     //日
          "h+": date.getHours(),     //小时
          "m+": date.getMinutes(),     //分
          "s+": date.getSeconds(),     //秒
          "q+": Math.floor((date.getMonth() + 3) / 3), //季度
          "S": date.getMilliseconds()    //毫秒
      };
      if (/(y+)/.test(fmt))
          fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
      for (var k in o)
          if (new RegExp("(" + k + ")").test(fmt))
              fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
      return fmt;
    }
  }
 
}



</script>

<style>
  .container {
    margin: 0;
    padding: 0;
    width: 100%;
    background-image: '../public/bg.jpg' !important;
  }
</style>