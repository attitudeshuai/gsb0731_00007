import axios from 'axios';
import { message } from 'ant-design-vue';

const instance = axios.create({
    baseURL: '/api',
    timeout: 10000
});

// Response interceptor for error handling
instance.interceptors.response.use(
    response => response.data,
    error => {
        const msg = error.response?.data?.message || '网络请求失败';
        message.error(msg);
        return Promise.reject(error);
    }
);

export default instance;
