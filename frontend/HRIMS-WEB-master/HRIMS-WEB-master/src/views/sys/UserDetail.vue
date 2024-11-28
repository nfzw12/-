<template>
    <el-row class="container-shadow">
        <el-col :span="24" style="text-align: center">
            <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
                <el-tab-pane name="zero">
                    <template #label>
                        <el-button type="primary" @click="backToPrePage" style="padding-bottom: 0.3rem">
                            <el-icon><ArrowLeft /></el-icon>
                        </el-button>
                    </template>
                </el-tab-pane>
                <el-tab-pane label="企业分配" name="first">
                    <el-transfer v-model="bindEnt" filterable :filter-method="filterMethod" :button-texts="['取消', '分配']" :titles="['未分配', '已分配']" filter-placeholder="企业名称搜索" :data="allEntData" @change="handleChange" />
                    <div style="height: 8rem"></div>
                </el-tab-pane>
                <el-tab-pane label="用户设置" name="second">
                    <div>
                        <h2>用户详情</h2>
                        <h2>{{ userId }}</h2>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </el-col>
    </el-row>
</template>

<script>
import { useStore } from 'vuex'
import { onBeforeMount, reactive, toRefs } from 'vue'
import { ArrowLeft, StarFilled } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { unbindEntList } from '@/api/request/ent.js'
import { distributeRelation, releaseRelation } from '@/api/request/relation.js'
import { Notify } from 'notiflix/build/notiflix-notify-aio'

export default {
    name: 'UserDetail',
    components: {
        ArrowLeft,
        StarFilled
    },
    setup() {
        const router = useRouter()

        const userDetailData = reactive({
            userId: '',
            activeName: 'first',
            allEntData: [],
            // 已绑定的key - entId
            bindEnt: [],
            relationValues: []
        })

        const store = useStore()

        onBeforeMount(() => {
            userDetailData.userId = store.state.userId
            console.log('GetCurrentUserId', userDetailData.userId)
            querUnbindEntList()
        })

        const querUnbindEntList = async () => {
            let param = { userId: userDetailData.userId, entName: '' }
            let response = await unbindEntList(param)
            if (response.status === 0) {
                console.log(response.data)
                buildTranferDate(response.data)
            } else {
                Notify.failure(response.msg)
            }
        }

        const buildTranferDate = (responseData) => {
            responseData.forEach((item) => {
                userDetailData.allEntData.push({
                    key: item.entId,
                    label: item.entName
                })
                if (item.bind) {
                    userDetailData.bindEnt.push(item.entId)
                }
            })
        }

        const filterMethod = (query, item) => {
            return item.label.toLowerCase().includes(query.toLowerCase())
        }

        // 返回
        const backToPrePage = () => {
            router.back()
        }

        const handleClick = (tab, event) => {
            console.log('点击标签页')
        }

        const handleChange = (value, direction, moveKeys) => {
            console.log(value)
            console.log(direction)
            console.log(moveKeys)
            if (direction === 'right') {
                distribute(value)
            } else if (direction === 'left') {
                release(moveKeys)
            }
        }

        const distribute = async (value) => {
            let param = { key: userDetailData.userId, values: value }
            console.log(param)
            let response = await distributeRelation(param)
            if (response.status === 0) {
                Notify.success(response.msg)
            } else {
                Notify.failure(response.msg)
            }
        }

        const release = async (value) => {
            let param = { key: userDetailData.userId, values: value }
            console.log(param)
            let response = await releaseRelation(param)
            if (response.status === 0) {
                Notify.success(response.msg)
            } else {
                Notify.failure(response.msg)
            }
        }

        return {
            ...toRefs(userDetailData),
            backToPrePage,
            handleClick,
            filterMethod,
            handleChange
        }
    }
}
</script>

<style>
.container-shadow {
    padding: 0.5rem;
    margin-top: 0.3rem;
    box-shadow: 0px 12px 32px 4px rgba(0, 0, 0, 0.04), 0px 8px 20px rgba(0, 0, 0, 0.08);
    border-radius: 4px;
}

.el-tabs__nav-wrap::after {
    margin-top: 12px !important;
    background-color: unset !important;
    border-bottom: 1px solid #9e9e9e !important;
}
</style>
