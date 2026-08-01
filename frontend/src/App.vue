<template>
  <a-layout class="layout">
    <a-layout-sider v-model:collapsed="collapsed" :trigger="null" collapsible>
      <div class="logo">
        <shopping-outlined class="logo-icon" />
        <span v-if="!collapsed" class="logo-text">超市管理</span>
      </div>
      <a-menu v-model:selectedKeys="selectedKeys" theme="dark" mode="inline">
        <a-menu-item key="/products" @click="$router.push('/products')">
          <shop-outlined />
          <span>商品管理</span>
        </a-menu-item>
        <a-menu-item key="/categories" @click="$router.push('/categories')">
          <tags-outlined />
          <span>分类管理</span>
        </a-menu-item>
        <a-menu-item key="/suppliers" @click="$router.push('/suppliers')">
          <team-outlined />
          <span>供应商管理</span>
        </a-menu-item>
      </a-menu>
    </a-layout-sider>
    <a-layout>
      <a-layout-header style="background: #fff; padding: 0; display: flex; justify-content: space-between; align-items: center;">
        <div>
          <menu-unfold-outlined
            v-if="collapsed"
            class="trigger"
            @click="() => (collapsed = !collapsed)"
          />
          <menu-fold-outlined v-else class="trigger" @click="() => (collapsed = !collapsed)" />
        </div>
        <div style="padding-right: 24px;">
          <a-dropdown>
            <a class="ant-dropdown-link" @click.prevent>
              <user-outlined /> {{ username }}
            </a>
            <template #overlay>
              <a-menu>
                <a-menu-item @click="handleLogout">
                  <logout-outlined /> 退出登录
                </a-menu-item>
              </a-menu>
            </template>
          </a-dropdown>
        </div>
      </a-layout-header>
      <a-layout-content
        :style="{ margin: '24px 16px', padding: '24px', background: '#fff', minHeight: '280px' }"
      >
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { ref, watch, computed } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import {
  ShopOutlined,
  TagsOutlined,
  TeamOutlined,
  ShoppingOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
  UserOutlined,
  LogoutOutlined,
} from '@ant-design/icons-vue';

const selectedKeys = ref(['/products']);
const collapsed = ref(false);
const route = useRoute();
const router = useRouter();

const username = computed(() => {
  const user = localStorage.getItem('user');
  if (user) {
    return JSON.parse(user).username;
  }
  return '';
});

const handleLogout = () => {
  localStorage.removeItem('user');
  router.push('/login');
};

watch(
  () => route.path,
  (path) => {
    selectedKeys.value = [path === '/' ? '/products' : path];
  },
  { immediate: true }
);
</script>

<style>
.layout {
  min-height: 100vh;
}
.logo {
  height: 64px;
  margin: 16px;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(102, 126, 234, 0.3);
  transition: all 0.3s ease;
}

.logo:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 16px rgba(102, 126, 234, 0.4);
}

.logo-icon {
  font-size: 28px;
  color: white;
}

.logo-text {
  color: white;
  font-weight: 600;
  font-size: 18px;
  letter-spacing: 1px;
  white-space: nowrap;
}
.trigger {
  font-size: 18px;
  line-height: 64px;
  padding: 0 24px;
  cursor: pointer;
  transition: color 0.3s;
}
.trigger:hover {
  color: #1890ff;
}
</style>
