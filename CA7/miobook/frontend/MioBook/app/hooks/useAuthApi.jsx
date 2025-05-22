import axiosInstance from "../api/axiosInstance.jsx";

const useAuthApi = () => {
    const login = async (username, password) => {
        const response = await axiosInstance.post("/auth/login", {
            username,
            password,
        });

        if (response.data.success) {
            localStorage.setItem("jwt", response.data.data.token);
        }

        return response.data;
    };

    const logout = async () => {
        await axiosInstance.post("/auth/logout");
        localStorage.removeItem("jwt");
    };

    const getUser = async () => {
        const response = await axiosInstance.get("/auth/user");
        return response.data.success ? response.data.data : null;
    };

    const getUserRole = async (username) => {
        const response = await axiosInstance.get(`/users/${username}`);
        return response.data.data.role;
    };

    return { login, logout, getUser, getUserRole };
};

export default useAuthApi;
