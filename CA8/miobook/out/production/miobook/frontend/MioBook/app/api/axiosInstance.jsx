import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "/api",
});

// Request interceptor: Add token automatically
axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem("jwt");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default axiosInstance;
