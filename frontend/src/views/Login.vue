<template>
  <div class="login-container">
    <a-card class="login-card" title="超市管理系统">
      <a-tabs v-model:activeKey="activeTab">
        <a-tab-pane key="login" tab="登录">
          <a-form :model="loginForm" @finish="handleLogin" autocomplete="off">
            <a-form-item
              name="username"
              :rules="[{ required: true, message: '请输入用户名' }]"
            >
              <a-input v-model:value="loginForm.username" placeholder="用户名" autocomplete="off">
                <template #prefix><user-outlined /></template>
              </a-input>
            </a-form-item>
            <a-form-item
              name="password"
              :rules="[{ required: true, message: '请输入密码' }]"
            >
              <a-input-password v-model:value="loginForm.password" placeholder="密码" autocomplete="new-password">
                <template #prefix><lock-outlined /></template>
              </a-input-password>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" html-type="submit" block :loading="loading">
                登录
              </a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        
        <a-tab-pane key="register" tab="注册">
          <a-form :model="registerForm" @finish="handleRegister" autocomplete="off">
            <a-form-item
              name="username"
              :rules="[{ required: true, message: '请输入用户名' }]"
            >
              <a-input v-model:value="registerForm.username" placeholder="用户名" autocomplete="off">
                <template #prefix><user-outlined /></template>
              </a-input>
            </a-form-item>
            <a-form-item
              name="password"
              :rules="[{ required: true, message: '请输入密码' }, { min: 6, message: '密码至少6位' }]"
            >
              <a-input-password v-model:value="registerForm.password" placeholder="密码" autocomplete="new-password">
                <template #prefix><lock-outlined /></template>
              </a-input-password>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" html-type="submit" block :loading="loading">
                注册
              </a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { UserOutlined, LockOutlined } from '@ant-design/icons-vue';
import request from '../api/request';
import { message } from 'ant-design-vue';

const router = useRouter();
const activeTab = ref('login');
const loading = ref(false);

const loginForm = reactive({
  username: '',
  password: ''
});

const registerForm = reactive({
  username: '',
  password: ''
});

const handleLogin = async () => {
  loading.value = true;
  try {
    const data = await request.post('/auth/login', loginForm);
    localStorage.setItem('user', JSON.stringify(data));
    message.success('登录成功');
    router.push('/products');
  } catch (error) {
    // Error handled by interceptor
  } finally {
    loading.value = false;
  }
};

const handleRegister = async () => {
  loading.value = true;
  try {
    await request.post('/auth/register', registerForm);
    message.success('注册成功，请登录');
    activeTab.value = 'login';
    loginForm.username = registerForm.username;
    loginForm.password = registerForm.password;
  } catch (error) {
    // Error handled by interceptor
  } finally {
    loading.value = false;
  }
};
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  width: 400px;
  box-shadow: 0 4px 20px rgba(0, 0, 0, 0.1);
}
</style>
