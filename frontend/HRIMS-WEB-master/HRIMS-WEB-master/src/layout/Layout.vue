<template>
    <div class="common-layout">
        <el-container>
            <el-aside class="app-main-side" :style="defaultHeight" :width="asideWidth">
                <el-affix :z-index="1200">
                    <div class="aside-logo" @click="onRefresh">
                        <el-image class="logo-image" :src="logo" fit="contain" />
                        <span v-show="!isCollapse" class="app-title">
                            <span>HRIMS</span>
                        </span>
                    </div>
                </el-affix>
                <el-menu router :default-active="$route.path" :collapse="isCollapse" :collapse-transition="false" @select="selectPage">
                    <div v-for="menu in routers" :key="menu">
                        <el-menu-item v-if="menu.children" :index="menu.children[0].path">
                            <el-icon>
                                <component :is="menu.children[0].icon"></component>
                            </el-icon>
                            <template #title>
                                {{ menu.children[0].name }}
                            </template>
                        </el-menu-item>
                    </div>
                </el-menu>
            </el-aside>
            <el-container>
                <el-header>
                    <div class="app-main-header">
                        <div class="app-side-btn">
                            <div @click="onCollapse" v-if="isCollapse">
                                <el-icon>
                                    <expand />
                                </el-icon>
                            </div>
                            <div @click="onCollapse" v-else>
                                <el-icon>
                                    <fold />
                                </el-icon>
                            </div>
                        </div>
                        <div>
                            <span style="font-weight: 400; color: #606266; cursor: text">{{ $route.name }}</span>
                        </div>
                        <div class="app-quit" @click="signout">退出</div>
                    </div>
                </el-header>
                <el-main>
                    <router-view v-slot="{ Component }">
                        <keep-alive>
                            <component :is="Component" v-if="$route.meta.keepAlive" :key="$route.name" />
                        </keep-alive>
                        <component :is="Component" v-if="!$route.meta.keepAlive" :key="$route.name" />
                        <!-- <component :is="Component" :key="$route.name" /> -->
                    </router-view>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
// 从vue库导入reactive, toRefs, 和 onBeforeMount函数
import { reactive, toRefs, onBeforeMount } from 'vue'
// 从vue-router库导入useRouter函数
import { useRouter } from 'vue-router'
// 从@element-plus/icons-vue库导入一系列的图标组件
import { User, OfficeBuilding, Setting, Location, Tickets, Expand, Fold } from '@element-plus/icons-vue'

