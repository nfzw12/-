<template>
    <div>
        <el-row>
      <el-col :span="24">
        <el-table :data="tableData" style="width: 100%">
          <el-table-column prop="id" label="员工编号"></el-table-column>
          <el-table-column prop="name" label="姓名"></el-table-column>
          <el-table-column prop="sex" label="性别"></el-table-column>
          <el-table-column prop="age" label="年龄"></el-table-column>
          <el-table-column prop="department" label="部门"></el-table-column>
          <el-table-column prop="position" label="职位"></el-table-column>
          <el-table-column prop="price" label="薪水"></el-table-column>
          <el-table-column fixed="right" min-width="120">
        </el-table-column>
    </el-table>
      </el-col>
        </el-row>
       <el-row> 
    <el-button type="primary" @click="clock_in()">
      <el-icon><CirclePlus /></el-icon>
      员工签到
    </el-button>
    <el-button type="primary" @click="clock_out()">
      <el-icon><CirclePlus /></el-icon>
      员工签退
    </el-button>
       </el-row>

    </div>
</template>

<script>
import { computed, reactive, onBeforeMount, toRefs } from 'vue'
import { Confirm } from 'notiflix/build/notiflix-confirm-aio'
import { Notify } from 'notiflix/build/notiflix-notify-aio'
import { Search, SortUp, SortDown, CirclePlus } from '@element-plus/icons-vue'
import { userList } from '@/api/request/user.js'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'
import { ref } from 'vue';
import axios from 'axios';
import { ElMessage } from 'element-plus';  // 确保引入 ElMessage
import { mapGetters } from 'vuex';

export default {
  name: 'Ent',
  data() {
    return {
      tableData: [],
      employeeId:''
    };
  },

  async created() {
    try {
      this.employeeId = localStorage.getItem('hrims-role')
      // 确保 employeeId 在 Vuex store 中是可用的
       if (this.employeeId) {
        const response = await axios.post(`http://localhost:8080/api/searchemployees/${this.employeeId}`);
        if (response.status === 200) {
          this.tableData = response.data;
        } else {
          console.error('请求失败，状态码：', response.status);
        }
      } else {
        console.error('employeeId 在 Vuex store 中不可用');
      } 
    } catch (error) {
      console.error('请求出错：', error);
    }
  },
  // 注意：这里没有使用 userId 计算属性，因为我们已经通过 mapGetters 获取了 employeeId
  methods:{
   clock_in(){
    axios.post(`http://localhost:8080/api/empclock_in/${this.employeeId}`).then(response =>{
    if(response.status==200)ElMessage.success("签到成功");
    else ElMessage.error("签到失败")
   }  
  ).catch(e=>{
    console.log(e)
  })
  },
   clock_out(){
    axios.post(`http://localhost:8080/api/empclock_out/${this.employeeId}`).then(response =>{
    if(response.status==200)ElMessage.success("签退成功");
    else ElMessage.error("签退失败")
   }  
  ).catch(e=>{
    console.log(e)
  })
  }
}
};
</script>

<style lang="scss">

</style>