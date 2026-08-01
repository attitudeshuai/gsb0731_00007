<template>
  <div class="category-manager">
    <div class="header-actions">
      <a-button type="primary" @click="showModal()">
        <template #icon><plus-outlined /></template>
        添加分类
      </a-button>
    </div>

    <a-table :columns="columns" :data-source="categories" :loading="loading" row-key="id">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-space>
            <a-popconfirm
              title="确定删除该分类吗？关联的商品可能会受到影响。"
              @confirm="deleteCategory(record.id)"
            >
              <a class="text-danger">删除</a>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal
      v-model:visible="modalVisible"
      title="添加分类"
      @ok="handleOk"
      :confirm-loading="submitLoading"
    >
      <a-form :model="formState" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item label="分类名称" required>
          <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item label="描述">
          <a-textarea v-model:value="formState.description" />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue';
import { PlusOutlined } from '@ant-design/icons-vue';
import request from '../api/request';
import { message } from 'ant-design-vue';

const categories = ref([]);
const loading = ref(false);
const modalVisible = ref(false);
const submitLoading = ref(false);

const formState = reactive({
  name: '',
  description: ''
});

const columns = [
  { title: '分类名称', dataIndex: 'name', key: 'name' },
  { title: '描述', dataIndex: 'description', key: 'description' },
  { title: '操作', key: 'action' },
];

const fetchCategories = async () => {
  loading.value = true;
  try {
    const data = await request.get('/categories');
    categories.value = data;
  } finally {
    loading.value = false;
  }
};

const showModal = () => {
  formState.name = '';
  formState.description = '';
  modalVisible.value = true;
};

const handleOk = async () => {
  if (!formState.name) return message.warning('请输入分类名称');
  
  submitLoading.value = true;
  try {
    await request.post('/categories', formState);
    message.success('添加成功');
    modalVisible.value = false;
    fetchCategories();
  } finally {
    submitLoading.value = false;
  }
};

const deleteCategory = async (id) => {
  try {
    await request.delete(`/categories/${id}`);
    message.success('删除成功');
    fetchCategories();
  } catch (error) {
    // Error handled by interceptor
  }
};

onMounted(() => {
  fetchCategories();
});
</script>

<style scoped>
.header-actions {
  margin-bottom: 16px;
  display: flex;
  justify-content: flex-end;
}
.text-danger {
  color: #ff4d4f;
}
</style>