export default {
    name: 'Layout', // 定义组件名称为Layout
    components: { // 注册子组件
        User,
        OfficeBuilding,
        Location,
        Tickets,
        Setting,
        Expand,
        Fold
    },
    setup() { // 使用Composition API的setup函数
        const router = useRouter() // 使用Vue Router的useRouter函数获取路由实例

        // 创建一个响应式状态对象
        const state = reactive({
            logo: require('@/assets/images/logo.png'), // 引入一个logo图片
            isCollapse: false, // 侧边菜单栏是否展开的状态
            asideWidth: '160px', // 侧边栏的宽度
            defaultHeight: { // 默认高度对象
                height: ''
            },
            routers: [] // 存储路由配置的数组
        })

        onBeforeMount(() => { // 组件挂载前的生命周期钩子
            let token = localStorage.getItem('hrims-token') // 尝试从localStorage获取token
            if (token == null || token.length == 0) { // 如果没有token
                // router.push({ path: '/login' }) // 跳转到登录页面（这行代码被注释掉了）
                console.log('请完成登录') // 打印提示信息
            }
            const myRouters = router.options.routes // 获取路由配置
            const currentRole = localStorage.getItem('hrims-role') // 从localStorage获取当前用户角色
            myRouters.forEach((element) => { // 遍历路由配置
                if (element.meta) { // 如果路由配置有meta属性
                    if ('0' === currentRole && element.meta.requireAdmin) { // 如果是管理员且路由需要管理员权限
                        state.routers.push(element) // 将该路由添加到state.routers
                    } else if ('0' != currentRole && !element.meta.requireAdmin) { // 如果是普通用户且路由不需要管理员权限
                        state.routers.push(element) // 将该路由添加到state.routers
                    }
                }
            })

            const currentPage = sessionStorage.getItem('hrims-current-page') // 从sessionStorage获取当前页面
            let paths = state.routers.map((item) => { // 获取所有路由的路径
                return item.path
            })
            let currentRoutePath = state.routers[0].path // 默认路由路径为第一个路由的路径
            if (currentPage) { // 如果有当前页面
                if (paths.includes(currentPage)) { // 如果当前页面在路由列表中
                    router.push({ path: currentPage }) // 跳转到当前页面
                } else { // 如果当前页面不在路由列表中
                    router.push({ path: currentRoutePath }) // 跳转到默认路由路径
                }
            } else { // 如果没有当前页面
                router.push({ path: currentRoutePath }) // 跳转到默认路由路径
            }
            state.defaultHeight.height = document.body.clientHeight + 'px' // 设置默认高度为页面可视高度
        })

        // 定义一些方法
        const onRefresh = () => {
            console.log('回到主页面') // 打印提示信息
            // router.push({ path: 'main' }) // 跳转到主页面（这行代码被注释掉了）
        }

        const selectPage = (index) => { // 根据提供的路径索引跳转到指定页面
            sessionStorage.setItem('hrims-current-page', index) // 将当前页面路径保存到sessionStorage
            router.push({ path: index }) // 跳转到指定路径
        }

        const signout = () => { // 注销功能
            localStorage.removeItem('hrims-token') // 移除localStorage中的token
            router.push({ path: '/login' }) // 跳转到登录页面
        }

        const onCollapse = () => { // 侧边栏展开/收起功能
            if (state.isCollapse) { // 如果当前是收起状态
                state.asideWidth = '160px' // 设置侧边栏宽度为160px
                state.isCollapse = false // 更新状态为展开
                console.log(state.defaultHeight.height) // 打印默认高度
            } else { // 如果当前是展开状态
                state.asideWidth = '64px' // 设置侧边栏宽度为64px
                state.isCollapse = true // 更新状态为收起
                console.log(state.defaultHeight.height) // 打印默认高度
            }
        }

        // 返回响应式状态和方法，以便在模板中使用
        return { ...toRefs(state), onCollapse, onRefresh, selectPage, signout }
    }
}
</script>

<style>
.el-header {
    --el-header-padding: 2px !important;
}

.el-menu {
    border-right: none !important;
}

.aside-logo {
    height: 3.5rem;
    cursor: pointer;
    /* border-bottom: 1px #cccccc solid; */
}

.logo-image {
    width: 40px;
    height: 40px;
    top: 12px;
    display: flex;
    padding-left: 12px;
    align-items: center;
}

.app-main-header {
    background: #ffffff;
    color: #333333;
    display: flex;
    align-items: center;
    padding: 0 1.5rem;
    height: 3.5rem;
    width: 100%;
    position: relative;
    box-shadow: 0 0 13px 0 rgb(0 0 0 / 7%);
    z-index: 999;
}

.app-main-side {
    background: #ffffff;
    color: #333333;
    box-shadow: 4px 0 5px rgb(0 0 0 / 7%);
}

.app-side-btn {
    padding-right: 0.83rem;
    padding-top: 0.45rem;
    font-size: 1.25rem;
    color: #004450;
    font-weight: 700;
    display: flex;
    align-items: center;
    top: 1rem;
}

.app-title {
    padding-left: 0.83rem;
    font-size: 1.25rem;
    color: #004450;
    font-weight: 700;
}

.app-quit {
    cursor: pointer;
    padding-right: 0.83rem;
    font-size: 1.25rem;
    color: #004450;
    font-weight: 700;
    margin-right: 1rem;
    margin-left: auto;
    align-items: center;
    justify-content: flex-end;
}
</style>
