<template>
    <div class="login">
        <el-row :gutter="10" class="login-container">
            <el-form label-width="1em" class="login-body">
                <el-image class="login-logo" :src="logoImg"></el-image>
                <el-form-item prop="username">
                    <el-input class="circle-input" v-model="username" placeholder="请输入用户名称">
                        <template #prepend>
                            <el-icon><IconUser /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input class="circle-input" type="password" v-model="password" show-password placeholder="请输入密码">
                        <template #prepend>
                            <el-icon><IconLock /></el-icon>
                        </template>
                    </el-input>
                </el-form-item>
                <el-form-item class="login-body-button">
                    <el-button class="login-submit" type="danger" :loading="load" round @click="signin">登录</el-button>
                </el-form-item>
            </el-form>
        </el-row>
    </div>
</template>

<script>
import { reactive, toRefs } from 'vue'
import { Notify } from 'notiflix/build/notiflix-notify-aio'
import { Lock as IconLock, User as IconUser } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'
import { mapActions } from 'vuex';
export default {
    name: 'Login',
    components: {
        IconLock,
        IconUser
    },
    setup() {
        Notify.init({
            // 延时1秒
            timeout: 1000,
            // 点击关闭
            clickToClose: true
        })

        const router = useRouter()
        const loginForm = reactive({
            username: '',
            password: '',
            logoImg: require('@/assets/images/logo.png'),
            load: false
        })

        // 登录
        const signin = async () => {
            
            loginForm.load = true
            // const loginParam = { loginName: loginForm.username, password: loginForm.password }
            Notify.success('登录成功')

            localStorage.setItem('hrims-role', loginForm.username)
            if ('0' === loginForm.username) {
                router.push({ path: '/sysuser' })
            } else  {
                router.push({ path: '/ent' })
            }
            loginForm.load = false
            // 事后解封
            // let response = await login(loginParam)
            // if (response.status == 0) {
            //     localStorage.setItem('hrims-token', response.data.token)
            //     Notify.success('登录成功')
            //     router.push({ path: '/index' })
            //     loginForm.load = false
            // } else {
            //     Notify.warning(response.msg)
            //     loginForm.load = false
            // }
        }

        return { ...toRefs(loginForm), signin }
    }
}
</script>

<style>
.login {
    background-size: cover;
    background-image: url('@/assets/images/background.png');
    background-attachment: fixed;
    background-repeat: no-repeat;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    min-height: 100vh;
}
.login-logo {
    width: 120px;
    height: 120px;
    display: block;
    margin: 2rem auto;
}

.login-container {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    background: rgba(233, 233, 233, 0.3);
    border-radius: 0.4rem;
    border: 1px white solid;
    padding: 0.8rem;
}

.login-body {
    padding: 1rem 2rem;
    background: rgba(233, 233, 233, 0.6);
    width: 25rem;
    border-radius: 0.2rem;
}

.circle-input {
    border-radius: 20px;
}

.codeImg {
    height: 40px;
}

.login-submit {
    width: 100%;
}
</style>