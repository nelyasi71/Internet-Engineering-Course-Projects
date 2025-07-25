import axios from "axios";

const axiosInstance = axios.create({
    baseURL: "http://localhost:9090/api",
});

// Request interceptor: Add token automatically
axiosInstance.interceptors.request.use((config) => {
    const token = localStorage.getItem("accessToken");
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
}, (error) => {
    return Promise.reject(error);
});

export default axiosInstance;
