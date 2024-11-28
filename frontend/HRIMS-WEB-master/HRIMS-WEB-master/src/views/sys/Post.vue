<template>
    <div>
        <el-row>
            <el-col :span="6">
                <div class="mt-4">
                    <el-input placeholder="输入姓名查询" v-model="queryUserName" class="input-with-select">
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
        </el-row>
        <el-row>
            <el-col :span="24">
                <el-table :data="paginatedTableData" style="width: 100%">
                    <el-table-column prop="id" label="应聘者编号">
                </el-table-column>
                    <el-table-column prop="name" label="姓名" >
                    </el-table-column>
                    <el-table-column prop="sex" label="性别" >
                    </el-table-column>
                    <el-table-column prop="age" label="年龄" >
                    </el-table-column>
                    <el-table-column prop="position" label="应聘岗位">
                    </el-table-column>
                     <el-table-column fixed="right" min-width="120">
                        <template #default="scope">
                            <el-button type="primary" size="small" @click="openDialog(scope.row.id)">聘用</el-button>
                            <el-button type="danger" size="small" @click="deleteUser(scope.row.id)">删除</el-button>
                        </template>
                    </el-table-column> 
                </el-table>
            </el-col>
         </el-row>
         <el-dialog title="添加员工" v-model="dialogVisible" width="500px">
      <el-form :model="employeeForm" ref="employeeForm" label-width="100px">
        <el-form-item label="编号" prop="id" :rules="[{ required: true, message: '请输入员工编号', trigger: 'blur' }]">
          <el-input v-model="employeeForm.id"></el-input>
        </el-form-item>

        <el-form-item label="姓名" prop="name" :rules="[{ required: true, message: '请输入员工姓名', trigger: 'blur' }]">
          <el-input v-model="employeeForm.name"></el-input>
        </el-form-item>

        <el-form-item label="性别" prop="sex" :rules="[{ required: true, message: '请选择员工性别', trigger: 'change' }]">
          <el-radio-group v-model="employeeForm.sex">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="年龄" prop="age" :rules="[{ required: true, message: '请输入员工年龄', trigger: 'blur' }]">
          <el-input v-model="employeeForm.age" type="number"></el-input>
        </el-form-item>

        <el-form-item label="部门" prop="department" :rules="[{ required: true, message: '请输入员工部门', trigger: 'blur' }]">
          <el-input v-model="employeeForm.department"></el-input>
        </el-form-item>

        <el-form-item label="岗位" prop="position" :rules="[{ required: true, message: '请输入员工岗位', trigger: 'blur' }]">
          <el-input v-model="employeeForm.position"></el-input>
        </el-form-item>

        <el-form-item label="薪水" prop="price" :rules="[{ required: true, message: '请输入员工薪水', trigger: 'blur' }]">
          <el-input v-model="employeeForm.price" type="number"></el-input>
        </el-form-item>
      </el-form>

      <!-- 使用 v-slot 来代替 slot="footer" -->
      <template v-slot:footer>
        <el-button @click="closeDialog">取消</el-button>
        <el-button type="primary" @click="submitForm">提交</el-button>
      </template>
    </el-dialog>
      <el-pagination
    :page-size="pageSize"
    :pager-count="11"
    layout="prev, pager, next"
    :total="50"
    @current-change="handlePageChange"

  />
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
import { ElMessage } from 'element-plus';  // 确保引入 ElMessage
import axios from 'axios';
    export default {
        name: 'SysPost',
        components: {
        Search,
        SortUp,
        SortDown,
        CirclePlus
    },
    data() {
      return {
        tableData: [],
        dialogVisible: false,
        queryUserName: '',
        currentPage: 1,
      pageSize: 15,
      paginatedTableData: [],
      employeeForm: {
        id: '',
        name: '',
        sex: '',
        age: null,
        department: '',
        position: '',
        price: null,
      }
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
    const response = await axios.get('http://localhost:8080/api/applicant');
    
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
        axios.delete(`http://localhost:8080/api/deleteapplicant/${userId}`)
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
    openDialog(userId) {
      const user = this.tableData.find(user => user.id === userId);
      this.employeeForm = { ...user };
      this.dialogVisible = true;
    },

    closeDialog() {
      this.dialogVisible = false;
    },
    async submitForm() {
      try {
        const response = await axios.post('http://localhost:8080/api/addemployees', this.employeeForm);
        if (response.status === 200) {
          ElMessage.success('员工添加成功!');
          const user = this.tableData.find(user => user.name === this.employeeForm.name);
          this.dialogVisible = false;
          this.employeeForm={};
          axios.delete(`http://localhost:8080/api/deleteapplicant/${user.id}`)
          .then(response => {
            // 成功删除后，刷新页面或更新数据
            this.fetchEmployeeList();
          })
        } else {
          ElMessage.error('提交失败!');
        }
      } catch (error) {
        console.error('提交表单时发生错误:', error);
        ElMessage.error('提交失败，请稍后再试');
      }
    },
    queryByUserName() {
      const query = this.queryUserName.trim().toLowerCase();
      if (query) {
        this.paginatedTableData = this.paginatedTableData.filter(user =>
          user.name.toLowerCase().includes(query)
        );
      } else {
        // 如果查询框为空，显示所有数据
        this.fetchEmployeeList();
      }
    }
}
    }
</script>

<style lang="scss"></style>

    


<style lang="scss">

</style>