import { createRouter, createWebHistory } from 'vue-router';
import ProductList from '../views/ProductList.vue';
import CategoryManager from '../views/CategoryManager.vue';
import SupplierManager from '../views/SupplierManager.vue';
import Login from '../views/Login.vue';

const routes = [
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/',
        redirect: '/products'
    },
    {
        path: '/products',
        name: 'Products',
        component: ProductList,
        meta: { requiresAuth: true }
    },
    {
        path: '/categories',
        name: 'Categories',
        component: CategoryManager,
        meta: { requiresAuth: true }
    },
    {
        path: '/suppliers',
        name: 'Suppliers',
        component: SupplierManager,
        meta: { requiresAuth: true }
    }
];

const router = createRouter({
    history: createWebHistory(),
    routes
});

// Navigation guard
router.beforeEach((to, from, next) => {
    const user = localStorage.getItem('user');
    if (to.meta.requiresAuth && !user) {
        next('/login');
    } else if (to.path === '/login' && user) {
        next('/products');
    } else {
        next();
    }
});

export default router;
