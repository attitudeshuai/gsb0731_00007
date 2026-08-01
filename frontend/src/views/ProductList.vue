<template>
  <div class="product-list">
    <div class="header-actions">
      <a-space>
        <a-input-search
          v-model:value="searchText"
          placeholder="搜索商品名称"
          style="width: 200px"
          @search="handleSearch"
        />
        <a-button type="primary" @click="showModal()">
          <template #icon><plus-outlined /></template>
          添加商品
        </a-button>
      </a-space>
    </div>

    <a-table :columns="columns" :data-source="products" :loading="loading" row-key="id">
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'category'">
          {{ record.category?.name || '未分类' }}
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a @click="showModal(record)">编辑</a>
            <a-popconfirm
              title="确定删除该商品吗？"
              @confirm="deleteProduct(record.id)"
            >
              <a class="text-danger">删除</a>
            </a-popconfirm>
          </a-space>
        </template>
      </template>
    </a-table>

    <a-modal
      v-model:visible="modalVisible"
      :title="editingProduct ? '编辑商品' : '添加商品'"
      @ok="handleOk"
      :confirm-loading="submitLoading"
    >
      <a-form ref="formRef" :model="formState" :label-col="{ span: 6 }" :wrapper-col="{ span: 16 }">
        <a-form-item 
          label="商品名称" 
          name="name"
          :rules="[{ required: true, message: '请输入商品名称' }]"
        >
          <a-input v-model:value="formState.name" />
        </a-form-item>
        <a-form-item label="条形码">
          <a-input v-model:value="formState.barcode" />
        </a-form-item>
        <a-form-item 
          label="价格" 
          name="price"
          :rules="[{ required: true, message: '请输入价格' }]"
        >
          <a-input-number v-model:value="formState.price" :min="0" :precision="2" style="width: 100%" />
        </a-form-item>
        <a-form-item 
          label="库存" 
          name="stock"
          :rules="[{ required: true, message: '请输入库存' }]"
        >
          <a-input-number v-model:value="formState.stock" :min="0" :precision="0" style="width: 100%" />
        </a-form-item>
        <a-form-item 
          label="分类"
          name="categoryId"
          :rules="[{ required: true, message: '请选择分类' }]"
        >
          <a-select v-model:value="formState.categoryId" placeholder="选择分类">
            <a-select-option v-for="cat in categories" :key="cat.id" :value="cat.id">
              {{ cat.name }}
            </a-select-option>
          </a-select>
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

const products = ref([]);
const categories = ref([]);
const loading = ref(false);
const searchText = ref('');
const modalVisible = ref(false);
const editingProduct = ref(null);
const submitLoading = ref(false);
const formRef = ref();

const formState = reactive({
  name: '',
  barcode: '',
  price: 0,
  stock: 0,
  categoryId: null
});

const columns = [
  { title: '商品名称', dataIndex: 'name', key: 'name' },
  { title: '条形码', dataIndex: 'barcode', key: 'barcode' },
  { title: '分类', key: 'category' },
  { title: '价格', dataIndex: 'price', key: 'price' },
  { title: '库存', dataIndex: 'stock', key: 'stock' },
  { title: '操作', key: 'action' },
];

const fetchProducts = async (name = '') => {
  loading.value = true;
  try {
    const data = await request.get('/products', { params: { name } });
    products.value = data;
  } finally {
    loading.value = false;
  }
};

const fetchCategories = async () => {
  const data = await request.get('/categories');
  categories.value = data;
};

const handleSearch = () => {
  fetchProducts(searchText.value);
};

const showModal = (product = null) => {
  editingProduct.value = product;
  if (product) {
    Object.assign(formState, {
      name: product.name,
      barcode: product.barcode,
      price: product.price,
      stock: product.stock,
      categoryId: product.category?.id || null
    });
  } else {
    Object.assign(formState, {
      name: '',
      barcode: '',
      price: 0,
      stock: 0,
      categoryId: null
    });
  }
  modalVisible.value = true;
  // Reset validation on next tick
  setTimeout(() => {
    formRef.value?.clearValidate();
  }, 0);
};

const handleOk = async () => {
  try {
    await formRef.value.validate();
    submitLoading.value = true;
    
    // Prepare data with category object for backend
    const productData = {
      ...formState,
      category: { id: formState.categoryId }
    };
    
    if (editingProduct.value) {
      await request.put(`/products/${editingProduct.value.id}`, productData);
      message.success('更新成功');
    } else {
      await request.post('/products', productData);
      message.success('添加成功');
    }
    modalVisible.value = false;
    fetchProducts();
  } catch (error) {
    // Validation failed
  } finally {
    submitLoading.value = false;
  }
};

const deleteProduct = async (id) => {
  await request.delete(`/products/${id}`);
  message.success('删除成功');
  fetchProducts();
};

onMounted(() => {
  fetchProducts();
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
