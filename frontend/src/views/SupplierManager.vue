<template>
  <div class="supplier-manager">
    <div class="header-actions">
      <a-button type="primary" @click="showModal()">
        <template #icon><plus-outlined /></template>
        添加供应商
      </a-button>
    </div>

    <a-table :columns="columns" :data-source="suppliers" :loading="loading" row-key="id">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'action'">
          <a-space>
            <a @click="showModal(record)">编辑</a>
            <a-popconfirm
              title="确定删除该供应商吗？"
              @confirm="deleteSupplier(record.id)"
            >
              <a class="text-danger">删除</a>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal
      v-model:visible="modalVisible"
      :title="editingSupplier ? '编辑供应商' : '添加供应商'"
      @ok="handleOk"
      :confirm-loading="submitLoading"
    >
      <a-form ref="formRef" :model="formState" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item 
          label="供应商名称" 
          name="name"
          :rules="[{ required: true, message: '请输入供应商名称' }]"
        >
          <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item label="联系人">
          <a-input v-model:value="formState.contact" />
        </a-form-item>
        <a-form-item label="联系电话">
          <a-input v-model:value="formState.phone" />
        </a-form-item>
        <a-form-item label="地址">
          <a-textarea v-model:value="formState.address" />
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

const suppliers = ref([]);
const loading = ref(false);
const modalVisible = ref(false);
const editingSupplier = ref(null);
const submitLoading = ref(false);
const formRef = ref();

const formState = reactive({
  name: '',
  contact: '',
  phone: '',
  address: ''
});

const columns = [
  { title: '供应商名称', dataIndex: 'name', key: 'name' },
  { title: '联系人', dataIndex: 'contact', key: 'contact' },
  { title: '联系电话', dataIndex: 'phone', key: 'phone' },
  { title: '地址', dataIndex: 'address', key: 'address' },
  { title: '操作', key: 'action' },
];

const fetchSuppliers = async () => {
  loading.value = true;
  try {
    const data = await request.get('/suppliers');
    suppliers.value = data;
  } finally {
    loading.value = false;
  }
};

const showModal = (supplier = null) => {
  editingSupplier.value = supplier;
  if (supplier) {
    Object.assign(formState, supplier);
  } else {
    Object.assign(formState, {
      name: '',
      contact: '',
      phone: '',
      address: ''
    });
  }
  modalVisible.value = true;
  setTimeout(() => {
    formRef.value?.clearValidate();
  }, 0);
};

const handleOk = async () => {
  try {
    await formRef.value.validate();
    submitLoading.value = true;
    
    if (editingSupplier.value) {
      await request.put(`/suppliers/${editingSupplier.value.id}`, formState);
      message.success('更新成功');
    } else {
      await request.post('/suppliers', formState);
      message.success('添加成功');
    }
    modalVisible.value = false;
    fetchSuppliers();
  } catch (error) {
    // Validation failed or API error
  } finally {
    submitLoading.value = false;
  }
};

const deleteSupplier = async (id) => {
  await request.delete(`/suppliers/${id}`);
  message.success('删除成功');
  fetchSuppliers();
};

onMounted(() => {
  fetchSuppliers();
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
