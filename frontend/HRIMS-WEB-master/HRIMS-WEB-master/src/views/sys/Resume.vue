<template>
    <div>
      <!-- <el-row>
            <el-col :span="6">
                <div class="mt-4">
                    <el-input placeholder="输入员工id查询" v-model="queryUserName" class="input-with-select">
                        <template #append>
                            <el-button type="primary" @click="queryByUserName">
                                <el-icon><Search /></el-icon>
                            </el-button>
                        </template>
                    </el-input>
                </div>
            </el-col>
            
            <el-col :span="2" :offset="12">
            </el-col>
        </el-row> -->
<el-row>
            <el-col :span="24">
        <el-table :data="paginatedTableData" style="width: 100%">
          <el-table-column prop="record_id" label="签到编号"></el-table-column>
          <el-table-column prop="employee_id" label="员工编号"></el-table-column>
          <el-table-column prop="clock_in_time" label="签到时间"></el-table-column>
          <el-table-column prop="clock_out_time" label="签退时间"></el-table-column>
          <el-table-column prop="attendance_date" label="日期"></el-table-column>
          <el-table-column fixed="right" min-width="120">
            <template #default="scope">
              <el-button type="danger" size="small" @click="deleteUser(scope.row.record_id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
      <el-row>
      <el-pagination
    :page-size="pageSize"
    :pager-count="11"
    layout="prev, pager, next"
    :total="50"
    @current-change="handlePageChange"

  />

    </el-row>
    </div>
</template>

<script>
import axios from 'axios';
import { ElMessage } from 'element-plus';  // 确保引入 ElMessage
import { Confirm } from 'notiflix/build/notiflix-confirm-aio'
import { Search, SortUp, SortDown, CirclePlus } from '@element-plus/icons-vue'
    export default {
        name: 'SysResumeSource',
        components: {
        Search,
        SortUp,
        SortDown,
        CirclePlus
    },
        data() {
    return {
        tableData: [],
        queryUserName: '',
        currentPage: 1,
      pageSize: 10,
      paginatedTableData: []
    }
    },
    created() {
      this.fetchEmployeeList()

  },


  methods: {

    handlePageChange(page) {
      this.currentPage = page;
      const start = (page - 1) * this.pageSize;
      this.paginatedTableData = this.tableData.slice(start, start + this.pageSize);
    },

    async fetchEmployeeList() {
  try {
    // 发送 GET 请求获取员工列表
    const response = await axios.get('http://localhost:8080/api/record');
    
    // 确保返回的数据是有效的
    if (response.status === 200) {
      // 更新数据，假设响应的数据结构是 { data: [...] }
      this.tableData = response.data; 
      this.employeeList = response.data;
        // 数据加载完成后，设置当前页码并触发分页逻辑
        this.currentPage = 1; // 通常这不需要显式设置，除非你有特定的逻辑需要这样做
        this.handlePageChange(this.currentPage); 
      console.log('员工列表获取成功:', this.tableData);
    } else {
      ElMessage.error('获取员工列表失败!');
    }
  } catch (error) {
    // 处理请求失败的情况
    console.error('获取员工列表时发生错误:', error);
    ElMessage.error('获取员工列表失败，请稍后再试');
  }
},
deleteUser(userId) {
      // 弹出确认框
      Confirm.show('警告', '确定删除该用户?', '删除', '取消', () => {
        // 在确认后，删除该员工
        // 发起 DELETE 请求到后端
        axios.delete(`http://localhost:8080/api/deleterecord/${userId}`)
          .then(response => {
            // 成功删除后，刷新页面或更新数据
            ElMessage('删除成功');
            this.fetchEmployeeList();
          })
          .catch(error => {
            console.error('删除失败:', error);
            ElMessage('删除失败，请稍后再试');
          });
      });
    },
    queryByUserName() {
      const query = parseInt(this.queryEmployeeId.trim(), 10);
 
      if (!isNaN(query)) {
    // 如果查询数字有效，精确查找数据
    this.paginatedTableData = this.paginatedTableData.filter(user =>
      user.employee_id === query // 假设employee_id也是数字类型，或者已经是解析为数字的字符串
    );
  } else {
    // 如果查询框输入无效（不是数字），或者为空，重新获取所有数据
    this.fetchEmployeeList();
  }
    }
  },
}
</script>

<style lang="scss">

</style>